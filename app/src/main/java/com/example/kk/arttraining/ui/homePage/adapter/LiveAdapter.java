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
import com.example.kk.arttraining.ui.homePage.bean.LiveListBean;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2017/1/7.
 * QQ邮箱:515849594@qq.com
 */
public class LiveAdapter extends BaseAdapter {
    int count;
    Context context;
    ViewHolder viewHolder;
    List<LiveListBean> liveList;

    public LiveAdapter(Context context,List<LiveListBean> liveList) {
        this.context = context;
        this.liveList = liveList;
        count = liveList.size();
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

    public void ChangeCount(int changeCount) {
        count = changeCount;
    }

    public int getSelfId() {
        return liveList.get(liveList.size() - 1).getRoom_id();
    }
}
