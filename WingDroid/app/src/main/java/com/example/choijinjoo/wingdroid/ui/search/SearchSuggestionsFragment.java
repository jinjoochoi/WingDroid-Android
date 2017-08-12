package com.example.choijinjoo.wingdroid.ui.search;


import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Category;
import com.example.choijinjoo.wingdroid.model.Gif;
import com.example.choijinjoo.wingdroid.model.Repository;
import com.example.choijinjoo.wingdroid.model.Tag;
import com.example.choijinjoo.wingdroid.ui.base.BaseFragment;
import com.example.choijinjoo.wingdroid.ui.detail.RepositoryDetailActivity;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.realm.RealmList;

/**
 * Created by choijinjoo on 2017. 8. 8..
 */

public class SearchSuggestionsFragment extends BaseFragment {
    @BindView(R.id.recvCategories) RecyclerView recvCategories;
    @BindView(R.id.recvSuggestions) RecyclerView recvSuggestions;
    CategorySearchAdapter categoryAdapter;
    SuggestionsAdapter suggestionsAdapter;

    public static SearchSuggestionsFragment newInstance() {
        return new SearchSuggestionsFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_search_suggestions;
    }

    @Override
    protected void initLayout() {
        categoryAdapter = new CategorySearchAdapter(getActivity(), position -> {
            categoryAdapter.getItem(position).selected();
            categoryAdapter.notifyItemChanged(position);
            moveToSearchResultFragment(categoryAdapter.getItem(position).getName());
        });
        suggestionsAdapter = new SuggestionsAdapter(getActivity(),
                position -> moveToDetailActivity(suggestionsAdapter.getItem(position)));
        recvCategories.setAdapter(categoryAdapter);
        recvCategories.setLayoutManager(new FlowLayoutManager());

        recvSuggestions.setLayoutManager(new GridLayoutManager(getActivity(),3, LinearLayoutManager.VERTICAL,false));
        recvSuggestions.setAdapter(suggestionsAdapter);

        makeMockCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categories -> categoryAdapter.setItems(categories));

        makeMockRepository()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(repositories -> suggestionsAdapter.setItems(repositories));
    }

    private void moveToSearchResultFragment(String name) {
        ((SearchFragment)getParentFragment()).hideSearchViewText();
        ((SearchFragment)getParentFragment()).showSuggestions(name);
    }

    // TODO: 2017. 8. 8. MOVETO DETAIL
    private void moveToDetailActivity(Repository repository) {
        Intent intent = RepositoryDetailActivity.getStartIntent(getActivity(),repository);
        startActivity(intent);
    }

    private Observable<List<Category>> makeMockCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Button"));
        categories.add(new Category("Calendar"));
        categories.add(new Category("Effect"));
        categories.add(new Category("Graph"));
        categories.add(new Category("Image"));
        categories.add(new Category("Label/Form"));
        categories.add(new Category("List/Grid"));
        categories.add(new Category("Loading"));
        categories.add(new Category("Menu"));
        categories.add(new Category("Progress"));
        categories.add(new Category("SeekBar"));
        categories.add(new Category("SideBar"));
        return Observable.just(categories);
    }

    private Observable<List<Repository>> makeMockRepository() {
        List<Repository> repositories = new ArrayList<>();
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


}
