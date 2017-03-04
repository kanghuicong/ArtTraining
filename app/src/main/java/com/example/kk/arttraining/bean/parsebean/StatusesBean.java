package com.example.kk.arttraining.bean.parsebean;

import com.example.kk.arttraining.bean.modelbean.NoDataResponseBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/20 11:03
 * 说明:
 */
public class StatusesBean extends NoDataResponseBean {

    private List<Object> statuses;

    public StatusesBean() {
    }

    public List<Object> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Object> statuses) {
        this.statuses = statuses;
    }
}
