package com.example.choijinjoo.wingdroid.ui.search;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.WingDroidApp;
import com.example.choijinjoo.wingdroid.model.Gif;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.Tag;
import com.example.choijinjoo.wingdroid.ui.base.BaseFragment;
import com.example.choijinjoo.wingdroid.ui.detail.RepositoryDetailActivity;

import java.util.List;

import butterknife.BindView;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by choijinjoo on 2017. 8. 8..
 */

public class SearchResultFragment extends BaseFragment {
    @BindView(R.id.recvResults) RecyclerView recvResults;
    SearchResultAdapter adapter;

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

    public void showSuggestions(String str){
        WingDroidApp.getInstance()
                .repositoryLocalSource()
                .getSuggestionRepository(str)
                .subscribe(this::setData);
    }

    private void setData(RealmResults<Repository> repositories){
        adapter = new SearchResultAdapter(getActivity(),makeMockRepository(),
                this::moveToDetailActivity);
        recvResults.setAdapter(adapter);
    }

    private List<Repository> makeMockRepository() {
        RealmList<Repository> repositories = new RealmList<>();
        RealmList<Gif> gifs = new RealmList<>();
        gifs.add(new Gif("https://github.com/airbnb/lottie-android/blob/master/gifs/Example2.gif?raw=true"));
        RealmList<Gif> gifs2 = new RealmList<>();
        gifs2.add(new Gif("https://github.com/wasabeef/awesome-android-ui/raw/master/art/discrollview.gif?raw-true"));
        RealmList<Tag> tags = new RealmList<>();
        tags.add(new Tag("1"));
        tags.add(new Tag("2"));
        tags.add(new Tag("3"));
        tags.add(new Tag("Expanding"));
        RealmList<Tag> tags2 = new RealmList<>();
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

        return repositories;
    }



    private void moveToDetailActivity(int position){
        Intent intent = RepositoryDetailActivity.getStartIntent(getActivity(),adapter.getItem(position));
        getActivity().startActivity(intent);
    }

}
