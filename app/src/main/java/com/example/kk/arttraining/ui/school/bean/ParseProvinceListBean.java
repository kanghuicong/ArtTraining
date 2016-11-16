package com.example.kk.arttraining.ui.school.bean;

import com.example.kk.arttraining.bean.NoDataResponseBean;
import com.example.kk.arttraining.bean.parsebean.ParseOrderListBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/26 11:59
 * 说明:院校省份bean
 */
public class ParseProvinceListBean extends NoDataResponseBean {

    List<ProvinceBean> province;

    public ParseProvinceListBean() {
    }

    public List<ProvinceBean> getProvince() {
        return province;
    }

    public void setProvince(List<ProvinceBean> province) {
        this.province = province;
    }

    @Override
    public String toString() {
        return "ParseProvinceListBean{" +
                "province=" + province +
                '}';
    }
}
