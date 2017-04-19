package com.example.kk.arttraining.ui.homePage.bean;

import com.example.kk.arttraining.bean.modelbean.NoDataResponseBean;

/**
 * Created by kanghuicong on 2017/4/14.
 * QQ邮箱:515849594@qq.com
 */
public class LiveHistoryBean{
    int room_id;
    int owner;
    String owner_type;
    String name;
    String head_pic;
    int browse_number;
    int fans_num;
    String start_time;
    String end_time;
    int chapter_id;
    String chapter_name;
    double live_price;
    double record_price;
    int is_free;
    String introduction;
    String live_status;
    String thumbnail;

    public int getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(int chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getChapter_name() {
        return chapter_name;
    }

    public void setChapter_name(String chapter_name) {
        this.chapter_name = chapter_name;
    }

    public int getFans_num() {
        return fans_num;
    }

    public void setFans_num(int fans_num) {
        this.fans_num = fans_num;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public int getBrowse_number() {
        return browse_number;
    }

    public void setBrowse_number(int browse_number) {
        this.browse_number = browse_number;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
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

    public int getIs_free() {
        return is_free;
    }

    public void setIs_free(int is_free) {
        this.is_free = is_free;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getLive_status() {
        return live_status;
    }

    public void setLive_status(String live_status) {
        this.live_status = live_status;
    }
}
