package com.example.kk.arttraining.ui.homePage.bean;

/**
 * 作者：wschenyongyin on 2016/11/6 19:27
 * 说明:机构名师列表
 */
public class Teachers {
    private int tec_id;
    private String tec_name;
    private String tec_post;
    private String tec_pic;

    public Teachers() {
    }

    public int getTec_id() {
        return tec_id;
    }

    public void setTec_id(int tec_id) {
        this.tec_id = tec_id;
    }

    public String getTec_name() {
        return tec_name;
    }

    public void setTec_name(String tec_name) {
        this.tec_name = tec_name;
    }

    public String getTec_pic() {
        return tec_pic;
    }

    public void setTec_pic(String tec_pic) {
        this.tec_pic = tec_pic;
    }

    public String getTec_post() {
        return tec_post;
    }

    public void setTec_post(String tec_post) {
        this.tec_post = tec_post;
    }
}
