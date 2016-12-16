package com.example.kk.arttraining.ui.course.bean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/12/15 11:46
 * 说明:教师列表
 */
public class ArtTeacherListBean {
    private int total_count;
    private int code;
    private List<ArtTeacherBean> artTeacherBeanList;

    public ArtTeacherListBean() {
    }

    public List<ArtTeacherBean> getArtTeacherBeanList() {
        return artTeacherBeanList;
    }

    public void setArtTeacherBeanList(List<ArtTeacherBean> artTeacherBeanList) {
        this.artTeacherBeanList = artTeacherBeanList;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    @Override
    public String toString() {
        return "ArtTeacherListBean{" +
                "artTeacherBeanList=" + artTeacherBeanList +
                ", total_count=" + total_count +
                ", code=" + code +
                '}';
    }
}
