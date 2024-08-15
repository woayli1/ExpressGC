package com.gc.expressOL.activity;

import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.gc.expressOL.R;
import com.gc.expressOL.base.BaseActivity;
import com.gc.expressOL.Servers;

import org.json.JSONArray;

/**
 * 发出订单
 * Created by gc on 2017/12/22.
 */

public class SendActivity extends BaseActivity {

    @Override
    public void onBind() {
        super.onBind();

        EditText etNames = findViewById(R.id.et_names);
        EditText etNum = findViewById(R.id.et_num);
        EditText etExc = findViewById(R.id.et_exc);
        EditText etThn = findViewById(R.id.et_thn);
        EditText etDes = findViewById(R.id.et_des);
        EditText etPay = findViewById(R.id.et_pay);
        EditText etTime = findViewById(R.id.et_time);

        Button btConfirmTake = findViewById(R.id.bt_confirm_take);
        btConfirmTake.setOnClickListener(view -> {
            JSONArray result;

            String s1; //发单结果

            String names = etNames.getText().toString();
            String num = etNum.getText().toString();
            String exc = etExc.getText().toString();
            String thn = etThn.getText().toString();
            String des = etDes.getText().toString();
            String pay = etPay.getText().toString();
            String time = etTime.getText().toString();
            String rs = spHelper.getCurrentAccount();

            if (ObjectUtils.isEmpty(names)) {
                showToast("收货人不能为空");
                return;
            }
            if (ObjectUtils.isEmpty(num)) {
                showToast("手机号不能为空");
                return;
            }
            if (RegexUtils.isMobileSimple(num)) {
                showToast("手机号不正确");
                return;
            }
            if (ObjectUtils.isEmpty(exc)) {
                showToast("快递公司不能为空");
                return;
            }
            if (ObjectUtils.isEmpty(thn)) {
                showToast("取货号不能为空");
                return;
            }
            if (ObjectUtils.isEmpty(des)) {
                showToast("送达地不能为空");
                return;
            }
            if (ObjectUtils.isEmpty(pay)) {
                showToast("金额不能为空");
                return;
            }
            if (ObjectUtils.isEmpty(time)) {
                showToast("送达时间不能为空");
                return;
            }
            try {
                Servers.readParse("http://" + getString(R.string.server_ip) + "/users");
            } catch (Exception E) {
                showToast("连接服务器超时");
                return;
            }
            try {
                result = new JSONArray("[" + Servers.readParse("http://" + getString(R.string.server_ip) + "/orders/fadan?nt=" + names + "&pt=" + num + "&ct=" + exc + "&it=" + thn + "&at=" + des + "&pat=" + pay + "&tt=" + time + "&rs=" + rs) + "]");
            } catch (Exception e) {
                showToast("发单失败");
                return;
            }
            try {
                s1 = result.getJSONObject(0).getString("affectedRows");
            } catch (Exception e) {
                showToast("发单失败");
                return;
            }
            if (s1.equals("1")) {
                showToast("发单成功");
                finish();
            } else {
                showToast("发单失败");
            }
        });
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_send;
    }

    @Override
    public String setTitle() {
        return "发单";
    }

}
