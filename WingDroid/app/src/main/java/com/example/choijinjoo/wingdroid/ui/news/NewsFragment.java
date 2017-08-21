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
import com.example.choijinjoo.wingdroid.model.event.Event;
import com.example.choijinjoo.wingdroid.model.eventbus.EventClickEvent;
import com.example.choijinjoo.wingdroid.model.eventbus.RepoClickEvent;
import com.example.choijinjoo.wingdroid.ui.EventFilterDialog;
import com.example.choijinjoo.wingdroid.ui.base.BaseFragment;
import com.example.choijinjoo.wingdroid.ui.detail.RepositoryDetailActivity;
import com.example.choijinjoo.wingdroid.ui.detail.WebViewAcitivty;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.example.choijinjoo.wingdroid.ui.news.EventAdapter.EVENT_RELEASE;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public class NewsFragment extends BaseFragment {
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

    List<Event> events = new ArrayList<>();

    private final Comparator<Event> eventComparator = new Comparator<Event>() {
        @Override
        public int compare(Event o1, Event o2) {
            return o2.getEvent().getLongTypeDate().compareTo(o1.getEvent().getLongTypeDate());
        }
    };

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

        eventAdapter = new EventAdapter(getActivity(), new EventAdapter.EventClickListener() {
            @Override
            public void clicked(int position) {
                Event event = eventAdapter.getItem(position);
                if (event.getType() == EVENT_RELEASE) {
                    releaseRepository.updateRelease(event.getRelease());
                } else {
                    CommitRepository.getInstance(getContext()).updateCommit(event.getCommit());
                }
                if (!event.getEvent().isRead()) {
                    event.getEvent().setRead(true);
                    if(event.getType() == EVENT_RELEASE){
                        releaseRepository.updateRelease(event.getRelease());
                    }else{
                        CommitRepository.getInstance(getContext()).updateCommit(event.getCommit());
                    }
                    EventBus.getDefault().post(new EventClickEvent(event, position));
                }
                moveToWebViewActivity(event);
            }

            @Override
            public boolean longClicked(int position) {
                //TODO 지우기
                return true;
            }
        });
        recvEvents.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recvEvents.setHasFixedSize(true);
        recvEvents.setDrawingCacheEnabled(true);
        recvEvents.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recvEvents.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recvEvents.setAdapter(eventAdapter);
        imgvFilter.setOnClickListener(it -> showEventFilterDialog());

        repositoryRepository = new RepositoryRepository(getContext());
        releaseRepository = new ReleaseRepository(getContext());
        EventBus.getDefault().register(this);


    }

    @Override
    protected void loadData() {
        super.loadData();
        Observable.merge(releaseRepository.getEvents(), CommitRepository.getInstance(getContext()).getEvents())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(event -> {
                    events.addAll(event);
                    Collections.sort(events,eventComparator);
                    eventAdapter.setItems(events);
                });
        loadNewRepoItem();
    }

    private void loadNewRepoItem() {
        disposables.add(repositoryRepository.getRecentRepo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setNewRepoItems));
    }

    private void setNewRepoItems(List<Repository> items) {
        newAdapter.setItems(items);

    }

    private void moveToWebViewActivity(Event event) {
        Intent intent = WebViewAcitivty.getStartIntent(getActivity(), event.getEvent().getMainUrl());
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
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RepoClickEvent event) {
        loadNewRepoItem();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventClickEvent event) {
        Event updatedEvent = null;
        if (event.getEvent().getEvent().getViewType() == EVENT_RELEASE) {
            updatedEvent = new Event(releaseRepository.getReleaseById(eventAdapter.getItem(event.getPosition()).getRelease()));
        } else {
            updatedEvent = new Event(CommitRepository.getInstance(getContext()).getCommitById(event.getEvent().getCommit()));
        }
        eventAdapter.change(event.getPosition(), updatedEvent);

    }
}
