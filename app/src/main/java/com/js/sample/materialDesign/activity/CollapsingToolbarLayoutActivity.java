package com.js.sample.materialDesign.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.js.sample.R;


// Created by JS on 2020/1/13.

public class CollapsingToolbarLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collapsingtoolbar_layout);
        initActionBar();
        initCollapsingToolbar();
        initFloatingActionButton();
    }

    private void initActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initCollapsingToolbar() {
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.toolbar_layout);
        collapsingToolbar.setTitle("杨幂");
    }

    private void initFloatingActionButton() {
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(v ->
            Snackbar.make(v, "正在打开邮箱...", Snackbar.LENGTH_LONG)
                    .setAction("好的", v1 ->
                        Toast.makeText(this, "好的", Toast.LENGTH_SHORT).show()
                    )
                    .setActionTextColor(getResources().getColor(R.color.orange))
                    .show()
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_more:
                Toast.makeText(this, "你点击了“更多”", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_setting:
                Toast.makeText(this, "你点击了“设置”", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
