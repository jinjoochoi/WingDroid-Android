package com.example.choijinjoo.wingdroid.ui.news;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.dao.CommitRepository;
import com.example.choijinjoo.wingdroid.dao.ReleaseRepository;
import com.example.choijinjoo.wingdroid.dao.RepositoryRepository;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.event.Commit;
import com.example.choijinjoo.wingdroid.model.event.Event;
import com.example.choijinjoo.wingdroid.model.event.Release;
import com.example.choijinjoo.wingdroid.ui.EventFilterDialog;
import com.example.choijinjoo.wingdroid.ui.base.BaseFragment;
import com.example.choijinjoo.wingdroid.ui.detail.RepositoryDetailActivity;
import com.example.choijinjoo.wingdroid.ui.detail.WebViewAcitivty;
import com.j256.ormlite.dao.Dao;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public class NewsFragment extends BaseFragment implements Dao.DaoObserver {
    @BindView(R.id.recvNew)
    RecyclerView recvNew;
    @BindView(R.id.recvEvents)
    RecyclerView recvEvents;
    @BindView(R.id.imgvFilter)
    ImageView imgvFilter;
    NewAdapter newAdapter;
    EventAdapter eventAdapter;
    RepositoryRepository repositoryRepository;
    ReleaseRepository releaseRepository;
    CommitRepository commitRepository;

    private int order_by;

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_news;
    }

    @Override
    protected void initLayout() {
        recvNew.setLayoutManager(new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false));
        newAdapter = new NewAdapter(getActivity(), this::moveToDetailActivity);
        recvNew.setHasFixedSize(true);
        recvNew.setAdapter(newAdapter);

        eventAdapter = new EventAdapter(getActivity(), this::moveToWebViewActivity);
        recvEvents.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recvEvents.setHasFixedSize(true);
        recvEvents.setDrawingCacheEnabled(true);
        recvEvents.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recvEvents.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recvEvents.setAdapter(eventAdapter);
        imgvFilter.setOnClickListener(it -> showEventFilterDialog());

        repositoryRepository = new RepositoryRepository(getContext());
        repositoryRepository.registerDaoObserver(this);
        releaseRepository = new ReleaseRepository(getContext());
        commitRepository = new CommitRepository(getContext());

    }

    @Override
    protected void loadData() {
        super.loadData();
        disposables.add(repositoryRepository.getRecentRepo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setNewRepoItems));

        disposables.add(releaseRepository.getReleases()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setReleaseItems));

        disposables.add(commitRepository.getCommits()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setCommitItems));
    }

    private void setReleaseItems(List<Release> items) {
        for (Release release : items) {
            eventAdapter.add(new Event(release));
        }
    }

    private void setCommitItems(List<Commit> items) {
        for(Commit commit : items){
            eventAdapter.add(new Event(commit));
        }
    }

    private void setNewRepoItems(List<Repository> items) {
        newAdapter.setItems(items);

    }

    private void moveToWebViewActivity(int position) {
        Intent intent = WebViewAcitivty.getStartIntent(getActivity(), eventAdapter.getItem(position).getEvent().getMainUrl());
        startActivity(intent);
        getActivity().overridePendingTransition(0, 0);
    }

    private void showEventFilterDialog() {
        EventFilterDialog.getInstance(getActivity(), order_by,
                order_by -> {
                    this.order_by = order_by;
                }).show();
    }

    private void moveToDetailActivity(int position) {
        Intent intent = RepositoryDetailActivity.getStartIntent(getActivity(), newAdapter.getItem(position).getId());
        startActivity(intent);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        repositoryRepository.unregisterDaoObserver(this);
    }

    @Override
    public void onChange() {
        loadData();
    }
}
