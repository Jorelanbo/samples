package com.js.sample.activity.materialDesign;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.js.sample.R;


// Created by JS on 2020/1/15.

public class TextInputLayoutSampleActivity extends AppCompatActivity {

    private TextInputEditText tieName;
    private TextInputLayout tilName;
    private TextInputLayout tilPsw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_input_layout);
        tilName = findViewById(R.id.til_name);
        tieName = findViewById(R.id.tie_name);
        tilPsw = findViewById(R.id.til_psw);
        TextInputEditText tiePsw = findViewById(R.id.tie_psw);

        tilName.setErrorEnabled(true);
        tilPsw.setErrorEnabled(true);
        
        initListener();
    }

    private void initListener() {
        tieName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                checkName(s.toString());
            }
        });
    }

    private void checkName(String name) {
        if (name.length() > 6) {
            tilName.setError("名字长度不能超过6");
        } else {
            tilName.setError(null);
        }
    }

    public void login(View view) {
        String password = tilPsw.getEditText().getText().toString();
        if (TextUtils.isEmpty(password) && password.length() < 6) {
            tilPsw.setError("密码长度太短！");
        } else {
            tilPsw.setError(null);
        }
    }
}
