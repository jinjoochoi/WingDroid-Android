package com.example.choijinjoo.wingdroid.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Gif;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public class FeedFragment extends BaseFragment {
    @BindView(R.id.recvRepositories)
    RecyclerView recvRepositories;

    public static FeedFragment newInstance() {
        return new FeedFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_feed;
    }

    @Override
    protected void initLayout() {
        recvRepositories.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recvRepositories.setAdapter(new RepositoryAdapter(getActivity(),makeMockRepository()));
    }


    private List<Repository> makeMockRepository() {
        List<Repository> repositories = new ArrayList<>();
        List<Gif> gifs = new ArrayList<>();
        gifs.add(new Gif("https://github.com/airbnb/lottie-android/blob/master/gifs/Example2.gif?raw=true"));
        List<Gif> gifs2 = new ArrayList<>();
        gifs2.add(new Gif("https://github.com/wasabeef/awesome-android-ui/raw/master/art/discrollview.gif?raw-true"));

        repositories.add(new Repository("lottie", gifs));
        repositories.add(new Repository("discrollview", gifs2));
        repositories.add(new Repository("lottie", gifs));
        repositories.add(new Repository("discrollview", gifs2));
        repositories.add(new Repository("lottie", gifs));
        repositories.add(new Repository("discrollview", gifs2));
        repositories.add(new Repository("lottie", gifs));
        repositories.add(new Repository("discrollview", gifs2));
        repositories.add(new Repository("lottie", gifs));
        repositories.add(new Repository("discrollview", gifs2));
        repositories.add(new Repository("lottie", gifs));
        repositories.add(new Repository("discrollview", gifs2));

        return repositories;
    }
}
