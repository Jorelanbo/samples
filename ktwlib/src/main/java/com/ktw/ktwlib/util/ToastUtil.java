package com.ktw.ktwlib.util;

// Created by JS on 2020/4/18.

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ktw.ktwlib.R;


public class ToastUtil {

    /**
     * 弹出toast提示信息
     *
     * @param activity 弹出的页面
     * @param message 要弹出的信息
     */
    public static void showToast(Activity activity, String message) {
        Toast toast = new Toast(activity);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LayoutInflater inflater = activity.getLayoutInflater();
        LinearLayout toastLayout = (LinearLayout) inflater.inflate(R.layout.toast_layout, null);
        TextView toastText = toastLayout.findViewById(R.id.toast_text);
        toastText.setText(message);
        toast.setView(toastLayout);
        toast.show();
    }

    /**
     * 在UI线程中弹出toast提示信息
     *
     * @param activity 要弹出来的页面
     * @param message 要弹出的信息
     */
    public static void showOnUiThreadToast(final Activity activity, final String message) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast(activity, message);
            }
        });
    }
}
