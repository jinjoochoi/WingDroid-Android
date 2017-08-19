package com.example.choijinjoo.wingdroid.ui.begin;

import android.content.Intent;
import android.os.Handler;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.service.FirebaseDataSyncService;
import com.example.choijinjoo.wingdroid.ui.base.BaseActivity;

/**
 * Created by choijinjoo on 2017. 8. 18..
 */

public class SplashActivity extends BaseActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected int getLayoutId() {
        return R.layout.actv_splash;
    }

    @Override
    protected void initLayout() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_DISPLAY_LENGTH);
    }

    @Override
    protected void loadData() {
        super.loadData();
        Intent intent = new Intent(this, FirebaseDataSyncService.class);
        startService(intent);
    }
}
