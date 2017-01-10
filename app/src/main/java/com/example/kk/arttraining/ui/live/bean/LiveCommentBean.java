package com.example.kk.arttraining.ui.live.bean;

/**
 * 作者：wschenyongyin on 2017/1/9 15:33
 * 说明:
 */
public class LiveCommentBean {

    private int comm_id;
    private int uid;
    private String utype;
    private String name;
    private String content;

    public int getComm_id() {
        return comm_id;
    }

    public void setComm_id(int comm_id) {
        this.comm_id = comm_id;
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

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }

    @Override
    public String toString() {
        return "ParseCommentListBean{" +
                "comm_id=" + comm_id +
                ", uid=" + uid +
                ", utype='" + utype + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
