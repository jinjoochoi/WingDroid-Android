package com.example.choijinjoo.wingdroid.ui;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.MenuItem;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.tools.BottomNavigationViewHelper;
import com.example.choijinjoo.wingdroid.tools.ScrollDisabledViewPager;
import com.example.choijinjoo.wingdroid.ui.base.BaseActivity;
import com.example.choijinjoo.wingdroid.ui.bookmark.BookMarkFragment;
import com.example.choijinjoo.wingdroid.ui.feed.FeedContainerFragment;
import com.example.choijinjoo.wingdroid.ui.news.NewsFragment;
import com.example.choijinjoo.wingdroid.ui.search.SearchFragment;

import butterknife.BindView;

import static com.example.choijinjoo.wingdroid.ui.MainActivity.ViewPagerAdapter.FRAG_BOOKMARK;
import static com.example.choijinjoo.wingdroid.ui.MainActivity.ViewPagerAdapter.FRAG_FEED;
import static com.example.choijinjoo.wingdroid.ui.MainActivity.ViewPagerAdapter.FRAG_NEWS;
import static com.example.choijinjoo.wingdroid.ui.MainActivity.ViewPagerAdapter.FRAG_SEARCH;

public class MainActivity extends BaseActivity {
    @BindView(R.id.viewPager) ScrollDisabledViewPager viewPager;
    @BindView(R.id.bottomNav) BottomNavigationView bottomNav;
    ViewPagerAdapter viewPagerAdapter;

    public static Intent getStartIntent(Context context){
        return new Intent(context,MainActivity.class);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.actv_main;
    }

    @Override
    public void initLayout() {
        bottomNav.getMenu().getItem(0).setChecked(true);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        bottomNav.setOnNavigationItemSelectedListener(this::swtichFragment);
        BottomNavigationViewHelper.disableShiftMode(bottomNav);


        viewPager.setOffscreenPageLimit(3);
    }

    private boolean swtichFragment(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_feed:
                viewPager.setCurrentItem(FRAG_FEED);
                break;
            case R.id.nav_search:
                viewPager.setCurrentItem(FRAG_SEARCH);
                break;
            case R.id.nav_news:
                viewPager.setCurrentItem(FRAG_NEWS);
                break;
            case R.id.nav_bookmark:
                viewPager.setCurrentItem(FRAG_BOOKMARK);
                break;
            default:
                throw new IllegalStateException();
        }
        return true;
    }

    public class ViewPagerAdapter extends FragmentPagerAdapter {
        public static final int FRAG_FEED = 0;
        public static final int FRAG_SEARCH = 1;
        public static final int FRAG_NEWS = 2;
        public static final int FRAG_BOOKMARK = 3;

        SparseArray<Fragment> fragments = new SparseArray<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            init();
        }

        private void init(){
            fragments.put(FRAG_FEED, FeedContainerFragment.newInstance());
            fragments.put(FRAG_SEARCH, SearchFragment.newInstance());
            fragments.put(FRAG_NEWS, NewsFragment.newInstance());
            fragments.put(FRAG_BOOKMARK, BookMarkFragment.newInstance());
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return 4;
        }

        public SparseArray<Fragment> getFragments() {
            return fragments;
        }
    }

    @Override
    public void onBackPressed() {
        if(viewPager.getCurrentItem() == FRAG_SEARCH && viewPagerAdapter.getFragments().get(FRAG_SEARCH).getChildFragmentManager().getBackStackEntryCount()>0){
            viewPagerAdapter.getFragments().get(FRAG_SEARCH).getChildFragmentManager().popBackStack();
        }else{
            super.onBackPressed();
        }
    }
}
