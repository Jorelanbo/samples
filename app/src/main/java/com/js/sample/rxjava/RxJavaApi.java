package com.js.sample.rxjava;

// Created by JS on 2020/6/16.

import com.js.sample.retrofit.bean.ChapterBean;
import com.js.sample.retrofit.network.Data;

import java.util.List;
import retrofit2.http.GET;
import rx.Observable;

public interface RxJavaApi {

    @GET("wxarticle/chapters/json")
    Observable<Data<List<ChapterBean>>> getChapters();
}
