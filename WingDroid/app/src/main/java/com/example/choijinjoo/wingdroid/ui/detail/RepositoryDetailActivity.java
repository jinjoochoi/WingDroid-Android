package com.example.choijinjoo.wingdroid.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Gif;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.Tag;
import com.example.choijinjoo.wingdroid.ui.base.BaseActivity;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.RealmList;

/**
 * Created by choijinjoo on 2017. 8. 8..
 */

public class RepositoryDetailActivity extends BaseActivity {
    @BindView(R.id.imgvBookMark) ImageView imgvBookMark;
    @BindView(R.id.imgvShare) ImageView imgvShare;
    @BindView(R.id.imgvPreview) ImageView imgvPreview;
    @BindView(R.id.txtvName) TextView txtvName;
    @BindView(R.id.txtvDescription) TextView txtvDescription;
    @BindView(R.id.txtvIssue) TextView txtvIssue;
    @BindView(R.id.txtvStar) TextView txtvStar;
    @BindView(R.id.txtvAuthor) TextView txtvAuthor;
    @BindView(R.id.txtvGithub) TextView txtvGithub;
    @BindView(R.id.recvSimmilarLibs) RecyclerView recvSimmilarLibs;
    SimmilarsAdapter simmilarsAdapter;


    public static Intent getStartIntent(Context context, Repository repository){
        Intent intent = new Intent(context, RepositoryDetailActivity.class);
        intent.putExtra("repository", Parcels.wrap(repository));
        return intent;

    }

    @Override
    protected int getLayoutId() {
        return R.layout.actv_repository_detail;
    }

    @Override
    protected void initLayout() {
        Repository repository = Parcels.unwrap(getIntent().getParcelableExtra("repository"));
        Glide.with(this)
                .load(repository.getGifs().get(0).getUrl())
                .into(imgvPreview);
        txtvName.setText(repository.getName());
        txtvDescription.setText(repository.getDescription());
        txtvAuthor.setText("by. " + repository.getAuthor());
        txtvIssue.setOnClickListener(it -> moveToWebViewActivity("https://github.com/MengTo/Spring/issues/272"));
        txtvGithub.setOnClickListener(it -> moveToWebViewActivity("https://github.com/MengTo/Spring/issues/272"));
        txtvStar.setText(repository.getFormattedStarString());

        recvSimmilarLibs.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        simmilarsAdapter = new SimmilarsAdapter(this,position -> moveToDetailActivity(simmilarsAdapter.getItem(position)));
        recvSimmilarLibs.setAdapter(simmilarsAdapter);

        makeMockRepository()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(repositories -> simmilarsAdapter.setItems(repositories));
    }


    private void moveToWebViewActivity(String url){
        addFragment(WebViewFragment.newInstance(url));
    }


    private void moveToDetailActivity(Repository repository){
        startActivity(RepositoryDetailActivity.getStartIntent(RepositoryDetailActivity.this,repository));
    }

    private Observable<List<Repository>> makeMockRepository() {
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

        return Observable.just(repositories);
    }

    public void addFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_ENTER_MASK);
        transaction.add(R.id.frameLayout, fragment);
        transaction.commitNow();
    }


}

