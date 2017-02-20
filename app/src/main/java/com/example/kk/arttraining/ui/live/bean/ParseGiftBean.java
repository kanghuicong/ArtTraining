package com.example.kk.arttraining.ui.live.bean;

import com.example.kk.arttraining.bean.NoDataResponseBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2017/2/8 15:06
 * 说明:
 */
public class ParseGiftBean extends NoDataResponseBean {

    List<GiftBean> gift_list;

    public List<GiftBean> getGift_list() {
        return gift_list;
    }

    public void setGift_list(List<GiftBean> gift_list) {
        this.gift_list = gift_list;
    }

    @Override
    public String toString() {
        return "ParseGiftBean{" +
                "gift_list=" + gift_list +
                '}';
    }
}
