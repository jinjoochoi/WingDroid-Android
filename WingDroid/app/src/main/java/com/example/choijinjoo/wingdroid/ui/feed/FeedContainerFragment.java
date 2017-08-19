package com.example.choijinjoo.wingdroid.ui.feed;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.dao.CategoryRepository;
import com.example.choijinjoo.wingdroid.model.Category;
import com.example.choijinjoo.wingdroid.ui.base.BaseFragment;

import java.util.List;

import butterknife.BindView;

/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public class FeedContainerFragment extends BaseFragment {
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.viewPager) ViewPager viewPager;
    CategoryRepository categoryRepository;
    ViewPagerAdapter viewPagerAdapter;

    public static FeedContainerFragment newInstance() {
        return new FeedContainerFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_feed_container;
    }

    @Override
    protected void initLayout() {
        repositories.add(categoryRepository = new CategoryRepository(getContext()));
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), categoryRepository.getCategoriesOrderByName());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager, true);
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        List<Category> items;

        public ViewPagerAdapter(FragmentManager fm, List<Category> items) {
            super(fm);
            this.items = items;
        }

        @Override
        public Fragment getItem(int position) {
            return FeedFragment.newInstance(items.get(position));
        }

        @Override
        public int getCount() {
            return items != null ? items.size() : 0;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return items.get(position).getName();
        }
    }

}
