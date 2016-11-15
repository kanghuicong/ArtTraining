package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.bean.Teachers;

import java.util.List;

/**
 * Created by kanghuicong on 2016/11/8.
 * QQ邮箱:515849594@qq.com
 */
public class InstitutionTeacherAdapter extends BaseAdapter {
    List<Teachers> teachersList;
    Teachers teachers;
    Context context;

    public InstitutionTeacherAdapter(Context context,List<Teachers> teachersList) {
        this.context = context;
        this.teachersList = teachersList;
    }

    @Override
    public int getCount() {
        return teachersList.size();
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
        teachers = teachersList.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.homepage_institution_teacher_item, null);
            holder.teacher_header = (ImageView) convertView.findViewById(R.id.iv_institution_teacher_header);
            holder.teacher_name = (TextView) convertView.findViewById(R.id.iv_institution_teacher_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(context).load(teachers.getTec_pic()).error(R.mipmap.default_user_header).into(holder.teacher_header);
        holder.teacher_name.setText(teachers.getTec_name());
        
        return convertView;
    }


    class ViewHolder {
        ImageView teacher_header;
        TextView teacher_name;
    }
}
