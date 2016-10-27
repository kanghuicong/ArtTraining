package com.example.kk.arttraining.ui.school.bean;

import com.example.kk.arttraining.bean.NoDataResponseBean;
import com.example.kk.arttraining.bean.parsebean.ParseOrderListBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/26 11:59
 * 说明:院校省份bean
 */
public class ParseProvinceListBean extends NoDataResponseBean {

    List<ProvinceBean> provinces;

    public ParseProvinceListBean() {
    }

    public List<ProvinceBean> getProvinces() {
        return provinces;
    }

    public void setProvinces(List<ProvinceBean> provinces) {
        this.provinces = provinces;
    }
}
