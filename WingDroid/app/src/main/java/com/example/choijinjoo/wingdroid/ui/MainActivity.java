package com.example.choijinjoo.wingdroid.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.model.Category;
import com.example.choijinjoo.wingdroid.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.actv_main;
    }

    @Override
    public void initLayout() {
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), makeMockCategories()));
        tabLayout.setupWithViewPager(viewPager, true);
    }

    private List<Category> makeMockCategories() {
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
        return categories;
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
