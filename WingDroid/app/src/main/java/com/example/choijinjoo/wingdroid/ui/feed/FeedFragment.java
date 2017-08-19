package com.example.choijinjoo.wingdroid.ui.feed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.dao.RTCategoryRepositoryRepository;
import com.example.choijinjoo.wingdroid.dao.RepositoryRepository;
import com.example.choijinjoo.wingdroid.model.Category;
import com.example.choijinjoo.wingdroid.model.SortCriteria;
import com.example.choijinjoo.wingdroid.ui.SelectSortCriteriaDialog;
import com.example.choijinjoo.wingdroid.ui.base.BaseFragment;
import com.example.choijinjoo.wingdroid.ui.detail.RepositoryDetailActivity;

import org.parceler.Parcels;

import butterknife.BindView;

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
    RepositoryAdapter adapter;
    StaggeredGridLayoutManager layoutManager;
    Category category;

    RepositoryRepository repoRepository;
    RTCategoryRepositoryRepository rtCategoryRepository;

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
        containerSort.setOnClickListener(this::showSelectSortCriteriaDialog);

        repositories.add(repoRepository = new RepositoryRepository(getContext()));
        repositories.add(rtCategoryRepository = new RTCategoryRepositoryRepository(getContext()));
        loadData(SortCriteria.RECENT);
    }

    protected void loadData(SortCriteria criteria) {
        category = Parcels.unwrap(getArguments().getParcelable(KEY_CATEGORY));
        if (category != null) {
            if (category.getName().equals("All")) {
                if (criteria == SortCriteria.RECENT)
                    adapter.setItems(repoRepository.getAllReposOrderByDate());
                else
                    adapter.setItems(repoRepository.getAllReposOrderByStar());
            } else {
                if (criteria == SortCriteria.RECENT)
                    adapter.setItems(rtCategoryRepository.getRepoForCategoryOrderByDate(category));
                else
                    adapter.setItems(rtCategoryRepository.getRepoForCategoryOrderByStar(category));
            }
        }
        order_by = criteria;
        txtvSort.setText(criteria == SortCriteria.RECENT ? getString(R.string.sort_recent) : getString(R.string.sort_star));
    }

    private void moveToDetailActivity(int position) {
        Intent intent = RepositoryDetailActivity.getStartIntent(getActivity(), adapter.getItem(position).getId());
        startActivity(intent);
    }

    private void showSelectSortCriteriaDialog(View view) {
        SelectSortCriteriaDialog.getInstance(getActivity(), order_by, this::loadData).show();
    }

}
