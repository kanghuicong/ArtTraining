package com.example.kk.arttraining.bean.modelbean;


/**
 * 作者：wschenyongyin on 2016/9/22 10:50
 * 说明:后台返回的数据Bean
 */
public class ResponseObject {
    //状态
    private int state;
    //提示信息
    private String msg;
    //数据内容
    private String data;


    public ResponseObject() {
    }

    public ResponseObject(int state, String msg, String data) {
        this.state = state;
        this.msg = msg;
        this.data = data;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
