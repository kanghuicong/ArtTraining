package com.example.kk.arttraining.bean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/24 16:35
 * 说明:订单表
 */
public class OrderBean extends NoDataResponseBean {
    private String order_type;
    private String order_number;
    private int order_id;
    private int order_status;
    private int order_element_num;
    private int order_total_price;
    private String order_time;
    private List<AssessmentsBean> assessments;

    public OrderBean() {
    }

    public List<AssessmentsBean> getAssessments() {
        return assessments;
    }

    public void setAssessments(List<AssessmentsBean> assessments) {
        this.assessments = assessments;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public int getOrder_element_num() {
        return order_element_num;
    }

    public void setOrder_element_num(int order_element_num) {
        this.order_element_num = order_element_num;
    }

    public int getOrder_total_price() {
        return order_total_price;
    }

    public void setOrder_total_price(int order_total_price) {
        this.order_total_price = order_total_price;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }
}
