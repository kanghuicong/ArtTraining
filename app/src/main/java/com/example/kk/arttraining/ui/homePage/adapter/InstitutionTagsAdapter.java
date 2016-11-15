package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.parsebean.OrgShowBean;

import java.util.List;

/**
 * Created by kanghuicong on 2016/11/15.
 * QQ邮箱:515849594@qq.com
 */
public class InstitutionTagsAdapter extends BaseAdapter {
    List<OrgShowBean.Tags> tagsList;
    OrgShowBean.Tags tags;
    Context context;

    public InstitutionTagsAdapter(Context context,List<OrgShowBean.Tags> tagsList) {
        this.context = context;
        this.tagsList = tagsList;
    }

    @Override
    public int getCount() {
        return tagsList.size();
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
        tags = tagsList.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.homepage_search_hot_gridview, null);
            holder.bt = (Button)convertView.findViewById(R.id.bt_search_hot);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.bt.setText(tags.getTag_name());
        return convertView;
    }


    class ViewHolder {
        Button bt;
    }
}