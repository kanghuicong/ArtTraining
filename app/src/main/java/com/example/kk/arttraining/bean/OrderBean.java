package com.example.kk.arttraining.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/24 16:35
 * 说明:订单表
 */
public class OrderBean extends NoDataResponseBean implements Serializable{
    private String order_type;
    private String order_number;
    private int order_id;
    private int order_status;
    private String order_time;
    private int order_element_num;
    private int order_total_price;
    private int work_id;
    private String work_title;
    String work_pic;

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

    public int getWork_id() {
        return work_id;
    }

    public void setWork_id(int work_id) {
        this.work_id = work_id;
    }

    public String getWork_pic() {
        return work_pic;
    }

    public void setWork_pic(String work_pic) {
        this.work_pic = work_pic;
    }

    public String getWork_title() {
        return work_title;
    }

    public void setWork_title(String work_title) {
        this.work_title = work_title;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "assessments=" + assessments +
                ", order_type='" + order_type + '\'' +
                ", order_number='" + order_number + '\'' +
                ", order_id=" + order_id +
                ", order_status=" + order_status +
                ", order_time='" + order_time + '\'' +
                ", order_element_num=" + order_element_num +
                ", order_total_price=" + order_total_price +
                ", work_id=" + work_id +
                ", work_title='" + work_title + '\'' +
                ", work_pic='" + work_pic + '\'' +
                '}';
    }
}
