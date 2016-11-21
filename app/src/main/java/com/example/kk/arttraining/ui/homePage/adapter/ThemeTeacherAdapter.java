package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.bean.parsebean.ParseStatusesBean;

import java.util.List;

/**
 * Created by kanghuicong on 2016/11/15.
 * QQ邮箱:515849594@qq.com
 */
public class ThemeTeacherAdapter extends BaseAdapter {
    Context context;
    List<TecInfoBean> tecInfoBeanList;
    TecInfoBean tecInfoBean;
    int count;

    public ThemeTeacherAdapter(Context context, List<TecInfoBean> tecInfoBeanList) {
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
            convertView = View.inflate(context, R.layout.homepage_teacher_list_item, null);
            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_teacher_item_name);
            holder.tv_professor = (TextView) convertView.findViewById(R.id.tv_teacher_item_professor);
            holder.tv_specialty = (TextView) convertView.findViewById(R.id.tv_teacher_item_specialty);
            holder.tv_comment = (TextView) convertView.findViewById(R.id.tv_teacher_item_comment);
            holder.tv_focus = (TextView) convertView.findViewById(R.id.tv_teacher_item_focus);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_name.setText(tecInfoBean.getName());
        holder.tv_professor.setText(tecInfoBean.getTitle());
        holder.tv_comment.setText("点评:"+tecInfoBean.getComment());
        holder.tv_focus.setText("粉丝:"+tecInfoBean.getFans_num());
        holder.tv_specialty.setText("擅长:"+tecInfoBean.getSpecialty());
        return convertView;
    }

    class ViewHolder {
        TextView tv_name;
        TextView tv_professor;
        TextView tv_specialty;
        TextView tv_comment;
        TextView tv_focus;
    }

    public int getSelfId() {
        return tecInfoBeanList.get(tecInfoBeanList.size() - 1).getTec_id();
    }

    public void ChangeCount(int changeCount) {
        count = changeCount;
    }
}