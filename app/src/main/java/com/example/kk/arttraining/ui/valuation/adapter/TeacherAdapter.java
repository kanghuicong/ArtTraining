package com.example.kk.arttraining.ui.valuation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.GlideCircleTransform;

import org.w3c.dom.Text;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/29 10:46
 * 说明:
 */
public class TeacherAdapter extends BaseAdapter {
    private Context context;
    private ViewHolder holder;
    private List<TecInfoBean> tecInfoBeanList;

    public TeacherAdapter(Context context) {
        this.context = context;
    }

    public TeacherAdapter(Context context, List<TecInfoBean> tecInfoBeanList) {
        this.tecInfoBeanList = tecInfoBeanList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 15;
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
//        TecInfoBean tecInfoBean = tecInfoBeanList.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.valuation_item_teacher, null);
            holder.teacher_header = (ImageView) convertView.findViewById(R.id.valuation_item_techer_iv_header);
            holder.tcacher_name = (TextView) convertView.findViewById(R.id.valuation_item_techer_tv_name);
            holder.teacher_school = (TextView) convertView.findViewById(R.id.valuation_item_techer_tv_school);
            holder.teacher_position = (TextView) convertView.findViewById(R.id.valuation_item_techer_tv_position);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(context).load(Config.USER_HEADER_Url).transform(new GlideCircleTransform(context)).into(holder.teacher_header);
//        holder.tcacher_name.setText(tecInfoBean.getName());
//        holder.teacher_school.setText(tecInfoBean.getCollege());
//        holder.teacher_position.setText(tecInfoBean.getSpecialty());

        return convertView;
    }

    class ViewHolder {
        //头像
        ImageView teacher_header;
        //姓名
        TextView tcacher_name;
        //学校
        TextView teacher_school;
        //职称
        TextView teacher_position;
    }
}
