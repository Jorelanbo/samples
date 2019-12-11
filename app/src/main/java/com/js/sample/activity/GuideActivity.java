package com.js.sample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.js.sample.R;

public class GuideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
    }

    /**
     *  主页面
     * @param view view
     */
    public void clickMain(View view) {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * activity 生命周期
     * @param view view
     */
    public void clickActivityLifeCycle(View view) {
        Intent intent = new Intent(this, LifeCycleActivity.class);
        startActivity(intent);
    }

    /**
     * fragment 生命周期
     * @param view view
     */
    public void clickFragmentLifeCycle(View view) {
        Intent intent = new Intent(this, FragmentLifeCycleActivity.class);
        startActivity(intent);
    }

    /**
     *
     *  窗口模式的activity
     * @param view view
     */
    public void clickDialogActivity(View view) {
        Intent intent = new Intent(this, DialogActivity.class);
        startActivity(intent);
    }

    /**
     *
     * logger框架
     * @param view view
     */
    public void clickLoggerActivity(View view) {
        Intent intent = new Intent(this, LoggerActivity.class);
        startActivity(intent);
    }

    /**
     *
     * 使用AsyncTask异步类
     * @param view view
     */
    public void clickAsyncTask(View view) {
        Intent intent = new Intent(this, AsyncTaskActivity.class);
        startActivity(intent);
    }

    /**
     *
     * 使用显示抽屉布局
     * @param view view
     */
    public void clickDrawerActivity(View view) {
        Intent intent = new Intent(this, DrawerActivity.class);
        startActivity(intent);
    }

    /**
     *
     * 使用百度地图SDK
     * @param view view
     */
    public void clickBaiduLocationActivity(View view) {
        Intent intent = new Intent(this, BaiduLocationActivity.class);
        startActivity(intent);
    }

    /**
     *
     * 使用ArcGis
     * @param view view
     */
    public void clickArcGISActivity(View view) {
        Intent intent = new Intent(this, ArcGisActivity.class);
        startActivity(intent);
    }

    /**
     *
     * 使用GreenDao
     * @param view view
     */
    public void clickGreenDaoActivity(View view) {
        Intent intent = new Intent(this, GreenDaoActivity.class);
        startActivity(intent);
    }

    /**
     *
     * splash页面
     * @param view view
     */
    public void clickSplashActivity(View view) {
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
    }

    /**
     *
     * 自定义下拉框页面
     * @param view view
     */
    public void clickPopDropdownActivity(View view) {
        Intent intent = new Intent(this, PopDropdownActivity.class);
        startActivity(intent);
    }

    /**
     * 测试防止多次点击页面
     * @param view view
     */
    public void clickSingleClickTestActivity(View view) {
        Intent intent = new Intent(this, SingleClickTestActivity.class);
        startActivity(intent);
    }

    /**
     * 测试三种服务的工作方式
     * @param view view
     */
    public void clickServiceTestActivity(View view) {
        Intent intent = new Intent(this, ServiceTestActivity.class);
        startActivity(intent);
    }

    /**
     * 可拖拽的recyclerview
     * @param view view
     */
    public void clickDraggableRecyclerView(View view) {
        Intent intent = new Intent(this, DraggableRecyclerviewActivity.class);
        startActivity(intent);
    }

    /**
     * 用RadioButton实现的底部菜单
     * @param view view
     */
    public void clickBottomMenuActivity(View view) {
        Intent intent = new Intent(this, BottomMenuActivity.class);
        startActivity(intent);
    }
}
