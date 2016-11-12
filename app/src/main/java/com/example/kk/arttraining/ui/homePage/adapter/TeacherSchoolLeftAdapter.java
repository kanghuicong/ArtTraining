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
    private Context context;
    private List<ProvinceBean> provinceBeanLeftList;
    private ProvinceBean provinceBean;


    public TeacherSchoolLeftAdapter(Context context, List<ProvinceBean> provinceBeanLeftList) {
        this.context = context;
        this.provinceBeanLeftList = provinceBeanLeftList;

    }

    @Override
    public int getCount() {
        return provinceBeanLeftList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        provinceBean = provinceBeanLeftList.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_school_province, null);
            holder.province_name = (TextView) convertView.findViewById(R.id.tv_school_province);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.province_name.setText(provinceBean.getName());

        return convertView;
    }


    class ViewHolder {
        TextView province_name;
    }
}
