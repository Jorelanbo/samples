package com.js.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.js.sample.widget.SpinnerPopupWindow;

import java.util.ArrayList;
import java.util.List;


// Created by JS on 2019/5/7.

public class PopDropdownActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PopDropdownActivity";
    private List<String> testData;
    private Button buttonSelect;
    private SpinnerPopupWindow mSpinnerPopupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_dropdown);
        initData();
        buttonSelect = findViewById(R.id.button_select);
        buttonSelect.setOnClickListener(this);
    }

    private void initData() {
        testData = new ArrayList<>();
        for (int i = 0; i < 10; i ++) {
            String data = new String("数据" + i);
            testData.add(data);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_select:
                Log.i(TAG, "clicle");
                String item = buttonSelect.getText().toString();
                mSpinnerPopupWindow = new SpinnerPopupWindow(this, item, testData, 600, ViewGroup.LayoutParams.WRAP_CONTENT, onItemClickListener);
                mSpinnerPopupWindow.showPopupWindow(buttonSelect);
                mSpinnerPopupWindow.setTitle("类型");
                break;
            default:
                break;
        }
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String value = testData.get(mSpinnerPopupWindow.getCheckedItemPosition());
            buttonSelect.setText(value);
            mSpinnerPopupWindow.dismissPopupWindow();
        }
    };
}
