package com.example.choijinjoo.wingdroid.source.remote.api;


import com.example.choijinjoo.wingdroid.model.event.Release;
import com.example.choijinjoo.wingdroid.model.response.CommitResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by choijinjoo on 2017. 8. 14..
 */

public interface GithubService {

    @GET("/repos/{owner}/{repo}/releases")
    Observable<Result<List<Release>>> release(@Path("owner") String owner, @Path("repo") String repo);

    @GET("/repos/{owner}/{repo}/commits")
    Observable<Result<List<CommitResponse>>> commits(@Path("owner") String owner, @Path("repo") String repo);
}
