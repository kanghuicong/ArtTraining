package com.example.kk.arttraining.bean;

/**
 * 作者：wschenyongyin on 2016/10/20 14:53
 * 说明:测评表
 */
public class AssessmentsBean {
    private int id;
    private int order_number;
    private String codes;
    private int stu_id;
    private int tec_id;
    private int status;
    private String ass_time;
    private String money;
    private int is_pay;
    private String pay_time;
    private String cancel_time;
    private int work_id;
    private int is_deleted;
    private String create_time;
    private String order_code;
    private String delete_time;
    private String remarks;
    private String attachment;

    public String getAss_time() {
        return ass_time;
    }

    public void setAss_time(String ass_time) {
        this.ass_time = ass_time;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getCancel_time() {
        return cancel_time;
    }

    public void setCancel_time(String cancel_time) {
        this.cancel_time = cancel_time;
    }

    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getDelete_time() {
        return delete_time;
    }

    public void setDelete_time(String delete_time) {
        this.delete_time = delete_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(int is_deleted) {
        this.is_deleted = is_deleted;
    }

    public int getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(int is_pay) {
        this.is_pay = is_pay;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public int Order_number() {
        return order_number;
    }

    public void Order_number(int order_number) {
        this.order_number = order_number;
    }

    public String getPay_time() {
        return pay_time;
    }

    public void setPay_time(String pay_time) {
        this.pay_time = pay_time;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStu_id() {
        return stu_id;
    }

    public void setStu_id(int stu_id) {
        this.stu_id = stu_id;
    }

    public int getTec_id() {
        return tec_id;
    }

    public void setTec_id(int tec_id) {
        this.tec_id = tec_id;
    }

    public int getWork_id() {
        return work_id;
    }

    public void setWork_id(int work_id) {
        this.work_id = work_id;
    }
}
