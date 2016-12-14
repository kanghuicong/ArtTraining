package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.bean.SchoolBean;
import com.example.kk.arttraining.utils.GlideCircleTransform;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/26 11:48
 * 说明:院校listview适配器
 */
public class SchoolListAdapter extends BaseAdapter {

    private ViewHolder holder;
    private Context context;
    private List<SchoolBean> beanList;
    SchoolBean schoolBean;
    int count;

    public SchoolListAdapter(Context context) {
        this.context = context;
    }

    public SchoolListAdapter(Context context, List<SchoolBean> beanList) {
        this.context = context;
        this.beanList = beanList;
        count = beanList.size();
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return beanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        schoolBean = beanList.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_school, null);

            holder.school_pic = (ImageView) convertView.findViewById(R.id.school_iv_pic);
            holder.school_name = (TextView) convertView.findViewById(R.id.tv_school_name);
            holder.school_school_sentiment = (TextView) convertView.findViewById(R.id.tv_school_sentiment_num);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(context).load(schoolBean.getInstitution_pic()).transform(new GlideCircleTransform(context)).error(R.mipmap.default_user_header).into(holder.school_pic);
        holder.school_name.setText(schoolBean.getName()+"");
        holder.school_school_sentiment.setText(schoolBean.getBrowse_num()+"");

        return convertView;
    }

    class ViewHolder {
        ImageView school_pic;
        TextView school_name;
        TextView school_school_sentiment;
    }

    public void ChangeCount(int changeCount) {
        count = changeCount;
    }
}
