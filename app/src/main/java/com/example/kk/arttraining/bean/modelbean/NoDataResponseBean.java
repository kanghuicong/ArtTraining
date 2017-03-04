package com.example.kk.arttraining.bean.modelbean;

/**
 * 作者：wschenyongyin on 2016/10/19 16:26
 * 说明:用于接收没有返回数据的结果
 */
public class NoDataResponseBean {
    private String error_code;
    private String error_msg;

    public NoDataResponseBean() {
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

    @Override
    public String toString() {
        return "NoDataResponseBean{" +
                "error_code='" + error_code + '\'' +
                ", error_msg='" + error_msg + '\'' +
                '}';
    }
}
