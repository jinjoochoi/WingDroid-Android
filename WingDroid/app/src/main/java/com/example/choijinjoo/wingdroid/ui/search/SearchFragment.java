package com.example.choijinjoo.wingdroid.ui.search;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.tools.StringUtils;
import com.example.choijinjoo.wingdroid.ui.base.BaseFragment;
import com.jakewharton.rxbinding2.widget.RxSearchView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by choijinjoo on 2017. 8. 8..
 */

public class SearchFragment extends BaseFragment {
    private static final int KEY_SUGGESTIONS = 101;
    private static final int KEY_HISTORY = 102;
    private static final int KEY_RESULT = 103;

    @BindView(R.id.searchView) SearchView searchView;
    @BindView(R.id.frameLayout) FrameLayout frameLayout;
    @BindView(R.id.txtvHint) TextView txtvHint;
    @BindView(R.id.imgvEmoji) ImageView imgvEmoji;
    @BindView(R.id.border) View border;

    SparseArray<BaseFragment> fragments = new SparseArray<>();

    interface FragmentInteractor {
        void showResults(String str);
    }


    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_search;
    }

    @Override
    protected void initLayout() {
        SearchSuggestionsFragment searchSuggestionsFragment = SearchSuggestionsFragment.newInstance();
        SearchHistoryFragment searchHistoryFragment = SearchHistoryFragment.newInstance();
        SearchResultFragment searchResultFragment = SearchResultFragment.newInstance();

        fragments.put(KEY_SUGGESTIONS, searchSuggestionsFragment);
        fragments.put(KEY_HISTORY, searchHistoryFragment);
        fragments.put(KEY_RESULT, searchResultFragment);

        replaceFragment(fragments.get(KEY_SUGGESTIONS));

        RxSearchView.queryTextChanges(searchView)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .filter(c -> !StringUtils.isEmpty(c))
                .map(c -> c.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showResults);


        searchView.setOnQueryTextFocusChangeListener(((view, focus) -> {
            if (focus) {
                hideSearchViewText();
                addFragment(searchHistoryFragment);
            } else {
                txtvHint.setVisibility(View.VISIBLE);
                imgvEmoji.setVisibility(View.VISIBLE);
                border.setVisibility(View.VISIBLE);
                getChildFragmentManager().popBackStack();
            }

        }));

    }

    public void hideSearchViewText() {
        txtvHint.setVisibility(View.GONE);
        imgvEmoji.setVisibility(View.GONE);
        border.setVisibility(View.GONE);
        searchView.setIconified(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        searchView.setOnQueryTextFocusChangeListener(null);
    }

    public void showResults(String str) {
        searchView.setQuery(str, false);
        FragmentInteractor fragment = (FragmentInteractor) addFragment(fragments.get(KEY_RESULT));
        fragment.showResults(str);
    }


    public Fragment addFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        return fragment;
    }
    public Fragment replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();
        return fragment;
    }
}
