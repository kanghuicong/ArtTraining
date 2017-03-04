package com.example.kk.arttraining.bean.parsebean;

import com.example.kk.arttraining.bean.modelbean.OrgBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/20 10:25
 * 说明:解析机构列表
 */
public class OrgListBean {
    private String error_code;
    private String error_msg;
    private List<OrgBean> org;

    public OrgListBean() {
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

    public List<OrgBean> getOrg() {
        return org;
    }

    public void setOrg(List<OrgBean> org) {
        this.org = org;
    }
}
