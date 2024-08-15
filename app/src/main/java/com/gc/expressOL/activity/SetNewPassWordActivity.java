package com.gc.expressOL.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ObjectUtils;
import com.gc.expressOL.R;
import com.gc.expressOL.base.BaseActivity;
import com.gc.expressOL.Servers;
import com.gc.expressOL.data.SpHelper;

import org.json.JSONObject;

/**
 * 设置新密码
 */
public class SetNewPassWordActivity extends BaseActivity {

    private String currentAccount = "";

    @Override
    public void onBind() {
        super.onBind();

        Bundle bundle = getIntent().getExtras();
        if (ObjectUtils.isNotEmpty(bundle)) {
            currentAccount = getIntent().getExtras().getString(SpHelper.TAG_SP_NAME);
        }
        if (ObjectUtils.isEmpty(currentAccount)) {
            currentAccount = spHelper.getCurrentAccount();
        }

        EditText etNewPwd = findViewById(R.id.et_new_pwd);
        Button btConfirm = findViewById(R.id.bt_confirm);

        btConfirm.setOnClickListener(view -> {
            JSONObject result;

            String newPwd = etNewPwd.getText().toString().trim(); //获取输入的密码
            String res; //返回值判定
            if (ObjectUtils.isEmpty(newPwd)) {
                showToast("新密码不能为空！");
            } else {
                try {
                    Servers.readParse("http://" + getString(R.string.server_ip) + "/users");
                } catch (Exception E) {
                    showToast("连接服务器超时");
                    return;
                }
                try {
                    result = new JSONObject(Servers.readParse("http://" + getString(R.string.server_ip) + "/users/xmm?us=" + currentAccount + "&pw=" + newPwd));
                } catch (Exception e) {
                    showToast("连接服务器错误");
                    return;
                }
                try {
                    res = result.getString("affectedRows");
                } catch (Exception e) {
                    showToast("修改失败");
                    return;
                }
                if (Integer.parseInt(res) > 0) {
                    spHelper.deleteData(); //清空当前用户，让用户重新登录
                    showToast("修改成功");
                    finish();
                } else {
                    showToast("修改失败");
                }
            }
        });
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_set_new_password;
    }

    @Override
    public String setTitle() {
        return "新密码";
    }
}
