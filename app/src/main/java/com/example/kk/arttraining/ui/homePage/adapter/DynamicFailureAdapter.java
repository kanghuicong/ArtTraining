package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.kk.arttraining.R;

/**
 * Created by kanghuicong on 2016/11/15.
 * QQ邮箱:515849594@qq.com
 */
public class DynamicFailureAdapter extends BaseAdapter{

    Context context;
    public DynamicFailureAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 1;
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
        convertView = View.inflate(context, R.layout.homepage_dynamic_advertisement_item, null);
        return convertView;
    }
}
