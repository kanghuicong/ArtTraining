package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.view.MyListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 作者：wschenyongyin on 2016/8/25 11:40
 * 说明:
 */
public class ChoseProvinceAdapter extends BaseAdapter {
    String[] province = {"北京", "天津", "上海", "江西", "河北", "山西", "辽宁", "吉林", "黑龙江", "江苏", "浙江", "安徽", "福建", "重庆", "山东", "河南", "湖北", "湖南", "广东", "海南", "四川", "贵州", "云南", "陕西", "甘肃", "青海", "内蒙古", "广西", "西藏", "宁夏", "新疆", "香港", "澳门", "台湾"};
    Context context;

    public ChoseProvinceAdapter(Context context) {
        this.context = context;

    }

    @Override
    public int getCount() {
        return province.length;
    }

    @Override
    public Object getItem(int position) {
        return province[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.homepage_province_item, null);
            holder = new ViewHolder();
            holder.tv_province = (TextView) convertView.findViewById(R.id.province_name);
//            holder.lv_province_county = (MyListView) convertView.findViewById(R.id.lv_province_county);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_province.setText(province[position]);
        return convertView;
    }

    class ViewHolder {
        TextView tv_province;
        MyListView lv_province_county;
    }
}
