package com.example.choijinjoo.wingdroid.ui.search;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Category;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.source.remote.firebase.CategoryDataSource;
import com.example.choijinjoo.wingdroid.tools.FirebaseArray;
import com.example.choijinjoo.wingdroid.ui.base.BaseFragment;
import com.example.choijinjoo.wingdroid.ui.detail.RepositoryDetailActivity;
import com.google.firebase.database.DatabaseError;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import butterknife.BindView;

/**
 * Created by choijinjoo on 2017. 8. 8..
 */

public class SearchFragment extends BaseFragment implements FirebaseArray.OnChangedListener{
    @BindView(R.id.recvCategories) RecyclerView recvCategories;
    @BindView(R.id.recvSuggestions) RecyclerView recvSuggestions;
    @BindView(R.id.btnSearch) RelativeLayout btnSearch;
    @BindView(R.id.searchView) SearchView searchView;
    CategorySearchAdapter categoryAdapter;
    SuggestionsAdapter suggestionsAdapter;


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
            categoryAdapter.notifyItemChanged(position);
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
        searchView.setIconified(false);

    }

    FirebaseArray categoryFBArray;

    @Override
    protected void loadData() {
        super.loadData();
        categoryFBArray = new FirebaseArray(CategoryDataSource.getInstance().categories());
        categoryFBArray.setOnChangedListener(this);
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

    /*
     *   Category DataSource change listener
     */

    @Override
    public void onChildChanged(EventType type, int index, int oldIndex) {
        switch (type){
            case ADDED:
                String id = categoryFBArray.getItem(index).getKey();
                Category category = categoryFBArray.getItem(index).getValue(Category.class);
                category.setId(id);
                categoryAdapter.add(category);
                break;
            case CHANGED:
                categoryAdapter.change(index,categoryFBArray.getItem(index).getValue(Category.class));
                break;
            case REMOVED:
                categoryAdapter.remove(index);
                break;
            case MOVED:
                categoryAdapter.notifyItemMoved(oldIndex, index);
                break;
            default:
                throw new IllegalStateException("Incomplete case statement");
        }
    }

    @Override
    public void onDataChanged() {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

}
