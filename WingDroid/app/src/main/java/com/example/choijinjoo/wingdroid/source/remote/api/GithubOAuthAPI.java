package com.example.choijinjoo.wingdroid.source.remote.api;

import com.example.choijinjoo.wingdroid.model.response.AuthResponse;

import io.reactivex.Observable;
import retrofit2.adapter.rxjava2.Result;


/**
 * Created by choijinjoo on 2017. 8. 14..
 */

public class GithubOAuthAPI extends API<GithubOAuthService> {
    private static GithubOAuthAPI instance = null;

    private GithubOAuthService service;

    public static GithubOAuthAPI getInstance() {
        if (instance == null) {
            instance = new GithubOAuthAPI();
        }
        return instance;
    }

    public GithubOAuthAPI() {
        super(GithubOAuthService.class);
        this.service = createCallService();
    }

    public Observable<Result<AuthResponse>> authorize(String client_id, String client_secret, String code) {
        return service.authorize(client_id, client_secret, code);
    }

}
