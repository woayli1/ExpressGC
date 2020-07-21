package com.example.administrator.express_gc;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

public class aboutAuthorActivity extends Activity {

    TextView aatv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_author);

        aatv = findViewById(R.id.aatv);

        aatv.setText(R.string.activity_about_author_txt);

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
