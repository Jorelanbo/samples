package com.js.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.js.sample.utils.Util;


// Created by JS on 2019/6/3.

public class SingleClickTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_click_test);
        Button testButton = findViewById(R.id.btn_test);
        testButton.setOnClickListener(new CustomOnClickListener() {
            @Override
            protected void onSingleClick() {
                Util.showToast(SingleClickTestActivity.this, "单次点击！");
            }

            @Override
            protected void onFastClick() {
                Util.showToast(SingleClickTestActivity.this, "快速点击！");
            }
        });
    }
}
