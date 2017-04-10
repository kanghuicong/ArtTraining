package com.example.kk.arttraining.ui.course.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.view.FilletImageView;
import com.example.kk.arttraining.ui.course.bean.CourseBean;
import com.example.kk.arttraining.utils.LruCacheUtils;
import com.example.kk.arttraining.utils.PhotoLoader;
import com.example.kk.arttraining.utils.ScreenUtils;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2016/12/16.
 * QQ邮箱:515849594@qq.com
 */
public class CourseListAdapter extends BaseAdapter {

    Context context;
    List<CourseBean> course_list;
    CourseBean courseBean;
    int count;
    //    int width;
    ViewHolder holder;
    int RepeatPosition = -1;
    int i = 0;

    public CourseListAdapter(Context context, List<CourseBean> course_list) {
        this.context = context;
        this.course_list = course_list;
        count = course_list.size();
//        width = ScreenUtils.getScreenWidth(context);
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return course_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        if (position == RepeatPosition) {
//            UIUtil.showLog("course", position + "-----" + "1");
//            //position==0会无限加载，对布局修改过好多次，也不觉得是布局问题
//        } else {
        courseBean = course_list.get(position);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.course_listview_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        UIUtil.showLog("course", position + "-----" + "2");
        Glide.with(context).load(courseBean.getIcon_url()).diskCacheStrategy(DiskCacheStrategy.SOURCE).error(R.mipmap.dynamic_music_pic).into(holder.ivCourseIcon);
        holder.tvCourseName.setText(courseBean.getCourse_name());
        holder.tvCourseTeacherName.setText(courseBean.getTeacher_name());
        RepeatPosition = position;
//        }
        return convertView;
    }


    public void changeCount(int changeCount) {
        count = changeCount;
    }

    public int getSelfId() {
        if (count > 1) {
            return course_list.size();
        } else {
            return -1;
        }
    }

    static class ViewHolder {
        @InjectView(R.id.iv_course_icon)
        FilletImageView ivCourseIcon;
        @InjectView(R.id.tv_course_name)
        TextView tvCourseName;
        @InjectView(R.id.tv_course_teacher_name)
        TextView tvCourseTeacherName;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
