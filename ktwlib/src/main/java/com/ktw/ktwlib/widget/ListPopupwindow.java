package com.ktw.ktwlib.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.ktw.ktwlib.R;


/**
 * Created by viroyal002 on 2016/7/22.
 * 下拉菜单
 */
public class ListPopupwindow {
    private PopupWindow mListPopupwindow;
    private ListView mListView;

    /**
     * 初始化下拉菜单
     *
     * @param context
     * @param width   popupwindow的宽
     * @param height  popupwindow的高
     */
    public void initPopWindow(Context context, int width, int height) {
        View view = LayoutInflater.from(context).inflate(R.layout.popup_list, null, false);
        mListView = (ListView) view.findViewById(R.id.popup_item);
        mListPopupwindow = new PopupWindow(view, width, height);
        mListPopupwindow.setBackgroundDrawable(new BitmapDrawable());
        mListPopupwindow.setFocusable(true);
        mListPopupwindow.setOutsideTouchable(true);

        //解决popupwindow上button的焦点问题
        mListPopupwindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    mListPopupwindow.dismiss();
                    return true;
                }
                return false;
            }
        });
    }

    //获取popupwindow对象
    public PopupWindow getListPopupwindow() {
        return mListPopupwindow;
    }

    //设置popupwindow的onDismiss事件
    public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
        if (listener != null) {
            mListPopupwindow.setOnDismissListener(listener);
        }
    }

    //设置popupwindow的位置(指定view的下方)
    public void showAsDropDown(View view) {
        mListPopupwindow.showAsDropDown(view);
    }

    //隐藏popupwindow
    public void disMissPopup() {
        if (mListPopupwindow.isShowing()) {
            mListPopupwindow.dismiss();
        }
    }

    public boolean isShowing() {
        return mListPopupwindow.isShowing();
    }

    //获取popupwindow的listview对象
    public ListView getListView() {
        return mListView;
    }

    //为listview设置adapter
    public void setAdapter(BaseAdapter adapter) {
        if (adapter != null) {
            mListView.setAdapter(adapter);
        }
    }

    //设置listview的子项点击事件
    public void setOnListItemClickListener(AdapterView.OnItemClickListener listener) {
        if (listener != null) {
            mListView.setOnItemClickListener(listener);
        }
    }
}
