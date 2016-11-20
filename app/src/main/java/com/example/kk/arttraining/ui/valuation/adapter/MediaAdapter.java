package com.example.kk.arttraining.ui.valuation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.MusicInfoBean;
import com.example.kk.arttraining.bean.VideoInfoBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/31 14:49
 * 说明:
 */
public class MediaAdapter extends BaseAdapter {
    private List<MusicInfoBean> musicInfoBeanList;
    private List<VideoInfoBean> videoInfoBeanList;

    private MusicInfoBean musicInfoBean;
    private VideoInfoBean videoInfoBean;

    private ViewHolder holder;
    private Context context;
    private String media_type;
    Object bean;

    private int count;

    public MediaAdapter() {
    }

    public MediaAdapter(Context context, List<MusicInfoBean> musicInfoBeanList) {
        this.context = context;
        this.musicInfoBeanList = musicInfoBeanList;
        media_type = "music";
        count = musicInfoBeanList.size();
    }

    public MediaAdapter(Context context, List<VideoInfoBean> videoInfoBeanList, String video_type) {
        this.context = context;
        this.videoInfoBeanList = videoInfoBeanList;
        media_type = video_type;
        count = videoInfoBeanList.size();
    }


    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {

        String path = null;
        switch (media_type) {
            case "music":
                bean = musicInfoBeanList.get(position);

                break;
            case "video":
                bean = videoInfoBeanList.get(position);
                break;
        }
        return bean;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        switch (media_type) {
            case "music":
                musicInfoBean = musicInfoBeanList.get(position);
                if (convertView == null) {
                    holder=new ViewHolder();
                    convertView = View.inflate(context, R.layout.item_music, null);
                    holder.music_date = (TextView) convertView.findViewById(R.id.item_music_time);
                    holder.music_size = (TextView) convertView.findViewById(R.id.item_music_size);
                    holder.music_title = (TextView) convertView.findViewById(R.id.item_music_name);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.music_date.setText(musicInfoBean.getMusic_time());
                holder.music_size.setText(musicInfoBean.getMusic_size());
                holder.music_title.setText(musicInfoBean.getMusic_name());
                break;
            case "video":
                videoInfoBean = videoInfoBeanList.get(position);
                if (convertView == null) {
                    holder=new ViewHolder();
                    convertView = View.inflate(context, R.layout.item_local_video, null);
                    holder.video_size = (TextView) convertView.findViewById(R.id.item_video_size);
                    holder.video_title = (TextView) convertView.findViewById(R.id.item_video_title);
                    holder.video_date = (TextView) convertView.findViewById(R.id.item_video_time);
                    holder.video_pic = (ImageView) convertView.findViewById(R.id.item_video_pic);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.video_size.setText(videoInfoBean.getVideo_size());
                holder.video_title.setText(videoInfoBean.getVideo_name());
                holder.video_date.setText(videoInfoBean.getVideo_time());
//                Glide.with(context).load(videoInfoBean.getVideo_pic()).asBitmap().into(holder.video_pic);
                holder.video_pic.setImageBitmap(videoInfoBean.getVideo_pic());


                break;
        }
        return convertView;
    }

    class ViewHolder {
        ImageView video_pic;
        TextView video_title;
        String video_url;
        TextView video_size;
        TextView video_date;

        TextView music_title;
        String music_url;
        TextView music_size;
        TextView music_date;
    }
}
