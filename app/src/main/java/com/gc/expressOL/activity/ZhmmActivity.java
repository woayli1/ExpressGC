package com.gc.expressOL.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gc.expressOL.R;
import com.gc.expressOL.data.DataHelpler;
import com.gc.expressOL.Servers;

import org.json.JSONArray;

public class ZhmmActivity extends Activity {

    private GestureDetector mGestureDetector;

    EditText etzhm; //存放输入的姓名
    EditText etzhm2; //存放输入的手机号

    DataHelpler dataHelpler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhmm);

        etzhm = findViewById(R.id.etzhmm);
        etzhm2 = findViewById(R.id.etzhmm2);

        dataHelpler = new DataHelpler(this);

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

    public void pd(View view) {
        String s = etzhm.getText().toString().trim(); //获取输入的姓名
        String s1 = etzhm2.getText().toString().trim(); //获取输入的手机号

        JSONArray result;
        String s2;
        if (s.equals("") || s1.equals("")) {
            showmessgae("姓名或手机号不能为空！");
        } else {
            try {
                Servers.readParse("http://" + getString(R.string.ips) + "/users");
            } catch (Exception E) {
                showmessgae("连接服务器超时");
                return;
            }
            try {
                result = new JSONArray(Servers.readParse("http://" + getString(R.string.ips) + "/users/zhmm?names=" + s + "&num=" + s1 + ""));
            } catch (Exception e) {
                showmessgae("连接服务器错误");
                etzhm.setText("");
                etzhm2.setText("");
                return;
            }
            try {
                s2 = result.getJSONObject(0).getString("cname");
            } catch (Exception e) {
                showmessgae("连接服务器错误");
                etzhm.setText("");
                etzhm2.setText("");
                return;
            }
            if (s2.equals("")) {
                showmessgae("姓名或手机号错误");
                etzhm.setText("");
                etzhm2.setText("");
            } else {
                etzhm.setText("");
                etzhm2.setText("");
                showmessgae("请输入新密码");
                Intent intent = new Intent(this, XmmActivity.class);
                intent.putExtra("cname", s2);
                startActivity(intent);
            }
        }

    }

    public void showmessgae(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public boolean onTouchEvent(MotionEvent event) {
        //2.让手势识别器生效
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void bczm(View view) {
        finish();
    }


}
