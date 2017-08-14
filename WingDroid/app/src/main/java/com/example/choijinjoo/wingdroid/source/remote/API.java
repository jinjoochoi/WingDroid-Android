package com.example.choijinjoo.wingdroid.source.remote;

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
        mRetrofit = createRetrofit();
    }

    private Retrofit createRetrofit() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .client(new OkHttpClient().newBuilder()
                        .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .addNetworkInterceptor(new StethoInterceptor()).build())
                        .baseUrl(getBaseURL())
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();

        return retrofit;
    }


    protected T createCallService() {
        return mRetrofit.create(mCallClazz);
    }

    protected String getBaseURL() {
        return Config.URL_GITHUB_OAUTH;
    }

}