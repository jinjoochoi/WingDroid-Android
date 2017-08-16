package com.example.choijinjoo.wingdroid.ui.feed;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Category;
import com.example.choijinjoo.wingdroid.source.remote.firebase.CategoryDataSource;
import com.example.choijinjoo.wingdroid.ui.base.BaseFragment;
import com.example.choijinjoo.wingdroid.ui.base.FirebaseViewPagerAdapter;
import com.google.firebase.database.Query;

import butterknife.BindView;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public class FeedContainerFragment extends BaseFragment {
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.viewPager) ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    Query ref;

    public static FeedContainerFragment newInstance() {
        return new FeedContainerFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_feed_container;
    }

    @Override
    protected void initLayout() {
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), ref, Category.class);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager, true);
    }

    @Override
    protected void loadData() {
        super.loadData();
        ref = CategoryDataSource.getInstance().categories();
    }


    private class ViewPagerAdapter extends FirebaseViewPagerAdapter<Category> {

        public ViewPagerAdapter(FragmentManager fm, Query ref, Class<Category> clazz) {
            super(fm, ref, clazz);
        }

        @Override
        public Fragment getItem(int position) {
            return FeedFragment.newInstance(datas.get(position));
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return datas.get(position).getName();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        viewPagerAdapter.addChildEventListener();
    }

    @Override
    public void onStop() {
        super.onStop();
        viewPagerAdapter.removeChildEventListener();
    }
}
