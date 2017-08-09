package com.example.choijinjoo.wingdroid.ui.feed;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.LinearLayout;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Gif;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.Tag;
import com.example.choijinjoo.wingdroid.ui.detail.RepositoryDetailActivity;
import com.example.choijinjoo.wingdroid.ui.SelectSortCriteriaDialog;
import com.example.choijinjoo.wingdroid.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public class FeedFragment extends BaseFragment {
    @BindView(R.id.recvRepositories) RecyclerView recvRepositories;
    @BindView(R.id.containerSort) LinearLayout containerSort;
    RepositoryAdapter adapter;

    public static FeedFragment newInstance() {
        return new FeedFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_feed;
    }

    @Override
    protected void initLayout() {
        recvRepositories.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        adapter = new RepositoryAdapter(getActivity(), this::moveToDetailActivity);
        recvRepositories.setAdapter(adapter);

        // TODO: 2017. 8. 8. data load는 onCrateView() 시점으로 이동
        makeMockRepository()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(repositories -> adapter.setItems(repositories));
        containerSort.setOnClickListener(it -> showSelectSortCriteriaDialog());
    }

    private void moveToDetailActivity(int position){
        Intent intent = RepositoryDetailActivity.getStartIntent(getActivity(),adapter.getItem(position));
        startActivity(intent);
    }

    private void showSelectSortCriteriaDialog() {
        SelectSortCriteriaDialog.getInstance(getActivity(),
                criteria -> {}).show();
    }

    private Observable<List<Repository>> makeMockRepository() {
        List<Repository> repositories = new ArrayList<>();
        List<Gif> gifs = new ArrayList<>();
        gifs.add(new Gif("https://github.com/airbnb/lottie-android/blob/master/gifs/Example2.gif?raw=true"));
        List<Gif> gifs2 = new ArrayList<>();
        gifs2.add(new Gif("https://github.com/wasabeef/awesome-android-ui/raw/master/art/discrollview.gif?raw-true"));
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("1"));
        tags.add(new Tag("2"));
        tags.add(new Tag("3"));
        tags.add(new Tag("Expanding"));
        List<Tag> tags2 = new ArrayList<>();
        tags2.add(new Tag("Shimmer"));
        tags2.add(new Tag("ripple"));


        repositories.add(new Repository("lottie", gifs, tags, 850));
        repositories.add(new Repository("discrollview", gifs2, tags, 1500));
        repositories.add(new Repository("lottie", gifs, tags2, 250));
        repositories.add(new Repository("discrollview", gifs2, tags2, 12000));
        repositories.add(new Repository("lottie", gifs, tags2, 1800));
        repositories.add(new Repository("discrollview", gifs2, tags, 20));
        repositories.add(new Repository("lottie", gifs, tags, 30));
        repositories.add(new Repository("discrollview", gifs2, tags, 450));
        repositories.add(new Repository("lottie", gifs, tags2, 1804));
        repositories.add(new Repository("discrollview", gifs2, tags2, 250));
        repositories.add(new Repository("lottie", gifs, tags2, 250));
        repositories.add(new Repository("discrollview", gifs2, tags2, 350));

        return Observable.just(repositories);
    }

}
