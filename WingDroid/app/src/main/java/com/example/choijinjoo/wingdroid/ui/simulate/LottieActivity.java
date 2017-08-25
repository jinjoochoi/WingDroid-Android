package com.example.choijinjoo.wingdroid.ui.simulate;

import android.content.Context;
import android.content.Intent;

import com.example.choijinjoo.wingdroid.R;

/**
 * Created by choijinjoo on 2017. 8. 24..
 */

public class LottieActivity extends SimulateActivity {


    public static Intent getStartIntent(Context context, String title){
        Intent intent = new Intent(context,LottieActivity.class);
        intent.putExtra("title",title);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.actv_lottie;
    }

    @Override
    public String getToolbarTitle() {
        return getIntent().getStringExtra("title");
    }
}
