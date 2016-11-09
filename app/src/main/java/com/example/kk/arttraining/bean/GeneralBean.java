package com.example.kk.arttraining.bean;

/**
 * 作者：wschenyongyin on 2016/10/20 09:07
 * 说明:通用bean
 */
public class GeneralBean {
    private String error_code;
    private String error_msg;
    private int id;
    private String user_code;
    private String name;


    public GeneralBean() {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    @Override
    public String toString() {
        return "GeneralBean{" +
                "error_code='" + error_code + '\'' +
                ", error_msg='" + error_msg + '\'' +
                ", id=" + id +
                ", user_code='" + user_code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
