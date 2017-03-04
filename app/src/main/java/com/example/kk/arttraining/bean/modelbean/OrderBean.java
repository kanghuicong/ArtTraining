package com.example.kk.arttraining.bean.modelbean;

import com.example.kk.arttraining.ui.me.bean.OrderTecBean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/24 16:35
 * 说明:订单表
 */
public class OrderBean extends NoDataResponseBean implements Serializable {
    private String order_type;
    private String order_number;
    private String order_id;
    private int order_status;
    private String order_time;
    private int order_element_num;
    private double order_total_price;
    private int work_id;
    private String work_title;
    private int ass_num;
    private String work_pic;
    //订单有效时间
    private String active_time;
    //订单剩余支付时间
    private int remaining_time;
    private List<AssessmentsBean> assessments;
    //用户测评选择的测评老师
    private List<OrderTecBean> ass_tec_list;
    //优惠券id
    private int coupon_id;
    //优惠券类型
    private int coupon_type;

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

    public double getOrder_total_price() {
        return order_total_price;
    }

    public void setOrder_total_price(double order_total_price) {
        this.order_total_price = order_total_price;
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

    public int getAss_num() {
        return ass_num;
    }

    public void setAss_num(int ass_num) {
        this.ass_num = ass_num;
    }

    public String getActive_time() {
        return active_time;
    }

    public void setActive_time(String active_time) {
        this.active_time = active_time;
    }

    public int getRemaining_time() {
        return remaining_time;
    }

    public void setRemaining_time(int remaining_time) {
        this.remaining_time = remaining_time;
    }

    public List<OrderTecBean> getAss_tec_list() {
        return ass_tec_list;
    }

    public void setAss_tec_list(List<OrderTecBean> ass_tec_list) {
        this.ass_tec_list = ass_tec_list;
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public int getCoupon_type() {
        return coupon_type;
    }

    public void setCoupon_type(int coupon_type) {
        this.coupon_type = coupon_type;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "active_time='" + active_time + '\'' +
                ", order_type='" + order_type + '\'' +
                ", order_number='" + order_number + '\'' +
                ", order_id='" + order_id + '\'' +
                ", order_status=" + order_status +
                ", order_time='" + order_time + '\'' +
                ", order_element_num=" + order_element_num +
                ", order_total_price=" + order_total_price +
                ", work_id=" + work_id +
                ", work_title='" + work_title + '\'' +
                ", ass_num=" + ass_num +
                ", work_pic='" + work_pic + '\'' +
                ", remaining_time=" + remaining_time +
                ", assessments=" + assessments +
                ", ass_tec_list=" + ass_tec_list +
                ", coupon_id=" + coupon_id +
                ", coupon_type=" + coupon_type +
                '}';
    }
}
