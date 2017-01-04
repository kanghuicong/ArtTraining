package com.example.kk.arttraining.bean;

import com.example.kk.arttraining.ui.me.bean.UserCountBean;

/**
 * 作者：wschenyongyin on 2016/10/18 15:48
 * 说明:用于接收用户登陆返回的结果
 */
public class UserLoginBean  extends NoDataResponseBean{

    private int uid;
    private String user_code;
    private String name;
    private String mobile;
    private String head_pic;
    private String sex;
    private String city;
    private String title;
    private String identity;
    private String school;
    private String email;
    private String intentional_college;
    private String org;
    private int score;
    private int rank;
    private String access_token;
    private String is_follow;
    private int bbs_num;
    private int group_num;
    private int favorite_num;
    private int comment_num;
    private int follow_num;
    private int fans_num;
    private int work_num;
    private String is_bind;

    public UserLoginBean() {
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getBbs_num() {
        return bbs_num;
    }

    public void setBbs_num(int bbs_num) {
        this.bbs_num = bbs_num;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public int getFans_num() {
        return fans_num;
    }

    public void setFans_num(int fans_num) {
        this.fans_num = fans_num;
    }

    public int getFavorite_num() {
        return favorite_num;
    }

    public void setFavorite_num(int favorite_num) {
        this.favorite_num = favorite_num;
    }

    public int getFollow_num() {
        return follow_num;
    }

    public void setFollow_num(int follow_num) {
        this.follow_num = follow_num;
    }

    public int getGroup_num() {
        return group_num;
    }

    public void setGroup_num(int group_num) {
        this.group_num = group_num;
    }

    public int getWork_num() {
        return work_num;
    }

    public void setWork_num(int work_num) {
        this.work_num = work_num;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getIntentional_college() {
        return intentional_college;
    }

    public void setIntentional_college(String intentional_college) {
        this.intentional_college = intentional_college;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAccess_token() {
        return access_token;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getIs_follow() {
        return is_follow;
    }

    public void setIs_follow(String is_follow) {
        this.is_follow = is_follow;
    }

    public String getIs_bind() {
        return is_bind;
    }

    public void setIs_bind(String is_bind) {
        this.is_bind = is_bind;
    }

    @Override
    public String toString() {
        return "UserLoginBean{" +
                "access_token='" + access_token + '\'' +
                ", uid=" + uid +
                ", user_code='" + user_code + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", head_pic='" + head_pic + '\'' +
                ", sex='" + sex + '\'' +
                ", city='" + city + '\'' +
                ", title='" + title + '\'' +
                ", identity='" + identity + '\'' +
                ", school='" + school + '\'' +
                ", email='" + email + '\'' +
                ", intentional_college='" + intentional_college + '\'' +
                ", org='" + org + '\'' +
                ", score=" + score +
                ", rank=" + rank +
                ", is_follow='" + is_follow + '\'' +
                ", bbs_num=" + bbs_num +
                ", group_num=" + group_num +
                ", favorite_num=" + favorite_num +
                ", comment_num=" + comment_num +
                ", follow_num=" + follow_num +
                ", fans_num=" + fans_num +
                ", work_num=" + work_num +
                ", is_bind='" + is_bind + '\'' +
                '}';
    }
}
