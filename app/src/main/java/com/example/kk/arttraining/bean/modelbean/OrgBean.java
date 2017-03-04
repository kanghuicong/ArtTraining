package com.example.kk.arttraining.bean.modelbean;

/**
 * 作者：wschenyongyin on 2016/10/20 10:22
 * 说明:机构表
 */
public class OrgBean {


    private int org_id;
    private String name;
    private String pic;
    private int comment;
    private int fans_num;
    private String auth;
    private int sign_up;
    private int browse_num;
    private String introduction;
    private String remarks;
    private String city;
    private String province;
    String type;

    public int getOrg_id() {
        return org_id;
    }

    public void setOrg_id(int org_id) {
        this.org_id = org_id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public OrgBean() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getSign_up() {
        return sign_up;
    }

    public void setSign_up(int sign_up) {
        this.sign_up = sign_up;
    }

    public int getBrowse_num() {
        return browse_num;
    }

    public void setBrowse_num(int browse_num) {
        this.browse_num = browse_num;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "OrgBean{" +
                "auth='" + auth + '\'' +
                ", org_id=" + org_id +
                ", name='" + name + '\'' +
                ", pic='" + pic + '\'' +
                ", comment=" + comment +
                ", fans_num=" + fans_num +
                ", sign_up=" + sign_up +
                ", browse_num=" + browse_num +
                ", introduction='" + introduction + '\'' +
                ", remarks='" + remarks + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
