package com.ktw.ktwlib.adapter;

// Created by JS on 2019/5/7.

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.ktw.ktwlib.R;

import java.util.List;

public class SpinnerPopAdapter extends BaseAdapter {
    private List<String> content;
    private Context context;
    private LayoutInflater mInflater;

    public SpinnerPopAdapter(Context context, List<String> content) {
        this.context = context;
        this.content = content;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return content == null ? 0 : content.size();
    }

    @Override
    public Object getItem(int position) {
        return content.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder viewholder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_popuwindow_spinner, null);
            viewholder = new Viewholder();
            viewholder.tv_spinner = (TextView) convertView.findViewById(R.id.tv_spinner);
            convertView.setTag(viewholder);
        } else {
            viewholder = (Viewholder) convertView.getTag();
        }
        String spinnerText = content.get(position);
        viewholder.tv_spinner.setText(spinnerText);
        return convertView;
    }

    public class Viewholder{
        TextView tv_spinner;
    }
}
