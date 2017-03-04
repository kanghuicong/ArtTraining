package com.example.kk.arttraining.bean.modelbean;

/**
 * 作者：wschenyongyin on 2016/10/20 08:33
 * 说明:
 */
public class ReplyBean {
    private int user_id;
    private String user_type;
    private String name;
    private String content;
    private String type;

    public ReplyBean() {
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
