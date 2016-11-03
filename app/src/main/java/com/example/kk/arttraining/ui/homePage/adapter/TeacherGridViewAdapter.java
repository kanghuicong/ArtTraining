package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.function.homepage.Location;
import com.yixia.camera.util.Log;

import java.util.List;
import java.util.Map;

/**
 * Created by kanghuicong on 2016/11/2.
 * QQ邮箱:515849594@qq.com
 */
public class TeacherGridViewAdapter extends BaseAdapter{
    Context context;
    List<Map<String, String>> list;
    Map<String, String> map;

    public TeacherGridViewAdapter(Context context, List<Map<String, String>> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        map = list.get(position);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.homepage_teacher_grid_item, null);
            holder = new ViewHolder();
            holder.tv_item = (TextView) convertView.findViewById(R.id.tv_teacher_grid_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Log.i("tv_item",map.get("name"));
        holder.tv_item.setText(map.get("name"));

        return convertView;
    }

    class ViewHolder {
        TextView tv_item;
    }
}
