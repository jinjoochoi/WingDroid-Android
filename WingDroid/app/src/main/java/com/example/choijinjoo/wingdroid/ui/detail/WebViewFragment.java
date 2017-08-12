package com.example.choijinjoo.wingdroid.ui.detail;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.ui.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by choijinjoo on 2017. 8. 8..
 */

public class WebViewFragment extends BaseFragment {
    @BindView(R.id.webview) WebView webView;
    @BindView(R.id.imgvBack) ImageView imgvBack;

    public static WebViewFragment newInstance(String load_url){
        WebViewFragment fragment = new WebViewFragment();
        Bundle bundle = new Bundle();
        bundle.putString("load_url",load_url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_webview;
    }

    @Override
    protected void initLayout() {
        String load_url = getArguments().getString("load_url");
        webView.loadUrl(load_url);
    }
}
