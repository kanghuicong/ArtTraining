package com.example.kk.arttraining.ui.valuation.bean;

import com.example.kk.arttraining.bean.NoDataResponseBean;

import java.io.Serializable;

/**
 * 作者：wschenyongyin on 2016/10/30 19:41
 * 说明:提交订单信息bean
 */
public class CommitOrderBean extends NoDataResponseBean implements Serializable {

    private String order_title;
    private String order_number;
    private String order_price;
    private String create_time;
    private String file_path;
    private int order_id;

    public CommitOrderBean() {
    }

    public CommitOrderBean(String order_number, String order_price, String order_title,String file_path) {
        this.order_number = order_number;
        this.order_price = order_price;
        this.order_title = order_title;
        this.file_path=file_path;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
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

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    @Override
    public String toString() {
        return "CommitOrderBean{" +
                "create_time='" + create_time + '\'' +
                ", order_title='" + order_title + '\'' +
                ", order_number='" + order_number + '\'' +
                ", order_price='" + order_price + '\'' +
                ", file_path='" + file_path + '\'' +
                ", order_id=" + order_id +
                '}';
    }
}
