package com.example.kk.arttraining.bean.parsebean;

import com.example.kk.arttraining.bean.NoDataResponseBean;
import com.example.kk.arttraining.bean.OrderBean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/24 16:38
 * 说明:解析订单的bean
 */
public class ParseOrderListBean extends NoDataResponseBean implements Serializable{

    private List<OrderBean> orders;

    public ParseOrderListBean() {
    }

    public List<OrderBean> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderBean> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "ParseOrderListBean{" +
                "orders=" + orders +
                '}';
    }
}
