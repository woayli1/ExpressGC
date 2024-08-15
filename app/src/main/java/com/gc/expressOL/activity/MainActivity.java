package com.gc.expressOL.activity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.gc.expressOL.R;
import com.gc.expressOL.base.BaseActivity;
import com.gc.expressOL.Servers;
import com.gc.expressOL.data.SpHelper;

import org.json.JSONArray;

/**
 * 首页
 */
public class MainActivity extends BaseActivity {

    @Override
    public void onBind() {
        super.onBind();

        TextView tvSentOrder = findViewById(R.id.tv_sent_order);
        TextView tvTakenOrder = findViewById(R.id.tv_taken_order);
        TextView tvExpressCheck = findViewById(R.id.tv_express_check);
        TextView tvPersonalCenter = findViewById(R.id.tv_personal_center);
        Button btSendOrder = findViewById(R.id.bt_send_order);
        Button btTakeOrder = findViewById(R.id.bt_take_order);

        tvSentOrder.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString(SpHelper.TAG_SP_STATE, "已发订单");
            ActivityUtils.startActivity(MainActivity.this, ReceiveOrderActivity.class, bundle);
        });

        tvTakenOrder.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString(SpHelper.TAG_SP_STATE, "已接订单");
            ActivityUtils.startActivity(MainActivity.this, ReceiveOrderActivity.class, bundle);
        });

        tvExpressCheck.setOnClickListener(view -> ActivityUtils.startActivity(MainActivity.this, InquireActivity.class));

        tvPersonalCenter.setOnClickListener(view -> ActivityUtils.startActivity(MainActivity.this, UsersActivity.class));

        btSendOrder.setOnClickListener(view -> ActivityUtils.startActivity(MainActivity.this, SendActivity.class));

        btTakeOrder.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString(SpHelper.TAG_SP_STATE, "接单");
            ActivityUtils.startActivity(MainActivity.this, ReceiveOrderActivity.class, bundle);
        });

        connectServers();
        checkLogin();
    }

    public void connectServers() {
        String msg = "连接服务器失败";
        JSONArray result;
        try {
            result = new JSONArray(Servers.readParse("http://" + getString(R.string.server_ip) + "/users"));
        } catch (Exception e) {
            showToast("连接服务器超时");
            return;
        }
        try {
            msg = result.getJSONObject(0).getString("solution");
        } catch (Exception e) {
            showToast(msg);
        }
        if (!msg.equals("连接服务器失败")) {
            showToast("连接服务器成功");
        }

    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public String setTitle() {
        return "";
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    private static boolean isExit = false;
    private Handler mHandler = new Handler();

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.postDelayed(() -> isExit = false, 2000);
        } else {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ObjectUtils.isNotEmpty(mHandler)) {
            mHandler = null;
        }
    }
}
