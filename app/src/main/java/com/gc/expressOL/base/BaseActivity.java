package com.gc.expressOL.base;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gc.expressOL.R;
import com.gc.expressOL.data.SpHelper;

/**
 * author gc
 * company enjoyPartyTime
 * date 2024/8/13
 */
public abstract class BaseActivity extends Activity {

    public SpHelper spHelper;
    public boolean isLogin = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        spHelper = new SpHelper();

        setContentView(setContentLayout());

        BarUtils.setNavBarColor(getWindow(), getResources().getColor(R.color.white, null));
        BarUtils.setStatusBarColor(getWindow(), getResources().getColor(R.color.green, null));

        ImageView ivBaseBack = findViewById(R.id.iv_base_back);
        if (ObjectUtils.isNotEmpty(ivBaseBack)) {
            ivBaseBack.setOnClickListener(view -> finish());
        }

        TextView tvBaseTitle = findViewById(R.id.tv_base_title);
        if (ObjectUtils.isNotEmpty(tvBaseTitle)) {
            tvBaseTitle.setText(setTitle());
        }

        onBind();
    }

    public void onBind() {

    }

    public void checkLogin() {
        String currentAccount = spHelper.getCurrentAccount();
        if (ObjectUtils.isEmpty(currentAccount)) {
            showToast("未登录");
            isLogin = false;
        } else {
            isLogin = true;
        }
    }

    public void showToast(String msg) {
        ToastUtils.showShort(msg);
    }

    public abstract int setContentLayout();

    public abstract String setTitle();
}
