package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.modelbean.MajorBean;

import java.util.List;

/**
 * Created by kanghuicong on 2016/11/6.
 * QQ邮箱:515849594@qq.com
 */
public class TeacherMajorRightAdapter extends BaseAdapter {
    Context context;
    List<MajorBean> majorBeanRightList;
    MajorBean majorBean;
    int count;

    public TeacherMajorRightAdapter(Context context, List<MajorBean> majorBeanRightList) {
        this.context = context;
        this.majorBeanRightList = majorBeanRightList;
        count = majorBeanRightList.size();
    }

    @Override
    public int getCount() {
        return count;
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
        majorBean = majorBeanRightList.get(position);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.homepage_teacher_major_right_item, null);
            holder = new ViewHolder();
            holder.tv_major = (TextView) convertView.findViewById(R.id.tv_major_right);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_major.setText(majorBean.getMajor_name());
        return convertView;
    }

    class ViewHolder {
        TextView tv_major;
    }

    public void changeCount(int changecount){
        count=changecount;
    }

}