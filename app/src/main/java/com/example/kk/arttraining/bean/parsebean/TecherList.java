package com.example.kk.arttraining.bean.parsebean;

import com.example.kk.arttraining.bean.modelbean.TecInfoBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/20 10:18
 * 说明:解析名师列表
 */
public class TecherList {
    private String error_code;
    private String error_msg;
    private List<TecInfoBean> tec;

    public TecherList() {

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

    public List<TecInfoBean> getTec() {
        return tec;
    }

    public void setTec(List<TecInfoBean> tec) {
        this.tec = tec;
    }
}
