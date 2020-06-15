package com.js.sample.retrofit;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.js.sample.R;
import com.js.sample.retrofit.bean.ArticleBean;
import com.js.sample.retrofit.bean.ChapterBean;
import com.js.sample.retrofit.network.Api;
import com.js.sample.retrofit.network.ArticlesData;
import com.js.sample.retrofit.network.Data;
import com.js.sample.retrofit.network.SampleRetrofit;
import com.js.sample.utils.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


// Created by JS on 2020/6/13.

public class RetrofitTestActivity extends AppCompatActivity {

    private static final String TAG = "RetrofitTestActivity";

    @BindView(R.id.bt_get_data1)
    Button btGetData;
    @BindView(R.id.tv_show_data)
    TextView tvShowData;
    @BindView(R.id.bt_get_data2)
    Button btGetData2;
    private Api api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_test);
        ButterKnife.bind(this);

        tvShowData.setMovementMethod(new ScrollingMovementMethod());
        api = SampleRetrofit.getRetrofit().create(Api.class);
    }

    @OnClick({R.id.bt_get_data1, R.id.bt_get_data2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_get_data1:
                getJsonData();
                break;
            case R.id.bt_get_data2:
                getJsonDataWithQuery();
                break;
        }
    }

    /**
     * 无参数的GET请求
     */
    private void getJsonData() {
        Call<Data<List<ChapterBean>>> chaptersCall = api.getChapters();
        chaptersCall.enqueue(new Callback<Data<List<ChapterBean>>>() {
            @Override
            public void onResponse(Call<Data<List<ChapterBean>>> call, Response<Data<List<ChapterBean>>> response) {
                Data<List<ChapterBean>> data = response.body();
                int code = response.code();
                Log.i(TAG, "code: " + code);
                if (data.getData() == null) return;
                if (data.getErrorCode() != 0) {
                    Log.i(TAG, "ErrorCode：" + data.getErrorCode() + "\nErrorMsg：" + data.getErrorMsg());
                    return;
                }
                List<ChapterBean> chapterBeans = data.getData();
                tvShowData.setText(chapterBeans.toString());
            }

            @Override
            public void onFailure(Call<Data<List<ChapterBean>>> call, Throwable throwable) {
                tvShowData.setText(throwable.getMessage());
            }
        });
    }

    /**
     * 使用query注解的GET请求
     */
    private void getJsonDataWithQuery() {
        Call<Data<ArticlesData>> dataCall = api.getQueryArticles("鸿洋");
        dataCall.enqueue(new Callback<Data<ArticlesData>>() {
            @Override
            public void onResponse(Call<Data<ArticlesData>> call, Response<Data<ArticlesData>> response) {
                int code = response.code();
                if (code == 200) {
                    if (response.body() != null) {
                        Data<ArticlesData> dataData = response.body();
                        List<ArticleBean> datas = dataData.getData().getDatas();
                        tvShowData.setText(datas.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<Data<ArticlesData>> call, Throwable throwable) {
                tvShowData.setText(throwable.getMessage());
            }
        });
    }
}
