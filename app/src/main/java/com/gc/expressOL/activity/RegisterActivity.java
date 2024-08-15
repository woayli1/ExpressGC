package com.gc.expressOL.activity;

import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.gc.expressOL.R;
import com.gc.expressOL.Servers;
import com.gc.expressOL.base.BaseActivity;

import org.json.JSONArray;

/**
 * 账号注册
 */
public class RegisterActivity extends BaseActivity {

    @Override
    public void onBind() {
        super.onBind();

        EditText etNickname = findViewById(R.id.et_nickname);
        EditText etName = findViewById(R.id.et_name);
        EditText etPwd = findViewById(R.id.et_pwd);
        EditText etPhone = findViewById(R.id.et_phone);
        Button btConfirm = findViewById(R.id.bt_confirm);

        btConfirm.setOnClickListener(view -> {
            JSONArray result;

            String nickname = etNickname.getText().toString().trim();  //昵称
            String name = etName.getText().toString().trim();  //姓名
            String pwd = etPwd.getText().toString().trim();  //密码
            String phone = etPhone.getText().toString().trim();  //手机号
            String res;//注册结果

            if (ObjectUtils.isEmpty(nickname)) {
                showToast("昵称不能为空");
                return;
            }
            if (ObjectUtils.isEmpty(name)) {
                showToast("姓名不能为空");
                return;
            }
            if (ObjectUtils.isEmpty(pwd)) {
                showToast("密码不能为空");
                return;
            }
            if (ObjectUtils.isEmpty(phone)) {
                showToast("手机号不能为空");
                return;
            }
            if (RegexUtils.isMobileSimple(phone)) {
                showToast("手机号不正确");
                return;
            }

            try {
                Servers.readParse("http://" + getString(R.string.server_ip) + "/users");
            } catch (Exception E) {
                showToast("连接服务器超时");
                return;
            }
            try {   //验证昵称
                result = new JSONArray(Servers.readParse("http://" + getString(R.string.server_ip) + "/users/yznc?cnm=" + nickname));
            } catch (Exception e) {
                showToast("连接服务器错误");
                return;
            }
            if (result.length() >= 1) {
                showToast("昵称已存在");
                return;
            }
            try {   //验证手机号
                result = new JSONArray(Servers.readParse("http://" + getString(R.string.server_ip) + "/users/yznu?num=" + phone));
            } catch (Exception e) {
                showToast("连接服务器错误");
                return;
            }
            if (result.length() >= 1) {
                showToast("手机号已被注册");
                return;
            }
            try {   //验证姓名
                result = new JSONArray(Servers.readParse("http://" + getString(R.string.server_ip) + "/users/yzxm?us=" + name));
            } catch (Exception e) {
                showToast("连接服务器错误");
                return;
            }
            if (result.length() >= 1) {
                showToast("姓名已被注册");
                return;
            }
            try {   //执行注册
                result = new JSONArray("[" + Servers.readParse("http://" + getString(R.string.server_ip) + "/users/zc?cnm=" + nickname + "&num=" + phone + "&us=" + name + "&pw=" + pwd) + "]");
            } catch (Exception e) {
                showToast("注册失败");
                return;
            }
            try {
                res = result.getJSONObject(0).getString("affectedRows");
            } catch (Exception e) {
                showToast("注册失败");
                return;
            }
            if (res.equals("1")) {
                showToast("注册成功");
                finish();
            } else {
                showToast("注册失败");
            }

            //{"fieldCount":0,"affectedRows":1,"insertId":0,"serverStatus":2,"warningCount":0,"message":"","protocol41":true,"changedRows":0} 注册成功
            //注册需判定 昵称 和 手机号 是否已经存在
            //连接数据库超时反馈

        });

    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_register;
    }

    @Override
    public String setTitle() {
        return "账号注册";
    }
}
