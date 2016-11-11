package com.example.kk.arttraining.ui.homePage.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baidu.platform.comapi.map.C;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.CitysBean;
import com.example.kk.arttraining.bean.MajorBean;
import com.example.kk.arttraining.ui.school.bean.ProvinceBean;

import java.util.List;

/**
 * Created by kanghuicong on 2016/11/6.
 * QQ邮箱:515849594@qq.com
 */
public class TeacherMajorLeftAdapter extends BaseAdapter {

    private ViewHolder viewHolder;
    private Context context;
    List<MajorBean> majorBeanList;
    MajorBean majorBean;


    public TeacherMajorLeftAdapter(Context context, List<MajorBean> majorBeanList) {
        this.context = context;
        this.majorBeanList = majorBeanList;
    }

    @Override
    public int getCount() {
        return 5;
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
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_school_province, null);
            viewHolder.province_name = (TextView) convertView.findViewById(R.id.tv_school_province);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }


    class ViewHolder {
        TextView province_name;
    }
}
