package com.js.sample.materialDesign.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.js.sample.R;
import com.ktw.ktwlib.util.DisplayUtil;


// Created by JS on 2020/1/11.

public class BottomSheetSampleActivity extends AppCompatActivity {
    private static final String TAG = "BottomSheetSample";

    private LinearLayout llBottom;
    private BottomSheetBehavior<LinearLayout> bottomSheetBehavior;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_sample);
        llBottom = findViewById(R.id.ll_bottom);
        bottomSheetBehavior = BottomSheetBehavior.from(llBottom);
        bottomSheetBehavior.setHideable(false);
        bottomSheetBehavior.setPeekHeight(DisplayUtil.dip2px(this, 80));
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                Log.i(TAG, "onStateChanged: ");
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
    }

    /**
     * 使用behavior控制bottom
     * 可设置状态：STATE_EXPANDED = 3、STATE_COLLAPSED = 4、STATE_HIDDEN = 5、STATE_HALF_EXPANDED = 6
     * @param view view
     */
    public void OpenBottomBehavior(View view) {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    /**
     * 弹出SheetDialogFragment
     * @param view view
     */
    public void OpenSheetDialogFragment(View view) {
        MyBottomSheetDialogFragment dialogFragment = new MyBottomSheetDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "MyBottomSheetDialogFragment");
    }

    /**
     * 弹出BottomSheetDialog
     * @param view view
     */
    public void OpenBottomSheetDialog(View view) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.fragment_my_bottom_sheet_dialog, null);
        TextView tvGotIt = dialogView.findViewById(R.id.tv_got_it);
        tvGotIt.setOnClickListener(v ->
            bottomSheetDialog.dismiss()
        );
        bottomSheetDialog.setContentView(dialogView);
        bottomSheetDialog.setCanceledOnTouchOutside(false);
        bottomSheetDialog.show();
    }
}
