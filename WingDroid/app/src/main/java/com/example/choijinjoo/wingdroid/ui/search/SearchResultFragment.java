package com.example.choijinjoo.wingdroid.ui.search;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.ui.base.BaseFragment;
import com.example.choijinjoo.wingdroid.ui.detail.RepositoryDetailActivity;

import java.util.List;

import butterknife.BindView;

/**
 * Created by choijinjoo on 2017. 8. 8..
 */

public class SearchResultFragment extends BaseFragment implements SearchFragment.FragmentInteractor{
    @BindView(R.id.recvResults) RecyclerView recvResults;
    SearchResultAdapter adapter;

    SearchFragment.FragmentInteractor interactor;

    public static SearchResultFragment newInstance() {
        return new SearchResultFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_search_result;
    }

    @Override
    protected void initLayout() {
        recvResults.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recvResults.setHasFixedSize(true);
    }


    private void setData(List<Repository> repositories){
//        if(recvResults != null) {
//            adapter = new SearchResultAdapter(getActivity(), makeMockRepository(),
//                    this::moveToDetailActivity);
//            recvResults.setAdapter(adapter);
//        }
    }


    @Override
    public void showResults(String str) {
//        WingDroidApp.getInstance()
//                .repositoryLocalSource()
//                .getSuggestionRepository(str)
//                .subscribe(this::setData);
    }



    private void moveToDetailActivity(int position){
        Intent intent = RepositoryDetailActivity.getStartIntent(getActivity(),adapter.getItem(position));
        getActivity().startActivity(intent);
    }

}
