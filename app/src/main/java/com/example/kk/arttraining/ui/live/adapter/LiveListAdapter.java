package com.example.kk.arttraining.ui.live.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.view.FilletImageView;
import com.example.kk.arttraining.custom.view.GlideRoundTransform;
import com.example.kk.arttraining.ui.homePage.bean.LiveListBean;
import com.example.kk.arttraining.utils.LruCacheUtils;
import com.example.kk.arttraining.utils.PhotoLoader;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by kanghuicong on 2017/1/7.
 * QQ邮箱:515849594@qq.com
 */
public class LiveListAdapter extends BaseAdapter {
    int count;
    Context context;
    ViewHolder holder;
    List<LiveListBean> liveList;
    LiveListBean liveListBean;
    Drawable drawable;

    public LiveListAdapter(Context context, List<LiveListBean> liveList) {
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

    public int getLiveRoom(int position) {
        return liveList.get(position).getRoom_id();
    }

    public int getLiveChapter(int position) {
        return liveList.get(position).getChapter_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        liveListBean = liveList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.homepage_live_item, null);
            holder = new ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //设置背景
        String thumbnail = liveListBean.getThumbnail();
        Glide.with(context).load(thumbnail).diskCacheStrategy(DiskCacheStrategy.SOURCE).thumbnail(0.5f).error(R.mipmap.default_video_icon).into(holder.ivHomepageLiveHeader);

        holder.tvLiveTeacher.setText(liveListBean.getName());
        holder.tvLiveBrowse.setText(liveListBean.getBrowse_number() + "");

        int type = liveListBean.getLive_status();
        switch (type) {
            //还未开始直播状态
            case 0:
                holder.tvLiveType.setText(liveListBean.getPre_time());
                holder.tvLiveType.setTextColor(context.getResources().getColor(R.color.white));
                holder.tvLiveType.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                holder.tvLiveCourse.setText(liveListBean.getChapter_name());
                break;
            //正在直播
            case 1:
                holder.tvLiveType.setText("直播中");
                holder.tvLiveType.setTextColor(context.getResources().getColor(R.color.color_bule));
                holder.tvLiveCourse.setText(liveListBean.getChapter_name());
                if (drawable == null) {
                    drawable = context.getResources().getDrawable(R.mipmap.red_dot);
                }
                holder.tvLiveType.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
                break;
            //直播结束
            case 2:
                holder.tvLiveType.setText("已完结，共" + liveListBean.getChapter_number() + "课时");
                holder.tvLiveType.setTextColor(context.getResources().getColor(R.color.rb_text));
                holder.tvLiveType.setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
                holder.tvLiveCourse.setText(liveListBean.getLive_name());
                break;
        }

        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.iv_homepage_live_header)
        FilletImageView ivHomepageLiveHeader;
        @InjectView(R.id.tv_live_teacher)
        TextView tvLiveTeacher;
        @InjectView(R.id.tv_live_browse)
        TextView tvLiveBrowse;
        @InjectView(R.id.tv_live_type)
        TextView tvLiveType;
        @InjectView(R.id.tv_live_course)
        TextView tvLiveCourse;
        @InjectView(R.id.ll_homepage_authority)
        LinearLayout llHomepageAuthority;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    public void ChangeCount(int changeCount) {
        count = changeCount;
    }

    public LiveListBean getSelfInfo() {
        return liveList.get(liveList.size() - 1);
    }




}
