package com.js.sample.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.js.sample.R;
import com.js.sample.db.GreenDaoService;
import com.js.sample.entity.User;

import java.util.Date;


// Created by JS on 2019/1/28.

public class GreenDaoActivity extends AppCompatActivity {

    private static final String TAG = "GreenDaoActivity";
    private GreenDaoService greenDaoService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greendao);
        greenDaoService = GreenDaoService.getInstance(this);
    }

    /**
     * 增加条目
     * @param view 被点击控件
     */
    public void clickInsert(View view) {
        Date date = new Date();
        String num = "10100" + date.toString();
        Log.i(TAG, num);
        User user = new User(null, num, "JS");
        greenDaoService.insertUser(user);
    }

    /**
     * 删除条目
     * @param view 被点击控件
     */
    public void clickDelete(View view) {
        greenDaoService.deleteUser();
    }

    /**
     * 更新条目
     * @param view 被点击控件
     */
    public void clickUpdate(View view) {
        greenDaoService.updateUser();
    }

    /**
     * 查找条目
     * @param view 被点击控件
     */
    public void clickQuery(View view) {
        User user = greenDaoService.queryUser();
        Toast.makeText(this, user.toString(), Toast.LENGTH_SHORT).show();
    }
}
