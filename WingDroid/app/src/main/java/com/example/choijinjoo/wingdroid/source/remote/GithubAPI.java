package com.example.choijinjoo.wingdroid.source.remote;

import com.example.choijinjoo.wingdroid.model.response.AuthResponse;

import io.reactivex.Observable;
import retrofit2.adapter.rxjava2.Result;


/**
 * Created by choijinjoo on 2017. 8. 14..
 */

public class GithubAPI extends API<GithubService> {
    private static GithubAPI instance = null;

    private GithubService githubService;

    public static GithubAPI getInstance() {
        if (instance == null) {
            instance = new GithubAPI();
        }
        return instance;
    }

    public GithubAPI() {
        super(GithubService.class);
        this.githubService = createCallService();
    }

    public Observable<Result<AuthResponse>> authorize(String client_id, String client_secret, String code) {
        return githubService.authorize(client_id, client_secret, code);
    }
}
