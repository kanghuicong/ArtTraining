package com.example.kk.arttraining.ui.discover.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.platform.comapi.map.C;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.GroupBean;

import java.util.List;

/**
 * Created by kanghuicong on 2016/11/10.
 * QQ邮箱:515849594@qq.com
 */
public class DiscoverGroupAdapter extends BaseAdapter{
    Context context;
    List<GroupBean> groupBeanList;
    GroupBean groupBean;

    public DiscoverGroupAdapter(Context context, List<GroupBean> groupBeanList) {
        this.context = context;
        this.groupBeanList = groupBeanList;
    }

    @Override
    public int getCount() {
        return 3;
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
        ViewHolder holder;
        groupBean = groupBeanList.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.discover_circle_group_item, null);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }


    class ViewHolder {
    }
}
