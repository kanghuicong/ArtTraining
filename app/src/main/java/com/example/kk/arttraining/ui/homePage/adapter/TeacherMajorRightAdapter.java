package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.MajorBean;
import com.example.kk.arttraining.bean.SearchEntity;
import com.example.kk.arttraining.custom.view.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kanghuicong on 2016/11/6.
 * QQ邮箱:515849594@qq.com
 */
public class TeacherMajorRightAdapter extends BaseAdapter {
    Context context;
    MajorCallBack majorCallBack;
    List<MajorBean> majorBeanLeftList;
    List<MajorBean> son_majors;
    MajorBean majorBean;

    public TeacherMajorRightAdapter(Context context, List<MajorBean> majorBeanLeftList, MajorCallBack majorCallBack) {
        this.context = context;
        this.majorCallBack = majorCallBack;
        this.majorBeanLeftList = majorBeanLeftList;
    }

    @Override
    public int getCount() {
        return 5;
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
        majorBean = majorBeanLeftList.get(position);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.homepage_teacher_major_right_item, null);
            holder = new ViewHolder();
            holder.tv_major = (TextView) convertView.findViewById(R.id.tv_major_right);
            holder.gv_major = (MyGridView) convertView.findViewById(R.id.gv_major_right);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_major.setText(majorBean.getMajor_name());

        son_majors = majorBean.getSon_majors();

        List<Map<String, String>> mList = new ArrayList<Map<String, String>>();
        for (int i = 0; i < son_majors.size(); i++) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("content", son_majors.get(i).getMajor_name());
            mList.add(map);
        }

        SimpleAdapter gv_adapter = new SimpleAdapter(context, mList,
                R.layout.homepage_province_grid_item, new String[]{"content"},
                new int[]{R.id.tv_province_hot});
        holder.gv_major.setAdapter(gv_adapter);
        holder.gv_major.setOnItemClickListener(new MajorItemClick());

        return convertView;
    }

    class ViewHolder {
        TextView tv_major;
        MyGridView gv_major;
    }

    private class MajorItemClick implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            majorCallBack.getMajorCallBack(son_majors.get(position).getMajor_name());
        }
    }

    public interface MajorCallBack {
        void getMajorCallBack(String major);
    }
}