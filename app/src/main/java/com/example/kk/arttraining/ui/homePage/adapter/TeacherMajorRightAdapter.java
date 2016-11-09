package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.MajorBean;
import com.example.kk.arttraining.bean.SearchEntity;
import com.example.kk.arttraining.custom.view.MyGridView;

import java.util.List;

/**
 * Created by kanghuicong on 2016/11/6.
 * QQ邮箱:515849594@qq.com
 */
public class TeacherMajorRightAdapter extends BaseAdapter {
    Context context;
    MajorCallBack majorCallBack;

    public TeacherMajorRightAdapter(Context context, List<MajorBean> majorBeanLeftList,MajorCallBack majorCallBack) {
        this.context = context;
        this.majorCallBack = majorCallBack;
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

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.homepage_teacher_major_right_item, null);
            holder = new ViewHolder();
            holder.tv_major = (TextView) convertView.findViewById(R.id.tv_major_right);
            holder.gv_major = (MyGridView) convertView.findViewById(R.id.gv_major_right);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

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

            majorCallBack.getMajorCallBack("");
        }
    }

    public interface MajorCallBack{
        void getMajorCallBack(String major);
    }
}