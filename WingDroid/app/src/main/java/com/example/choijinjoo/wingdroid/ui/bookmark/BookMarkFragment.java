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
import com.example.choijinjoo.wingdroid.model.SortCriteria;
import com.example.choijinjoo.wingdroid.source.local.SharedPreferenceHelper;
import com.example.choijinjoo.wingdroid.ui.CategoryFilterDialog;
import com.example.choijinjoo.wingdroid.ui.SelectSortCriteriaDialog;
import com.example.choijinjoo.wingdroid.ui.base.BaseFragment;
import com.example.choijinjoo.wingdroid.ui.detail.RepositoryDetailActivity;
import com.j256.ormlite.dao.Dao;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.BindView;

import static com.example.choijinjoo.wingdroid.model.Repository.ID_FIELD_BOOKMARKED_AT;
import static com.example.choijinjoo.wingdroid.model.Repository.ID_FIELD_STAR;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public class BookMarkFragment extends BaseFragment implements Dao.DaoObserver {
    @BindView(R.id.recvRepositories)
    RecyclerView recvRepositories;
    @BindView(R.id.containerSort)
    LinearLayout containerSort;
    @BindView(R.id.imgvFilter)
    ImageView imgvFilter;
    BookMarkAdapter adapter;
    RTCategoryRepositoryRepository rtCategoryRepositoryRepository;
    RepositoryRepository repositoryRepository;
    CategoryRepository categoryRepository;
    List<Category> categories;

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
        repositoryRepository = new RepositoryRepository(getContext());
        rtCategoryRepositoryRepository.registerDaoObserver(this);
        repositoryRepository.registerDaoObserver(this);
        order_by = SortCriteria.RECENT;
        categories = categoryRepository.getCategoriesOrderByName();

        loadSelectedCategories();

        loadData();

    }

    private void loadSelectedCategories() {
        Set<String> selectedCategory = SharedPreferenceHelper.getInstance().getStringSetValue(
                getContext(), SharedPreferenceHelper.Config.BOOKMARK_CATEGORY_FILTER,
                categoryRepository.getCategoryByName("All").getId());

        Iterator<String> iterator = selectedCategory.iterator();
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
            if (category.getName().equals("All") && category.getSelected())
                return true;
            else
                return false;
        }
        return false;
    }

    protected void loadData() {
        if (order_by == SortCriteria.RECENT) {
            if (isAll())
                adapter.setItems(repositoryRepository.getAllReposOrderByDate());
            else
                adapter.setItems(rtCategoryRepositoryRepository.getBookmarkForCategories(ID_FIELD_BOOKMARKED_AT, categories));
        }else {
            if (isAll())
                adapter.setItems(repositoryRepository.getAllReposOrderByStar());
            else
                adapter.setItems(rtCategoryRepositoryRepository.getBookmarkForCategories(ID_FIELD_STAR, categories));
        }
    }

    private void moveToDetailActivity(int position) {
        Intent intent = RepositoryDetailActivity.getStartIntent(getActivity(), adapter.getItem(position).getId());
        startActivity(intent);
    }

    private void showCategoryFilterDialog() {
        CategoryFilterDialog.getInstance(getActivity(), categories, categories -> {
            SharedPreferenceHelper.getInstance().putStringSetValue(getContext(), SharedPreferenceHelper.Config.BOOKMARK_CATEGORY_FILTER, categories);
            loadSelectedCategories();
            loadData();
        }).show();
    }

    private void showSelectSortCriteriaDialog() {
        SelectSortCriteriaDialog.getInstance(getActivity(), order_by, criteria -> {
            order_by = criteria;
            loadData();
        }).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        repositoryRepository.unregisterDaoObserver(this);
        rtCategoryRepositoryRepository.unregisterDaoObserver(this);
    }

    @Override
    public void onChange() {
        loadData();
    }
}
