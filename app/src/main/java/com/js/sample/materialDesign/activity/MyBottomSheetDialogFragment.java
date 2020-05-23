package com.js.sample.materialDesign.activity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.js.sample.R;


// Created by JS on 2020/1/11.

public class MyBottomSheetDialogFragment extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_bottom_sheet_dialog, container, false);
        TextView tvGotIt = view.findViewById(R.id.tv_got_it);
        tvGotIt.setOnClickListener(v -> {
            dismiss();
        });
        return view;
    }
}
