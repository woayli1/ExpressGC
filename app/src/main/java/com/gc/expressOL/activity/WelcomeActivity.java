package com.gc.expressOL.activity;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.gc.expressOL.R;

/**
 * 欢迎页
 * Created by gc on 2018/3/16.
 */
public class WelcomeActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        BarUtils.setStatusBarVisibility(this, false);
        ActivityUtils.startActivity(WelcomeActivity.this, MainActivity.class);
        finish();
    }
}
