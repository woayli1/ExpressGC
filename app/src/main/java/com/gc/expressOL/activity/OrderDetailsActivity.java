package com.gc.expressOL.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.blankj.utilcode.util.ObjectUtils;
import com.gc.expressOL.R;
import com.gc.expressOL.base.BaseActivity;
import com.gc.expressOL.bean.OrderListBean;
import com.gc.expressOL.data.SpHelper;
import com.gc.expressOL.Servers;

import org.json.JSONArray;


/**
 * 订单详情
 * Created by gc on 2017/12/22.
 */

public class OrderDetailsActivity extends BaseActivity {

    private OrderListBean orderListBean;
    private String state;
    private String orderNum;

    private TextView tvNames;
    private TextView tvNum;
    private TextView tvExc;
    private TextView tvThn;
    private TextView tvDes;
    private TextView tvTime;
    private TextView tvPay;
    private TextView tvRc;
    private Button btConfirmReceipt;
    private Button btConfirmTake;
    private Button btConfirmDelivery;
    private Button btCancelOrder;
    private Button btDeleteOrder;

    @Override
    public void onBind() {
        super.onBind();

        Bundle bundle = getIntent().getExtras();
        if (ObjectUtils.isNotEmpty(bundle)) {
            orderListBean = bundle.getParcelable(SpHelper.TAG_SP_ORDER_DATA);
            state = bundle.getString(SpHelper.TAG_SP_STATE);
            orderNum = bundle.getString(SpHelper.TAG_SP_ORDER_NUM);
        }

        tvNames = findViewById(R.id.tv_names); //收货人姓名
        tvNum = findViewById(R.id.et_num);//收货人电话
        tvExc = findViewById(R.id.tv_exc);//物流公司
        tvThn = findViewById(R.id.tv_thn);//取货号
        tvDes = findViewById(R.id.tv_des);//宿舍楼号
        tvTime = findViewById(R.id.tv_time);//送达时间
        tvPay = findViewById(R.id.tv_pay); //费用
        tvRc = findViewById(R.id.tv_rc);//接单者姓名

        btConfirmReceipt = findViewById(R.id.bt_confirm_receipt);//确认收货
        btConfirmTake = findViewById(R.id.bt_confirm_take);//确认接单
        btConfirmDelivery = findViewById(R.id.bt_confirm_delivery);//确认送达
        btCancelOrder = findViewById(R.id.bt_cancel_order);//取消订单
        btDeleteOrder = findViewById(R.id.bt_delete_order);//删除订单

        btConfirmReceipt.setOnClickListener(view -> confirmReceipt());

        btConfirmTake.setOnClickListener(view -> confirmTake());

        btConfirmDelivery.setOnClickListener(view -> confirmDelivery());

        btCancelOrder.setOnClickListener(view -> showToast("该功能未实现"));

        btDeleteOrder.setOnClickListener(view -> showToast("该功能未实现"));

        setData();

    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_order_details;
    }

    @Override
    public String setTitle() {
        return "订单详情";
    }

    public void setData() {

        if (ObjectUtils.isEmpty(orderListBean)) {
            showToast("数据异常,请重新进入");
            return;
        }

        tvNames.setText(orderListBean.getNames()); //收货人姓名
        tvNum.setText(orderListBean.getNum());//收货人电话
        tvExc.setText(orderListBean.getExc());//物流公司
        tvThn.setText(orderListBean.getThn());//取货号
        tvDes.setText(orderListBean.getDes());//宿舍楼号
        tvPay.setText(orderListBean.getPay()); //费用
        tvTime.setText(orderListBean.getTime());//送达时间
        if (ObjectUtils.isNotEmpty(orderListBean.getRc()) && !orderListBean.getRc().equals("null")) {
            tvRc.setText(orderListBean.getRc());//接单者姓名
        }

        switch (state) {
            case "接单":
                btConfirmTake.setVisibility(View.VISIBLE);
                break;
            case "已接订单":
                if (orderListBean.getStm().equals("1") || orderListBean.getStm().equals("2") || orderListBean.getStm().equals("3")) {
                    btConfirmDelivery.setVisibility(View.VISIBLE);
                } else {
                    showToast("该订单已确认送达");
                }
                break;
            default:
                if (orderListBean.getStm().equals("0")) {
                    showToast("该订单暂无人接单");
                    btCancelOrder.setVisibility(View.VISIBLE);
                } else {
                    if (orderListBean.getStm().equals("4")) {
                        showToast("该订单已经确认送达");
                    }
                    if (orderListBean.getStm().equals("5")) {
                        btDeleteOrder.setVisibility(View.VISIBLE);
                        showToast("该订单已经完成");
                        return;
                    }
                    if (orderListBean.getStm().equals("6")) {
                        btDeleteOrder.setVisibility(View.VISIBLE);
                        showToast("该订单已取消");
                        return;
                    }
                    btConfirmReceipt.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    //确认收货
    public void confirmReceipt() {
        String s11;
        JSONArray result;
        try {
            Servers.readParse("http://" + getString(R.string.server_ip) + "/orders");
        } catch (Exception E) {
            showToast("连接服务器超时");
            return;
        }
        try {
            result = new JSONArray("[" + Servers.readParse("http://" + getString(R.string.server_ip) + "/orders/qr?an=" + orderNum) + "]");
        } catch (Exception e) {
            showToast("确认收货失败");
            return;
        }
        try {
            s11 = result.getJSONObject(0).getString("affectedRows");
        } catch (Exception e) {
            showToast("确认收货失败");
            return;
        }
        if (s11.equals("1")) {
            showToast("确认收货成功");
            finish();
        } else {
            showToast("确认收货失败");
        }
    }

    // 确认接单
    public void confirmTake() {
        String rc = spHelper.getCurrentAccount();
        String s11 = null;
        JSONArray result;

        if (rc.equals(orderListBean.getRs())) {
            showToast("不能接自己的订单");
            return;
        }

        try {
            Servers.readParse("http://" + getString(R.string.server_ip) + "/orders");
        } catch (Exception E) {
            showToast("连接服务器超时");
            return;
        }

        try {
            result = new JSONArray(Servers.readParse("http://" + getString(R.string.server_ip) + "/orders/qrcx?AN=" + orderNum));
            s11 = result.getJSONObject(0).getString("rc");
        } catch (Exception e) {
            showToast("确认接单失败");
        }

        if ("null".equals(s11) || "".equals(s11)) {

            try {
                result = new JSONArray("[" + Servers.readParse("http://" + getString(R.string.server_ip) + "/orders/qrjd?AN=" + orderNum + "&rc=" + rc) + "]");
            } catch (Exception e) {
                showToast("确认接单失败");
                return;
            }

            try {
                s11 = result.getJSONObject(0).getString("affectedRows");
            } catch (Exception e) {
                showToast("确认接单失败");
                return;
            }

            switch (s11) {
                case "1":
                    showToast("确认接单成功");
                    finish();
                    break;
                case "2":
                    showToast("该订单已经被其他人接单");
                    break;
                default:
                    showToast("确认接单失败");
                    break;
            }
        } else {
            showToast("该订单已被接单");
        }
    }

    //确认送达
    public void confirmDelivery() {
        String s11;
        JSONArray result;
        try {
            Servers.readParse("http://" + getString(R.string.server_ip) + "/orders");
        } catch (Exception E) {
            showToast("连接服务器超时");
            return;
        }
        try {
            result = new JSONArray("[" + Servers.readParse("http://" + getString(R.string.server_ip) + "/orders/qrsd?AN=" + orderNum) + "]");
            s11 = result.getJSONObject(0).getString("affectedRows");
        } catch (Exception e) {
            showToast("确认送达失败");
            return;
        }

        if (s11.equals("1")) {
            showToast("确认送达成功");
            finish();
        } else {
            showToast("确认送达失败");
        }
    }
}
