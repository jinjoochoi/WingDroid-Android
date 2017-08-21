package com.example.choijinjoo.wingdroid.source.remote.api;

import com.example.choijinjoo.wingdroid.config.Config;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class API<T> {

    private Retrofit mRetrofit;

    protected Class<T> mCallClazz;


    public API(Class<T> clazz) {
        mCallClazz = clazz;
    }

    private Retrofit createRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient().newBuilder()
                        .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .addNetworkInterceptor(new StethoInterceptor()).build())
                .baseUrl(getAuthURL())
                .addConverterFactory(GsonConverterFactory.create(createGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit;
    }


    private Retrofit createAPIRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient().newBuilder()
                        .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .addNetworkInterceptor(new StethoInterceptor()).build())
                .baseUrl(getAPIURL())
                .addConverterFactory(GsonConverterFactory.create(createGson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit;
    }

    private Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setLenient()
                .create();

    }


    protected T createCallService() {
        mRetrofit = createRetrofit();
        return mRetrofit.create(mCallClazz);
    }


    protected T createAPIService() {
        mRetrofit = createAPIRetrofit();
        return mRetrofit.create(mCallClazz);
    }

    protected String getAuthURL() {
        return Config.URL_GITHUB_OAUTH;
    }

    protected String getAPIURL() {
        return Config.URL_GITHUB_API;
    }

}