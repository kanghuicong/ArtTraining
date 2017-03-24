package com.example.kk.arttraining.ui.me.bean;

import com.example.kk.arttraining.bean.modelbean.NoDataResponseBean;

/**
 * Created by kanghuicong on 2017/3/22.
 * QQ邮箱:515849594@qq.com
 */
public class MyCourseBean extends NoDataResponseBean {
    int room_id;
    int buy_id;
    String order_number;
    int chapter_id;
    String chapter_name;
    double chapter_price;
    String buy_time;
    String start_time;
    int live_status;
    String record_url;

    public String getRecord_url() {
        return record_url;
    }

    public void setRecord_url(String record_url) {
        this.record_url = record_url;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getBuy_id() {
        return buy_id;
    }

    public void setBuy_id(int buy_id) {
        this.buy_id = buy_id;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
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

    public double getChapter_price() {
        return chapter_price;
    }

    public void setChapter_price(double chapter_price) {
        this.chapter_price = chapter_price;
    }

    public String getBuy_time() {
        return buy_time;
    }

    public void setBuy_time(String buy_time) {
        this.buy_time = buy_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public int getLive_status() {
        return live_status;
    }

    public void setLive_status(int live_status) {
        this.live_status = live_status;
    }
}
