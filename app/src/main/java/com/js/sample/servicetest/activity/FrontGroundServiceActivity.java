package com.js.sample.servicetest.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.js.sample.R;
import com.js.sample.servicetest.service.FrontGroundService;


// Created by JS on 2020/5/15.

public class FrontGroundServiceActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_ground_service);
        initView();
    }

    private void initView() {
        Button btnStartFrontService = findViewById(R.id.btn_start_front_service);
        Button btnStopFrontService = findViewById(R.id.btn_stop_front_service);
        btnStartFrontService.setOnClickListener(this);
        btnStopFrontService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_front_service:
                Intent intent = new Intent(this, FrontGroundService.class);
                startService(intent);
                break;
            case R.id.btn_stop_front_service:
                break;
            default:
                break;
        }
    }
}
