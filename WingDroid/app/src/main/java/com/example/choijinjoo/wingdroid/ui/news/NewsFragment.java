package com.example.choijinjoo.wingdroid.ui.news;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Gif;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.event.Event;
import com.example.choijinjoo.wingdroid.model.event.EventType;
import com.example.choijinjoo.wingdroid.model.event.Issue;
import com.example.choijinjoo.wingdroid.model.event.IssueType;
import com.example.choijinjoo.wingdroid.model.event.Push;
import com.example.choijinjoo.wingdroid.model.event.Release;
import com.example.choijinjoo.wingdroid.ui.SelectSortCriteriaDialog;
import com.example.choijinjoo.wingdroid.ui.base.BaseFragment;
import com.example.choijinjoo.wingdroid.ui.detail.RepositoryDetailActivity;
import com.example.choijinjoo.wingdroid.ui.detail.WebViewFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.RealmList;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public class NewsFragment extends BaseFragment {
    @BindView(R.id.recvNew) RecyclerView recvNew;
    @BindView(R.id.recvEvents) RecyclerView recvEvents;
    @BindView(R.id.imgvFilter) ImageView imgvFilter;
    @BindView(R.id.frameLayout) FrameLayout frameLayout;
    NewAdapter newAdapter;
    EventAdapter eventAdapter;

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_news;
    }

    @Override
    protected void initLayout() {
        recvNew.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        newAdapter = new NewAdapter(getActivity(), this::moveToDetailActivity);
        recvNew.setHasFixedSize(true);
        recvNew.setAdapter(newAdapter);

        eventAdapter = new EventAdapter(getActivity(),this::moveToWebViewActivity);
        recvEvents.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recvEvents.setHasFixedSize(true);
        recvEvents.setDrawingCacheEnabled(true);
        recvEvents.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recvEvents.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recvEvents.setAdapter(eventAdapter);

        // TODO: 2017. 8. 8. data load는 onCrateView() 시점으로 이동
        makeMockRepositories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(news-> newAdapter.setItems(news));

        imgvFilter.setOnClickListener(it -> showSelectSortCriteriaDialog());

        makeMockEvents()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(events -> eventAdapter.setItems(events));
    }

    private void moveToWebViewActivity(int position){
        addFragment(WebViewFragment.newInstance(eventAdapter.getItem(position).getEvent().getMainUrl()));
    }

    private void showSelectSortCriteriaDialog() {
        SelectSortCriteriaDialog.getInstance(getActivity(),
                criteria -> {

                }).show();
    }

    private void moveToDetailActivity(int position){
        Intent intent = RepositoryDetailActivity.getStartIntent(getActivity(),newAdapter.getItem(position));
        startActivity(intent);
    }


    private Observable<List<Event>> makeMockEvents() {
        List<Event> events = new ArrayList<>();

        events.add(new Event(EventType.ISSUE.value, new Issue(IssueType.OPEN.toString(), "npm package on package.json", "if I put on my package.json:any idea why?")));
        events.add(new Event(EventType.PUSH.value, new Push()));
        events.add(new Event(EventType.RELEASE.value, new Release()));

        events.add(new Event(EventType.ISSUE.value, new Issue(IssueType.CLOSE.toString(), "Cannot read property 'forEach' of undefined ",
                        "Error: Cannot read property 'forEach' of undefined. at (...) \\node_modules\\messenger-bot\\index.js:188:12")));
        events.add(new Event(EventType.ISSUE.value, new Issue(IssueType.CLOSE.toString(), "Cannot read property 'forEach' of undefined ",
                "There's a error when running a simple echo test without middlware.\n" +
                        "\n" +
                        "Error: Cannot read property 'forEach' of undefined. at (...) \\node_modules\\messenger-bot\\index.js:188:12")));

        return Observable.just(events);
    }

    private Observable<List<Repository>> makeMockRepositories() {
        RealmList<Repository> repositories = new RealmList<>();
        RealmList<Gif> gifs = new RealmList<>();
        gifs.add(new Gif("https://github.com/airbnb/lottie-android/blob/master/gifs/Example2.gif?raw=true"));

        repositories.add(new Repository("lottie", gifs, 1700));
        repositories.add(new Repository("lottie", gifs, 840));
        repositories.add( new Repository("lottie", gifs, 2400));
        repositories.add(new Repository("lottie", gifs, 380));

        return Observable.just(repositories);
    }

    public void addFragment(Fragment fragment){
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
        transaction.add(R.id.frameLayout, fragment);
        transaction.commitNow();
    }


}
