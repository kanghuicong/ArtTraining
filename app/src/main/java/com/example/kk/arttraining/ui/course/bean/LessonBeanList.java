package com.example.kk.arttraining.ui.course.bean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/12/15 14:13
 * 说明:课堂列表
 */
public class LessonBeanList {
    private int code;
    private int total_count;
    private List<LessonBean> lessonBeen;

    public LessonBeanList() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<LessonBean> getLessonBeen() {
        return lessonBeen;
    }

    public void setLessonBeen(List<LessonBean> lessonBeen) {
        this.lessonBeen = lessonBeen;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    @Override
    public String toString() {
        return "LessonBeanList{" +
                "code=" + code +
                ", total_count=" + total_count +
                ", lessonBeen=" + lessonBeen +
                '}';
    }
}
