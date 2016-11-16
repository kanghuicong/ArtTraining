package com.example.kk.arttraining.ui.school.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.school.bean.ParseSchoolListBean;
import com.example.kk.arttraining.ui.school.bean.SchoolBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.GlideCircleTransform;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/26 11:48
 * 说明:院校listview适配器
 */
public class SchoolAdapter extends BaseAdapter {

    private ViewHolder holder;
    private Context context;
    private List<SchoolBean> beanList;

    public SchoolAdapter(Context context) {
        this.context = context;
    }

    public SchoolAdapter(Context context, List<SchoolBean> beanList) {
        this.context = context;
        this.beanList = beanList;
    }

    @Override
    public int getCount() {
        return beanList.size();
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
        SchoolBean schoolBean = beanList.get(position);
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
        Glide.with(context).load(Config.SCHOOL_PIC).transform(new GlideCircleTransform(context)).into(holder.school_pic);
        holder.school_name.setText(schoolBean.getName());
        holder.school_school_sentiment.setText(schoolBean.getIntroduction());

        UIUtil.showLog("schoolAdapter", "执行次数" + position);

        return convertView;
    }

    class ViewHolder {
        ImageView school_pic;
        TextView school_name;
        TextView school_school_sentiment;

    }
}
