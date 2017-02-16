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
    private String type;
    private String gift_pic;
    private int gift_num;
    private String gift_name;

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

    public int getGift_num() {
        return gift_num;
    }

    public void setGift_num(int gift_num) {
        this.gift_num = gift_num;
    }

    public String getGift_pic() {
        return gift_pic;
    }

    public void setGift_pic(String gift_pic) {
        this.gift_pic = gift_pic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGift_name() {
        return gift_name;
    }

    public void setGift_name(String gift_name) {
        this.gift_name = gift_name;
    }

    @Override
    public String toString() {
        return "LiveCommentBean{" +
                "comm_id=" + comm_id +
                ", uid=" + uid +
                ", utype='" + utype + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", gift_pic='" + gift_pic + '\'' +
                ", gift_num=" + gift_num +
                ", gift_name='" + gift_name + '\'' +
                '}';
    }
}
