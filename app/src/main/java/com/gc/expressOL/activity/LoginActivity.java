package com.gc.expressOL.activity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.gc.expressOL.R;
import com.gc.expressOL.base.BaseActivity;
import com.gc.expressOL.Servers;

import org.json.JSONArray;

/**
 * 登录
 */
public class LoginActivity extends BaseActivity {

    @Override
    public void onBind() {
        super.onBind();

        EditText etName = findViewById(R.id.et_name);
        EditText etPwd = findViewById(R.id.et_pwd);
        TextView tvRegister = findViewById(R.id.tv_register);
        TextView tvForgetPwd = findViewById(R.id.tv_forget_pwd);
        Button btConfirm = findViewById(R.id.bt_confirm);

        tvRegister.setOnClickListener(view -> ActivityUtils.startActivity(LoginActivity.this, RegisterActivity.class));

        tvForgetPwd.setOnClickListener(view -> ActivityUtils.startActivity(LoginActivity.this, RetrievePwdActivity.class));

        btConfirm.setOnClickListener(view -> {
            String name = etName.getText().toString().trim();
            String pwd = etPwd.getText().toString().trim();

            JSONArray result;
            String s2; //存放昵称
            String s3; //存放姓名
            String s4; //存放手机号
            if (ObjectUtils.isEmpty(name) || ObjectUtils.isEmpty(pwd)) {
                showToast("用户名或密码不能为空！");
            } else {
                try {
                    Servers.readParse("http://" + getString(R.string.server_ip) + "/users");
                } catch (Exception E) {
                    showToast("连接服务器超时");
                    return;
                }
                try {
                    result = new JSONArray(Servers.readParse("http://" + getString(R.string.server_ip) + "/users/yanzeng?us=" + name + "&pw=" + pwd));
                } catch (Exception e) {
                    showToast("连接服务器错误");
                    etName.setText("");
                    etPwd.setText("");
                    return;
                }
                try {
                    s2 = result.getJSONObject(0).getString("cname");
                    s3 = result.getJSONObject(0).getString("names");
                    s4 = result.getJSONObject(0).getString("num");
                } catch (Exception e) {
                    showToast("连接服务器错误");
                    etName.setText("");
                    etPwd.setText("");
                    return;
                }
                if (ObjectUtils.isEmpty(s2)) {
                    showToast("用户名或密码错误");
                    etName.setText("");
                    etPwd.setText("");
                } else {
                    showToast(s2 + " 欢迎登录");
                    spHelper.insetData(s2, s3, s4);
                    finish();
                }
            }
        });
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_login;
    }

    @Override
    public String setTitle() {
        return "登录";
    }

}
