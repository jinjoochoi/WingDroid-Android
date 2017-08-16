package com.example.choijinjoo.wingdroid.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;


/**
 * Created by choijinjoo on 2017. 8. 4..
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract int getLayoutId();

    protected abstract void initLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initLayout();
        loadData();
    }

    protected void showToastMessage(int messageId){
        Toast.makeText(this,getString(messageId),Toast.LENGTH_SHORT).show();
    }

    protected void loadData(){}


}
