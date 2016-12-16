package com.example.kk.arttraining.ui.course.bean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/12/15 14:17
 * 说明:课堂资源列表
 */
public class LesRecourseBeanList {

    private int code ;
    private int total_count;
    private List<LesRecourseBean> les_source_list;

    public LesRecourseBeanList() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<LesRecourseBean> getLes_source_list() {
        return les_source_list;
    }

    public void setLes_source_list(List<LesRecourseBean> les_source_list) {
        this.les_source_list = les_source_list;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    @Override
    public String toString() {
        return "LesRecourseBeanList{" +
                "code=" + code +
                ", total_count=" + total_count +
                ", les_source_list=" + les_source_list +
                '}';
    }
}
