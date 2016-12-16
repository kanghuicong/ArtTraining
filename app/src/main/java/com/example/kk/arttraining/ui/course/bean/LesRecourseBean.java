package com.example.kk.arttraining.ui.course.bean;

/**
 * 作者：wschenyongyin on 2016/12/15 14:15
 * 说明:课堂资源
 */
public class LesRecourseBean {
    private String les_source_id;
    private String name;
    private String is_free;

    public LesRecourseBean() {
    }

    public String getIs_free() {
        return is_free;
    }

    public void setIs_free(String is_free) {
        this.is_free = is_free;
    }

    public String getLes_source_id() {
        return les_source_id;
    }

    public void setLes_source_id(String les_source_id) {
        this.les_source_id = les_source_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LesRecourseBean{" +
                "is_free='" + is_free + '\'' +
                ", les_source_id='" + les_source_id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
