package com.js.sample.selfmadeViews.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.js.sample.R;

import java.util.List;


// Created by JS on 2020/5/21.

public class MyAdapter extends ArrayAdapter<String> {

    public MyAdapter(@NonNull Context context, int resource, List<String> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.item_my_list_viw, null);
        } else {
            view = convertView;
        }
        TextView textView = view.findViewById(R.id.text_view);
        textView.setText(getItem(position));
        return view;
    }
}
