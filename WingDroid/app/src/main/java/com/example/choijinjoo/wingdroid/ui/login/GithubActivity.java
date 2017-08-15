package com.example.choijinjoo.wingdroid.ui.login;

import android.content.Context;
import android.content.Intent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.choijinjoo.wingdroid.R;
import com.example.choijinjoo.wingdroid.config.Config;
import com.example.choijinjoo.wingdroid.source.local.SharedPreferenceHelper;
import com.example.choijinjoo.wingdroid.source.remote.api.GithubAPI;
import com.example.choijinjoo.wingdroid.ui.base.BaseFragmentActivity;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;

import static com.example.choijinjoo.wingdroid.config.Config.CLIENT_ID;
import static com.example.choijinjoo.wingdroid.config.Config.CLIENT_SECRET;
import static com.example.choijinjoo.wingdroid.source.local.SharedPreferenceHelper.Config.AUTH_TOKEN;

/**
 * Created by choijinjoo on 2017. 8. 14..
 */

public class GithubActivity extends BaseFragmentActivity {
    @BindView(R.id.webview) public WebView webview;

    public static Intent getStartIntent(Context context){
        return new Intent(context,GithubActivity.class);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.actv_github;
    }

    @Override
    protected void initLayout() {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                super.shouldOverrideUrlLoading(view, url);
                // Try catch to allow in app browsing without crashing.
                try {
                    if (!url.contains("?code=")) return false;

                    String code = url.substring(url.lastIndexOf("?code=") + 1);
                    String[] token_code = code.split("=");
                    String tokenFetchedIs = token_code[1];
                    String[] cleanToken = tokenFetchedIs.split("&");

                    fetchOauthTokenWithCode(cleanToken[0]);

                } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
                return false;
            }
        });

        HttpUrl.Builder builder = HttpUrl.parse(Config.URL_GITHUB_OAUTH).newBuilder();
        builder.addPathSegment("authorize");
        builder.addQueryParameter("client_id",Config.CLIENT_ID);

        webview.loadUrl(builder.build().toString());
    }


    private void fetchOauthTokenWithCode(String code){
        GithubAPI.getInstance().authorize(CLIENT_ID,CLIENT_SECRET,code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(it -> !it.isError())
                .map(it -> it.response().body().getAccessToken())
                .subscribe(this::setAccessToken);
    }

    private void setAccessToken(String accessToken){
        SharedPreferenceHelper.getInstance().putStringValue(this, AUTH_TOKEN, accessToken);
        setResult(RESULT_OK);
        finish();
    }
}
