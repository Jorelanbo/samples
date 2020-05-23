package com.js.sample.servicetest.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.js.sample.R;
import com.js.sample.servicetest.service.NormalService;


public class NormalServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "NormalServiceActivity";

    private Button btnStartService;
    private Button btnStopService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_service);
        initView();
        btnStartService.setOnClickListener(this);
        btnStopService.setOnClickListener(this);
    }

    private void initView() {
        btnStartService = findViewById(R.id.btn_start_service);
        btnStopService = findViewById(R.id.btn_stop_service);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_service:
                Intent NormalServiceIntent = new Intent(NormalServiceActivity.this, NormalService.class);
                startService(NormalServiceIntent);
                break;
            case R.id.btn_stop_service:

                break;
            default:
                break;
        }
    }
}
