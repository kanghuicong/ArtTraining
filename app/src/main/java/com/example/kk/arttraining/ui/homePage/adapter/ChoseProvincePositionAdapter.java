package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.modelbean.LocationBean;

import java.util.List;

/**
 * Created by kanghuicong on 2016/10/27.
 * QQ邮箱:515849594@qq.com
 */
public class ChoseProvincePositionAdapter extends BaseAdapter {
    Context context;

    List<LocationBean> locationList;
    LocationBean locationBean;
    ViewHolder holder;

    public ChoseProvincePositionAdapter(Context context, List<LocationBean> locationList) {
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

        locationBean = locationList.get(position);

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.homepage_province_postion_item, null);
            holder = new ViewHolder();
            holder.province_name = (TextView) convertView.findViewById(R.id.tv_province_name);
            holder.view_splitter = (View) convertView.findViewById(R.id.view_splitter);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.province_name.setText(locationBean.getName());

        if (position == locationList.size() - 1) {
            holder.view_splitter.setVisibility(View.GONE);
        }else {
            holder.view_splitter.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    class ViewHolder {
        TextView province_name;
        View view_splitter;
    }
}

