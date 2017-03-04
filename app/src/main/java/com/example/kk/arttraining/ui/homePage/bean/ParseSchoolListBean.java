package com.example.kk.arttraining.ui.homePage.bean;

import com.example.kk.arttraining.bean.modelbean.NoDataResponseBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/26 12:00
 * 说明:院校bean
 */
public class ParseSchoolListBean extends NoDataResponseBean {
    private List<SchoolBean> institutions;

    public ParseSchoolListBean() {
    }

    public List<SchoolBean> getInstitutions() {
        return institutions;
    }

    public void setInstitutions(List<SchoolBean> institutions) {
        this.institutions = institutions;
    }
}
