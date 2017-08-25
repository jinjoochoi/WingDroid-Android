package com.example.choijinjoo.wingdroid.ui.feed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.dao.RTCategoryRepositoryRepository;
import com.example.choijinjoo.wingdroid.dao.RepositoryRepository;
import com.example.choijinjoo.wingdroid.model.Category;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.SortCriteria;
import com.example.choijinjoo.wingdroid.ui.SelectSortCriteriaDialog;
import com.example.choijinjoo.wingdroid.ui.base.BaseFragment;
import com.example.choijinjoo.wingdroid.ui.detail.RepositoryDetailActivity;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public class FeedFragment extends BaseFragment {
    @BindView(R.id.recvRepositories)
    RecyclerView recvRepositories;
    @BindView(R.id.containerSort)
    LinearLayout containerSort;
    @BindView(R.id.txtvSort)
    TextView txtvSort;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    RepositoryAdapter adapter;
    StaggeredGridLayoutManager layoutManager;
    Category category;

    RepositoryRepository repoRepository;
    RTCategoryRepositoryRepository rtCategoryRepository;
    List<Repository> items;


    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 3;
    public static final int ITEM_IN_PAGE = 6;
    private int currentPage = PAGE_START;

    private static final String KEY_CATEGORY = "category";

    private SortCriteria order_by;

    public static FeedFragment newInstance(Category category) {
        FeedFragment fragment = new FeedFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_CATEGORY, Parcels.wrap(category));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_feed;
    }

    @Override
    protected void initLayout() {
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recvRepositories.setLayoutManager(layoutManager);
        adapter = new RepositoryAdapter(getActivity(), this::moveToDetailActivity);
        recvRepositories.setAdapter(adapter);
        nestedScrollView.setOnScrollChangeListener(listener);
        containerSort.setOnClickListener(this::showSelectSortCriteriaDialog);
        recvRepositories.setNestedScrollingEnabled(false);

        repositories.add(repoRepository = new RepositoryRepository(getContext()));
        repositories.add(rtCategoryRepository = new RTCategoryRepositoryRepository(getContext()));

        loadData(SortCriteria.RECENT);
    }

    protected void loadData(SortCriteria criteria) {
        category = Parcels.unwrap(getArguments().getParcelable(KEY_CATEGORY));
        if (category != null) {
            if (category.getName().equals("ALL")) {
                if (criteria == SortCriteria.RECENT)
                    loadInitialRepoData(repoRepository.getInitialReposOrderByDate());
                else
                    loadInitialRepoData(repoRepository.getInitialReposOrderByStar());

            } else {
                if (criteria == SortCriteria.RECENT)
                    loadInitialRepoData(rtCategoryRepository.getInitialRepoForCategoryOrderByDate(category));
                else
                    loadInitialRepoData(rtCategoryRepository.getInitialRepoForCategoryOrderByStar(category));
            }
        }
        order_by = criteria;
        txtvSort.setText(criteria == SortCriteria.RECENT ? getString(R.string.sort_recent) : getString(R.string.sort_star));
    }

    private void loadInitialRepoData(Observable<List<Repository>> repoObservable) {
        disposables.add(repoObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setItems));
    }

    private void loadNextRepoData(Observable<List<Repository>> repoObservable) {
        disposables.add(repoObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addItems));
    }

    private void setItems(List<Repository> items) {
        isLoading = true;
        adapter.setItems(items);
        isLastPage = false;
        isLoading = false;
    }

    private void addItems(List<Repository> items) {
        if(items.size() < ITEM_IN_PAGE)
            isLastPage = true;
        adapter.add(new ArrayList<>(items));

        isLoading = false;
    }

    private void moveToDetailActivity(int position) {
        Intent intent = RepositoryDetailActivity.getStartIntent(getActivity(), adapter.getItem(position).getId());
        startActivity(intent);
    }

    private void showSelectSortCriteriaDialog(View view) {
        SelectSortCriteriaDialog.getInstance(getActivity(), order_by, this::loadData).show();
    }

    NestedScrollView.OnScrollChangeListener listener = new NestedScrollView.OnScrollChangeListener() {
        @Override
        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            if (oldScrollY - scrollY < 0) {
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int[] firstVisibleItemPosition = layoutManager.findFirstVisibleItemPositions(null);

                if (!isLoading() && !isLastPage()) {
                    if ((visibleItemCount + firstVisibleItemPosition[0]) >= totalItemCount
                            && firstVisibleItemPosition[0] >= 0 && totalItemCount >= ITEM_IN_PAGE) {
                        loadMoreItems();
                    }
                }
            }
        }
    };


    public int getTotalPageCount() {
        return TOTAL_PAGES;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public boolean isLoading() {
        return isLoading;
    }

    private void loadMoreItems() {
        isLoading = true;
        currentPage += 1; //Increment page index to load the next one
        loadNextPage();

    }


    private void loadNextPage() {
        Repository lastItem = adapter.getLastItem();
        isLoading = true;

        if (category != null) {
            if (category.getName().equals("ALL")) {
                if (order_by == SortCriteria.RECENT)
                    loadNextRepoData(repoRepository.getNextReposOrderByDate(lastItem));
                else
                    loadNextRepoData(repoRepository.getNextReposOrderByStar(lastItem));

            } else {
                if (order_by == SortCriteria.RECENT)
                    loadNextRepoData(rtCategoryRepository.getNextRepoForCategoryOrderByDate(category,lastItem));
                else
                    loadNextRepoData(rtCategoryRepository.getNextRepoForCategoryOrderByStar(category,lastItem));
            }
        }

    }
}
