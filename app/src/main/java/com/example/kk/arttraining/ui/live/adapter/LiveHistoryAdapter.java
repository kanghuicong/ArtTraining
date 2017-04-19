package com.example.kk.arttraining.ui.live.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.view.FilletImageView;
import com.example.kk.arttraining.custom.view.GlideRoundTransform;
import com.example.kk.arttraining.ui.homePage.bean.LiveHistoryBean;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2017/4/14.
 * QQ邮箱:515849594@qq.com
 */
public class LiveHistoryAdapter extends BaseAdapter {
    Context context;
    List<LiveHistoryBean> historyList;
    LiveHistoryBean historyBean;
    int count;
    ViewHolder holder;

    public LiveHistoryAdapter(Context context, List<LiveHistoryBean> historyList) {
        this.context = context;
        this.historyList = historyList;
        count = historyList.size();
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public LiveHistoryBean getItem(int position) {
        return historyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public int getChapterId(int position) {
        return historyList.get(position).getChapter_id();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        historyBean = historyList.get(position);
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.live_history_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        Glide.with(context).load(historyBean.getThumbnail()).diskCacheStrategy(DiskCacheStrategy.SOURCE).thumbnail(0.5f).error(R.mipmap.default_video_icon).into(holder.ivHomepageLiveHeader);
        holder.tvLiveTeacher.setText(historyBean.getName());
//        holder.tvHistoryFans.setText(historyBean.getFans_num() + "");
        holder.tvLiveCourse.setText(historyBean.getChapter_name());
        holder.tvLiveType.setText(historyBean.getStart_time());
        holder.tvLiveBrowse.setText(historyBean.getBrowse_number() + "");
        if (historyBean.getIs_free() == 0) {
            holder.tvLivePrice.setText("¥" + historyBean.getRecord_price());
            holder.tvLivePrice.setTextColor(context.getResources().getColor(R.color.color_bule2));
        } else {
            holder.tvLivePrice.setText("免费");
            holder.tvLivePrice.setTextColor(context.getResources().getColor(R.color.green));
        }

        return convertView;
    }

    public void changeCount(int changeCount) {
        count = changeCount;
    }

    public int getSelfInfo() {
        return historyList.get(historyList.size() - 1).getRoom_id();
    }

    static class ViewHolder {
        @InjectView(R.id.iv_homepage_live_header)
        FilletImageView ivHomepageLiveHeader;
        @InjectView(R.id.tv_live_type)
        TextView tvLiveType;
        @InjectView(R.id.tv_live_course)
        TextView tvLiveCourse;
        @InjectView(R.id.tv_live_teacher)
        TextView tvLiveTeacher;
        @InjectView(R.id.tv_live_browse)
        TextView tvLiveBrowse;
        @InjectView(R.id.tv_live_price)
        TextView tvLivePrice;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}
