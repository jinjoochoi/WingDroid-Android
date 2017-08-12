package com.example.choijinjoo.wingdroid.ui.search;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.WingDroidApp;
import com.example.choijinjoo.wingdroid.model.SearchHistory;
import com.example.choijinjoo.wingdroid.tools.StringUtils;
import com.example.choijinjoo.wingdroid.ui.base.BaseFragment;
import com.jakewharton.rxbinding2.widget.RxSearchView;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by choijinjoo on 2017. 8. 8..
 */

public class SearchFragment extends BaseFragment {
    private static final String TAG_SUGGESTIONS = "fragment_suggestions";
    private static final String TAG_HISTORY = "fragment_history";
    private static final String TAG_RESULT = "fragment_result";

    @BindView(R.id.searchView) SearchView searchView;
    @BindView(R.id.frameLayout) FrameLayout frameLayout;
    @BindView(R.id.txtvHint) TextView txtvHint;
    @BindView(R.id.imgvEmoji) ImageView imgvEmoji;
    @BindView(R.id.border) View border;


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

        addFragment(searchSuggestionsFragment, TAG_SUGGESTIONS);


        RxSearchView.queryTextChanges(searchView)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .filter(c -> !StringUtils.isEmpty(c))
                .map(c -> c.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::showSuggestions);


        searchView.setOnQueryTextFocusChangeListener(((view, focus) -> {
            if(focus) {
                hideSearchViewText();
                addFragment(searchHistoryFragment, TAG_HISTORY);
            }else{
                if(!getActivity().isFinishing()) {
                    txtvHint.setVisibility(View.VISIBLE);
                    imgvEmoji.setVisibility(View.VISIBLE);
                    border.setVisibility(View.VISIBLE);

                    // SuggestionsFrag or SearchResultFrag
                    if (getActiveFragment() instanceof SearchResultFragment) {
                        removeFragment(getActiveFragment());
                        removeFragment(searchHistoryFragment);
                    } else if (getActiveFragment() instanceof SearchHistoryFragment) {
                        removeFragment(getActiveFragment());
                    }
                }
            }

        }));

    }

    public void hideSearchViewText(){
        txtvHint.setVisibility(View.GONE);
        imgvEmoji.setVisibility(View.GONE);
        border.setVisibility(View.GONE);
        searchView.setIconified(false);
    }


    public void showSuggestions(String str){
        //FIXME DELETE
        WingDroidApp.getInstance().searchHistoryLocalSource().saveSearchHistory(new SearchHistory(str, Calendar.getInstance().getTime()));

        searchView.setQuery(str,false);
        Fragment fragment = getChildFragmentManager().findFragmentByTag(TAG_RESULT);
        if(fragment == null) {
            SearchResultFragment resultFragment = SearchResultFragment.newInstance();
            addFragment(resultFragment,TAG_RESULT);
        }
        SearchResultFragment resultFragment = (SearchResultFragment)getChildFragmentManager().findFragmentByTag(TAG_RESULT);
        resultFragment.showSuggestions(str);
    }


    public void addFragment(Fragment fragment, String tag){
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
        transaction.add(R.id.frameLayout, fragment, tag);
        transaction.commitNow();
    }

    private void removeFragment(Fragment fragment){
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_EXIT_MASK);
        transaction.remove(fragment);
        transaction.commitNow();
    }

    public BaseFragment getActiveFragment() {
        int index = getChildFragmentManager().getFragments().size()-1;
        return (BaseFragment)getChildFragmentManager().getFragments().get(index);
    }


}
