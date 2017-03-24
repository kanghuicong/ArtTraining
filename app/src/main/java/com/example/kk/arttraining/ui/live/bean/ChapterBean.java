package com.example.kk.arttraining.ui.live.bean;


import com.example.kk.arttraining.bean.modelbean.NoDataResponseBean;

/**
 * 作者：wschenyongyin on 2017/1/21 15:47
 * 说明:课时bean
 */
public class ChapterBean extends NoDataResponseBean {
    private int chapter_id;
    private String chapter_name;
    private String start_time;
    private String end_time;
    private double live_price;
    private double record_price;
    private int is_free;
    private String thumbnail;
    private int live_status;
    private String introduction;
    private String record_url;
    private int order_status;
    private int buy_number;

    public int getBuy_number() {
        return buy_number;
    }

    public void setBuy_number(int buy_number) {
        this.buy_number = buy_number;
    }

    public ChapterBean() {
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

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getIs_free() {
        return is_free;
    }

    public void setIs_free(int is_free) {
        this.is_free = is_free;
    }

    public double getLive_price() {
        return live_price;
    }

    public void setLive_price(double live_price) {
        this.live_price = live_price;
    }

    public int getLive_status() {
        return live_status;
    }

    public void setLive_status(int live_status) {
        this.live_status = live_status;
    }

    public double getRecord_price() {
        return record_price;
    }

    public void setRecord_price(double record_price) {
        this.record_price = record_price;
    }

    public String getRecord_url() {
        return record_url;
    }

    public void setRecord_url(String record_url) {
        this.record_url = record_url;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    @Override
    public String toString() {
        return "ChapterBean{" +
                "chapter_id=" + chapter_id +
                ", chapter_name='" + chapter_name + '\'' +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", live_price=" + live_price +
                ", record_price=" + record_price +
                ", is_free=" + is_free +
                ", thumbnail='" + thumbnail + '\'' +
                ", live_status=" + live_status +
                ", introduction='" + introduction + '\'' +
                ", record_url='" + record_url + '\'' +
                ", order_status=" + order_status +
                '}';
    }
}
