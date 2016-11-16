package com.example.kk.arttraining.ui.me.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.OrderBean;
import com.example.kk.arttraining.pay.PayActivity;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/23 13:47
 * 说明:
 */
public class OrderAdapter extends BaseAdapter {

    private List<OrderBean> list;
    private OrderBean orderBean;
    private Context context;
    private int count;
    ViewHolder holder;

    public OrderAdapter(Context context, int count) {
        this.count = count;
        this.context = context;
    }

    public OrderAdapter(Context context, List<OrderBean> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        orderBean = list.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_me_order, null);
            holder.orderId = (TextView) convertView.findViewById(R.id.item_tv_orderId);
            holder.orderNum = (TextView) convertView.findViewById(R.id.item_tv_orderNum);
            holder.orderTitle = (TextView) convertView.findViewById(R.id.item_tv_orderTitle);
            holder.orderPrice = (TextView) convertView.findViewById(R.id.item_tv_orderPrice);
            holder.btnOrder = (Button) convertView.findViewById(R.id.item_btn_order);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final int status = orderBean.getOrder_status();
        if (status == 0) {
            holder.btnOrder.setBackgroundResource(R.mipmap.icon_pay_success);
        } else if (status == 1) {
            holder.btnOrder.setBackgroundResource(R.mipmap.icon_payment);
        } else if (status == 2) {

        }

        holder.btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderBean = list.get(position);
                switch (status) {
                    case 0:
                        Intent intent = new Intent(context, PayActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("order_bean", orderBean);
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                        break;
                    case 1:
                        break
                                ;
                }
            }
        });

        return convertView;
    }

    class ViewHolder {
        TextView orderId;
        TextView orderTitle;
        TextView orderNum;
        TextView orderPrice;
        Button btnOrder;
    }

}
