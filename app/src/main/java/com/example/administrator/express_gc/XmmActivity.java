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

public class XmmActivity extends Activity {

    private GestureDetector mGestureDetector;

    DataHelpler dataHelpler;
    EditText etxmm;
    Servers se;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xmm);

        etxmm = (EditText) findViewById(R.id.etxmm);

        dataHelpler=new DataHelpler(this);

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

    public boolean onTouchEvent(MotionEvent event) {
        //2.让手势识别器生效
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void bcxmm(View view) {
        finish();
    }

    public void btxmm(View view) {
        JSONObject result = null;
        String us = null;
        Intent intent = getIntent();
        us = intent.getStringExtra("cname");
        if (us == null || us == "") {
            us = dataHelpler.getcname(); //获取当前用户昵称
        }
        String pw = etxmm.getText().toString().trim(); //获取输入的密码
        String s1 = "0"; //返回值判定
        if (pw.equals("") || pw.equals(null)) {
            showmessgae("新密码不能为空！");
            return;
        } else {
            try {
                se.readParse("http://" + getString(R.string.ips) + "/users");
            } catch (Exception E) {
                showmessgae("连接服务器超时");
                return;
            }
            try {
                result = new JSONObject(se.readParse("http://" + getString(R.string.ips) + "/users/xmm?us=" + us + "&pw=" + pw + ""));
            } catch (Exception e) {
                showmessgae("连接服务器错误");
                return;
            }
            try {
                s1 = result.getString("affectedRows");
            } catch (Exception e) {
                showmessgae("修改失败");
                return;
            }
            if (Integer.parseInt(s1) > 0) {
                showmessgae("修改成功");
                finish();
                dataHelpler.deletedata(); //清空当前用户，让用户重新登录
                return;
            } else {
                showmessgae("修改失败");
                return;
            }
        }
    }


    public void showmessgae(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        return;
    }
}
