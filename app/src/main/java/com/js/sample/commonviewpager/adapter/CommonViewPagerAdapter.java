package com.js.sample.commonviewpager.adapter;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.js.sample.R;
import com.js.sample.commonviewpager.holder.ViewPagerHolder;
import com.js.sample.commonviewpager.holder.ViewPagerHolderCreator;

import java.util.List;


// Created by JS on 2020/5/12.

public class CommonViewPagerAdapter<T> extends PagerAdapter {
    private List<T> mDatas;
    private ViewPagerHolderCreator mCreator; // ViewHolder生成器
    private PageClickListener mPageClickListener;

    public CommonViewPagerAdapter(List<T> datas, ViewPagerHolderCreator creator) {
        mDatas = datas;
        mCreator = creator;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // 重点在这儿了，不再是把布局写死，而是用接口提供的布局
        // 也不在这儿绑定数据，数据绑定交给Api调用者。
        View view = getView(position, null, container);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    /**
     *  获取viewpager页面展示的view
     * @param position
     * @param view
     * @param container
     * @return
     */
    private View getView(int position, View view, ViewGroup container) {
        ViewPagerHolder holder;
        if (view == null) {
            // 创建holder
            holder = mCreator.createViewHolder();
            view = holder.createView(container.getContext());
            view.setTag(R.id.common_view_pager_item_tag, holder);
        } else {
            holder = (ViewPagerHolder) view.getTag(R.id.common_view_pager_item_tag);
        }
        if (holder != null && mDatas != null && mDatas.size() > 0) {
            // 数据绑定
            holder.onBind(container.getContext(), position, mDatas.get(position));
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPageClickListener != null) {
                    mPageClickListener.onPageClick(v, position);
                }
            }
        });
        return view;
    }

    public void setPageClickListener(PageClickListener pageClickListener) {
        mPageClickListener = pageClickListener;
    }

    /**
     * page 点击回调
     */
    public interface PageClickListener{
        void onPageClick(View view, int position);
    }
}