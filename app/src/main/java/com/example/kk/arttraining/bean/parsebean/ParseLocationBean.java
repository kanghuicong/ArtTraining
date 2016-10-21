package com.example.kk.arttraining.bean.parsebean;

import com.example.kk.arttraining.bean.LocationBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/20 15:36
 * 说明:
 */
public class ParseLocationBean {
    private String error_code;
    private String error_msg;
    List<LocationBean> city;
    List<LocationBean> province;

    public ParseLocationBean() {
    }

    public List<LocationBean> getCity() {
        return city;
    }

    public void setCity(List<LocationBean> city) {
        this.city = city;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public List<LocationBean> getProvince() {
        return province;
    }

    public void setProvince(List<LocationBean> province) {
        this.province = province;
    }
}
