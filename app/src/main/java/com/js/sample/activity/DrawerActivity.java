package com.js.sample.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.js.sample.R;


// Created by JS on 2018/12/21.

// demo from https://blog.csdn.net/qq_20964593/article/details/51335497

public class DrawerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        initView();
    }

    private void initView() {

    }
}
