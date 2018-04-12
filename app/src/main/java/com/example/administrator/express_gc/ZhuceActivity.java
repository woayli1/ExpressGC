package com.example.administrator.express_gc;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class ZhuceActivity extends Activity {

    private GestureDetector mGestureDetector;

    EditText etzc;  //存放昵称
    EditText etzc2;  //存放姓名
    EditText etzc3;  //存放密码
    EditText etzc4;  //存放手机号

    Servers se;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce);

        etzc = (EditText) findViewById(R.id.etzc);
        etzc2 = (EditText) findViewById(R.id.etzc2);
        etzc3 = (EditText) findViewById(R.id.etzc3);
        etzc4 = (EditText) findViewById(R.id.etzc4);

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
    }

    public void zc(View view) {

        JSONArray result = null;

        String s = etzc.getText().toString().trim();  //昵称
        String s2 = etzc2.getText().toString().trim();  //姓名
        String s3 = etzc3.getText().toString().trim();  //密码
        String s4 = etzc4.getText().toString().trim();  //手机号
        String s5=null;//注册结果

        if (s == null || s.length() <= 0) {
            showmessgae("昵称不能为空");
            return;
        }
        if (s2 == null || s2.length() <= 0) {
            showmessgae("姓名不能为空");
            return;
        }
        if (s3 == null || s3.length() <= 0) {
            showmessgae("密码不能为空");
            return;
        }
        if (s4 == null || s4.length() <= 0) {
            showmessgae("手机号不能为空");
            return;
        }
        if (s4.length() != 11) {
            showmessgae("手机号长度不正确");
            return;
        }

        try {
            se.readParse("http://" + getString(R.string.ips) + "/users");
        } catch (Exception E) {
            showmessgae("连接服务器超时");
            return;
        }
        try {   //验证昵称
            result = new JSONArray(se.readParse("http://" + getString(R.string.ips) + "/users/yznc?cnm=" + s + ""));
        } catch (Exception e) {
            showmessgae("连接服务器错误");
            return;
        }
        if (result.length()>=1) {
            showmessgae("昵称已存在");
            return;
        }
        try {   //验证手机号
            result = new JSONArray(se.readParse("http://" + getString(R.string.ips) + "/users/yznu?num=" + s4 + ""));
        } catch (Exception e) {
            showmessgae("连接服务器错误");
            return;
        }
        if (result.length()>=1) {
            showmessgae("手机号已被注册");
            return;
        }
        try {   //验证姓名
            result = new JSONArray(se.readParse("http://" + getString(R.string.ips) + "/users/yzxm?us=" + s2 + ""));
        } catch (Exception e) {
            showmessgae("连接服务器错误");
            return;
        }
        if (result.length()>=1) {
            showmessgae("姓名已被注册");
            return;
        }
        try {   //执行注册
            result = new JSONArray("["+se.readParse("http://" + getString(R.string.ips) + "/users/zc?cnm=" + s + "&num=" + s4 + "&us=" + s2 + "&pw=" + s3 + "")+"]");
        } catch (Exception e) {
            showmessgae("注册失败");
            return;
        }
        try {
            s5 = result.getJSONObject(0).getString("affectedRows");
        }
        catch (Exception e) {
            showmessgae("注册失败");
            return;
        }
        if(s5.equals("1")){
            showmessgae("注册成功");
            finish();
        }else {
            showmessgae("注册失败");
        }

//{"fieldCount":0,"affectedRows":1,"insertId":0,"serverStatus":2,"warningCount":0,"message":"","protocol41":true,"changedRows":0} 注册成功
        //注册需判定 昵称 和 手机号 是否已经存在
        //连接数据库超时反馈

    }

    public boolean onTouchEvent(MotionEvent event) {
        //2.让手势识别器生效
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void showmessgae(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        return;
    }

    public void bczc(View view) {
        finish();
    }
}
