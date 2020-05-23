package com.js.sample.selfmadeViews.views;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.js.sample.R;


// Created by JS on 2020/5/21.

public class TitleView extends FrameLayout {

    private final TextView titleText;
    private final Button buttonLeft;

    public TitleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_title_view, this);
        titleText = findViewById(R.id.title_text);
        buttonLeft = findViewById(R.id.button_left);
        buttonLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });
    }

    public void setTitleText(String text) {
        titleText.setText(text);
    }

    public void setButtonLeftText(String text) {
        buttonLeft.setText(text);
    }

    public void setButtonLeftListener(OnClickListener l) {
        buttonLeft.setOnClickListener(l);
    }
}
