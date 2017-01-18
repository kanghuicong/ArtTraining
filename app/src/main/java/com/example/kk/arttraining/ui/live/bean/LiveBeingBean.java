package com.example.kk.arttraining.ui.live.bean;

import com.example.kk.arttraining.bean.NoDataResponseBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2017/1/7 17:05
 * 说明:用户进入直播房间返回的bean
 */
public class LiveBeingBean extends NoDataResponseBean {
    private int owner;
    private String owner_type;
    private String name;
    private String head_pic;
    private int like_number;
    private int follow_number;
    private int chapter_number;
    private String play_url;
    private String snapshot_url;
    private String chapter_name;
    private List<LiveCommentBean> comment_list;

    public LiveBeingBean() {
    }

    public String getChapter_name() {
        return chapter_name;
    }

    public void setChapter_name(String chapter_name) {
        this.chapter_name = chapter_name;
    }

    public int getChapter_number() {
        return chapter_number;
    }

    public void setChapter_number(int chapter_number) {
        this.chapter_number = chapter_number;
    }

    public List<LiveCommentBean> getComment_list() {
        return comment_list;
    }

    public void setComment_list(List<LiveCommentBean> comment_list) {
        this.comment_list = comment_list;
    }

    public int getFollow_number() {
        return follow_number;
    }

    public void setFollow_number(int follow_number) {
        this.follow_number = follow_number;
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

    @Override
    public String toString() {
        return "LiveBeingBean{" +
                "chapter_name='" + chapter_name + '\'' +
                ", owner=" + owner +
                ", owner_type='" + owner_type + '\'' +
                ", name='" + name + '\'' +
                ", head_pic='" + head_pic + '\'' +
                ", like_number=" + like_number +
                ", follow_number=" + follow_number +
                ", chapter_number=" + chapter_number +
                ", play_url='" + play_url + '\'' +
                ", snapshot_url='" + snapshot_url + '\'' +
                ", comment_list=" + comment_list +
                '}';
    }
}
