package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.bean.Course;

import java.util.List;

/**
 * Created by kanghuicong on 2016/11/9.
 * QQ邮箱:515849594@qq.com
 */
public class InstitutionCourseAdapter extends BaseAdapter{
    Context context;
    List<Course> courseList;
    Course course;

    public  InstitutionCourseAdapter(Context context,List<Course> courseList) {
        this.context = context;
        this.courseList = courseList;
    }

    @Override
    public int getCount() {
        return courseList.size();
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
        ViewHolder holder;
        course = courseList.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.homepage_institution_course_item, null);
            holder.tv_id = (TextView) convertView.findViewById(R.id.tv_institution_course_id);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_institution_course_name);
            holder.tv_instro = (TextView) convertView.findViewById(R.id.tv_institution_course_intro);
            holder.tv_pay = (TextView) convertView.findViewById(R.id.tv_institution_course_pay_zh);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_id.setText(course.getCourse_id()+"");
        holder.tv_name.setText(course.getCourse_name());
        holder.tv_instro.setText(course.getCourse_intro());
        holder.tv_pay.setText(course.getCourse_pay_zh());


        return convertView;
    }


    class ViewHolder {
        TextView tv_id;
        TextView tv_name;
        TextView tv_instro;
        TextView tv_pay;
    }
}
