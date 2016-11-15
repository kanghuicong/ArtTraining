package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.bean.Trainees;

import java.util.List;

/**
 * Created by kanghuicong on 2016/11/8.
 * QQ邮箱:515849594@qq.com
 */
public class InstitutionStudentAdapter extends BaseAdapter{
    List<Trainees> traineesList;
    Trainees trainees;
    Context context;

    public InstitutionStudentAdapter(Context context,List<Trainees> traineesList) {
        this.context = context;
        this.traineesList = traineesList;
    }

    @Override
    public int getCount() {
        return traineesList.size();
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
        trainees = traineesList.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.homepage_institution_student_item, null);
            holder.iv_header = (ImageView) convertView.findViewById(R.id.iv_institution_student_header);
            holder.tv_name = (TextView) convertView.findViewById(R.id.iv_institution_student_name);
            holder.tv_school = (TextView) convertView.findViewById(R.id.iv_institution_student_school);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(context).load(trainees.getTra_pic()).into(holder.iv_header);
        holder.tv_name.setText(trainees.getTra_name());
        holder.tv_school.setText(trainees.getTra_school());
        return convertView;
    }


    class ViewHolder {
        ImageView iv_header;
        TextView tv_name;
        TextView tv_school;
    }
}
