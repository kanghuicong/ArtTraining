package com.example.kk.arttraining.ui.homePage.bean;

import java.util.List;

/**
 * Created by kanghuicong on 2016/11/17.
 * QQ邮箱:515849594@qq.com
 */
public class FollowList {

    String error_code;
    String error_msg;
    List<Follow>  follows;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public List<Follow> getFollows() {
        return follows;
    }

    public void setFollows(List<Follow> follows) {
        this.follows = follows;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}
