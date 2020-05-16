package com.js.sample.dragableRecyclerview.activity;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.js.sample.R;
import com.js.sample.dragableRecyclerview.adapter.DraggableRecyclerViewAdapter;
import com.js.sample.dragableRecyclerview.bean.ItemBean;
import com.js.sample.dragableRecyclerview.callback.CustomItemTouchCallback;
import com.js.sample.utils.ColorUtils;

import java.util.ArrayList;
import java.util.List;


// Created by JS on 2019/11/19.

public class DraggableRecyclerviewActivity extends AppCompatActivity {

    private final LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draggable_recyclerview);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        List<ItemBean> itemBeans = generateDataList(50);
        DraggableRecyclerViewAdapter adapter = new DraggableRecyclerViewAdapter(this, itemBeans);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.addItemDecoration(mItemDecoration);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new CustomItemTouchCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private List<ItemBean> generateDataList(int count) {
        List<ItemBean> dataList = new ArrayList<>();
        for (int i = 0; i < count; i ++) {
            ItemBean bean = new ItemBean();
            bean.setString("position = " + i);
            bean.setColor(Color.parseColor(ColorUtils.generateRandomColor()));
            dataList.add(bean);
        }
        return dataList;
    }

    private RecyclerView.ItemDecoration mItemDecoration = new RecyclerView.ItemDecoration() {

        private static final int DEFAULT_OFFSET = 10;

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            int adapterPosition = parent.getChildViewHolder(view).getAdapterPosition();
            if (adapterPosition == parent.getAdapter().getItemCount() - 1) {
                outRect.set(0, 0, 0, 0);
            } else {
                outRect.set(0, 0, 0, DEFAULT_OFFSET);
            }
        }
    };
}
