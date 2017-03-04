package com.example.kk.arttraining.bean.modelbean;

/**
 * 作者：wschenyongyin on 2016/10/19 16:35
 * 说明:用户更换头像返回的bean
 */
public class UpdateHeadBean {
    private String error_code;
    private String error_msg;
    private String id;
    private String user_code;
    private String name;
    private String head_pic;

    public UpdateHeadBean() {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }
}
