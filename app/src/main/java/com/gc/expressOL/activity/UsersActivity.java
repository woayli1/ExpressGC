package com.gc.expressOL.activity;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.gc.expressOL.R;
import com.gc.expressOL.base.BaseActivity;
import com.lxj.xpopup.XPopup;

/**
 * 个人中心
 */
public class UsersActivity extends BaseActivity {

    @Override
    public void onBind() {
        super.onBind();

        TextView tvLoginState = findViewById(R.id.tv_login_state);
        TextView tvLogin = findViewById(R.id.tv_login);

        LinearLayout llLogin = findViewById(R.id.ll_login);
        LinearLayout llAboutAuthor = findViewById(R.id.ll_about_author);
        TextView tvVersion = findViewById(R.id.tv_version);
        TextView tvExitLogin = findViewById(R.id.tv_exit_login);

        checkLogin();

        if (!isLogin) {
            llLogin.setOnClickListener(view -> ActivityUtils.startActivity(UsersActivity.this, LoginActivity.class));
        } else {
            tvLoginState.setText("您已登录");
            tvLogin.setText(spHelper.getCurrentAccount());
            llLogin.setOnClickListener(view -> ActivityUtils.startActivity(UsersActivity.this, SetNewPassWordActivity.class));
        }

        llAboutAuthor.setOnClickListener(view -> ActivityUtils.startActivity(UsersActivity.this, AboutAuthorActivity.class));

        tvExitLogin.setOnClickListener(view -> {
            if (!isLogin) {
                showToast("您未登录");
            } else {
                new XPopup.Builder(this).asConfirm("退出登录?", "",
                                () -> {
                                    spHelper.deleteData();
                                    showToast("退出成功");
                                    finish();
                                })
                        .show();
            }
        });

        String version = "v" + AppUtils.getAppVersionName();
        tvVersion.setText(version);
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_users;
    }

    @Override
    public String setTitle() {
        return "个人中心";
    }

}
