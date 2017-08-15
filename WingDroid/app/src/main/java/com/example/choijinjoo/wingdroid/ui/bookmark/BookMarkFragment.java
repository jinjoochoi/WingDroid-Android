package com.example.choijinjoo.wingdroid.ui.bookmark;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.ui.CategoryFilterDialog;
import com.example.choijinjoo.wingdroid.ui.SelectSortCriteriaDialog;
import com.example.choijinjoo.wingdroid.ui.base.BaseFragment;
import com.example.choijinjoo.wingdroid.ui.detail.RepositoryDetailActivity;

import butterknife.BindView;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public class BookMarkFragment extends BaseFragment {
    @BindView(R.id.recvRepositories) RecyclerView recvRepositories;
    @BindView(R.id.containerSort) LinearLayout containerSort;
    @BindView(R.id.imgvFilter) ImageView imgvFilter;
    BookMarkAdapter adapter;

    public static BookMarkFragment newInstance() {
        return new BookMarkFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_bookmark;
    }

    @Override
    protected void initLayout() {
        recvRepositories.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        adapter = new BookMarkAdapter(getActivity(),this::moveToDetailActivity);
        recvRepositories.setAdapter(adapter);
        recvRepositories.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recvRepositories.setHasFixedSize(true);
        recvRepositories.setDrawingCacheEnabled(true);
        recvRepositories.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        // TODO: 2017. 8. 8. data load는 onCrateView() 시점으로 이동
//        makeMockRepository()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(repositories -> adapter.setItems(repositories));

        containerSort.setOnClickListener(it -> showSelectSortCriteriaDialog());
        imgvFilter.setOnClickListener(it -> showCategoryFilterDialog());
    }

    @Override
    protected void loadData() {

    }

    private void moveToDetailActivity(int position){
        Intent intent = RepositoryDetailActivity.getStartIntent(getActivity(),adapter.getItem(position));
        startActivity(intent);
    }

    private void showCategoryFilterDialog() {
        CategoryFilterDialog.getInstance(getActivity(),
                category -> {

                }).show();
    }

    private void showSelectSortCriteriaDialog() {
        SelectSortCriteriaDialog.getInstance(getActivity(),
                criteria -> {

                }).show();
    }


}
