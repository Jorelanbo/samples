package com.js.sample.activity.recyclerView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.js.sample.R;
import com.js.sample.activity.recyclerView.adapters.SingleCheckAdapter;
import com.js.sample.activity.recyclerView.bean.CheckItem;

import java.util.ArrayList;
import java.util.List;


// Created by JS on 2020/4/11.

public class CheckBoxSingleCheckActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "CheckBoxSingleCheck";

    private RecyclerView rvSingleCheck;
    private Button btShowCheckedItem;
    private List<CheckItem> items = new ArrayList<>();
    private SingleCheckAdapter singleCheckAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkbox_single_check);
        initView();
        initData();
    }

    private void initView() {
        btShowCheckedItem = findViewById(R.id.bt_show_checked_item);
        rvSingleCheck = findViewById(R.id.rv_single_check);
        btShowCheckedItem.setOnClickListener(this);
    }

    private void initData() {
        for (int i = 0; i < 50; i++) {
            CheckItem checkItem = new CheckItem("条目 " + i, false);
            items.add(checkItem);
        }
        rvSingleCheck.setLayoutManager(new LinearLayoutManager(this));
        singleCheckAdapter = new SingleCheckAdapter(this, items);
        rvSingleCheck.setAdapter(singleCheckAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_show_checked_item:
                int checkedIndex = singleCheckAdapter.getCheckedIndex();
                if (checkedIndex < 0) {
                    Toast.makeText(this, "当前无选中项", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, items.get(checkedIndex).getName(), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
