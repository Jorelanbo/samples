package com.js.sample.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.js.sample.R;


// Created by JS on 2020/1/15.

public class Tab2Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int tab = getArguments().getInt("TAB");
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText("" + tab);
        return view;
    }
}
