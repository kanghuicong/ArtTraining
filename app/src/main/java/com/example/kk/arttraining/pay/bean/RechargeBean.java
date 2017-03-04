package com.example.kk.arttraining.pay.bean;

/**
 * 作者：wschenyongyin on 2017/3/2 09:15
 * 说明:充值云币的充值列表bean
 */
public class RechargeBean {

    //售价
    double money;
    //云币
    double cloud_money;

    int tranform_id;

    boolean select;

    public double getCloud_money() {
        return cloud_money;
    }

    public void setCloud_money(double cloud_money) {
        this.cloud_money = cloud_money;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getTranform_id() {
        return tranform_id;
    }

    public void setTranform_id(int tranform_id) {
        this.tranform_id = tranform_id;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    @Override
    public String toString() {
        return "RechargeBean{" +
                "cloud_money=" + cloud_money +
                ", money=" + money +
                '}';
    }
}

