package com.example.kk.arttraining.ui.valuation.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/1 10:57
 * 说明:音频bean
 */
public class AudioInfoBean implements Serializable {
    //文件大小
    private Long audio_size;
    //文件地址
    private String audio_path;
    //视频截图
    private String video_pic;
    //时长
    private String audio_length;

    private String media_type;

    private ArrayList<String> image_att;

    public AudioInfoBean() {
    }

    public AudioInfoBean(String audio_path, Long audio_size) {
        this.audio_path = audio_path;
        this.audio_size = audio_size;
    }

    public String getAudio_path() {
        return audio_path;
    }

    public void setAudio_path(String audio_path) {
        this.audio_path = audio_path;
    }

    public Long getAudio_size() {
        return audio_size;
    }

    public void setAudio_size(Long audio_size) {
        this.audio_size = audio_size;
    }

    public String getVideo_pic() {
        return video_pic;
    }

    public void setVideo_pic(String video_pic) {
        this.video_pic = video_pic;
    }

    public String getAudio_length() {
        return audio_length;
    }

    public void setAudio_length(String audio_length) {
        this.audio_length = audio_length;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public ArrayList<String> getImage_att() {
        return image_att;
    }

    public void setImage_att(ArrayList<String> image_att) {
        this.image_att = image_att;
    }

    @Override
    public String toString() {
        return "AudioInfoBean{" +
                "audio_length='" + audio_length + '\'' +
                ", audio_size=" + audio_size +
                ", audio_path='" + audio_path + '\'' +
                ", video_pic='" + video_pic + '\'' +
                ", media_type='" + media_type + '\'' +
                '}';
    }
}
