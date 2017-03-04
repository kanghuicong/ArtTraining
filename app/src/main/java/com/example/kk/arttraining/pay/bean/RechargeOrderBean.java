package com.example.kk.arttraining.pay.bean;

/**
 * 作者：wschenyongyin on 2017/3/2 11:29
 * 说明:云币充值订单bean
 */
public class RechargeOrderBean {
    String order_number;
    String create_time;
    String order_id;

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    @Override
    public String toString() {
        return "RechargeOrderBean{" +
                "create_time='" + create_time + '\'' +
                ", order_number='" + order_number + '\'' +
                ", order_id='" + order_id + '\'' +
                '}';
    }
}
