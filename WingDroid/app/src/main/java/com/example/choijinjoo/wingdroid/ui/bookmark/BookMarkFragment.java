package com.example.choijinjoo.wingdroid.ui.bookmark;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Gif;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.Tag;
import com.example.choijinjoo.wingdroid.ui.CategoryFilterDialog;
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
        makeMockRepository()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(repositories -> adapter.setItems(repositories));

        containerSort.setOnClickListener(it -> showSelectSortCriteriaDialog());
        imgvFilter.setOnClickListener(it -> showCategoryFilterDialog());
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

    private Observable<List<Repository>> makeMockRepository() {
        List<Repository> repositories = new ArrayList<>();
        List<Gif> gifs = new ArrayList<>();
        gifs.add(new Gif("https://github.com/airbnb/lottie-android/blob/master/gifs/Example2.gif?raw=true"));
        List<Gif> gifs2 = new ArrayList<>();
        gifs2.add(new Gif("https://github.com/wasabeef/awesome-android-ui/raw/master/art/discrollview.gif?raw-true"));
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("Expanding"));
        tags.add(new Tag("Snap"));
        tags.add(new Tag("span"));
        tags.add(new Tag("Expanding"));
        List<Tag> tags2 = new ArrayList<>();
        tags.add(new Tag("Shimmer"));
        tags.add(new Tag("ripple"));


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
