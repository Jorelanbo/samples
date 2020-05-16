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

public class SingleCheckAdapter extends RecyclerView.Adapter<SingleCheckAdapter.ViewHolder> {

    private Context mContext;
    private List<CheckItem> items;

    private int checkedIndex = -1;

    public SingleCheckAdapter(Context context, List<CheckItem> items) {
        this.mContext = context;
        this.items = items;
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
        viewHolder.mCheckBox.setOnClickListener(v -> {
            changeStatus(i);
        });
        viewHolder.mCheckBox.setChecked(item.isChecked());
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    private void changeStatus(int position) {
        CheckItem item = items.get(position);
        if (checkedIndex == -1) {
            // 说明在此之前没有选中项，此时直接把选中项设为当前项
            item.setChecked(true);
            checkedIndex = position;
        } else if (checkedIndex == position){
            // 说明此前说中项即为当前项，此时将当前项的选中项标签设为false，选中位置设为-1
            item.setChecked(false);
            checkedIndex = -1;
        } else {
            // 说明选中了出之前选中项的其它项，此时将选中项设置为新的选择项
            item.setChecked(true);
            items.get(checkedIndex).setChecked(false);
            checkedIndex = position;
        }
        // 更新列表
        notifyDataSetChanged();
    }

    public int getCheckedIndex() {
        return checkedIndex;
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
