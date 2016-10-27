package com.example.kk.arttraining.ui.school.bean;

import com.example.kk.arttraining.bean.NoDataResponseBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/26 12:00
 * 说明:院校bean
 */
public class ParseSchoolListBean extends NoDataResponseBean {
    private List<SchoolBean> schools;

    public ParseSchoolListBean() {
    }

    public List<SchoolBean> getSchools() {
        return schools;
    }

    public void setSchools(List<SchoolBean> schools) {
        this.schools = schools;
    }
}
