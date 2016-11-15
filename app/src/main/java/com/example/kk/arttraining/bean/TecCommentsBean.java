package com.example.kk.arttraining.bean;

/**
 * 作者：wschenyongyin on 2016/10/19 19:54
 * 说明:老师评论bean
 */
public class TecCommentsBean {
    private String type;
    private String content;
    private  String comm_type;

    public TecCommentsBean() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getComm_type() {
        return comm_type;
    }

    public void setComm_type(String comm_type) {
        this.comm_type = comm_type;
    }
}
