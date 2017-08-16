package com.example.choijinjoo.wingdroid.ui.search;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RelativeLayout;

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
//        SearchSuggestionsFragment searchSuggestionsFragment = SearchSuggestionsFragment.newInstance();
//        SearchHistoryFragment searchHistoryFragment = SearchHistoryFragment.newInstance();
//        SearchResultFragment searchResultFragment = SearchResultFragment.newInstance();
//
//        fragments.put(KEY_SUGGESTIONS, searchSuggestionsFragment);
//        fragments.put(KEY_HISTORY, searchHistoryFragment);
//        fragments.put(KEY_RESULT, searchResultFragment);

//        replaceFragment(fragments.get(KEY_SUGGESTIONS));

//        RxSearchView.queryTextChanges(searchView)
//                .debounce(1000, TimeUnit.MILLISECONDS)
//                .filter(c -> !StringUtils.isEmpty(c))
//                .map(c -> c.toString())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(this::showResults);
//
//
//        searchView.setOnQueryTextFocusChangeListener(((view, focus) -> {
//            if (focus) {
//                hideSearchViewText();
//                addFragment(searchHistoryFragment);
//            } else {
//                txtvHint.setVisibility(View.VISIBLE);
//                imgvEmoji.setVisibility(View.VISIBLE);
//                border.setVisibility(View.VISIBLE);
//                getChildFragmentManager().popBackStack();
//            }
//
//        }));


        categoryAdapter = new CategorySearchAdapter(getActivity(), position -> {
//            categoryAdapter.getItem(position).selected();
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
        Intent intent = RepositoryDetailActivity.getStartIntent(getActivity(),repository);
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

    //    @Override
//    public void onPause() {
//        super.onPause();
//        searchView.setOnQueryTextFocusChangeListener(null);
//    }
//
//    public void showResults(String str) {
//        searchView.setQuery(str, false);
//        FragmentInteractor fragment = (FragmentInteractor) addFragment(fragments.get(KEY_RESULT));
//        fragment.showResults(str);
//    }


//    public Fragment addFragment(Fragment fragment) {
//        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//        transaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
//        transaction.replace(R.id.frameLayout, fragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
//        return fragment;
//    }
//    public Fragment replaceFragment(Fragment fragment) {
//        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
//        transaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
//        transaction.replace(R.id.frameLayout, fragment);
//        transaction.commit();
//        return fragment;
//    }
}
