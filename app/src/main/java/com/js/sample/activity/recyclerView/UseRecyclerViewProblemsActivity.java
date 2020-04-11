package com.js.sample.activity.recyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.js.sample.R;


// Created by JS on 2020/4/11.

public class UseRecyclerViewProblemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_recyclerview_problems);
    }

    public void clickResolveCheckProblemOne(View view) {
        Intent intent = new Intent(this, ResolveCheckBoxProblemOneActivity.class);
        startActivity(intent);
    }
}
