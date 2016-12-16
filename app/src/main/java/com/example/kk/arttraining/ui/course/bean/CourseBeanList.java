package com.example.kk.arttraining.ui.course.bean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/12/15 14:05
 * 说明:课程列表
 */
public class CourseBeanList {

    private int code;
    private int total_count;
    private   List<CourseBean> course_list;

    public CourseBeanList() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<CourseBean> getCourse_list() {
        return course_list;
    }

    public void setCourse_list(List<CourseBean> course_list) {
        this.course_list = course_list;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    @Override
    public String toString() {
        return "CourseBeanList{" +
                "code=" + code +
                ", total_count=" + total_count +
                ", course_list=" + course_list +
                '}';
    }
}
