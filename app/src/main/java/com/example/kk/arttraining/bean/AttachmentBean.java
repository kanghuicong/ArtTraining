package com.example.kk.arttraining.bean;

import java.io.Serializable;

/**
 * 作者：wschenyongyin on 2016/10/19 19:48
 * 说明:附件bean
 */
public class AttachmentBean implements Serializable {
    private int att_id;
    private int duration;
    private String att_type;
    private String thumbnail;
    private String store_path;

    public AttachmentBean() {
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getAtt_id() {
        return att_id;
    }

    public void setAtt_id(int att_id) {
        this.att_id = att_id;
    }

    public String getStore_path() {
        return store_path;
    }

    public void setStore_path(String store_path) {
        this.store_path = store_path;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getAtt_type() {
        return att_type;
    }

    public void setAtt_type(String att_type) {
        this.att_type = att_type;
    }
}
