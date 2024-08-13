package com.gc.expressOL.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.gc.expressOL.R;
import com.gc.expressOL.data.DataHelpler;
import com.gc.expressOL.Servers;

import org.json.JSONArray;


/**
 * Created by Administrator on 2017/12/22.
 */

public class ChakanActivity extends Activity {

    Intent intent;
    private GestureDetector mGestureDetector;

    DataHelpler dataHelpler;

    String ss;
    String st;

    private TextView nametext;
    private TextView phonetext;
    private TextView company;
    private TextView idtext;
    private TextView address;
    private TextView time;
    private TextView textView11;
    private TextView textView1;
    private Button button;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;

    String s1, s2, s3, s4, s5, s6, s7, s8, s9, s10;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chakan);

        dataHelpler = new DataHelpler(this);

        nametext = findViewById(R.id.nack); //收货人姓名
        phonetext = findViewById(R.id.ptck);//收货人电话
        company = findViewById(R.id.cpck);//物流公司
        idtext = findViewById(R.id.idtck);//取货号
        address = findViewById(R.id.arck);//宿舍楼号
        time = findViewById(R.id.tick);//送达时间
        textView11 = findViewById(R.id.ssck); //费用
        textView1 = findViewById(R.id.jdck);//接单者姓名
        button = findViewById(R.id.qrck);//确认收货按钮
        button2 = findViewById(R.id.qrjdck);//确认接单
        button3 = findViewById(R.id.qrsdck);//确认送达
        button4 = findViewById(R.id.qxdd);//取消订单
        button5 = findViewById(R.id.scdd);//删除订单

        intent = getIntent();
        ss = intent.getStringExtra("state").trim();   //接收进入界面
        st = intent.getStringExtra("auto_nums").trim(); //接收订单编号

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
        detail();
    }

    public void detail() {
        JSONArray result;

        try {
            Servers.readParse("http://" + getString(R.string.ips) + "/orders");
        } catch (Exception E) {
            showmessgae("连接服务器超时");
            return;
        }
        try {
            result = new JSONArray(Servers.readParse("http://" + getString(R.string.ips) + "/orders/AN?AN=" + st + ""));
        } catch (Exception e) {
            showmessgae("连接服务器错误");
            return;
        }
        try {
            for (int i = 0; i < result.length(); i++) {
                s1 = result.getJSONObject(i).getString("names").trim(); //收货者姓名
                s2 = result.getJSONObject(i).getString("num").trim();   //收货者电话
                s3 = result.getJSONObject(i).getString("exc").trim();   //快递公司
                s4 = result.getJSONObject(i).getString("thn").trim();   //取货号
                s5 = result.getJSONObject(i).getString("des").trim();   //送达地点
                s6 = result.getJSONObject(i).getString("pay").trim();   //pay
                s7 = result.getJSONObject(i).getString("time").trim();  //送达时间
                s8 = result.getJSONObject(i).getString("rc").trim();    //接单者
                s9 = result.getJSONObject(i).getString("rs").trim();    //发单者
                s10 = result.getJSONObject(i).getString("stm").trim();   //订单状态
            }
        } catch (Exception e) {
            Log.e("错误1！！！！！", e.toString());
            showmessgae("数据异常,请重新进入");
            return;
        }

        nametext.setText(s1); //收货人姓名
        phonetext.setText(s2);//收货人电话
        company.setText(s3);//物流公司
        idtext.setText(s4);//取货号
        address.setText(s5);//宿舍楼号
        textView11.setText(s6); //费用
        time.setText(s7);//送达时间
        if (!s8.equals("null")) {
            textView1.setText(s8);//接单者姓名
        }

        switch (ss) {
            case "接单":
                button2.setVisibility(View.VISIBLE);
                break;
            case "已接订单":
                if (s10.equals("1") || s10.equals("2") || s10.equals("3")) {
                    button3.setVisibility(View.VISIBLE);
                } else {
                    showmessgae("该订单已确认送达");
                }
                break;
            default:
                if (s10.equals("0")) {
                    showmessgae("该订单暂无人接单");
                    button4.setVisibility(View.VISIBLE);
                } else {
                    if (s10.equals("4")) {
                        showmessgae("该订单已经确认送达");
                    }
                    if (s10.equals("5")) {
                        button5.setVisibility(View.VISIBLE);
                        showmessgae("该订单已经完成");
                        return;
                    }
                    if (s10.equals("6")) {
                        button5.setVisibility(View.VISIBLE);
                        showmessgae("该订单已取消");
                        return;
                    }
                    button.setVisibility(View.VISIBLE);
                }
                break;
        }
    }


    public boolean onTouchEvent(MotionEvent event) {
        //2.让手势识别器生效
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void showmessgae(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void bcck(View view) {
        finish();
    }

    public void qrck(View view) // 确认收货按钮
    {
        String s11;
        JSONArray result;
        try {
            Servers.readParse("http://" + getString(R.string.ips) + "/orders");
        } catch (Exception E) {
            showmessgae("连接服务器超时");
            return;
        }
        try {
            result = new JSONArray("[" + Servers.readParse("http://" + getString(R.string.ips) + "/orders/qr?an=" + st + "") + "]");
        } catch (Exception e) {
            showmessgae("确认收货失败");
            return;
        }
        try {
            s11 = result.getJSONObject(0).getString("affectedRows");
        } catch (Exception e) {
            showmessgae("确认收货失败");
            return;
        }
        if (s11.equals("1")) {
            showmessgae("确认收货成功");
            finish();
        } else {
            showmessgae("确认收货失败");
        }
    }

    public void qrjdck(View view) // 确认接单按钮
    {
        String rc = dataHelpler.getcname();
        String s11 = null;
        JSONArray result;

        if (rc.equals(s9)) {
            showmessgae("不能接自己的订单");
            return;
        }

        try {
            Servers.readParse("http://" + getString(R.string.ips) + "/orders");
        } catch (Exception E) {
            showmessgae("连接服务器超时");
            return;
        }

        try {
            result = new JSONArray(Servers.readParse("http://" + getString(R.string.ips) + "/orders/qrcx?AN=" + st + ""));
            s11 = result.getJSONObject(0).getString("rc");
        } catch (Exception e) {
            showmessgae("确认接单失败");
        }

        if ("null".equals(s11) || "".equals(s11)) {

            try {
                result = new JSONArray("[" + Servers.readParse("http://" + getString(R.string.ips) + "/orders/qrjd?AN=" + st + "&rc=" + rc + "") + "]");
            } catch (Exception e) {
                showmessgae("确认接单失败");
                return;
            }

            try {
                s11 = result.getJSONObject(0).getString("affectedRows");
            } catch (Exception e) {
                showmessgae("确认接单失败");
                return;
            }

            switch (s11) {
                case "1":
                    showmessgae("确认接单成功");
                    finish();
                    break;
                case "2":
                    showmessgae("该订单已经被其他人接单");
                    break;
                default:
                    showmessgae("确认接单失败");
                    break;
            }
        } else {
            showmessgae("该订单已被接单");
        }
    }

    public void qrsdck(View view) //确认送达按钮
    {
        String s11;
        JSONArray result;
        try {
            Servers.readParse("http://" + getString(R.string.ips) + "/orders");
        } catch (Exception E) {
            showmessgae("连接服务器超时");
            return;
        }
        try {
            result = new JSONArray("[" + Servers.readParse("http://" + getString(R.string.ips) + "/orders/qrsd?AN=" + st + "") + "]");
            s11 = result.getJSONObject(0).getString("affectedRows");
        } catch (Exception e) {
            showmessgae("确认送达失败");
            return;
        }

        if (s11.equals("1")) {
            showmessgae("确认送达成功");
            finish();
        } else {
            showmessgae("确认送达失败");
        }
    }

    public void qxdd(View view) {  //取消订单
        showmessgae("该功能未实现");
    }

    public void scdd(View view) {  //删除订单
        showmessgae("该功能未实现");
    }
}
