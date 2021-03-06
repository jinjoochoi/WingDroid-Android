package com.example.choijinjoo.wingdroid.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import butterknife.ButterKnife;


/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public abstract class BaseFragmentActivity extends FragmentActivity {

    protected abstract int getLayoutId();

    protected abstract void initLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initLayout();
    }


}
