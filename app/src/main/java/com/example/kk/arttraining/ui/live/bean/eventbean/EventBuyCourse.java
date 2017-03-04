package com.example.kk.arttraining.ui.live.bean.eventbean;

/**
 * 作者：wschenyongyin on 2017/3/4 14:29
 * 说明:用于传输购买直播课程信息
 */
public class EventBuyCourse {
    private int room_id;
    private int chapter_id;
    private String buy_type;
    private double chapter_price;
    private String from;


    public String getBuy_type() {
        return buy_type;
    }

    public void setBuy_type(String buy_type) {
        this.buy_type = buy_type;
    }

    public int getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(int chapter_id) {
        this.chapter_id = chapter_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public double getChapter_price() {
        return chapter_price;
    }

    public void setChapter_price(double chapter_price) {
        this.chapter_price = chapter_price;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return "EventBuyCourse{" +
                "buy_type='" + buy_type + '\'' +
                ", room_id=" + room_id +
                ", chapter_id=" + chapter_id +
                ", chapter_price=" + chapter_price +
                '}';
    }
}
