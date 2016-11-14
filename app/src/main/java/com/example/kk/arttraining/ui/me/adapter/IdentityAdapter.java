package com.example.kk.arttraining.ui.me.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.me.bean.IdentityBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/13 10:49
 * 说明:身份列表adapter
 */
public class IdentityAdapter extends BaseAdapter {
    List<IdentityBean> identityBeanList;
    private ViewHolder holder;
    private Context context;
    private int count;

    public IdentityAdapter(Context context, List<IdentityBean> identityBeanList) {
        this.identityBeanList = identityBeanList;
        count=identityBeanList.size();
        this.context = context;

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
        IdentityBean identityBean = identityBeanList.get(position);
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_me_identity, null);
            holder.identity_name = (TextView) convertView.findViewById(R.id.tv_identity);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.identity_name.setText(identityBean.getIdentity_name());
        return convertView;
    }

    class ViewHolder {
        TextView identity_name;

    }
}
