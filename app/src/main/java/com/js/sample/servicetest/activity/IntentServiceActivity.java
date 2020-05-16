package com.js.sample.servicetest.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.js.sample.R;
import com.js.sample.servicetest.service.HelloIntentService;


public class IntentServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnStartService;
    private Button btnStopService;
    private TextView tvStatus;
    private ProgressBar progressbar;
    private TextView tvProgress;

    public final static String ACTION_TYPE_THREAD = "action.type.thread";

    private LocalBroadcastManager mLocalBroadcastManager;
    private MyBroadcastReceiver mBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);

        // 注册广播
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_TYPE_THREAD);
        mLocalBroadcastManager.registerReceiver(mBroadcastReceiver, intentFilter);
        initView();
    }

    private void initView() {
        btnStartService = findViewById(R.id.btn_start_service);
        btnStopService = findViewById(R.id.btn_stop_service);
        tvStatus = findViewById(R.id.tv_status);
        progressbar = findViewById(R.id.progressbar);
        tvProgress = findViewById(R.id.tv_progress);

        tvStatus.setText("线程状态：未运行");
        progressbar.setMax(100);
        progressbar.setProgress(0);
        tvProgress.setText("0%");
        btnStartService.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销广播
        mLocalBroadcastManager.unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_service:
                Intent serviceIntent = new Intent(IntentServiceActivity.this, HelloIntentService.class);
                serviceIntent.putExtra("ID", "9527");
                serviceIntent.putExtra("code", "5139");
                startService(serviceIntent);
                break;
            default:
                break;
        }
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case ACTION_TYPE_THREAD:
                    int progress = intent.getIntExtra("progress", 0);
                    tvStatus.setText("线程状态：" + intent.getStringExtra("status"));
                    progressbar.setProgress(progress);
                    tvProgress.setText(progress + "%");
                    if (progress >= 100) {
                        tvStatus.setText("线程结束");
                    }
                    break;
            }
        }
    }
}
