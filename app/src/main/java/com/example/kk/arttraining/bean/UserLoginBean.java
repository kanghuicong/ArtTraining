package com.example.kk.arttraining.bean;

/**
 * 作者：wschenyongyin on 2016/10/18 15:48
 * 说明:用于接收用户登陆返回的结果
 */
public class UserLoginBean {

    private String error_code;
    private String error_msg;
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


    public UserLoginBean() {
    }

    ;

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

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
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

    @Override
    public String toString() {
        return "UserLoginBean{" +
                "access_token='" + access_token + '\'' +
                ", error_code='" + error_code + '\'' +
                ", error_msg='" + error_msg + '\'' +
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
                ", org_name='" + org + '\'' +
                ", score=" + score +
                ", rank=" + rank +
                ", is_follow='" + is_follow + '\'' +
                '}';
    }
}
