package com.example.kk.arttraining.ui.me.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.me.bean.CouponBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/1 19:09
 * 说明:
 */
public class CouponAdapter extends BaseAdapter {

    private ViewHolder viewHolder;
    Context context;
    List<CouponBean> couponBeanList;

    public CouponAdapter(Context context){
        this.context=context;
    }

    public CouponAdapter(Context context, List<CouponBean> couponBeanList) {

        this.context = context;
        this.couponBeanList = couponBeanList;
    }

    @Override
    public int getCount() {
        return couponBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return couponBeanList.get(position);
//        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CouponBean couponBean = couponBeanList.get(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_coupon, null);
//            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.item_coupon_checkbox);
            viewHolder.coupon_describe = (TextView) convertView.findViewById(R.id.coupon_describe);
            viewHolder.coupon_type = (TextView) convertView.findViewById(R.id.coupon_type);
            viewHolder.coupon_validity_date = (TextView) convertView.findViewById(R.id.coupon_validity_date);
            viewHolder.coupon_value = (TextView) convertView.findViewById(R.id.coupon_value);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.coupon_value.setText(couponBean.getFace_value());
        viewHolder.coupon_describe.setText(couponBean.getDescribe());
        viewHolder.coupon_type.setText(couponBean.getCoupon_type()+"");
        viewHolder.coupon_validity_date.setText(couponBean.getExpiry_date());

        return convertView;
    }

    class ViewHolder {
        TextView coupon_type;
        TextView coupon_describe;
        TextView coupon_validity_date;
        TextView coupon_value;
        CheckBox checkBox;
    }
}
