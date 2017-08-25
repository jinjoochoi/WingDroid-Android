package com.example.choijinjoo.wingdroid.ui.begin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.source.local.SharedPreferenceHelper;
import com.example.choijinjoo.wingdroid.ui.MainActivity;
import com.example.choijinjoo.wingdroid.ui.base.BaseActivity;

import static com.example.choijinjoo.wingdroid.source.local.SharedPreferenceHelper.Config.LOAD;

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
    }

    @Override
    protected void loadData() {
        super.loadData();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (SharedPreferenceHelper.getInstance().getBooleanValue(this, LOAD, false)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    moveToNextActivity();
                }
            }, SPLASH_DISPLAY_LENGTH);
        } else {
            LocalBroadcastManager.getInstance(this)
                    .registerReceiver(messageReceiver,
                            new IntentFilter("initial-load"));
        }
    }

    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (RESULT_OK == intent.getIntExtra("result", -1)) {
                moveToNextActivity();
            }
        }
    };

    private void moveToNextActivity() {
        Intent startIntent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(startIntent);
        finish();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this)
                .unregisterReceiver(messageReceiver);
        super.onPause();
    }

}
