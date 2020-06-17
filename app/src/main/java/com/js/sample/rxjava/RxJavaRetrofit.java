package com.js.sample.rxjava;

// Created by JS on 2020/6/15.

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxJavaRetrofit {

    private volatile static Retrofit retrofit;

    private RxJavaRetrofit() {
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (RxJavaRetrofit.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .client(new OkHttpClient())
                            .baseUrl("https://www.wanandroid.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }
}
