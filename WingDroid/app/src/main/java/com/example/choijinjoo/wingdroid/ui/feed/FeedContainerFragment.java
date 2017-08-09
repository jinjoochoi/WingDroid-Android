package com.example.choijinjoo.wingdroid.ui.feed;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Category;
import com.example.choijinjoo.wingdroid.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public class FeedContainerFragment extends BaseFragment {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;


    public static FeedContainerFragment newInstance() {
        return new FeedContainerFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_feed_container;
    }

    @Override
    protected void initLayout() {
        makeMockCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categories -> {
                    viewPager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), categories));
                    tabLayout.setupWithViewPager(viewPager, true);
                });
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

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        List<Category> categories;

        public ViewPagerAdapter(FragmentManager fm, List<Category> categories) {
            super(fm);
            this.categories = categories;
        }

        @Override
        public Fragment getItem(int position) {
            return FeedFragment.newInstance();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return categories.get(position).getName();
        }

        @Override
        public int getCount() {
            return categories != null ? categories.size() : 0;
        }
    }
}
