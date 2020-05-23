package com.js.sample.activity;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.js.sample.R;
import com.js.sample.fragment.LifeCycleFragment;

public class FragmentLifeCycleActivity extends AppCompatActivity {
    private static final String TAG = "FragmentLifeCycle";
    protected final String LOG_TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "FragmentLifeCycleActivity: onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_life_cycle);


        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new LifeCycleFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "FragmentLifeCycleActivity: onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "FragmentLifeCycleActivity: onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "FragmentLifeCycleActivity: onPause");
        super.onPause();
    }

    @Override
    protected void onRestart() {
        Log.i(TAG, "FragmentLifeCycleActivity: onRestart");
        super.onRestart();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "FragmentLifeCycleActivity: onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "FragmentLifeCycleActivity: onDestory");
        super.onDestroy();
    }
}
