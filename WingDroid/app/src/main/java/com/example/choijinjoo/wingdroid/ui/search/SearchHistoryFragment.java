package com.example.choijinjoo.wingdroid.ui.search;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.ui.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by choijinjoo on 2017. 8. 8..
 */

public class SearchHistoryFragment extends BaseFragment {
    @BindView(R.id.recvHistories) RecyclerView recvHistories;
    SearchHistoryAdapter adapter;

    public static SearchHistoryFragment newInstance() {
        return new SearchHistoryFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_search_history;
    }

    @Override
    protected void initLayout() {
        recvHistories.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recvHistories.setHasFixedSize(true);
//        WingDroidApp.getInstance()
//                .searchHistoryLocalSource()
//                .getSearchHistories()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(this::setData);
    }

//    private void setData(RealmResults<SearchHistory> data){
//        adapter = new SearchHistoryAdapter(data, true,
//                new SearchHistoryAdapter.SearchHistoryListener() {
//                    @Override
//                    public void selected(int position) {
//                        ((SearchFragment)getParentFragment()).showResults(adapter.getItem(position).getSearch());
//                    }
//
//                    @Override
//                    public void delete(int position) {
////                        WingDroidApp.getInstance().searchHistoryLocalSource()
////                                .deleteSearchHistory(adapter.getItem(position));
//                    }
//                }, getActivity());
//        recvHistories.setAdapter(adapter);
//    }


}
