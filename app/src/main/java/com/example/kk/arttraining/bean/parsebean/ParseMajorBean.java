package com.example.kk.arttraining.bean.parsebean;

import com.example.kk.arttraining.bean.modelbean.MajorBean;
import com.example.kk.arttraining.bean.modelbean.NoDataResponseBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/6 15:19
 * 说明:解析专业
 */
public class ParseMajorBean extends NoDataResponseBean {

    List<MajorBean> majors;

    public List<MajorBean> getMajors() {
        return majors;
    }

    public void setMajors(List<MajorBean> majors) {
        this.majors = majors;
    }
}
