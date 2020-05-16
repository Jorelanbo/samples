package com.js.sample.recyclerView.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.js.sample.R;
import com.js.sample.recyclerView.bean.CheckItem;

import java.util.List;


// Created by JS on 2020/4/13.

public class ResolveTwoAdapter extends RecyclerView.Adapter<ResolveTwoAdapter.ViewHolder> {

    private Context mContext;
    private List<CheckItem> items;

    public ResolveTwoAdapter(Context context, List<CheckItem> items) {
        this.mContext = context;
        this.items = items;
    }

    // 全选
    public void selectAll() {
        initCheck(true);
        notifyDataSetChanged();
    }

    // 全不选
    public void unSelectAll() {
        initCheck(false);
        notifyDataSetChanged();
    }

    // 更改集合内部存储状态
    private void initCheck(boolean flag) {
        for (int i = 0; i < items.size(); i ++) {
            items.get(i).setChecked(flag);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rv, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        CheckItem item = items.get(i);
        viewHolder.mTextView.setText(item.getName());
        // 清除监听器
        viewHolder.mCheckBox.setOnCheckedChangeListener(null);
        // 设置选中状态
        viewHolder.mCheckBox.setChecked(item.isChecked());
        // 重新设置选中监听器，当状态变化时更新选中状态
        viewHolder.mCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> item.setChecked(isChecked));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox mCheckBox;
        TextView mTextView;

        ViewHolder(View itemView) {
            super(itemView);
            mCheckBox = itemView.findViewById(R.id.cb_check);
            mTextView = itemView.findViewById(R.id.tv_name);
        }
    }
}
