package com.js.sample.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.js.sample.R;

public class LifeCycleFragment extends Fragment {
    private static final String TAG = "FragmentLifeCycle";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "LifeCycleFragment: onCreateView");
        View view = inflater.inflate(R.layout.fragment_life_cycle, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "LifeCycleFragment: onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "LifeCycleFragment: onCreate");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "LifeCycleFragment: onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "LifeCycleFragment: onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "LifeCycleFragment: onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "LifeCycleFragment: onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "LifeCycleFragment: onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "LifeCycleFragment: onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "LifeCycleFragment: onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "LifeCycleFragment: onDetach");
    }
}
