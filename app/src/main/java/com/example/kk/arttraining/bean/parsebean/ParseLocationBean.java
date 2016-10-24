package com.example.kk.arttraining.bean.parsebean;

import com.example.kk.arttraining.bean.CitysBean;
import com.example.kk.arttraining.bean.LocationBean;
import com.example.kk.arttraining.bean.NoDataResponseBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/20 15:36
 * 说明:解析城市数据bean
 */
public class ParseLocationBean extends NoDataResponseBean{

    List<CitysBean> citys;

    public ParseLocationBean() {
    }

    public List<CitysBean> getCitys() {
        return citys;
    }

    public void setCitys(List<CitysBean> citys) {
        this.citys = citys;
    }
}
