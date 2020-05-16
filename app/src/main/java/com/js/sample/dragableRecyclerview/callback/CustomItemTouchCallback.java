package com.js.sample.dragableRecyclerview.callback;



import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.js.sample.commonviewpager.interfaces.ItemTouchStatus;

public class CustomItemTouchCallback extends ItemTouchHelper.Callback {

    private final ItemTouchStatus mItemTouchStatus;

    public CustomItemTouchCallback(ItemTouchStatus mItemTouchStatus) {
        this.mItemTouchStatus = mItemTouchStatus;
    }


    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        // 上下拖动
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        // 左右滑动
        int swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return mItemTouchStatus.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        mItemTouchStatus.onItemRemove(viewHolder.getAdapterPosition());
    }
}
