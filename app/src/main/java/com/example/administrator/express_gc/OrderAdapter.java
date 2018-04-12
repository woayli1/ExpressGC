package com.example.administrator.express_gc;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/6.
 */

public class OrderAdapter extends BaseAdapter {
    private Context context = null;
    ArrayList<String> company=new ArrayList<>();
    public Button order;
    String ss;

    Intent intent;

    public OrderAdapter(Context context) {
        this.context = context;
        company = Receive_orderActivity.s2;
        ss=Receive_orderActivity.ss;
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

        order = (Button) view1.findViewById(R.id.order);
        TextView textView = (TextView) view1.findViewById(R.id.kd9);
        textView.setText(company.get(position).toString());
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s20=Receive_orderActivity.s1.get(position).toString();
                intent = new Intent(context, ChakanActivity.class);//接单界面
                intent.putExtra("auto_nums",s20);
                intent.putExtra("state", ss);
                context.startActivity(intent);
            }
        });
        return view1;
    }
}
