package com.js.sample.materialDesign.activity;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.js.sample.R;


// Created by JS on 2020/1/15.

public class SnackBarSampleActivity extends AppCompatActivity {

    private Button btnStart;
    private RadioGroup rgGroup;

    private int type = R.id.rb_one;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snack_bar);
        btnStart = findViewById(R.id.btn_start);
        rgGroup = findViewById(R.id.rg_group);
        rgGroup.setOnCheckedChangeListener((group, checkedId) -> {
            type = checkedId;
        });
    }

    public void showSnackBar(View view) {
        View.OnClickListener confirm = v -> {
            Toast.makeText(this, "确定了", Toast.LENGTH_SHORT).show();
        };
        switch (type) {
            case R.id.rb_one:
                Snackbar.make(btnStart, "这就是SnackBar", Snackbar.LENGTH_LONG).show();
                break;
            case R.id.rb_two:
                Snackbar.make(btnStart, "这就是SnackBar", Snackbar.LENGTH_LONG)
                        .setAction("确定", confirm)
                        .show();
                break;
            case R.id.rb_three:
                Snackbar.make(btnStart, "要点击确定SnackBar才会消失", Snackbar.LENGTH_INDEFINITE)
                        .setAction("确定", confirm)
                        .show();
                break;
            case R.id.rb_four:
                Snackbar.make(btnStart, "SnackBar有弹出与弹走的状态回调", Snackbar.LENGTH_INDEFINITE)
                        .setAction("确定", confirm)
                        .addCallback(new Snackbar.Callback() {
                            @Override
                            public void onShown(Snackbar sb) {
                                super.onShown(sb);
                                Toast.makeText(SnackBarSampleActivity.this, "弹出了", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {
                                super.onDismissed(transientBottomBar, event);
                                Toast.makeText(SnackBarSampleActivity.this, "消失了", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                break;
            case R.id.rb_five:
                Snackbar snackbar = Snackbar.make(btnStart, "要点击确定SnackBar才会消失", Snackbar.LENGTH_INDEFINITE)
                        .setAction("确定", confirm);
                snackbar.setActionTextColor(Color.GREEN);
                snackbar.getView().setBackgroundResource(R.color.light_blue);
                /* snackbar 并没有提供修改提示文字颜色的方法。不过可以通过找到snackbar的布局design_layout_snackbar_include
                   通过布局可以找到textview的id。
                   在通过snackbar.getView().findViewById(R.id.snackbar_text);
                 */
                TextView textView = snackbar.getView().findViewById(R.id.snackbar_text);
                textView.setTextColor(getResources().getColor(R.color.colorAccent));
                snackbar.show();
                break;
        }
    }
}
