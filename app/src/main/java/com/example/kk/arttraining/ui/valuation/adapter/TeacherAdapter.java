package com.example.kk.arttraining.ui.valuation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kk.arttraining.R;

/**
 * 作者：wschenyongyin on 2016/10/29 11:15
 * 说明:
 */
public class TeacherAdapter extends BaseAdapter {
    private ViewHolder holder;
    private Context context;

    public TeacherAdapter(Context context) {
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

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_school_province, null);
            holder.school_name = (TextView) convertView.findViewById(R.id.tv_school_province);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

//        holder.school_name.setText();
        return convertView;
    }

    class ViewHolder {
        TextView school_name;
    }
}
