package com.example.kk.arttraining.ui.valuation.bean;

import java.io.Serializable;

/**
 * 作者：wschenyongyin on 2016/11/1 10:57
 * 说明:音频bean
 */
public class AudioInfoBean implements Serializable {
    private Long audio_size;
    private String audio_path;

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
}
