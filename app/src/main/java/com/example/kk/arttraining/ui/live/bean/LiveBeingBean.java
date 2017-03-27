package com.example.kk.arttraining.ui.live.bean;

import com.example.kk.arttraining.bean.modelbean.NoDataResponseBean;

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
    private String pre_time;
    private String curr_time;
    private String play_url;
    private String snapshot_url;
    private String chapter_name;
    private String is_talk;
    private double live_price;
    private double record_price;
    private double order_status;
    private String live_name;
    private String introduction;
    int is_free;

    public LiveBeingBean() {
    }

    public int getIs_free() {
        return is_free;
    }

    public void setIs_free(int is_free) {
        this.is_free = is_free;
    }

    public String getLive_name() {
        return live_name;
    }

    public void setLive_name(String live_name) {
        this.live_name = live_name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getPre_time() {
        return pre_time;
    }

    public void setPre_time(String pre_time) {
        this.pre_time = pre_time;
    }

    public String getCurr_time() {
        return curr_time;
    }

    public void setCurr_time(String curr_time) {
        this.curr_time = curr_time;
    }

    public double getLive_price() {
        return live_price;
    }

    public void setLive_price(double live_price) {
        this.live_price = live_price;
    }

    public double getRecord_price() {
        return record_price;
    }

    public void setRecord_price(double record_price) {
        this.record_price = record_price;
    }

    public double getOrder_status() {
        return order_status;
    }

    public void setOrder_status(double order_status) {
        this.order_status = order_status;
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

    public String getIs_talk() {
        return is_talk;
    }

    public void setIs_talk(String is_talk) {
        this.is_talk = is_talk;
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
                ", is_talk='" + is_talk + '\'' +
                '}';
    }
}
