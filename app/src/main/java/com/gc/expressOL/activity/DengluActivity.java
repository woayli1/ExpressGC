package com.gc.expressOL.activity;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.expressOL.R;
import com.gc.expressOL.data.DataHelpler;
import com.gc.expressOL.Servers;

import org.json.JSONArray;

public class DengluActivity extends Activity {

    private GestureDetector mGestureDetector;

    EditText dlus;  //用户名
    EditText dlpw;  //密码
    TextView dlzc;
    TextView dlzh;

    Intent intent;

    DataHelpler dataHelpler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denglu);

        dlus = findViewById(R.id.dlus);
        dlpw = findViewById(R.id.dlpw);
        dlzc = findViewById(R.id.dlzc);
        dlzh = findViewById(R.id.dlzh);

        dataHelpler = new DataHelpler(this);

        dlzc.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                intent = new Intent(DengluActivity.this, ZhuceActivity.class);
                startActivity(intent);
            }
        });

        dlzh.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                intent = new Intent(DengluActivity.this, ZhmmActivity.class);
                startActivity(intent);
            }
        });

        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {// e1: 第一次按下的位置   e2   当手离开屏幕 时的位置  velocityX  沿x 轴的速度  velocityY： 沿Y轴方向的速度
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

    public void bcdl(View view) {
        finish();
    }

    public void dlbt(View view) {
        String us = dlus.getText().toString().trim();
        String pw = dlpw.getText().toString().trim();

        JSONArray result;
        String s2; //存放昵称
        String s3; //存放姓名
        String s4; //存放手机号
        if (us.equals("") || pw.equals("")) {
            showmessgae("用户名或密码不能为空！");
        } else {
            try {
                Servers.readParse("http://" + getString(R.string.ips) + "/users");
            } catch (Exception E) {
                showmessgae("连接服务器超时");
                return;
            }
            try {
                result = new JSONArray(Servers.readParse("http://" + getString(R.string.ips) + "/users/yanzeng?us=" + us + "&pw=" + pw + ""));
            } catch (Exception e) {
                showmessgae("连接服务器错误");
                dlus.setText("");
                dlpw.setText("");
                return;
            }
            try {
                s2 = result.getJSONObject(0).getString("cname");
                s3 = result.getJSONObject(0).getString("names");
                s4 = result.getJSONObject(0).getString("num");
            } catch (Exception e) {
                showmessgae("连接服务器错误");
                dlus.setText("");
                dlpw.setText("");
                return;
            }
            if (s2.equals("")) {
                showmessgae("用户名或密码错误");
                dlus.setText("");
                dlpw.setText("");
            } else {
                showmessgae("" + s2 + " 欢迎登录");
                dataHelpler.insertdata(s2, s3, s4);
                finish();
            }
        }
    }


    public void showmessgae(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
