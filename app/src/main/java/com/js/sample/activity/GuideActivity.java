package com.js.sample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.js.sample.R;
import com.js.sample.arcgis.GraphicsOverlayActivity;
import com.js.sample.bottomMenu.activity.BottomMenuActivity;
import com.js.sample.commonviewpager.CommonViewPagerActivity;
import com.js.sample.downloadFile.DownloadFileActivity;
import com.js.sample.dragableRecyclerview.activity.DraggableRecyclerviewActivity;
import com.js.sample.eventBusDemo.EventBusTestActivity;
import com.js.sample.greendaodemo.activity.GreenDaoActivity;
import com.js.sample.materialDesign.activity.MaterialDesignActivity;
import com.js.sample.notifycation.NotificationTestActivity;
import com.js.sample.popDropdown.PopDropdownActivity;
import com.js.sample.progressBar.activity.TestProgressBarActivity;
import com.js.sample.recyclerView.activity.UseRecyclerViewProblemsActivity;
import com.js.sample.retrofit.RetrofitTestActivity;
import com.js.sample.rxjava.RxJavaTestActivity;
import com.js.sample.selfmadeViews.activity.SelfMadeViewsActivity;
import com.js.sample.servicetest.activity.ServiceTestActivity;
import com.js.sample.singleClick.activity.SingleClickTestActivity;
import com.js.sample.splash.activity.SplashActivity;
import com.js.sample.takePhoto.TakePhotoActivity;
import com.js.sample.views.fish.activity.TestFishActivity;
import com.js.sample.views.load.LoadingActivity;

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
        Intent intent = new Intent(this, GraphicsOverlayActivity.class);
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

    /**
     * 使用ConstraintLayout
     * @param view view
     */
    public void clickConstraintLayoutActivity(View view) {
        Intent intent = new Intent(this, ConstraintLayoutActivity.class);
        startActivity(intent);
    }

    /**
     * 使用MaterialDesign
     * @param view view
     */
    public void clickMaterialDesigntActivity(View view) {
        Intent intent = new Intent(this, MaterialDesignActivity.class);
        startActivity(intent);
    }

    /**
     * 使用ViewAnimator做页面切换
     * @param view view
     */
    public void clickViewAnimatorActivity(View view) {
        Intent intent = new Intent(this, ViewAnimatorActivity.class);
        startActivity(intent);
    }

    /**
     * Recyclerview的几个用法
     * @param view view
     */
    public void clickRecyclerViewProblem(View view) {
        Intent intent = new Intent(this, UseRecyclerViewProblemsActivity.class);
        startActivity(intent);
    }

    /**
     * 带有权限控制的加载页面
     * @param view view
     */
    public void clickPermissionSplashActivity(View view) {
        Intent intent = new Intent(this, PermissionSplashActivity.class);
        startActivity(intent);
    }

    /**
     * 使用自定义的CommonViewPager
     * @param view view
     */
    public void clickCommonViewPagerActivity(View view) {
        Intent intent = new Intent(this, CommonViewPagerActivity.class);
        startActivity(intent);
    }

    /**
     * 查看设备的多有存储路径
     * @param view view
     */
    public void clickCheckStoragePathActivity(View view) {
        Intent intent = new Intent(this, CheckStoragePathActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转通知测试使用页面
     * @param view view
     */
    public void clickNotificationTestActivity(View view) {
        Intent intent = new Intent(this, NotificationTestActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转自定义控件使用页面
     * @param view view
     */
    public void clickSelfMadeViewsActivity(View view) {
        Intent intent = new Intent(this, SelfMadeViewsActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转EventBus使用页面
     * @param view view
     */
    public void clickEventBusTestDemo(View view) {
        Intent intent = new Intent(this, EventBusTestActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转到Retrofit使用页面
     * @param view view
     */
    public void clickRetrofitDemo(View view) {
        Intent intent = new Intent(this, RetrofitTestActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转到RxJava使用页面
     * @param view view
     */
    public void clickRxJavaDemo(View view) {
        Intent intent = new Intent(this, RxJavaTestActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转到文件下载页面
     * @param view view
     */
    public void clickDownloadFileDemo(View view) {
        Intent intent = new Intent(this, DownloadFileActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转到拍照页面
     * @param view
     */
    public void clickTakePhotoDemo(View view) {
        startActivity(new Intent(this, TakePhotoActivity.class));
    }

    /**
     * 跳转自定义小鱼页面
     * @param view
     */
    public void clickFishView(View view) {
        startActivity(new Intent(this, TestFishActivity.class));
    }

    /**
     * 跳转自定义进度条样式页面
     * @param view
     */
    public void clickTestProgressBar(View view) {
        startActivity(new Intent(this, TestProgressBarActivity.class));
    }

    /**
     * 跳转加载动画展示页面
     * @param view
     */
    public void clickLoadingView(View view) {
        startActivity(new Intent(this, LoadingActivity.class));
    }
}
