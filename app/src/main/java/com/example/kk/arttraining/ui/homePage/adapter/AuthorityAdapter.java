package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.kk.arttraining.R;

/**
 * Created by kanghuicong on 2016/10/19.
 * QQ邮箱:515849594@qq.com
 */
public class AuthorityAdapter extends BaseAdapter {
    private Context mContext;
    private ViewHolder holder;

    public AuthorityAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.homepage_authority_item, null);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Log.i("authority", position+"---");
        return convertView;
    }

    private class ViewHolder {

    }
}