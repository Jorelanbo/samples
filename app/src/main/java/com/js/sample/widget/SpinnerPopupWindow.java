package com.js.sample.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.js.sample.R;
import com.js.sample.adapter.SpinnerPopAdapter;

import java.util.List;


// Created by JS on 2019/5/7.

public class SpinnerPopupWindow extends PopupWindow {

    private Activity context;

    private View contentView;
    private ListView listView;
    private TextView popTitle;
    private TextView popCancel;


    public SpinnerPopupWindow(Activity context, String item, List<String> itemList,int width, int height, AdapterView.OnItemClickListener itemClickListener) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        contentView = inflater.inflate(R.layout.layout_popuwindow_spinner, null);
        this.setContentView(contentView);
        this.setWidth(width);
        this.setHeight(height);
        this.setFocusable(true); // 设置库窗体可点击
        this.update(); // 刷新状态
        this.setOutsideTouchable(false);
        ColorDrawable dw = new ColorDrawable(0x000000); // 实例化一个ColorDrawable颜色为半透明
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
//                darkenBackground(1f);
            }
        });
        // 解决软键盘挡住弹窗的问题
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.MyPopupWindow_anim_style);
        SpinnerPopAdapter adapter = new SpinnerPopAdapter(context, itemList);
        listView = (ListView) contentView.findViewById(R.id.pop_listView);
        listView.setOnItemClickListener(itemClickListener);
        listView.setAdapter(adapter);
        // setAdapter 是异步进行的，为了使弹窗能及时刷新，所以使用post + Runnable
        listView.post(new Runnable() {
            @Override
            public void run() {
                for (int j = 0; j < itemList.size(); j ++) {
                    if (item.equals(itemList.get(j).toString())) {
                        listView.setItemChecked(j, true);
                    } else {
                        listView.setItemChecked(j, false);
                    }
                }
            }
        });

        popTitle = (TextView) contentView.findViewById(R.id.pop_title);
        popCancel = (TextView) contentView.findViewById(R.id.pop_cancel);

        popCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
//                darkenBackground(1f);
            }
        });
    }

    // 给下拉列表设置标题
    public void setTitle(String title) {
        popTitle.setText(title);
    }

    // 获取选中列表中的数据对应的position
    public int getCheckedItemPosition() {
        return listView.getCheckedItemPosition();
    }

    /**
     * 显示popupWindow
     *
     * @param parent 显示的组件
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupWindow
            this.showAsDropDown(parent, 0, 10);
//            this.showAtLocation(parent, Gravity.BOTTOM|Gravity.CENTER, 0, 0);
//            darkenBackground(0.9f);//弹出时让页面背景回复给原来的颜色降低透明度，让背景看起来变成灰色
        }
    }

    public void dismissPopupWindow() {
        this.dismiss();
//        darkenBackground(1f);//关闭时让页面背景回复为原来的颜色
    }

    /**
     * 改变背景颜色，主要是在PopupWindow弹出时背景变化，通过透明度设置
     * @param bgColor 背景颜色
     */
    private void darkenBackground(float bgColor) {
        WindowManager.LayoutParams layoutParams = context.getWindow().getAttributes();
        layoutParams.alpha = bgColor;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(layoutParams);
    }
}
