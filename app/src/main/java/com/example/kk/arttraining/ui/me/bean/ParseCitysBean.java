package com.example.kk.arttraining.ui.me.bean;

import com.example.kk.arttraining.bean.LocationBean;
import com.example.kk.arttraining.bean.NoDataResponseBean;
import com.example.kk.arttraining.ui.homePage.function.homepage.Location;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/11 19:32
 * 说明:解析城市列表
 */
public class ParseCitysBean extends NoDataResponseBean {
    List<LocationBean> citys;

    public List<LocationBean> getCitys() {
        return citys;
    }

    public void setCitys(List<LocationBean> citys) {
        this.citys = citys;
    }

    @Override
    public String toString() {
        return "ParseCitysBean{" +
                "citys=" + citys +
                '}';
    }
}
