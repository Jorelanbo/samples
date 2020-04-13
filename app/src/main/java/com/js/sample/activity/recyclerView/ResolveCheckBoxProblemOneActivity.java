package com.js.sample.activity.recyclerView;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
    private ResolveOneAdapter resolveOneAdapter;

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
        resolveOneAdapter = new ResolveOneAdapter(this, items);
        rvResolveOne.setAdapter(resolveOneAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_show_items:
                List<CheckItem> checkedItems = resolveOneAdapter.getCheckedItems();
                Toast toast = Toast.makeText(this, "", Toast.LENGTH_LONG);
                StringBuilder message = new StringBuilder();
                if (checkedItems.size() > 0) {
                    message.append("选中项为：");
                    for (int i = 0; i < checkedItems.size(); i ++) {
                        CheckItem checkItem = checkedItems.get(i);
                        message.append("\n").append(checkItem.getName());
                    }
                } else {
                    message.append("当前没有选中任何项！");
                }
                // 先给toast的message消息设为空，然后设置要提示的信息，以此解决小米手机toast自带app名称的bug
                toast.setText(message.toString());
                toast.show();
                break;
            default:
                break;
        }
    }
}
