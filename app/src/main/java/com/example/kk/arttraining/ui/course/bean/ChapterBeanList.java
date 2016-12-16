package com.example.kk.arttraining.ui.course.bean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/12/15 14:10
 * 说明:章节列表
 */
public class ChapterBeanList {
    private int code;
    private int total_code;
    private List<ChapterBean> chapter_list;

    public ChapterBeanList() {
    }

    public List<ChapterBean> getChapter_list() {
        return chapter_list;
    }

    public void setChapter_list(List<ChapterBean> chapter_list) {
        this.chapter_list = chapter_list;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getTotal_code() {
        return total_code;
    }

    public void setTotal_code(int total_code) {
        this.total_code = total_code;
    }

    @Override
    public String toString() {
        return "ChapterBeanList{" +
                "chapter_list=" + chapter_list +
                ", code=" + code +
                ", total_code=" + total_code +
                '}';
    }
}
