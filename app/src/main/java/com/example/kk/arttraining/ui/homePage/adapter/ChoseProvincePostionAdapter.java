package com.example.kk.arttraining.ui.homePage.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.CitysBean;
import com.example.kk.arttraining.bean.LocationBean;
import com.example.kk.arttraining.bean.parsebean.ParseLocationBean;
import com.example.kk.arttraining.custom.view.MyListView;
import com.example.kk.arttraining.ui.homePage.activity.HomePageMain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kanghuicong on 2016/10/27.
 * QQ邮箱:515849594@qq.com
 */
public class ChoseProvincePostionAdapter extends BaseAdapter {
    Context context;

    List<LocationBean> locationList;
    LocationBean locationBean;


    public ChoseProvincePostionAdapter(Context context, List<LocationBean> locationList) {
        this.context = context;
        this.locationList = locationList;

    }

    @Override
    public int getCount() {
        return locationList.size();
    }

    @Override
    public Object getItem(int position) {
        return locationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        locationBean = locationList.get(position);

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.homepage_province_postion_item, null);
            holder = new ViewHolder();
            holder.province_name = (TextView) convertView.findViewById(R.id.tv_province_name);
            holder.arrow_right = (ImageView) convertView.findViewById(R.id.iv_province_arrow_right);
            holder.arrow_bottom = (ImageView) convertView.findViewById(R.id.iv_province_arrow_bottom);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.arrow_right.setVisibility(View.GONE);
        holder.arrow_bottom.setVisibility(View.VISIBLE);
        holder.province_name.setText(locationBean.getName());

        return convertView;
    }

    class ViewHolder {
        TextView province_name;
        ImageView arrow_right;
        ImageView arrow_bottom;
    }
}

