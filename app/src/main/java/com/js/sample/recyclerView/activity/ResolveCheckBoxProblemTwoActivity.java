package com.js.sample.recyclerView.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.js.sample.R;
import com.js.sample.recyclerView.adapters.ResolveTwoAdapter;
import com.js.sample.recyclerView.bean.CheckItem;

import java.util.ArrayList;
import java.util.List;


// Created by JS on 2020/4/11.

public class ResolveCheckBoxProblemTwoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "CheckBoxProblemTwo";

    private RecyclerView rvResolveTwo;
    private Button btShowItems;
    private Button btSelectAll;
    private Button btUnSelectAll;
    private List<CheckItem> items = new ArrayList<>();
    private ResolveTwoAdapter resolveTwoAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resolve_checkbox_problem_two);
        initView();
        initData();
    }

    private void initView() {
        btShowItems = findViewById(R.id.bt_show_items_two);
        btSelectAll = findViewById(R.id.bt_select_all);
        btUnSelectAll = findViewById(R.id.bt_unselect_all);
        rvResolveTwo = findViewById(R.id.rv_resolve_two);
        btShowItems.setOnClickListener(this);
        btSelectAll.setOnClickListener(this);
        btUnSelectAll.setOnClickListener(this);
    }

    private void initData() {
        for (int i = 0; i < 50; i ++) {
            CheckItem checkItem = new CheckItem("条目 " + i, false);
            items.add(checkItem);
        }
        rvResolveTwo.setLayoutManager(new LinearLayoutManager(this));
        resolveTwoAdapter = new ResolveTwoAdapter(this, items);
        rvResolveTwo.setAdapter(resolveTwoAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_show_items_two:
                Log.i(TAG, items.toString());
                break;
            case R.id.bt_select_all:
                resolveTwoAdapter.selectAll();
                break;
            case R.id.bt_unselect_all:
                resolveTwoAdapter.unSelectAll();
                break;
            default:
                break;
        }
    }
}
