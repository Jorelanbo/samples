package com.js.sample.eventBusDemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.js.sample.R;
import com.js.sample.eventBusDemo.bean.Item;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


// Created by JS on 2020/6/8.

public class ItemDetialFragment extends Fragment {

    private TextView tvItemDetail;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);
        tvItemDetail = rootView.findViewById(R.id.item_detail);
        return rootView;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onItemDetailEvent(Item item) {
        tvItemDetail.setText(item.content);
    }
}
