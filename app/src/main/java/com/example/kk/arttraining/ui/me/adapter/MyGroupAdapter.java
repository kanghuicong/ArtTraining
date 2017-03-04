package com.example.kk.arttraining.ui.me.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.modelbean.GroupBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/10 19:34
 * 说明:我的小组适配器
 */
public class MyGroupAdapter extends BaseAdapter {
    private Context context;
    private List<GroupBean> groupBeanList;
    private ViewHolder holder;
    private int count;
    private GroupBean groupBean;

    public MyGroupAdapter(Context context, List<GroupBean> groupBeanList) {
        this.groupBeanList = groupBeanList;
        this.context = context;
        count = groupBeanList.size();

    }

    @Override
    public int getCount() {
        return 10;
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
        groupBean = groupBeanList.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_mygroup_lv, null);
            holder.group_name = (TextView) convertView.findViewById(R.id.mygroup_group_name);
            holder.group_userNum = (TextView) convertView.findViewById(R.id.mygroup_group_user_num);
            holder.group_pic = (ImageView) convertView.findViewById(R.id.group_pic);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.group_name.setText(groupBean.getName());
        holder.group_userNum.setText(groupBean.getUsers_num());
        Glide.with(context).load(groupBean.getPic()).into(holder.group_pic);

        return convertView;
    }

    public void RefreshCount(int count) {
        this.count = count;
    }

    class ViewHolder {
        ImageView group_pic;
        TextView group_name;
        TextView group_userNum;
    }

}
