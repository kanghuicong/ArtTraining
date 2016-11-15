package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.OrgBean;
import com.example.kk.arttraining.utils.GlideCircleTransform;

import java.util.List;

/**
 * Created by kanghuicong on 2016/11/2.
 * QQ邮箱:515849594@qq.com
 */
public class InstitutionFragmentAdapter extends BaseAdapter {
    Context context;
    List<OrgBean> orgBeanList;
    OrgBean orgBean;

    public InstitutionFragmentAdapter(Context context, List<OrgBean> orgBeanList){
        this.context = context;
        this.orgBeanList = orgBeanList;
    }

    @Override
    public int getCount() {
        return orgBeanList.size();
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
        final ViewHolder holder;
        orgBean = orgBeanList.get(position);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.homepage_institution_fragment_item, null);
            holder = new ViewHolder();
            holder.iv_header = (ImageView) convertView.findViewById(R.id.iv_institution_header);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_institution_name);
            holder.tv_comment = (TextView) convertView.findViewById(R.id.tv_institution_comment);
            holder.tv_fans = (TextView) convertView.findViewById(R.id.tv_institution_fans);
            holder.tv_label = (TextView) convertView.findViewById(R.id.tv_institution_label);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(context).load(orgBean.getPic()).transform(new GlideCircleTransform(context)).error(R.mipmap.default_user_header).into(holder.iv_header);
        holder.tv_name.setText(orgBean.getName());
        holder.tv_comment.setText(orgBean.getComment() + "");
        holder.tv_fans.setText(orgBean.getFans_num()+"");
        holder.tv_label.setText(position+"");

        return convertView;
    }

    class ViewHolder {
        ImageView iv_header;
        TextView tv_name;
        TextView tv_comment;
        TextView tv_fans;
        TextView tv_label;
    }
}
