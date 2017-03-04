package com.example.kk.arttraining.bean.parsebean;

import com.example.kk.arttraining.bean.modelbean.AssessmentsBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/20 15:00
 * 说明:获取测评列表
 */
public class AssessmentsListBean {
    private String error_code;
    private String error_msg;
    List<AssessmentsBean> assessments;

    public AssessmentsListBean() {
    }

    public List<AssessmentsBean> getAssessments() {
        return assessments;
    }

    public void setAssessments(List<AssessmentsBean> assessments) {
        this.assessments = assessments;
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
}
