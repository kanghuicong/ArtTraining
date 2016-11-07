package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.MajorBean;
import com.example.kk.arttraining.ui.school.bean.ProvinceBean;

import java.util.List;

/**
 * Created by kanghuicong on 2016/11/6.
 * QQ邮箱:515849594@qq.com
 */
public class TeacherSchoolLeftAdapter extends BaseAdapter {

    private ViewHolder viewHolder;
    private Context context;
    private List<ProvinceBean> provinceBeanLeftList;


    public TeacherSchoolLeftAdapter(Context context, List<ProvinceBean> provinceBeanLeftList) {
        this.context = context;
        this.provinceBeanLeftList = provinceBeanLeftList;

    }

    @Override
    public int getCount() {
        return 6;
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
