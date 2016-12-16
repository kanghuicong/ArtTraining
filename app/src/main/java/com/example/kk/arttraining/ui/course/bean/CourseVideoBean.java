package com.example.kk.arttraining.ui.course.bean;

/**
 * 作者：wschenyongyin on 2016/12/15 14:21
 * 说明:课程视频信息
 */
public class CourseVideoBean {
    String url_high;
    String url_low;
    String url_mid;

    public CourseVideoBean() {
    }

    public String getUrl_high() {
        return url_high;
    }

    public void setUrl_high(String url_high) {
        this.url_high = url_high;
    }

    public String getUrl_low() {
        return url_low;
    }

    public void setUrl_low(String url_low) {
        this.url_low = url_low;
    }

    public String getUrl_mid() {
        return url_mid;
    }

    public void setUrl_mid(String url_mid) {
        this.url_mid = url_mid;
    }

    @Override
    public String toString() {
        return "CourseVideoBean{" +
                "url_high='" + url_high + '\'' +
                ", url_low='" + url_low + '\'' +
                ", url_mid='" + url_mid + '\'' +
                '}';
    }
}
