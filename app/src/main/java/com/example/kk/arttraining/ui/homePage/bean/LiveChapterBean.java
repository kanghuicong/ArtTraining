package com.example.kk.arttraining.ui.homePage.bean;

/**
 * Created by kanghuicong on 2017/1/18.
 * QQ邮箱:515849594@qq.com
 */
public class LiveChapterBean {
    int chapter_id;
    String chapter_name;
    String start_time;
    String end_time;
    int live_price;
    int record_price;
    int is_free;
    String thumbnail;
    int live_status;
    String introduction;
    String record_url;
    int order_status;

    public int getLive_price() {
        return live_price;
    }

    public void setLive_price(int live_price) {
        this.live_price = live_price;
    }

    public int getRecord_price() {
        return record_price;
    }

    public void setRecord_price(int record_price) {
        this.record_price = record_price;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getRecord_url() {
        return record_url;
    }

    public void setRecord_url(String record_url) {
        this.record_url = record_url;
    }

    public int getLive_status() {
        return live_status;
    }

    public void setLive_status(int live_status) {
        this.live_status = live_status;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getIs_free() {
        return is_free;
    }

    public void setIs_free(int is_free) {
        this.is_free = is_free;
    }

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


}
