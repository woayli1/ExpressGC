package com.example.administrator.express_gc;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import java.util.TimerTask;

public class MainActivity extends Activity {

    TextView textView; //已发订单
    TextView textView2; //已接订单
    TextView textView3; //快递查询
    TextView textView4; //个人中心

    Button button;  //我要发单
    Button button2; //我要接单

    Intent intent;

    DataHelpler dataHelpler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        dataHelpler = new DataHelpler(this);

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);

        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);

        textView.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                intent = new Intent(MainActivity.this, Receive_orderActivity.class);
                intent.putExtra("state", "已发订单");
                startActivity(intent);
            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                intent = new Intent(MainActivity.this, Receive_orderActivity.class);
                intent.putExtra("state", "已接订单");
                startActivity(intent);
            }
        });

        textView3.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                intent = new Intent(MainActivity.this, ChaxunActivity.class);
                startActivity(intent);
            }
        });

        textView4.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                intent = new Intent(MainActivity.this, UsersActivity.class);
                startActivity(intent);
            }
        });

        lianjie();
        dl();
    }




    public void lianjie() {
        String s2 = "连接服务器失败";
        JSONArray result;
        try {
            result = new JSONArray(Servers.readParse("http://" + getString(R.string.ips) + "/users"));
        } catch (Exception e) {
            showmessgae("连接服务器超时");
            return;
        }
        try {
            s2 = result.getJSONObject(0).getString("solution");
        } catch (Exception e) {
            showmessgae(s2);
        }
        if (!s2.equals("连接服务器失败")) {
            showmessgae("连接服务器成功");
        }

    }

    public void dl(){
        String s=dataHelpler.getcname();
        if(s == null || s.equals("")){
            showmessgae("未登录");
        }
    }


    public void showmessgae(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void bonclick(View view) {
        intent = new Intent(this, SendActivity.class);
        startActivity(intent);
    }

    public void b2onclick(View view) {
        intent = new Intent(MainActivity.this, Receive_orderActivity.class);
        intent.putExtra("state", "接单");
        startActivity(intent);
    }

    private static boolean isExit = false;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }


}
