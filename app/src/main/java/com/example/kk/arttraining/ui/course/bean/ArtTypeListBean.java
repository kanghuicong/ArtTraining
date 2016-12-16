package com.example.kk.arttraining.ui.course.bean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/12/15 11:35
 * 说明:艺术类别列表
 */
public class ArtTypeListBean {
    private int code;
    private List<ArtTypeBean> type_list;

    public ArtTypeListBean() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ArtTypeBean> getType_list() {
        return type_list;
    }

    public void setType_list(List<ArtTypeBean> type_list) {
        this.type_list = type_list;
    }


    @Override
    public String toString() {
        return "ArtTypeListBean{" +
                "code=" + code +
                ", type_list=" + type_list +
                '}';
    }
}
