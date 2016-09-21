package com.example.kk.arttraining.ui.homePage.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.Course;

import java.util.List;

/**
 * Created by kanghuicong on 2016/9/21.
 * QQ邮箱:515849594@qq.com
 */
public class CourseGridAdapter extends BaseAdapter{
    Activity activity;
    List<Course> listCourse;

    public CourseGridAdapter(Activity activity, List<Course> listCourse){
        this.activity = activity;
        this.listCourse = listCourse;
    }

    @Override
    public int getCount() {
        return listCourse.size();
    }

    @Override
    public Course getItem(int position) {
        return listCourse.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        Course coure = listCourse.get(position);
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.homepage_course_gridview, null);
            holder = new ViewHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.tv_gridview_course);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(coure.getName());
        Log.i("listCourse", coure.getName() + "-------" + position);
        return convertView;
    }

    class ViewHolder {
        TextView tv;
    }
}
