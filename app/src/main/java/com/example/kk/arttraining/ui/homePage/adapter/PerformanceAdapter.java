package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
//import com.google.android.exoplayer.C;

/**
 * Created by kanghuicong on 2016/11/3.
 * QQ邮箱:515849594@qq.com
 */
public class PerformanceAdapter extends BaseAdapter{
    Context context;
    public PerformanceAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return 10;
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
        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.homepage_performance_item, null);
            holder = new ViewHolder();
            holder.iv_posters = (ImageView)convertView.findViewById(R.id.iv_performance_posters);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_performance_title);
            holder.iv_type = (ImageView)convertView.findViewById(R.id.iv_performance_type);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_performance_time);
            holder.tv_address = (TextView) convertView.findViewById(R.id.tv_performance_address);
            holder.tv_price = (TextView) convertView.findViewById(R.id.tv_performance_price);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    class ViewHolder {
        ImageView iv_posters;
        TextView tv_title;
        ImageView iv_type;
        TextView tv_time;
        TextView tv_address;
        TextView tv_price;
    }
}
