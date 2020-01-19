package com.js.sample.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.js.sample.R;


// Created by JS on 2020/1/19.

public class ViewAnimatorActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "ViewAnimatorActivity";

    private ViewAnimator va;
    private float startX;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animator);
        va = findViewById(R.id.va);
        Button btnOne = findViewById(R.id.btn_one);
        Button btnTwo = findViewById(R.id.btn_two);
        Button btnThree = findViewById(R.id.btn_three);
        btnOne.setOnClickListener(this);
        btnTwo.setOnClickListener(this);
        btnThree.setOnClickListener(this);
        va.setDisplayedChild(1);
        Log.i(TAG, "子孩子数：" + va.getChildCount());
        va.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = motionEvent.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        float length = motionEvent.getX() - startX;
                        int rightSwapeMaxIndex = va.getChildCount() - 1;
                        if ((length > 50) && (va.getDisplayedChild() > 0)) {
                            va.showPrevious();
                        } else if ((length < -50) && (va.getDisplayedChild() < rightSwapeMaxIndex)) {
                            va.showNext();
                        }
                        break;
                    default:
                        view.performClick();
                }
                return true;
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_one:
                va.setDisplayedChild(0);
                break;
            case R.id.btn_two:
                va.setDisplayedChild(1);
                break;
            case R.id.btn_three:
                va.setDisplayedChild(2);
                break;
        }
        Log.i(TAG, "当前显示的子控件: " + va.getDisplayedChild());
    }

    public void clickText(View view) {
        Toast.makeText(this, "第一页", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
