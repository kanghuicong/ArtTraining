package com.example.kk.arttraining.ui.me.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.me.bean.OrderTecBean;
import com.example.kk.arttraining.utils.GlideCircleTransform;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/12/8 15:19
 * 说明:用于显示订单页面所选老师adapter
 */
public class TecHeaderAdapter extends BaseAdapter {
    private ViewHolder viewHolder;
    private Context context;
    List<OrderTecBean> ass_tec_list;
    OrderTecBean orderTecBean;

    public TecHeaderAdapter(Context context, List<OrderTecBean> ass_tec_list) {
        this.context = context;
        this.ass_tec_list = ass_tec_list;

    }

    @Override
    public int getCount() {
        return ass_tec_list.size();
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
        orderTecBean = ass_tec_list.get(position);
        viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_order_tec_gridview, null);
            viewHolder.tec_name = (TextView) convertView.findViewById(R.id.tv_tec_name);
            viewHolder.valuation_status = (TextView) convertView.findViewById(R.id.tv_valuation_status);
            viewHolder.tec_pic = (ImageView) convertView.findViewById(R.id.iv_tec_pic);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tec_name.setText(orderTecBean.getTec_name());
        if (orderTecBean.isTec_status()) {
            viewHolder.valuation_status.setText("已点评");
            viewHolder.valuation_status.setTextColor(context.getResources().getColor(R.color.blue_overlay));
        } else {
            viewHolder.valuation_status.setText("未点评");
            viewHolder.valuation_status.setTextColor(context.getResources().getColor(R.color.red));
        }
        Glide.with( context.getApplicationContext()).load(orderTecBean.getTec_pic()).error(R.mipmap.default_user_header).transform(new GlideCircleTransform(context)).into(viewHolder.tec_pic);
        viewHolder.tec_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderTecBean = ass_tec_list.get(position);
                Intent intent = new Intent();
                intent.putExtra("tec_id", orderTecBean.getTec_id() + "");
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView tec_name;
        TextView valuation_status;
        ImageView tec_pic;

    }
}
