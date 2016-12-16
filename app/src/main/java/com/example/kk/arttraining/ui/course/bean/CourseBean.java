package com.example.kk.arttraining.ui.course.bean;

/**
 * 作者：wschenyongyin on 2016/12/15 11:53
 * 说明:课程bean
 */
public class CourseBean {
    private String course_id;
    private String course_name;
    private String course_profile;
    private String art_type_name;
    private String icon_url;
    private String level_max;
    private String level_min;
    private String teacher_name;

    public CourseBean(String art_type_name, String course_id, String course_name, String course_profile, String icon_url, String level_max, String level_min, String teacher_name) {
        this.art_type_name = art_type_name;
        this.course_id = course_id;
        this.course_name = course_name;
        this.course_profile = course_profile;
        this.icon_url = icon_url;
        this.level_max = level_max;
        this.level_min = level_min;
        this.teacher_name = teacher_name;
    }

    public String getArt_type_name() {
        return art_type_name;
    }

    public void setArt_type_name(String art_type_name) {
        this.art_type_name = art_type_name;
    }

    public String getCourse_id() {
        return course_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getCourse_profile() {
        return course_profile;
    }

    public void setCourse_profile(String course_profile) {
        this.course_profile = course_profile;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getLevel_max() {
        return level_max;
    }

    public void setLevel_max(String level_max) {
        this.level_max = level_max;
    }

    public String getLevel_min() {
        return level_min;
    }

    public void setLevel_min(String level_min) {
        this.level_min = level_min;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public void setTeacher_name(String teacher_name) {
        this.teacher_name = teacher_name;
    }

    @Override
    public String toString() {
        return "CourseBean{" +
                "art_type_name='" + art_type_name + '\'' +
                ", course_id='" + course_id + '\'' +
                ", course_name='" + course_name + '\'' +
                ", course_profile='" + course_profile + '\'' +
                ", icon_url='" + icon_url + '\'' +
                ", level_max='" + level_max + '\'' +
                ", level_min='" + level_min + '\'' +
                ", teacher_name='" + teacher_name + '\'' +
                '}';
    }
}
