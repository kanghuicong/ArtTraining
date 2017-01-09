package com.example.kk.arttraining.ui.live.bean;

import com.example.kk.arttraining.bean.NoDataResponseBean;

/**
 * 作者：wschenyongyin on 2017/1/7 17:05
 * 说明:用户进入直播房间返回的bean
 */
public class RoomBean extends NoDataResponseBean{
    private String play_url;
    private String owner_type;
    private int owner;
    private String name;
    private String head_pic;
    private int live_number;
    private String title;
    private int like_number;
    private String snapshot_url;

    public RoomBean() {
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public int getLike_number() {
        return like_number;
    }

    public void setLike_number(int like_number) {
        this.like_number = like_number;
    }

    public int getLive_number() {
        return live_number;
    }

    public void setLive_number(int live_number) {
        this.live_number = live_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String getOwner_type() {
        return owner_type;
    }

    public void setOwner_type(String owner_type) {
        this.owner_type = owner_type;
    }

    public String getPlay_url() {
        return play_url;
    }

    public void setPlay_url(String play_url) {
        this.play_url = play_url;
    }

    public String getSnapshot_url() {
        return snapshot_url;
    }

    public void setSnapshot_url(String snapshot_url) {
        this.snapshot_url = snapshot_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
