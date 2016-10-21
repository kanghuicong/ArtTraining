package com.example.kk.arttraining.bean.parsebean;

import com.example.kk.arttraining.bean.UsersBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/20 14:24
 * 说明:获取点赞列表
 */
public class LikeListBean {
    private String error_code;
    private String error_msg;
    private List<UsersBean> users;

    public LikeListBean() {
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

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }
}
