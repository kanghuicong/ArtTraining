package com.example.kk.arttraining.ui.course.bean;

/**
 * 作者：wschenyongyin on 2016/12/15 14:08
 * 说明:
 */
public class ChapterBean {
    private String chapter_id;
    private String name;
    private String icon_url;
    private String is_free;
    private String course_name;
    private String number;

    public ChapterBean() {
    }

    public String getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getIs_free() {
        return is_free;
    }

    public void setIs_free(String is_free) {
        this.is_free = is_free;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "ChapterBean{" +
                "chapter_id='" + chapter_id + '\'' +
                ", name='" + name + '\'' +
                ", icon_url='" + icon_url + '\'' +
                ", is_free='" + is_free + '\'' +
                ", course_name='" + course_name + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
