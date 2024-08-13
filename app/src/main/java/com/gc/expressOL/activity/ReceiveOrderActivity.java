package com.gc.expressOL.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.gc.expressOL.R;
import com.gc.expressOL.data.DataHelpler;
import com.gc.expressOL.adapter.OrderAdapter;
import com.gc.expressOL.Servers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/12/22.
 */

public class ReceiveOrderActivity extends Activity {

    Intent intent;
    TextView textView; //标题
    public ListView orderlist;
    OrderAdapter orderAdapter;

    DataHelpler dataHelpler;
    public static ArrayList<String> s1 = new ArrayList<>();
    public static ArrayList<String> s2 = new ArrayList<>();
    public static String ss;

    private GestureDetector mGestureDetector;
    private boolean isPause;

    String nm = null;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_order);

        dataHelpler = new DataHelpler(this);

        textView = findViewById(R.id.tex5);
        intent = getIntent();
        ss = intent.getStringExtra("state").trim();
        textView.setText(ss);

        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                   float velocityY) {// e1: 第一次按下的位置   e2   当手离开屏幕 时的位置  velocityX  沿x 轴的速度  velocityY： 沿Y轴方向的速度
                //判断竖直方向移动的大小
                if ((e2.getRawX() - e1.getRawX()) > 200) {  //向左滑动 表示 上一页
                    //显示上一页
                    //pre(null);
                    finish();
                    return true;//消费掉当前事件  不让当前事件继续向下传递
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });


        nm = dataHelpler.getcname();
        if (nm == null) {
            showmessgae("未登录");
            finish();
            Intent intent1 = new Intent(this, DengluActivity.class);
            startActivity(intent1);
            return;
        }
        States(textView.getText().toString().trim());
    }

    public void States(String s) {
        String result = null;
        try {
            Servers.readParse("http://" + getString(R.string.ips) + "/orders");
        } catch (Exception E) {
            showmessgae("连接服务器超时");
            return;
        }
        try {
            if (s.equals("接单")) {
                result = Servers.readParse("http://" + getString(R.string.ips) + "/orders/chaxun");
            }
            if (s.equals("已发订单")) {
                result = Servers.readParse("http://" + getString(R.string.ips) + "/orders/chaxunF?rs=" + nm + "");
            }
            if (s.equals("已接订单")) {
                result = Servers.readParse("http://" + getString(R.string.ips) + "/orders/chaxunJ?rc=" + nm + "");
            }
        } catch (Exception e) {
            showmessgae("连接服务器错误");
            return;
        }
        try {
            if (result == null) {
                showmessgae("无数据");
            } else {
                s1.clear();
                s2.clear();

                List<HashMap> list = JSON.parseArray(result, HashMap.class);
                for (int i = 0; i < list.size(); i++) {
                    s1.add(list.get(i).get("auto_nums").toString());
                    s2.add(list.get(i).get("exc").toString());
                }
            }
        } catch (Exception e) {
            Log.e("错误2222222！！！！！", e.toString());
            showmessgae("数据异常,请重新进入");
            return;
        }
        orderlist = findViewById(R.id.orderl2);
        orderAdapter = new OrderAdapter(this);
        orderlist.setAdapter(orderAdapter);
    }


    public boolean onTouchEvent(MotionEvent event) {
        //2.让手势识别器生效
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void showmessgae(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void bcro(View view) {
        finish();
    }

    protected void onPause() {
        super.onPause();
        isPause = true; //记录页面已经被暂停
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPause) { //判断是否暂停
            isPause = false;
            //orderAdapter.notifyDataSetChanged();//刷新
            States(textView.getText().toString().trim());
        }
    }
}
