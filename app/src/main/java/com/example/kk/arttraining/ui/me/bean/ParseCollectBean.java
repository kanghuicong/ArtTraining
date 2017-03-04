package com.example.kk.arttraining.ui.me.bean;

import com.example.kk.arttraining.bean.modelbean.GeneralBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/9 16:59
 * 说明:解析收藏页面
 */
public class ParseCollectBean extends GeneralBean {
    List<CollectBean> favorites;

    public List<CollectBean> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<CollectBean> favorites) {
        this.favorites = favorites;
    }

    @Override
    public String toString() {
        return "ParseCollectBean{" +
                "favorites=" + favorites +
                '}';
    }
}
