package com.example.kk.arttraining.bean;

/**
 * 作者：wschenyongyin on 2016/10/20 14:53
 * 说明:测评表
 */
public class AssessmentsBean {
    private int ass_id;
    private String order_number;
    private int order_id;
    private String codes;
    private String ass_time;
    private int status;
    private int stu_id;
    private String stu;
    private int tec_id;
    private String tec;
    private String tec_pic;

    public AssessmentsBean() {
    }

    public String getTec_pic() {
        return tec_pic;
    }

    public void setTec_pic(String tec_pic) {
        this.tec_pic = tec_pic;
    }

    public int getAss_id() {
        return ass_id;
    }

    public void setAss_id(int ass_id) {
        this.ass_id = ass_id;
    }

    public String getAss_time() {
        return ass_time;
    }

    public void setAss_time(String ass_time) {
        this.ass_time = ass_time;
    }

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStu() {
        return stu;
    }

    public void setStu(String stu) {
        this.stu = stu;
    }

    public int getStu_id() {
        return stu_id;
    }

    public void setStu_id(int stu_id) {
        this.stu_id = stu_id;
    }

    public String getTec() {
        return tec;
    }

    public void setTec(String tec) {
        this.tec = tec;
    }

    public int getTec_id() {
        return tec_id;
    }

    public void setTec_id(int tec_id) {
        this.tec_id = tec_id;
    }
}
