package com.gc.expressOL.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.gc.expressOL.R;
import com.gc.expressOL.activity.ChakanActivity;
import com.gc.expressOL.activity.ReceiveOrderActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/6.
 */

public class OrderAdapter extends BaseAdapter {
    private Context context;
    ArrayList<String> company;
    public Button order;
    String ss;

    Intent intent;

    public OrderAdapter(Context context) {
        this.context = context;
        company = ReceiveOrderActivity.s2;
        ss = ReceiveOrderActivity.ss;
    }

    @Override
    public int getCount() {
        return company.size();
    }

    @Override
    public Object getItem(int position) {
        return company.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view1 = layoutInflater.inflate(R.layout.messagelist, null);

        order = view1.findViewById(R.id.order);
        TextView textView = view1.findViewById(R.id.kd9);
        textView.setText(company.get(position));
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s20 = ReceiveOrderActivity.s1.get(position);
                intent = new Intent(context, ChakanActivity.class);//接单界面
                intent.putExtra("auto_nums", s20);
                intent.putExtra("state", ss);
                context.startActivity(intent);
            }
        });
        return view1;
    }
}
