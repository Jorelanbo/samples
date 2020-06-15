package com.js.sample.retrofit.network;

// Created by JS on 2020/6/13.

import com.js.sample.retrofit.bean.ChapterBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("wxarticle/chapters/json")
    Call<Data<List<ChapterBean>>> getChapters();

    @GET("article/list/0/json")
    Call<Data<ArticlesData>> getQueryArticles(@Query("author") String author);
}
