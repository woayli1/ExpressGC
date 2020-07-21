package com.example.administrator.express_gc;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

/**
 * Created by Administrator on 2017/12/22.
 */

public class SendActivity extends Activity {

    private GestureDetector mGestureDetector;

    EditText nt; //姓名
    EditText pt; //手机号
    EditText ct; //快递公司
    EditText it; //取货号
    EditText at; //宿舍楼
    EditText pat; //金额
    EditText tt; //送达时间

    DataHelpler dataHelpler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        dataHelpler=new DataHelpler(this);

        mGestureDetector = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                   float velocityY) {// e1: 第一次按下的位置   e2   当手离开屏幕 时的位置  velocityX  沿x 轴的速度  velocityY： 沿Y轴方向的速度
                //判断竖直方向移动的大小
                if((e2.getRawX() - e1.getRawX()) >200){  //向左滑动 表示 上一页
                    //显示上一页
                    //pre(null);
                    finish();
                    return true;//消费掉当前事件  不让当前事件继续向下传递
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });

        nt= findViewById(R.id.nametext);
        pt= findViewById(R.id.phonetext);
        ct= findViewById(R.id.company);
        it= findViewById(R.id.idtext);
        at= findViewById(R.id.address);
        pat= findViewById(R.id.payText);
        tt= findViewById(R.id.time);

    }

    public boolean onTouchEvent(MotionEvent event) {
        //2.让手势识别器生效
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void bcse(View view) {
        finish();
    }

    public void fb(View view) {
        JSONArray result;

        String s1; //发单结果

        String nt1=nt.getText().toString().trim();
        String pt1=pt.getText().toString().trim();
        String ct1=ct.getText().toString().trim();
        String it1=it.getText().toString().trim();
        String at1=at.getText().toString().trim();
        String pat1=pat.getText().toString().trim();
        String tt1=tt.getText().toString().trim();
        String rs= dataHelpler.getcname().trim();

        if (nt1.equals("") || nt1.equals("null")) {
            showmessgae("收货人不能为空");
            return;
        }
        if (pt1.equals("") || pt1.equals("null")) {
            showmessgae("手机号不能为空");
            return;
        }
        if (pt1.length() != 11) {
            showmessgae("手机号长度不正确");
            return;
        }
        if (ct1.equals("") || ct1.equals("null")) {
            showmessgae("快递公司不能为空");
            return;
        }
        if (it1.equals("") || it1.equals("null")) {
            showmessgae("取货号不能为空");
            return;
        }
        if (at1.equals("") || at1.equals("null")) {
            showmessgae("送达地不能为空");
            return;
        }
        if (pat1.equals("") || pat1.equals("null")) {
            showmessgae("金额不能为空");
            return;
        }
        if (tt1.equals("") || tt1.equals("null")) {
            showmessgae("送达时间不能为空");
            return;
        }
        try{
            Servers.readParse("http://"+getString(R.string.ips) + "/users");
        }catch (Exception E){
            showmessgae("连接服务器超时");
            return;
        }
        try {
            result = new JSONArray("["+ Servers.readParse("http://"+getString(R.string.ips) + "/orders/fadan?nt=" + nt1 + "&pt=" + pt1 + "&ct=" + ct1 + "&it=" + it1 + "&at=" + at1 + "&pat=" + pat1 + "&tt=" + tt1 + "&rs=" + rs + "")+"]");
        } catch (Exception e) {
            showmessgae("发单失败");
            return;
        }
        try {
            s1 = result.getJSONObject(0).getString("affectedRows");
        }
        catch (Exception e) {
            showmessgae("发单失败");
            return;
        }
        if(s1.equals("1")){
            showmessgae("发单成功");
            finish();
        }else {
            showmessgae("发单失败");
        }

    }

    public void showmessgae(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
