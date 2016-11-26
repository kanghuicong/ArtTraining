package com.example.kk.arttraining.ui.me.bean;

import com.example.kk.arttraining.bean.NoDataResponseBean;
import com.example.kk.arttraining.bean.parsebean.ParseStatusesBean;
import com.example.kk.arttraining.bean.parsebean.StatusesBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/9 16:46
 * 说明:收藏bean
 */
public class CollectBean{
    private int fav_id;
    private int b_fav_id;
    private String collect_time;
    private String fav_type;
    private ParseStatusesBean statuses;

    public CollectBean() {
    }


    public int getFav_id() {
        return fav_id;
    }

    public void setFav_id(int fav_id) {
        this.fav_id = fav_id;
    }

    public int getB_fav_id() {
        return b_fav_id;
    }

    public void setB_fav_id(int b_fav_id) {
        this.b_fav_id = b_fav_id;
    }

    public ParseStatusesBean getStatuses() {
        return statuses;
    }

    public void setStatuses(ParseStatusesBean statuses) {
        this.statuses = statuses;
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

    @Override
    public String toString() {
        return "CollectBean{" +
                "collect_time='" + collect_time + '\'' +
                ", fav_type='" + fav_type + '\'' +
                '}';
    }
}
