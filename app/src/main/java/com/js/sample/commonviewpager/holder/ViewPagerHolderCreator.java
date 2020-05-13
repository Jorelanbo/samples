package com.js.sample.commonviewpager.holder;

// Created by JS on 2020/5/12.

public interface ViewPagerHolderCreator<VH extends ViewPagerHolder> {

    /**
     *  创建viewHolder
     * @return
     */
    public VH createViewHolder();
}
