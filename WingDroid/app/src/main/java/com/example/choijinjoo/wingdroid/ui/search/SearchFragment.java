package com.example.choijinjoo.wingdroid.ui.search;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Category;
import com.example.choijinjoo.wingdroid.model.Gif;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.Tag;
import com.example.choijinjoo.wingdroid.ui.detail.RepositoryDetailActivity;
import com.example.choijinjoo.wingdroid.ui.base.BaseFragment;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by choijinjoo on 2017. 8. 8..
 */

public class SearchFragment extends BaseFragment {
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
        SearchResultFragment searchResultFragment = SearchResultFragment.newInstance();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.frameLayout, searchSuggestionsFragment);
        transaction.commit();

        searchView.setOnQueryTextFocusChangeListener(((view, focus) -> {
            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            if(focus) {
                txtvHint.setVisibility(View.GONE);
                imgvEmoji.setVisibility(View.GONE);
                border.setVisibility(View.GONE);
                fragmentTransaction.add(R.id.frameLayout, searchResultFragment);
                fragmentTransaction.commit();
            }else{
                txtvHint.setVisibility(View.VISIBLE);
                imgvEmoji.setVisibility(View.VISIBLE);
                border.setVisibility(View.VISIBLE);
                fragmentTransaction.remove(searchResultFragment);
                fragmentTransaction.commit();
            }

        }));

    }


}
