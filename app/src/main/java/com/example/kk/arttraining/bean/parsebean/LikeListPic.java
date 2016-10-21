package com.example.kk.arttraining.bean.parsebean;

import com.example.kk.arttraining.bean.UsersPic;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/20 09:35
 * 说明:获取点赞用户头像列表bean
 */
public class LikeListPic {
    private String error_code;
    private String error_msg;
    private List<UsersPic> users;

    public LikeListPic() {
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

    public List<UsersPic> getUsers() {
        return users;
    }

    public void setUsers(List<UsersPic> users) {
        this.users = users;
    }
}
