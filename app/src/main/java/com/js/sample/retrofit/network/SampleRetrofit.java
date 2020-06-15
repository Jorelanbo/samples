package com.js.sample.retrofit.network;

// Created by JS on 2020/6/15.

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SampleRetrofit {

    private volatile static Retrofit retrofit;

    private SampleRetrofit() {
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            synchronized (SampleRetrofit.class) {
                if (retrofit == null) {
                    retrofit = new Retrofit.Builder()
                            .baseUrl("https://www.wanandroid.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }
}
