package com.example.kk.arttraining.ui.valuation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.modelbean.TecInfoBean;

import java.util.List;

/**
 * Created by kanghuicong on 2016/11/2.
 * QQ邮箱:515849594@qq.com
 */
public class ValuationGridViewAdapter extends BaseAdapter{
    Context context;
    List<TecInfoBean> tecInfoBeanList ;
    TecInfoBean tecInfoBean;

    public ValuationGridViewAdapter(Context context, List<TecInfoBean> tecInfoBeanList) {
        this.context = context;
        this.tecInfoBeanList = tecInfoBeanList;
    }

    @Override
    public int getCount() {
        return tecInfoBeanList.size();
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
        tecInfoBean = tecInfoBeanList.get(position);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.homepage_teacher_grid_item, null);
            holder = new ViewHolder();
            holder.tv_item = (TextView) convertView.findViewById(R.id.tv_teacher_grid_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_item.setText(tecInfoBean.getName());

        return convertView;
    }

    class ViewHolder {
        TextView tv_item;
    }
}
