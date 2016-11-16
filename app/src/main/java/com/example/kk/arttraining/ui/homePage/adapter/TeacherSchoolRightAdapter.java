package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.MajorBean;
import com.example.kk.arttraining.ui.school.bean.SchoolBean;

import java.util.List;

/**
 * Created by kanghuicong on 2016/11/6.
 * QQ邮箱:515849594@qq.com
 */
public class TeacherSchoolRightAdapter extends BaseAdapter {
    Context context;
    List<SchoolBean> schoolBeanRightList;
    SchoolBean schoolBean;

    public TeacherSchoolRightAdapter(Context context, List<SchoolBean> schoolBeanRightList) {
        this.context = context;
        this.schoolBeanRightList = schoolBeanRightList;
    }

    @Override
    public int getCount() {
        return schoolBeanRightList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        schoolBean = schoolBeanRightList.get(position);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_school_province, null);
            holder = new ViewHolder();
            holder.tv_name = (TextView)convertView.findViewById(R.id.tv_school_province);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_name.setText(schoolBean.getName());

        return convertView;
    }

    class ViewHolder {
        TextView tv_name;
    }
}