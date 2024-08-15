package com.gc.expressOL.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.gc.expressOL.R;
import com.gc.expressOL.base.BaseActivity;
import com.gc.expressOL.bean.OrderListBean;
import com.gc.expressOL.data.SpHelper;
import com.gc.expressOL.adapter.OrderAdapter;
import com.gc.expressOL.Servers;

import java.util.ArrayList;

/**
 * 已发订单/已接订单/我要接单
 * Created by gc on 2017/12/22.
 */

public class ReceiveOrderActivity extends BaseActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;

    private String state = "";

    @Override
    public void onBind() {
        super.onBind();

        swipeRefreshLayout = findViewById(R.id.id_swipe_Layout);
        recyclerView = findViewById(R.id.recycler_view);

        checkLogin();
        if (!isLogin) {
            finish();
        }

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            getOrderList();
        });
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_receive_order;
    }

    @Override
    public String setTitle() {
        Bundle bundle = getIntent().getExtras();
        if (ObjectUtils.isNotEmpty(bundle)) {
            state = bundle.getString(SpHelper.TAG_SP_STATE);
        }
        return state;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //刷新
        getOrderList();
    }

    public void getOrderList() {
        String result = null;
        try {
            Servers.readParse("http://" + getString(R.string.server_ip) + "/orders");
        } catch (Exception E) {
            showToast("连接服务器超时");
            return;
        }
        try {
            if (state.equals("接单")) {
                result = Servers.readParse("http://" + getString(R.string.server_ip) + "/orders/chaxun");
            }
            if (state.equals("已发订单")) {
                result = Servers.readParse("http://" + getString(R.string.server_ip) + "/orders/chaxunF?rs=" + spHelper.getCurrentAccount());
            }
            if (state.equals("已接订单")) {
                result = Servers.readParse("http://" + getString(R.string.server_ip) + "/orders/chaxunJ?rc=" + spHelper.getCurrentAccount());
            }
        } catch (Exception e) {
            showToast("连接服务器错误");
            return;
        }
        try {
            if (ObjectUtils.isEmpty(result)) {
                showToast("无数据");
            } else {
                ArrayList<OrderListBean> orderListBeanList = GsonUtils.fromJson(result, GsonUtils.getListType(OrderListBean.class));
                OrderAdapter orderAdapter = getOrderAdapter(orderListBeanList);

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(orderAdapter);
            }
        } catch (Exception e) {
            Log.e("错误2222222！！！！！", e.toString());
            showToast("数据异常,请重新进入");
        }
    }

    private OrderAdapter getOrderAdapter(ArrayList<OrderListBean> orderListBeanList) {
        OrderAdapter orderAdapter = new OrderAdapter(getApplicationContext(), orderListBeanList);
        orderAdapter.setOnItemClickListener((position, itemId) -> {
            Bundle bundle = new Bundle();
            bundle.putString(SpHelper.TAG_SP_ORDER_NUM, orderListBeanList.get(position).getAuto_nums());
            bundle.putString(SpHelper.TAG_SP_ORDER_NUM, state);
            bundle.putParcelable(SpHelper.TAG_SP_ORDER_DATA, orderListBeanList.get(position));
            ActivityUtils.startActivity(ReceiveOrderActivity.this, OrderDetailsActivity.class);
        });
        return orderAdapter;
    }
}
