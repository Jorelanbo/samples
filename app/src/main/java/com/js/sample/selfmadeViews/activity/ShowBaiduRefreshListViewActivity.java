package com.js.sample.selfmadeViews.activity;

// Created by JS on 2021/2/23.

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.js.sample.R;
import com.ktw.ktwlib.util.NavigationBarUtil;
import com.ktw.ktwlib.util.StatusBarUtil;
import com.ktw.ktwlib.widget.BaiDuRefreshListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShowBaiduRefreshListViewActivity extends AppCompatActivity implements BaiDuRefreshListView.OnBaiduRefreshListener {

    private BaiDuRefreshListView mListView;
    private List<String> mDatas;
    private ArrayAdapter<String> mAdapter;
    private final static int REFRESH_COMPLETE = 0;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    mListView.setOnRefreshComplete();
                    mAdapter.notifyDataSetChanged();
                    mListView.setSelection(0);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_baidu_refresh_listview);
        StatusBarUtil.setStatusBarColor(this, R.color.black);
        NavigationBarUtil.setSysNavigationColor(this, Color.parseColor("#000000"));
        initView();
    }

    private void initView() {
        mListView = findViewById(R.id.lv);
        String[] data = new String[]{"a","b","c","d",
                "e","f","g","h","i",
                "j","k","l","m","n","o","p","q","r","s"};
        mDatas = new ArrayList<String>(Arrays.asList(data));
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,mDatas);
        mListView.setAdapter(mAdapter);
        mListView.setOnBaiduRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    mDatas.add(0, "new data");
                    mHandler.sendEmptyMessage(REFRESH_COMPLETE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
