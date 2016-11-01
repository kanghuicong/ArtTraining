package com.example.kk.arttraining.ui.valuation.bean;

import java.io.Serializable;

/**
 * 作者：wschenyongyin on 2016/10/30 19:41
 * 说明:提交订单信息bean
 */
public class CommitOrderBean implements Serializable {

    private String order_title;
    private String order_id;
    private String order_price;

    public CommitOrderBean() {
    }

    public CommitOrderBean(String order_id, String order_price, String order_title) {
        this.order_id = order_id;
        this.order_price = order_price;
        this.order_title = order_title;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }

    public String getOrder_title() {
        return order_title;
    }

    public void setOrder_title(String order_title) {
        this.order_title = order_title;
    }
}
