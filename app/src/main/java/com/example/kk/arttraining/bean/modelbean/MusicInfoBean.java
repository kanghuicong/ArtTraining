package com.example.kk.arttraining.bean.modelbean;

import java.io.Serializable;

/**
 * 作者：wschenyongyin on 2016/10/31 14:28
 * 说明:音频信息bean
 */
public class MusicInfoBean implements Serializable{

    private String music_name;
    private String music_url;
    private String music_time;
    private String music_size;
    private String duration;

    public MusicInfoBean() {
    }

    public String getMusic_name() {
        return music_name;
    }

    public void setMusic_name(String music_name) {
        this.music_name = music_name;
    }

    public String getMusic_size() {
        return music_size;
    }

    public void setMusic_size(String music_size) {
        this.music_size = music_size;
    }

    public String getMusic_time() {
        return music_time;
    }

    public void setMusic_time(String music_time) {
        this.music_time = music_time;
    }

    public String getMusic_url() {
        return music_url;
    }

    public void setMusic_url(String music_url) {
        this.music_url = music_url;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
