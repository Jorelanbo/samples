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

import java.util.ArrayList;
import java.util.List;


// Created by JS on 2020/4/11.

/*
我们选中的CheckBox在下滑以后，再回去发现并没有选中。

关于这个bug的原因其实很简单，我们都知道RecyclerView和ListView都是做到复用的，当我们Debug的时候你会发现，每当我们划动的时候，由于item被复用，checkbox的选中状态会发生改变，每当我们滑动的时候我们的setOnCheckedChangeListener的代码都会被调用。这就是真正造成我们CheckBox状态异常的元凶。

其实想要解决这个bug其实也很简单，我们只要不使用setOnCheckedChangeListener，将监听器改为setOnClickListener就能解决这个问题，
因为在setOnCheckedChangeListener中使用notifyDataSetChanged会产生渲染错误。*/

public class ResolveOneAdapter extends RecyclerView.Adapter<ResolveOneAdapter.ViewHolder> {

    private Context mContext;
    private List<CheckItem> items;

    public ResolveOneAdapter(Context context, List<CheckItem> items) {
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
        CheckItem checkItem = items.get(i);
        viewHolder.mTextView.setText(checkItem.getName());
        viewHolder.mCheckBox.setOnClickListener(v -> {
            viewHolder.mCheckBox.setChecked(!checkItem.isChecked());
            checkItem.setChecked(!checkItem.isChecked());
            notifyDataSetChanged(); // 这里可以重新刷新数据，使用setOnCheckedChangeListener则会报错
        });
        viewHolder.mCheckBox.setChecked(checkItem.isChecked());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    /**
     *  获取所有选中项
     * @return 所有选中项
     */
    public List<CheckItem> getCheckedItems() {
        List<CheckItem> checkItems = new ArrayList<>();
        for (int i = 0; i < items.size(); i ++) {
            CheckItem item = items.get(i);
            if (item.isChecked()) {
                checkItems.add(item);
            }
        }
        return checkItems;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        CheckBox mCheckBox;
        TextView mTextView;

        ViewHolder(View itemView) {
            super(itemView);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.cb_check);
            mTextView = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
