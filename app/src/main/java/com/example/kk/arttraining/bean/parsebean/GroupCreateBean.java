package com.example.kk.arttraining.bean.parsebean;

/**
 * 作者：wschenyongyin on 2016/10/19 19:12
 * 说明:
 */
public class GroupCreateBean {
    private String error_code;
    private String error_msg;
    private String group_code;
    private int group_id;

    private GroupCreateBean() {
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

    public String getGroup_code() {
        return group_code;
    }

    public void setGroup_code(String group_code) {
        this.group_code = group_code;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }
}
