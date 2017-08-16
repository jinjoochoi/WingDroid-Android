package com.example.choijinjoo.wingdroid.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.ui.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by choijinjoo on 2017. 8. 8..
 */

public class WebViewAcitivty extends BaseActivity implements View.OnClickListener{
    @BindView(R.id.webview) WebView webView;
    @BindView(R.id.imgvBack) ImageView imgvBack;

    public static Intent getStartIntent(Context context, String load_url){
        Intent intent = new Intent(context,WebViewAcitivty.class);
        intent.putExtra("load_url",load_url);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.actv_webview;
    }

    @Override
    protected void initLayout() {
        String load_url = getIntent().getStringExtra("load_url");
        webView.loadUrl(load_url);
        imgvBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgvBack :
                onBackPressed();
        }
    }
}
