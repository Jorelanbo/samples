package com.js.sample.progressBar.activity;

// Created by JS on 2020/11/5.

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.js.sample.R;
import com.ktw.ktwlib.widget.FiveNodesToggleButton;
import com.ktw.ktwlib.widget.SeekBar;
import com.ktw.ktwlib.widget.SlideButtonView;

public class TestProgressBarActivity extends AppCompatActivity {

    private FiveNodesToggleButton fiveNodesToggleButton;
    private SlideButtonView slideView;
    private SeekBar seekBar;
    private TextView tvProgress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_progress_bar);
        fiveNodesToggleButton = findViewById(R.id.fiveNodesToggleButton);
        fiveNodesToggleButton.setNodesChangeListener(new FiveNodesToggleButton.NodesChangeListener() {
            @Override
            public void onNodesChange(int currentNode) {
                Toast.makeText(TestProgressBarActivity.this, "节点：" + currentNode, Toast.LENGTH_SHORT).show();
            }
        });

        seekBar = findViewById(R.id.seekBar);
        tvProgress = findViewById(R.id.tv_progress);
        seekBar.setProgressChangedListener(progress -> {
            String progressText = progress * 100 + "%";
            tvProgress.setText(progressText);
        });

        slideView = findViewById(R.id.slideView);
        slideView.setOnSlideFinishListener(() -> {
            slideView.setState(SlideButtonView.STATE_ACTIVATING);
            slideView.postDelayed(() -> slideView.setState(SlideButtonView.STATE_ACTIVATE_SUCCESS), 1000);
            slideView.postDelayed(() -> slideView.setState(SlideButtonView.STATE_UN_ACTIVATE), 2000);
        });
    }
}
