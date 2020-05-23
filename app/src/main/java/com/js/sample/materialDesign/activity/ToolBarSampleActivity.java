package com.js.sample.materialDesign.activity;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.js.sample.R;


// Created by JS on 2020/1/15.

public class ToolBarSampleActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_sample);
        toolbar = findViewById(R.id.t_toolbar);
        setSupportActionBar(toolbar);
        setToolbarProperty();
    }

    private void setToolbarProperty() {
        // 设置正标题
        toolbar.setTitle("正标题");
        // 设置副标题
        toolbar.setSubtitle("副标题");
        // 设置标题颜色
        toolbar.setTitleTextColor(Color.WHITE);
        // 设置副标题颜色
        toolbar.setSubtitleTextColor(Color.GRAY);
        // 设置左边按钮图片
        toolbar.setNavigationIcon(R.mipmap.ic_github);
        // 设置左边按钮和标题之间的图片
        toolbar.setLogo(R.mipmap.myicon);
        // 设置左边按钮的点击回调
        toolbar.setNavigationOnClickListener(
                v -> Toast.makeText(this, "点击了Navigation按钮", Toast.LENGTH_SHORT).show());
        // 设置toolbar菜单项的点击回调(这是其中的一种方法，还有一种就是重写activity的onOptionsItemSelected方法)
//        toolbar.setOnMenuItemClickListener(menuItem -> false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 使用代码创建menu
        menu.add(0, 0, 0, "搜索")
                .setIcon(R.mipmap.icon_search)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        // 使用加载xml文件的方式创建
        getMenuInflater().inflate(R.menu.menu_test, menu);

        // 添加子菜单
        menu.addSubMenu(0, 1, 0, "多级菜单项")
                .setIcon(R.mipmap.icon_more)
                .addSubMenu(0, 2, 0, "子菜单项");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Toast.makeText(this, "点击了搜索", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(this, "点击了多级菜单项", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, "点击了子菜单项", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                Toast.makeText(this, "点击了设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_share:
                Toast.makeText(this, "点击了分享", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_edit:
                Toast.makeText(this, "点击了编辑", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
