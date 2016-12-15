package com.example.kk.arttraining.ui.me.bean;

/**
 * 作者：wschenyongyin on 2016/12/8 15:42
 * 说明:用于显示订单列表页面  名师信息  是否点评
 */
public class OrderTecBean {
    private int tec_id;
    private String tec_name;
    private String tec_pic;
    private boolean tec_status;

    public OrderTecBean() {
    }

    public int getTec_id() {
        return tec_id;
    }

    public void setTec_id(int tec_id) {
        this.tec_id = tec_id;
    }

    public String getTec_name() {
        return tec_name;
    }

    public void setTec_name(String tec_name) {
        this.tec_name = tec_name;
    }

    public String getTec_pic() {
        return tec_pic;
    }

    public void setTec_pic(String tec_pic) {
        this.tec_pic = tec_pic;
    }

    public boolean isTec_status() {
        return tec_status;
    }

    public void setTec_status(boolean tec_status) {
        this.tec_status = tec_status;
    }

    @Override
    public String toString() {
        return "TecBean{" +
                "tec_id=" + tec_id +
                ", tec_name='" + tec_name + '\'' +
                ", tec_pic='" + tec_pic + '\'' +
                ", tec_status=" + tec_status +
                '}';
    }
}
