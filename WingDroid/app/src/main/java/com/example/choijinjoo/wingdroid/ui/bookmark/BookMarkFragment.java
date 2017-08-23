package com.example.choijinjoo.wingdroid.ui.bookmark;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.dao.CategoryRepository;
import com.example.choijinjoo.wingdroid.dao.RTCategoryRepositoryRepository;
import com.example.choijinjoo.wingdroid.dao.RepositoryRepository;
import com.example.choijinjoo.wingdroid.model.Category;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.SortCriteria;
import com.example.choijinjoo.wingdroid.model.eventbus.RepoBookMarkEvent;
import com.example.choijinjoo.wingdroid.source.local.SharedPreferenceHelper;
import com.example.choijinjoo.wingdroid.ui.CategoryFilterDialog;
import com.example.choijinjoo.wingdroid.ui.SelectSortCriteriaDialog;
import com.example.choijinjoo.wingdroid.ui.base.BaseFragment;
import com.example.choijinjoo.wingdroid.ui.detail.RepositoryDetailActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.choijinjoo.wingdroid.model.Repository.BOOKMARKEDAT_FIELD;
import static com.example.choijinjoo.wingdroid.model.Repository.CREATEDAT_FIELD;
import static com.example.choijinjoo.wingdroid.model.Repository.STAR_FIELD;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public class BookMarkFragment extends BaseFragment {
    @BindView(R.id.recvRepositories)
    RecyclerView recvRepositories;
    @BindView(R.id.containerSort)
    LinearLayout containerSort;
    @BindView(R.id.imgvFilter)
    ImageView imgvFilter;
    BookMarkAdapter adapter;
    RTCategoryRepositoryRepository rtCategoryRepositoryRepository;
    CategoryRepository categoryRepository;
    List<Category> categories;
    Set<String> criteria;

    private SortCriteria order_by;

    public static BookMarkFragment newInstance() {
        return new BookMarkFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_bookmark;
    }

    @Override
    protected void initLayout() {
        recvRepositories.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new BookMarkAdapter(getActivity(), this::moveToDetailActivity);
        recvRepositories.setAdapter(adapter);
        recvRepositories.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        containerSort.setOnClickListener(it -> showSelectSortCriteriaDialog());
        imgvFilter.setOnClickListener(it -> showCategoryFilterDialog());
        rtCategoryRepositoryRepository = new RTCategoryRepositoryRepository(getContext());
        categoryRepository = new CategoryRepository(getContext());
        EventBus.getDefault().register(this);
        order_by = SortCriteria.RECENT;

        loadCategoriesOrderByName();

    }

    private void loadCategoriesOrderByName() {
        disposables.add(categoryRepository.getCategoriesOrderByName()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::loadRepo));
    }


    private void initSelectedCategory() {
        Category defCategory = categoryRepository.getAllCategory();
        criteria = SharedPreferenceHelper.getInstance().getStringSetValue(
                getContext(), SharedPreferenceHelper.Config.BOOKMARK_CATEGORY_FILTER, defCategory.getId());
        Iterator<String> iterator = criteria.iterator();
        while (iterator.hasNext()) {
            String selected = iterator.next();
            for (Category category : categories) {
                if (category.getId().equals(selected))
                    category.setSelected(true);
            }
        }

    }

    private boolean isAll() {
        for (Category category : categories) {
            if (category.getName().equals("ALL") && category.getSelected())
                return true;
            else
                return false;
        }
        return false;
    }

    protected void loadRepo(List<Category> categories) {
        this.categories = categories;
        if (categories != null && categories.size() > 0)
            initSelectedCategory();

        if (order_by == SortCriteria.RECENT) {
            if (isAll())
                loadBookmarkItems(RepositoryRepository.getInstance(getContext()).getBookmark(CREATEDAT_FIELD, false));
            else
                loadBookmarkItems(rtCategoryRepositoryRepository.getBookmarkForCategories(BOOKMARKEDAT_FIELD, categories));
        } else {
            if (isAll())
                loadBookmarkItems(RepositoryRepository.getInstance(getContext()).getBookmark(STAR_FIELD, false));
            else
                loadBookmarkItems(rtCategoryRepositoryRepository.getBookmarkForCategories(STAR_FIELD, categories));
        }
    }

    private void loadBookmarkItems(Observable<List<Repository>> bookmarkObservable) {
        disposables.add(bookmarkObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setItems));
    }

    private void moveToDetailActivity(int position) {
        Intent intent = RepositoryDetailActivity.getStartIntent(getActivity(), adapter.getItem(position).getId());
        startActivity(intent);
    }


    private void showCategoryFilterDialog() {
        CategoryFilterDialog.getInstance(getActivity(), criteria, categories, results -> {
            SharedPreferenceHelper.getInstance().putStringSetValue(getContext(), SharedPreferenceHelper.Config.BOOKMARK_CATEGORY_FILTER, results);
            loadRepo(categories);
        }).show();
    }

    private void showSelectSortCriteriaDialog() {
        SelectSortCriteriaDialog.getInstance(getActivity(), order_by, criteria -> {
            order_by = criteria;
            loadRepo(categories);
        }).show();
    }

    private void setItems(List<Repository> items) {
        adapter.setItems(items);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RepoBookMarkEvent event) {
        loadRepo(categories);
    }

}
