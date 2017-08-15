package com.example.choijinjoo.wingdroid.source.remote.api;


import com.example.choijinjoo.wingdroid.model.response.AuthResponse;

import io.reactivex.Observable;
import retrofit2.adapter.rxjava2.Result;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by choijinjoo on 2017. 8. 14..
 */

public interface GithubService {
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("access_token")
    Observable<Result<AuthResponse>> authorize(@Field("client_id")String clientId, @Field("client_secret")String clientSecret, @Field("code")String code);
}
