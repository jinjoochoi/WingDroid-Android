package com.example.choijinjoo.wingdroid.source.remote.api;

import com.example.choijinjoo.wingdroid.model.event.Release;
import com.example.choijinjoo.wingdroid.model.response.CommitResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.adapter.rxjava2.Result;


/**
 * Created by choijinjoo on 2017. 8. 14..
 */

public class GithubAPI extends API<GithubService> {
    private static GithubAPI instance = null;

    private GithubService service;

    public static GithubAPI getInstance() {
        if (instance == null) {
            instance = new GithubAPI();
        }
        return instance;
    }

    public GithubAPI() {
        super(GithubService.class);
        this.service = createAPIService();
    }
    public Observable<Result<List<Release>>> release (String owner, String repo) {
        return service.release(owner,repo);
    }

    public Observable<Result<List<CommitResponse>>> commits (String owner, String repo) {
        return service.commits(owner,repo);
    }

}
