package com.example.choijinjoo.wingdroid.tools;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by choijinjoo on 2017. 8. 11..
 */

public class ScrollDisabledViewPager extends ViewPager {
    public ScrollDisabledViewPager(Context context) {
        super(context);
    }

    public ScrollDisabledViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private boolean enabled;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;

    }
}
