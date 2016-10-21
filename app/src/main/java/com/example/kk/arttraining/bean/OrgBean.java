package com.example.kk.arttraining.bean;

/**
 * 作者：wschenyongyin on 2016/10/20 10:22
 * 说明:机构表
 */
public class OrgBean {
    private int id;
    private String name;
    private String pic;
    private int comment;
    private int fans_num;
    private String auth;
    private int sign_up;
    private int browse_num;
    private String introduction;
    private String remarks;
    String type;


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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
