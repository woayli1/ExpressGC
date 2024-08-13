package com.gc.expressOL.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gc.expressOL.R;

public class ChaxunActivity extends Activity {

    Spinner spincx; //快递公司
    EditText etcx;  //快递单号

    String[] names = {"ems快递", "国通快递", "汇通快运", "全峰快递", "申通", "顺丰", "天天快递", "万象物流", "圆通速递", "韵达快运", "宅急送", "中通速递"};
    String[] nums = {"ems", "guotongkuaidi", "huitongkuaidi", "quanfengkuaidi", "shentong", "shunfeng", "tiantian", "wxwl", "yuantong", "yunda", "zhaijisong", "zhongtong"};

    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chaxun);

        spincx = findViewById(R.id.spincx);
        etcx = findViewById(R.id.etcx);

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

    public void chaxunonclick(View view) {

        String com = spincx.getSelectedItem().toString();
        String nu = etcx.getText().toString().trim();

        if (nu.length() <= 0) {
            showmessgae("快递单号不能为空");
            return;
        }
        for (int i = 0; i < 12; i++) {
            if (com.equals(names[i])) {
                com = nums[i];
            }
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.kuaidi100.com/result.jsp?com=" + com + "&nu=" + nu + ""));
        startActivity(intent);

    }

    public boolean onTouchEvent(MotionEvent event) {
        //2.让手势识别器生效
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void showmessgae(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void bccx(View view) {
        finish();
    }
}
