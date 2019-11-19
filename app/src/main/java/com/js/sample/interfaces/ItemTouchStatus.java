package com.js.sample.interfaces;

// Created by JS on 2019/11/19.

public interface ItemTouchStatus {
    boolean onItemMove(int fromPosition, int toPosition);
    void onItemRemove(int position);
}
