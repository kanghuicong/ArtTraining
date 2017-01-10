package com.example.kk.arttraining.ui.homePage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.view.FilletImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2017/1/7.
 * QQ邮箱:515849594@qq.com
 */
public class LiveAdapter extends BaseAdapter {
    Context context;
    ViewHolder viewHolder;

    public LiveAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 2;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.homepage_live_item, null);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.iv_homepage_live_header)
        FilletImageView ivHomepageLiveHeader;
        @InjectView(R.id.tv_live_teacher)
        TextView tvLiveTeacher;
        @InjectView(R.id.tv_live_course)
        TextView tvLiveCourse;
        @InjectView(R.id.tv_live_time)
        TextView tvLiveTime;
        @InjectView(R.id.ll_homepage_authority)
        LinearLayout llHomepageAuthority;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
