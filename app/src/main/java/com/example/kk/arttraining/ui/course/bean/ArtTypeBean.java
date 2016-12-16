package com.example.kk.arttraining.ui.course.bean;

/**
 * 作者：wschenyongyin on 2016/12/15 11:36
 * 说明:艺术类别
 */
public class ArtTypeBean {
    private String icon_url;
    private String name;
    private int type_id;

    public ArtTypeBean() {
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

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    @Override
    public String toString() {
        return "ArtTypeBean{" +
                "icon_url='" + icon_url + '\'' +
                ", name='" + name + '\'' +
                ", type_id=" + type_id +
                '}';
    }
}
