package com.example.administrator.express_gc;

import android.os.Bundle;
import android.app.Activity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class aboutAuthorActivity extends Activity {

    TextView aatv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_author);

        aatv=(TextView)findViewById(R.id.aatv);

        aatv.setText("　　我是上海师范大学-信息与机电工程学院-计算机科学与技术专业-的2014届本科毕业生郭灿。" +"\n"+
                "　　此款app由我个人独自完成，经过大量的测试，也许会有漏掉的BUG，在使用中出现BUG时，还请多多见谅，也可直接联系我的邮箱，" +
                "将BUG告知我，我再做调试修改。" +"\n"+
                "　　邮箱地址：guocan537@outlook.com");

        SwipeBackLayout swipeBackLayoutTwo = new SwipeBackLayout(this);
        swipeBackLayoutTwo.setCallBack(new SwipeBackLayout.CallBack() {
            @Override
            public void onFinish() {
                finish();
            }
        });
    }

    public void bcaa(View view) {
        finish();
    }

}
