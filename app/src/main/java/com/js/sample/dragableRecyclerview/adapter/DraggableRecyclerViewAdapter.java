package com.js.sample.dragableRecyclerview.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.js.sample.R;
import com.js.sample.dragableRecyclerview.bean.ItemBean;
import com.js.sample.commonviewpager.interfaces.ItemTouchStatus;

import java.util.Collections;
import java.util.List;


// Created by JS on 2019/11/19.

public class DraggableRecyclerViewAdapter extends RecyclerView.Adapter<DraggableRecyclerViewAdapter.RecyclerViewHolder> implements ItemTouchStatus {

    private static final String TAG = "DraggableRecyclerViewAdapter";

    private Context mContext;
    private List<ItemBean> mDataList;

    public DraggableRecyclerViewAdapter(Context context,List<ItemBean> dataList) {
        mContext = context;
        mDataList = dataList;
    }


    @NonNull
    @Override
    public DraggableRecyclerViewAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_draggable_recyclerview, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DraggableRecyclerViewAdapter.RecyclerViewHolder holder, int position) {
        holder.mTextView.setText(mDataList.get(position).getString());
        holder.itemView.setBackgroundColor(mDataList.get(position).getColor());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "点击位置：" + holder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mDataList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemRemove(int position) {
        mDataList.remove(position);
        notifyItemRemoved(position);
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.textView);
        }
    }
}