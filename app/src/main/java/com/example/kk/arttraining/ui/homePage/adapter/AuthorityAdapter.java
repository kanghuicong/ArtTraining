package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.utils.ScreenUtils;
import com.example.kk.arttraining.utils.UIUtil;

/**
 * Created by kanghuicong on 2016/10/19.
 * QQ邮箱:515849594@qq.com
 */
public class AuthorityAdapter extends BaseAdapter {
    private Context context;
    int width;

    public AuthorityAdapter(Context context) {
        this.context = context;
        width = ScreenUtils.getScreenWidth(context);
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

        convertView = LayoutInflater.from(context).inflate(R.layout.homepage_authority_item, null);
        LinearLayout layout = (LinearLayout)convertView.findViewById(R.id.ll_homepage_authority);
        //设置Item宽度

        ScreenUtils.accordWidth(layout,width,1,2);

        return convertView;
    }
}