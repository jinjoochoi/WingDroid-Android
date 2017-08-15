package com.example.choijinjoo.wingdroid.ui.feed;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.SortCriteria;
import com.example.choijinjoo.wingdroid.source.remote.firebase.RepositoryDataSource;
import com.example.choijinjoo.wingdroid.ui.SelectSortCriteriaDialog;
import com.example.choijinjoo.wingdroid.ui.base.BaseFragment;
import com.example.choijinjoo.wingdroid.ui.detail.RepositoryDetailActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import butterknife.BindView;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public class FeedFragment extends BaseFragment {
    @BindView(R.id.recvRepositories)
    RecyclerView recvRepositories;
    @BindView(R.id.containerSort)
    LinearLayout containerSort;
    RepositoryAdapter adapter;
    DatabaseReference ref;
    StaggeredGridLayoutManager layoutManager;

    public static FeedFragment newInstance() {
        return new FeedFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_feed;
    }

    @Override
    protected void initLayout() {
        Query query = ref.orderByChild("createdAt");
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setReverseLayout(false);
        recvRepositories.setLayoutManager(layoutManager);
        adapter = new RepositoryAdapter(Repository.class, R.layout.item_repository,
                RepositoryViewHolder.class, query,
                getActivity(), this::moveToDetailActivity);
        recvRepositories.setAdapter(adapter);

        containerSort.setOnClickListener(this::showSelectSortCriteriaDialog);
    }

    @Override
    protected void loadData() {
        ref = RepositoryDataSource.getInstance().repositories();
    }

    private void moveToDetailActivity(int position) {
        Intent intent = RepositoryDetailActivity.getStartIntent(getActivity(), adapter.getItem(position));
        startActivity(intent);
    }

    private void showSelectSortCriteriaDialog(View view) {
        SelectSortCriteriaDialog.getInstance(
                getActivity(), it -> {
                    if (it == SortCriteria.RECENT) {
                        adapter = new RepositoryAdapter(Repository.class, R.layout.item_repository,
                                RepositoryViewHolder.class, ref.orderByChild("updatedAt"),
                                getActivity(), this::moveToDetailActivity);
                        recvRepositories.setAdapter(adapter);
                    }
                    else {
                        adapter = new RepositoryAdapter(Repository.class, R.layout.item_repository,
                                RepositoryViewHolder.class, ref.orderByChild("star"),
                                getActivity(), this::moveToDetailActivity);
                        layoutManager.setReverseLayout(true);
                        recvRepositories.setAdapter(adapter);
                    }

                })
                .show();
    }

}
