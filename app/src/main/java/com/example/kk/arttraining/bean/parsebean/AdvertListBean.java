package com.example.kk.arttraining.bean.parsebean;

import com.example.kk.arttraining.bean.AdvertisBean;
import com.example.kk.arttraining.bean.NoDataResponseBean;

/**
 * 作者：wschenyongyin on 2016/10/20 17:34
 * 说明:
 */
public class AdvertListBean extends NoDataResponseBean {
    AdvertisBean ads;

    public AdvertListBean() {
    }

    public AdvertisBean getAds() {
        return ads;
    }

    public void setAds(AdvertisBean ads) {
        this.ads = ads;
    }
}
