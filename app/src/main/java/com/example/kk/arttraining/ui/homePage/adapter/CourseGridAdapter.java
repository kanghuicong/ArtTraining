package com.example.kk.arttraining.ui.homePage.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.CourseEntity;
import com.example.kk.arttraining.customview.MyGridView;
import com.example.kk.arttraining.ui.homePage.activity.CourseContent;
import com.example.kk.arttraining.ui.homePage.activity.CourseMain;

import java.util.List;

/**
 * Created by kanghuicong on 2016/9/21.
 * QQ邮箱:515849594@qq.com
 */
public class CourseGridAdapter extends BaseAdapter{
    Activity activity;
    MyGridView gridView;
    List<CourseEntity> listCourse;

    public CourseGridAdapter(Activity activity, MyGridView gridView, List<CourseEntity> listCourse){
        this.activity = activity;
        this.gridView = gridView;
        this.listCourse = listCourse;
    }

    @Override
    public int getCount() {
        return listCourse.size();
    }

    @Override
    public CourseEntity getItem(int position) {
        return listCourse.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        CourseEntity coure = listCourse.get(position);
        if (convertView == null) {
            convertView = View.inflate(activity, R.layout.homepage_course_gridview, null);
            holder = new ViewHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.tv_gridview_course);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(coure.getName());
        gridView.setOnItemClickListener(new MyItemClickListener());
        return convertView;
    }

    class MyItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(activity, CourseMain.class);
            activity.startActivity(intent);
        }
    }


    class ViewHolder {
        TextView tv;
    }
}
