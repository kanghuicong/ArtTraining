package com.example.kk.arttraining.ui.homePage.bean;

/**
 * Created by kanghuicong on 2016/12/11.
 * QQ邮箱:515849594@qq.com
 */
public class WorkComment {
    private int tec_id;
    private String name;
    private String title;
    private String identity;
    private String city;
    private String school;
    private String tec_pic;
    private String comm_time;
    private String type;
    private String comm_type;
    private String content;
    private String duration;
    private String thumbnail;
    private int listen_num;
    private int comm_id;

    public int getComm_id() {
        return comm_id;
    }

    public void setComm_id(int comm_id) {
        this.comm_id = comm_id;
    }

    public int getTec_id() {
        return tec_id;
    }

    public void setTec_id(int tec_id) {
        this.tec_id = tec_id;
    }

    public int getListen_num() {
        return listen_num;
    }

    public void setListen_num(int listen_num) {
        this.listen_num = listen_num;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getComm_type() {
        return comm_type;
    }

    public void setComm_type(String comm_type) {
        this.comm_type = comm_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getTec_pic() {
        return tec_pic;
    }

    public void setTec_pic(String tec_pic) {
        this.tec_pic = tec_pic;
    }

    public String getComm_time() {
        return comm_time;
    }

    public void setComm_time(String comm_time) {
        this.comm_time = comm_time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
