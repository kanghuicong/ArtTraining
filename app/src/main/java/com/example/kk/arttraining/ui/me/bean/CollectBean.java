package com.example.kk.arttraining.ui.me.bean;

import com.example.kk.arttraining.bean.NoDataResponseBean;
import com.example.kk.arttraining.bean.parsebean.ParseStatusesBean;
import com.example.kk.arttraining.bean.parsebean.StatusesBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/9 16:46
 * 说明:收藏bean
 */
public class CollectBean extends ParseStatusesBean {
    private String collect_time;
    private String fav_type;

    public CollectBean() {
    }

    public String getCollect_time() {
        return collect_time;
    }

    public void setCollect_time(String collect_time) {
        this.collect_time = collect_time;
    }

    public String getFav_type() {
        return fav_type;
    }

    public void setFav_type(String fav_type) {
        this.fav_type = fav_type;
    }
}
