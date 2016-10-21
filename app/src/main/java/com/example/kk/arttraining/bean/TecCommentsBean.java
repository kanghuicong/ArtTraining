package com.example.kk.arttraining.bean;

/**
 * 作者：wschenyongyin on 2016/10/19 19:54
 * 说明:老师评论bean
 */
public class TecCommentsBean {
    private String content;
    private   int sort;
    private  String comm_type;

    public TecCommentsBean() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getComm_type() {
        return comm_type;
    }

    public void setComm_type(String comm_type) {
        this.comm_type = comm_type;
    }
}
