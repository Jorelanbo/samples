package com.js.sample.activity.recyclerView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.js.sample.R;
import com.js.sample.activity.recyclerView.adapters.ResolveOneAdapter;
import com.js.sample.activity.recyclerView.bean.CheckItem;

import java.util.ArrayList;
import java.util.List;


// Created by JS on 2020/4/11.

public class ResolveCheckBoxProblemOneActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "CheckBoxProblemOne";

    private RecyclerView rvResolveOne;
    private Button btShowItems;
    private List<CheckItem> items = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resolve_checkbox_problem_one);
        initView();
        initData();
    }

    private void initView() {
        btShowItems = findViewById(R.id.bt_show_items);
        rvResolveOne = findViewById(R.id.rv_resolve_one);
        btShowItems.setOnClickListener(this);
    }

    private void initData() {
        for (int i = 0; i < 50; i ++) {
            CheckItem checkItem = new CheckItem("条目 " + i, false);
            items.add(checkItem);
        }
        rvResolveOne.setLayoutManager(new LinearLayoutManager(this));
        ResolveOneAdapter resolveOneAdapter = new ResolveOneAdapter(this, items);
        rvResolveOne.setAdapter(resolveOneAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_show_items:
                Log.i(TAG, items.toString());
                break;
            default:
                break;
        }
    }
}
