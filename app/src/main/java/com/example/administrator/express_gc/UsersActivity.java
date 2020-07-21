package com.example.administrator.express_gc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UsersActivity extends Activity {

    private GestureDetector mGestureDetector;

    TextView te5;
    TextView te6;
    Button dlbl;

    Intent int1;

    PackageManager packageManager;

    DataHelpler dataHelpler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        te5 = findViewById(R.id.te5);
        te6 = findViewById(R.id.t6);
        dlbl = findViewById(R.id.dlbl);

        dataHelpler = new DataHelpler(this);

        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                   float velocityY) {// e1: 第一次按下的位置   e2   当手离开屏幕 时的位置  velocityX  沿x 轴的速度  velocityY： 沿Y轴方向的速度
                //判断竖直方向移动的大小
                if (Math.abs(e1.getRawY() - e2.getRawY()) > 100) {
                    //Toast.makeText(getApplicationContext(), 动作不合法, 0).show();
                    return true;
                }
                if (Math.abs(velocityX) < 150) {
                    //Toast.makeText(getApplicationContext(), 移动的太慢, 0).show();
                    return true;
                }

                if ((e1.getRawX() - e2.getRawX()) > 200) {// 表示 向右滑动表示下一页
                    //显示下一页
                    //next(null);
                    return true;
                }

                if ((e2.getRawX() - e1.getRawX()) > 200) {  //向左滑动 表示 上一页
                    //显示上一页
                    //pre(null);
                    finish();
                    return true;//消费掉当前事件  不让当前事件继续向下传递
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });

        youdu();
    }

    //dataHelpler.getcname();
    public void youdu() {
        String s10 = dataHelpler.getcname();
//        try{
//         s10=dataHelpler.getcname();}
//        catch (Exception e){
//            int1 = new Intent(this, DengluActivity.class);
//        }
        if (s10 == null || s10.equals("")) {

            int1 = new Intent(this, DengluActivity.class);
        } else {
            te5.setText("您已登录");
            dlbl.setText(s10);
            int1 = new Intent(this, XmmActivity.class);
            int1.putExtra("cname", s10);
        }

        packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            te6.setText(String.format(getResources().getString(R.string.activity_users_version), packageInfo.versionName));
        } catch (PackageManager.NameNotFoundException e) {
            te6.setText(R.string.activity_users_version_err);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        //2.让手势识别器生效
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void bcus(View view) {
        finish();
    }

    public void usdl(View view) {
        finish();
        startActivity(int1);
    }

    public void tcdl(View view) {
        String s = dataHelpler.getcname();
        if (s == null || s.equals("")) {
            showmessgae("您未登录");
        } else {
            dialog2();
        }
    }

    public void dialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("退出登录？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dataHelpler.deletedata();
                finish();
                showmessgae("退出成功");
//                Intent intent=new Intent(UsersActivity.this,MainActivity.class);
//                startActivity(intent);
            }
        }).setNegativeButton("取消", null).show();
    }

    public void showmessgae(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void usaa(View view) {
        Intent intent = new Intent(this, aboutAuthorActivity.class);
        startActivity(intent);
    }
}
