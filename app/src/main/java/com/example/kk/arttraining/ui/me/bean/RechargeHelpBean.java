package com.example.kk.arttraining.ui.me.bean;

import com.example.kk.arttraining.bean.modelbean.NoDataResponseBean;

/**
 * Created by kanghuicong on 2017/4/6.
 * QQ邮箱:515849594@qq.com
 */
public class RechargeHelpBean extends NoDataResponseBean {
    int uid;
    String utype;
    String name;
    String telephone;
    String sex;
    String head_pic;
    String login_type;
    boolean type;

    public String getLogin_type() {
        return login_type;
    }

    public void setLogin_type(String login_type) {
        this.login_type = login_type;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }
}
