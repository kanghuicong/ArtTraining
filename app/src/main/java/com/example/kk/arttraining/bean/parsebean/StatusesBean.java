package com.example.kk.arttraining.bean.parsebean;

import com.example.kk.arttraining.bean.NoDataResponseBean;

/**
 * 作者：wschenyongyin on 2016/10/20 11:03
 * 说明:
 */
public class StatusesBean extends NoDataResponseBean {

    private String statuses;

    public StatusesBean() {
    }

    public String getStatuses() {
        return statuses;
    }

    public void setStatuses(String statuses) {
        this.statuses = statuses;
    }
}
