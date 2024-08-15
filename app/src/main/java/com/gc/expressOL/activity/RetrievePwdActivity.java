package com.gc.expressOL.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.gc.expressOL.R;
import com.gc.expressOL.base.BaseActivity;
import com.gc.expressOL.data.SpHelper;
import com.gc.expressOL.Servers;

import org.json.JSONArray;

/**
 * 找回密码
 */
public class RetrievePwdActivity extends BaseActivity {

    @Override
    public void onBind() {
        super.onBind();

        EditText etName = findViewById(R.id.et_name);
        EditText etPhone = findViewById(R.id.et_phone);
        Button btConfirm = findViewById(R.id.bt_confirm);

        btConfirm.setOnClickListener(view -> {
            String name = etName.getText().toString().trim(); //获取输入的姓名
            String phone = etPhone.getText().toString().trim(); //获取输入的手机号

            JSONArray result;
            String s2;
            if (ObjectUtils.isEmpty(name) || ObjectUtils.isEmpty(phone)) {
                showToast("姓名或手机号不能为空！");
            } else {
                try {
                    Servers.readParse("http://" + getString(R.string.server_ip) + "/users");
                } catch (Exception E) {
                    showToast("连接服务器超时");
                    return;
                }
                try {
                    result = new JSONArray(Servers.readParse("http://" + getString(R.string.server_ip) + "/users/zhmm?names=" + name + "&num=" + phone));
                } catch (Exception e) {
                    showToast("连接服务器错误");
                    return;
                }
                try {
                    s2 = result.getJSONObject(0).getString("cname");
                } catch (Exception e) {
                    showToast("连接服务器错误");
                    return;
                }
                if (ObjectUtils.isEmpty(s2)) {
                    showToast("姓名或手机号错误");
                } else {
                    etName.setText("");
                    etPhone.setText("");
                    showToast("请输入新密码");

                    Bundle bundle = new Bundle();
                    bundle.putString(SpHelper.TAG_SP_NAME, s2);
                    ActivityUtils.startActivity(RetrievePwdActivity.this, SetNewPassWordActivity.class, bundle);
                }
            }
        });

    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_retrieve_pwd;
    }

    @Override
    public String setTitle() {
        return "找回密码";
    }

}
