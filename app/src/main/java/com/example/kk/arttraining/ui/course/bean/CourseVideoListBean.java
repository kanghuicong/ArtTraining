package com.example.kk.arttraining.ui.course.bean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/12/15 14:23
 * 说明:课程视频列表
 */
public class CourseVideoListBean {
    private int code;
    private List<CourseVideoBean> video_info;

    public CourseVideoListBean() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<CourseVideoBean> getVideo_info() {
        return video_info;
    }

    public void setVideo_info(List<CourseVideoBean> video_info) {
        this.video_info = video_info;
    }

    @Override
    public String toString() {
        return "CourseVideoListBean{" +
                "code=" + code +
                ", video_info=" + video_info +
                '}';
    }
}
