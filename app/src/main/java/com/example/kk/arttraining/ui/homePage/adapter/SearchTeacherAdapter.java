package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.modelbean.TecInfoBean;
import com.example.kk.arttraining.custom.view.GlideCircleTransform;

import java.util.List;

/**
 * Created by kanghuicong on 2016/11/15.
 * QQ邮箱:515849594@qq.com
 */
public class SearchTeacherAdapter extends BaseAdapter {
    Context context;
    List<TecInfoBean> tecInfoBeanList;
    TecInfoBean tecInfoBean;
    int count;

    public SearchTeacherAdapter(Context context, List<TecInfoBean> tecInfoBeanList) {
        this.context = context;
        this.tecInfoBeanList = tecInfoBeanList;
        count = tecInfoBeanList.size();
    }

    @Override
    public int getCount() {
        return count;
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
        final ViewHolder holder;
        tecInfoBean = tecInfoBeanList.get(position);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.homepage_search_teacher_item, null);
            holder = new ViewHolder();
            holder.iv_header = (ImageView) convertView.findViewById(R.id.iv_teacher_list_header);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_teacher_item_name);
            holder.tv_professor = (TextView) convertView.findViewById(R.id.tv_teacher_item_professor);
            holder.tv_specialty = (TextView) convertView.findViewById(R.id.tv_teacher_item_specialty);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(context).load(tecInfoBean.getBg_pic()).transform(new GlideCircleTransform(context)).error(R.mipmap.default_user_header).into(holder.iv_header);
        holder.tv_name.setText(tecInfoBean.getName());
        holder.tv_professor.setText(tecInfoBean.getTitle());
        holder.tv_specialty.setText("擅长:"+tecInfoBean.getSpecialty());


        return convertView;
    }

    class ViewHolder {
        ImageView iv_header;
        TextView tv_name;
        TextView tv_professor;
        TextView tv_specialty;
    }

    public int getSelfId() {
        return tecInfoBeanList.get(tecInfoBeanList.size() - 1).getTec_id();
    }

    public void ChangeCount(int changeCount) {
        count = changeCount;
    }
}