package com.example.kk.arttraining.bean.parsebean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/20 10:41
 * 说明:
 */
public class OrgShowBean {
    private String error_code;
    private String error_msg;
    private int id;
    private String name;
    private List<pic> pic;
    private int comment;
    private int fans_num;
    private String auth;
    private int sign_up;
    private int browse_num;
    private String introduction;
    private String remarks;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public int getBrowse_num() {
        return browse_num;
    }

    public void setBrowse_num(int browse_num) {
        this.browse_num = browse_num;
    }

    public int getSign_up() {
        return sign_up;
    }

    public void setSign_up(int sign_up) {
        this.sign_up = sign_up;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public int getFans_num() {
        return fans_num;
    }

    public void setFans_num(int fans_num) {
        this.fans_num = fans_num;
    }

    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public List<OrgShowBean.pic> getPic() {
        return pic;
    }

    public void setPic(List<OrgShowBean.pic> pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public class pic {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
