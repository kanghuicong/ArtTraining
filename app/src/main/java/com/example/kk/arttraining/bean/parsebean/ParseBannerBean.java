package com.example.kk.arttraining.bean.parsebean;

import com.example.kk.arttraining.bean.modelbean.BannerBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/6 15:32
 * 说明:解析轮播列表
 */
public class ParseBannerBean extends BannerBean {
    List<BannerBean> banners;

    public ParseBannerBean() {
    }

    public List<BannerBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannerBean> banners) {
        this.banners = banners;
    }
}
