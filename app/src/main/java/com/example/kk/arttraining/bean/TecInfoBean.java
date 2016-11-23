package com.example.kk.arttraining.bean;

import com.example.kk.arttraining.bean.parsebean.OrgShowBean;

import java.io.Serializable;

/**
 * 作者：wschenyongyin on 2016/10/19 19:57
 * 说明:老师信息bean
 */
public class TecInfoBean implements Serializable{
    private int tec_id;
    private String name;
    private String time;
    private String city;
    private String school;
    private String identity;
    private String pic;
    private int comment;
    private int fans_num;
    private String title;
    private float ass_pay;
    private String auth;
    private int like_num;
    private String college;
    private String specialty;
    private String introduction;
    private String is_follow;
    private int position;
    private boolean click;
    private String tec_pic;

    public String getIs_follow() {
        return is_follow;
    }

    public void setIs_follow(String is_follow) {
        this.is_follow = is_follow;
    }

    public float getAss_pay() {
        return ass_pay;
    }

    public void setAss_pay(float ass_pay) {
        this.ass_pay = ass_pay;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getTec_pic() {
        return tec_pic;
    }

    public void setTec_pic(String tec_pic) {
        this.tec_pic = tec_pic;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public boolean isClick() {
        return click;
    }

    public void setClick(boolean click) {
        this.click = click;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public TecInfoBean() {
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getLike_num() {
        return like_num;
    }

    public void setLike_num(int like_num) {
        this.like_num = like_num;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getTec_id() {
        return tec_id;
    }

    public void setTec_id(int tec_id) {
        this.tec_id = tec_id;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getFans_num() {
        return fans_num;
    }

    public void setFans_num(int fans_num) {
        this.fans_num = fans_num;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }


    @Override
    public String toString() {
        return "TecInfoBean{" +
                "ass_pay=" + ass_pay +
                ", tec_id=" + tec_id +
                ", name='" + name + '\'' +
                ", time='" + time + '\'' +
                ", city='" + city + '\'' +
                ", school='" + school + '\'' +
                ", identity='" + identity + '\'' +
                ", pic='" + pic + '\'' +
                ", comment=" + comment +
                ", fans_num=" + fans_num +
                ", title='" + title + '\'' +
                ", auth='" + auth + '\'' +
                ", like_num=" + like_num +
                ", college='" + college + '\'' +
                ", specialty='" + specialty + '\'' +
                ", introduction='" + introduction + '\'' +
                ", position=" + position +
                ", click=" + click +
                ", tec_pic='" + tec_pic + '\'' +
                '}';
    }
}
