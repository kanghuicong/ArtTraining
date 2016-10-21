package com.example.kk.arttraining.bean.parsebean;

import com.example.kk.arttraining.bean.GroupBean;
import com.example.kk.arttraining.bean.OrgBean;
import com.example.kk.arttraining.bean.TecInfoBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/20 15:11
 * 说明:搜索bean
 */
public class SearchBean {

    private String error_code;
    private String error_msg;
    private List<OrgBean> org;
    private List<TecInfoBean> tec;
    private String statuses;
    private List<GroupBean> groups;

    public SearchBean() {
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

    public List<GroupBean> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupBean> groups) {
        this.groups = groups;
    }

    public List<OrgBean> getOrg() {
        return org;
    }

    public void setOrg(List<OrgBean> org) {
        this.org = org;
    }

    public String getStatuses() {
        return statuses;
    }

    public void setStatuses(String statuses) {
        this.statuses = statuses;
    }

    public List<TecInfoBean> getTec() {
        return tec;
    }

    public void setTec(List<TecInfoBean> tec) {
        this.tec = tec;
    }
}
