package com.example.kk.arttraining.bean.parsebean;

import com.example.kk.arttraining.bean.modelbean.HeadNews;
import com.example.kk.arttraining.bean.modelbean.NoDataResponseBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/20 17:09
 * 说明:
 */
public class HeadNewsListBean extends NoDataResponseBean {

    private List<HeadNews> informations;

    public HeadNewsListBean() {
    }

    public List<HeadNews> getInformations() {
        return informations;
    }

    public void setInformations(List<HeadNews> informations) {
        this.informations = informations;
    }
}
