package com.example.kk.arttraining.ui.me.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.modelbean.HelpBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/17 16:13
 * 说明:帮助页面adapter
 */
public class HelpAdapter extends BaseAdapter {
    private List<HelpBean> helpBeanList;
    HelpBean helpBean;
    Context context;
    ViewHolder viewHolder;

    public HelpAdapter(Context context, List<HelpBean> helpBeanList) {
       this. helpBeanList=helpBeanList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return helpBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return helpBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        helpBean = helpBeanList.get(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_me_help, null);
            viewHolder.tv_help = (TextView) convertView.findViewById(R.id.tv_me_item_help);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_help.setText(helpBean.getTitle());
        return convertView;
    }

    class ViewHolder {
        TextView tv_help;
    }
}
