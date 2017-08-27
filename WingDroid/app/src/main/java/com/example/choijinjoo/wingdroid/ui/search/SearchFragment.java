package com.example.choijinjoo.wingdroid.ui.search;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.dao.CategoryRepository;
import com.example.choijinjoo.wingdroid.dao.RepositoryRepository;
import com.example.choijinjoo.wingdroid.model.Category;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.ui.base.BaseFragment;
import com.example.choijinjoo.wingdroid.ui.detail.RepositoryDetailActivity;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by choijinjoo on 2017. 8. 8..
 */

public class SearchFragment extends BaseFragment{
    @BindView(R.id.recvCategories) RecyclerView recvCategories;
    @BindView(R.id.recvSuggestions) RecyclerView recvSuggestions;
    @BindView(R.id.btnSearch) RelativeLayout btnSearch;
    CategorySearchAdapter categoryAdapter;
    SuggestionsAdapter suggestionsAdapter;
    CategoryRepository categoryRepository;
    RepositoryRepository repositoryRepository;


    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_search;
    }

    @Override
    protected void initLayout() {
        categoryAdapter = new CategorySearchAdapter(getActivity(), position -> {
//            categoryAdapter.notifyItemChanged(position);
            moveToSearchResultActivity(categoryAdapter.getItem(position));
        });
        suggestionsAdapter = new SuggestionsAdapter(getActivity(),
                position -> moveToDetailActivity(suggestionsAdapter.getItem(position)));
        recvCategories.setAdapter(categoryAdapter);
        recvCategories.setLayoutManager(new FlowLayoutManager());

        recvSuggestions.setLayoutManager(new GridLayoutManager(getActivity(),3, LinearLayoutManager.VERTICAL,false));
        recvSuggestions.setAdapter(suggestionsAdapter);

        btnSearch.setOnClickListener(it -> {
            Intent intent = SearchResultActivity.getStartIntent(getActivity());
            startActivity(intent);
            getActivity().overridePendingTransition(0,0);
        });

    }


    @Override
    protected void loadData() {
        super.loadData();
        repositories.add(categoryRepository = new CategoryRepository(getContext()));
        repositories.add(repositoryRepository = new RepositoryRepository(getContext()));

        disposables.add(categoryRepository.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setCategoryItems));

        disposables.add(repositoryRepository.getSuggestedRepo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setSuggestedRepoItems));
    }

    private void setCategoryItems(List<Category> items){
        categoryAdapter.setItems(items);
    }

    private void setSuggestedRepoItems(List<Repository> repositories){
        if(repositories.size() > 2){
            suggestionsAdapter.setItems(repositories.subList(0,3));
        }else {
            suggestionsAdapter.setItems(repositories);
        }
    }

    private void moveToSearchResultActivity(Category category) {
        Intent intent = SearchResultActivity.getStartIntent(getActivity(),category);
        startActivity(intent);
        getActivity().overridePendingTransition(0,0);
    }

    private void moveToDetailActivity(Repository repository) {
        Intent intent = RepositoryDetailActivity.getStartIntent(getActivity(),repository.getId());
        startActivity(intent);
    }

}
