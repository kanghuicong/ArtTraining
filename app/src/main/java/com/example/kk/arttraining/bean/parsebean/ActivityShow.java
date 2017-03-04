package com.example.kk.arttraining.bean.parsebean;

import com.example.kk.arttraining.bean.modelbean.ActivityBean;

/**
 * 作者：wschenyongyin on 2016/10/20 16:08
 * 说明:
 */
public class ActivityShow extends ActivityBean {
    private String error_code;
    private String error_msg;
    ActivityHostOrg activ_org;

    public ActivityShow() {
    }

    public ActivityHostOrg getActiv_org() {
        return activ_org;
    }

    public void setActiv_org(ActivityHostOrg activ_org) {
        this.activ_org = activ_org;
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
}
