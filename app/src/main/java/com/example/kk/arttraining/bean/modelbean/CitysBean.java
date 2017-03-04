package com.example.kk.arttraining.bean.modelbean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/22 11:22
 * 说明:城市bean
 */
public class CitysBean {
    String city_type;
    String sort_word;
    List<LocationBean> sort_citys;

    public CitysBean() {
    }

    public String getCity_type() {
        return city_type;
    }

    public void setCity_type(String city_type) {
        this.city_type = city_type;
    }

    public List<LocationBean> getSort_citys() {
        return sort_citys;
    }

    public void setSort_citys(List<LocationBean> sort_citys) {
        this.sort_citys = sort_citys;
    }

    public String getSort_word() {
        return sort_word;
    }

    public void setSort_word(String sort_word) {
        this.sort_word = sort_word;
    }
}
