package com.example.kk.arttraining.bean.parsebean;

import com.example.kk.arttraining.bean.GroupBean;

import java.security.acl.Group;
import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/19 17:24
 * 说明:用于接收小组列表数据Bean
 */
public class GroupListBean {
    private String error_code;
    private String error_msg;
    private List<GroupBean> groups;

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

    public List<GroupBean> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupBean> groups) {
        this.groups = groups;
    }
}
