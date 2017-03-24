package com.example.kk.arttraining.ui.me.bean;

/**
 * Created by kanghuicong on 2017/3/15.
 * QQ邮箱:515849594@qq.com
 */
public class CloudContentBean{
    int cloud_id;
    double cloud_money;
    double curr_cloud_money;
    String consume_type;
    String consume_time;
    String remark;

    public int getCloud_id() {
        return cloud_id;
    }

    public void setCloud_id(int cloud_id) {
        this.cloud_id = cloud_id;
    }

    public double getCloud_money() {
        return cloud_money;
    }

    public void setCloud_money(double cloud_money) {
        this.cloud_money = cloud_money;
    }

    public double getCurr_cloud_money() {
        return curr_cloud_money;
    }

    public void setCurr_cloud_money(double curr_cloud_money) {
        this.curr_cloud_money = curr_cloud_money;
    }

    public String getConsume_type() {
        return consume_type;
    }

    public void setConsume_type(String consume_type) {
        this.consume_type = consume_type;
    }

    public String getConsume_time() {
        return consume_time;
    }

    public void setConsume_time(String consume_time) {
        this.consume_time = consume_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
