package com.example.choijinjoo.wingdroid.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.ui.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by choijinjoo on 2017. 8. 8..
 */

public class WebViewActivity extends BaseActivity {
    @BindView(R.id.webview) WebView webView;

    public static Intent getStartIntent(Context context, String load_url){
        Intent intent = new Intent(context,WebViewActivity.class);
        intent.putExtra("load_url",load_url);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.actv_webview;
    }

    @Override
    protected void initLayout() {
        String load_url = getIntent().getExtras().getString("load_url");
        webView.loadUrl(load_url);

    }
}
