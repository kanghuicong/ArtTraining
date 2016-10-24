package com.example.kk.arttraining.ui.me.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.OrderInfoBean;
import com.example.kk.arttraining.ui.me.view.OrderActivity;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/23 13:47
 * 说明:
 */
public class OrderAdapter extends BaseAdapter {

    private List<OrderInfoBean> list;
    private Context context;
    private int count;
    ViewHolder holder;

    public OrderAdapter(Context context, int count) {
        this.count = count;
        this.context = context;
    }

    public OrderAdapter(Context context, List<OrderInfoBean> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {

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
