package com.example.kk.arttraining.ui.live.bean;

import com.example.kk.arttraining.bean.NoDataResponseBean;

import java.util.List;


/**
 * 作者：wschenyongyin on 2017/1/21 15:44
 * 说明:课表bean
 */
public class TimeTableBean extends NoDataResponseBean {

    private int timetable_id;
    private String name;
    private String introduction;
    private double price;
    private int major_one;
    private int major_two;
    private int live_type;
    private int is_free;
    private List<ChapterBean> chapter_list;

    public List<ChapterBean> getChapter_list() {
        return chapter_list;
    }

    public void setChapter_list(List<ChapterBean> chapter_list) {
        this.chapter_list = chapter_list;
    }

    public int getTimetable_id() {
        return timetable_id;
    }

    public void setTimetable_id(int timetable_id) {
        this.timetable_id = timetable_id;
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

    public int getLive_type() {
        return live_type;
    }

    public void setLive_type(int live_type) {
        this.live_type = live_type;
    }

    public int getMajor_one() {
        return major_one;
    }

    public void setMajor_one(int major_one) {
        this.major_one = major_one;
    }

    public int getMajor_two() {
        return major_two;
    }

    public void setMajor_two(int major_two) {
        this.major_two = major_two;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "TimeTableBean{" +
                "chapter_list=" + chapter_list +
                ", timetable_id=" + timetable_id +
                ", name='" + name + '\'' +
                ", introduction='" + introduction + '\'' +
                ", price=" + price +
                ", major_one=" + major_one +
                ", major_two=" + major_two +
                ", live_type=" + live_type +
                ", is_free=" + is_free +
                '}';
    }
}
