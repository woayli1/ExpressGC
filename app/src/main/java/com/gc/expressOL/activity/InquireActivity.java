package com.gc.expressOL.activity;

import android.content.Intent;
import android.net.Uri;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.blankj.utilcode.util.ObjectUtils;
import com.gc.expressOL.R;
import com.gc.expressOL.base.BaseActivity;

/**
 * 快递查询
 */
public class InquireActivity extends BaseActivity {

    private final String[] companyNames = {"ems快递", "国通快递", "汇通快运", "全峰快递", "申通", "顺丰", "天天快递", "万象物流", "圆通速递", "韵达快运", "宅急送", "中通速递"};
    private final String[] companyEN = {"ems", "guotongkuaidi", "huitongkuaidi", "quanfengkuaidi", "shentong", "shunfeng", "tiantian", "wxwl", "yuantong", "yunda", "zhaijisong", "zhongtong"};


    @Override
    public void onBind() {
        super.onBind();

        Spinner spinnerItem = findViewById(R.id.spinner_item);
        EditText expressNum = findViewById(R.id.et_express_num);
        Button btInquire = findViewById(R.id.bt_inquire);

        btInquire.setOnClickListener(view -> {
            String item = spinnerItem.getSelectedItem().toString();
            String num = expressNum.getText().toString().trim();

            if (ObjectUtils.isEmpty(num)) {
                showToast("快递单号不能为空");
                return;
            }
            for (int i = 0; i < 12; i++) {
                if (item.equals(companyNames[i])) {
                    item = companyEN[i];
                }
            }

            //https://www.kuaidi.com/index-ajaxselectinfo-75614579193132.html
            //https://www.kuaidi.com/index-ajaxselectcourierinfo-75614579193132-zhongtong-KDQUERY1723629509246.html
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("http://www.kuaidi100.com/result.jsp?com=" + item + "&nu=" + num));
            startActivity(intent);
        });

    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_inquire;
    }

    @Override
    public String setTitle() {
        return "快递查询";
    }

}
