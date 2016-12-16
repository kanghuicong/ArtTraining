package com.example.kk.arttraining.ui.course.bean;

/**
 * 作者：wschenyongyin on 2016/12/15 11:40
 * 说明:
 */
public class ArtTeacherBean {
    private String teacher_id;
    private String art_type;
    private String icon_url;
    private String name;
    private String nation_type;
    private String style_name_list;

    public ArtTeacherBean() {
    }

    public String getArt_type() {
        return art_type;
    }

    public void setArt_type(String art_type) {
        this.art_type = art_type;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNation_type() {
        return nation_type;
    }

    public void setNation_type(String nation_type) {
        this.nation_type = nation_type;
    }

    public String getStyle_name_list() {
        return style_name_list;
    }

    public void setStyle_name_list(String style_name_list) {
        this.style_name_list = style_name_list;
    }

    public String getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(String teacher_id) {
        this.teacher_id = teacher_id;
    }

    @Override
    public String toString() {
        return "ArtTeacherBean{" +
                "art_type='" + art_type + '\'' +
                ", teacher_id='" + teacher_id + '\'' +
                ", icon_url='" + icon_url + '\'' +
                ", name='" + name + '\'' +
                ", nation_type='" + nation_type + '\'' +
                ", style_name_list='" + style_name_list + '\'' +
                '}';
    }
}
