package com.js.sample.commonviewpager.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.js.sample.R;
import com.js.sample.commonviewpager.adapter.CommonViewPagerAdapter;
import com.js.sample.commonviewpager.holder.ViewPagerHolderCreator;
import com.zhouwei.indicatorview.CircleIndicatorView;

import java.util.List;


// Created by JS on 2020/5/12.

public class CommonViewPager<T> extends RelativeLayout {

    private ViewPager mViewPager;
    private CommonViewPagerAdapter mAdapter;
    private CircleIndicatorView mCircleIndicatorView;

    public CommonViewPager(Context context) {
        super(context);
        init();
    }

    public CommonViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommonViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CommonViewPager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_common_view_pager, this, true);
        mViewPager = view.findViewById(R.id.common_view_pager);
        mCircleIndicatorView = view.findViewById(R.id.common_view_pager_indicator_view);
    }

    /**
     * 设置数据
     * @param data
     * @param creator
     */
    public void setPages(List<T> data, ViewPagerHolderCreator creator) {
        mAdapter = new CommonViewPagerAdapter(data, creator);
        mViewPager.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        mCircleIndicatorView.setUpWithViewPager(mViewPager);
    }

    public void setCurrentItem(int CurrentItem) {
        mViewPager.setCurrentItem(CurrentItem);
    }

    public int getCurrentItem() {
        return mViewPager.getCurrentItem();
    }

    public void setOffScreenPageLimit(int limit) {
        mViewPager.setOffscreenPageLimit(limit);
    }

    /**
     * 设置切换动画，see {@link ViewPager#setPageTransformer(boolean, ViewPager.PageTransformer)}
     * @param reverseDrawingOrder
     * @param transformer
     */
    public void setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer) {
        mViewPager.setPageTransformer(reverseDrawingOrder, transformer);
    }

    /**
     * see {@link ViewPager#setPageTransformer(boolean, ViewPager.PageTransformer)}
     * @param reverseDrawingOrder
     * @param transformer
     * @param pageLayerType
     */
    public void setPageTransformer(boolean reverseDrawingOrder, ViewPager.PageTransformer transformer, int pageLayerType) {
        mViewPager.setPageTransformer(reverseDrawingOrder, transformer, pageLayerType);
    }

    /**
     * see {@link ViewPager#addOnPageChangeListener(ViewPager.OnPageChangeListener)}
     * @param listener
     */
    public void addPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mViewPager.addOnPageChangeListener(listener);
    }

    /**
     * 设置是否显示Indicator
     * @param visible
     */
    public void setIndicatorVisible(boolean visible) {
        if (visible) {
            mCircleIndicatorView.setVisibility(VISIBLE);
        } else {
            mCircleIndicatorView.setVisibility(GONE);
        }
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }
}
