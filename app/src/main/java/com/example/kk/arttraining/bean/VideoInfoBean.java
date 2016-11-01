package com.example.kk.arttraining.bean;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * 作者：wschenyongyin on 2016/10/31 14:28
 * 说明:视频文件bean
 */
public class VideoInfoBean implements Serializable{
    private String video_url;
    private String video_name;
    private String video_time;
    private String video_size;
    private Bitmap video_pic;

    public VideoInfoBean() {
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public String getVideo_size() {
        return video_size;
    }

    public void setVideo_size(String video_size) {
        this.video_size = video_size;
    }

    public String getVideo_time() {
        return video_time;
    }

    public void setVideo_time(String video_time) {
        this.video_time = video_time;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public Bitmap getVideo_pic() {
        return video_pic;
    }

    public void setVideo_pic(Bitmap video_pic) {
        this.video_pic = video_pic;
    }
}
