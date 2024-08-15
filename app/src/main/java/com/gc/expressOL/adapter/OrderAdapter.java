package com.gc.expressOL.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ObjectUtils;
import com.gc.expressOL.R;
import com.gc.expressOL.bean.OrderListBean;

import java.util.ArrayList;

/**
 * Created by gc on 2018/3/6.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.RefreshItemHolder> {

    private final Context context;
    private final ArrayList<OrderListBean> orderListBeanArrayList;
    public OnItemClickListener onItemClickListener;

    public OrderAdapter(Context context, ArrayList<OrderListBean> orderListBeans) {
        this.context = context;
        orderListBeanArrayList = orderListBeans;
    }

    @NonNull
    @Override
    public RefreshItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_list, parent, false);
        return new RefreshItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RefreshItemHolder holder, int position) {
        holder.llItemOrder.setOnClickListener(view -> {
            if (ObjectUtils.isNotEmpty(onItemClickListener)) {
                onItemClickListener.onItemClick(holder.getAbsoluteAdapterPosition(), 0);
            }
        });

        holder.tvCompanyName.setText(orderListBeanArrayList.get(position).getExc());
    }

    @Override
    public int getItemCount() {
        return orderListBeanArrayList.size();
    }

    public static class RefreshItemHolder extends RecyclerView.ViewHolder {

        LinearLayout llItemOrder;
        TextView tvCompanyName;

        public RefreshItemHolder(@NonNull View itemView) {
            super(itemView);
            llItemOrder = itemView.findViewById(R.id.ll_item_order);
            tvCompanyName = itemView.findViewById(R.id.tv_company_name);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
