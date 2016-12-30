package com.example.kk.arttraining.ui.me.bean;

import java.io.Serializable;

/**
 * 作者：wschenyongyin on 2016/12/30 09:51
 * 说明:消息bean
 */
public class MessageBean implements Serializable {
    private int msg_id;
    private int uid;
    private int utype;
    private String name;
    private String b_name;
    private String b_utype;
    private int b_uid;
    private int head_pic;
    private String msg_time;
    private String msg_type;
    private String msg_content;
    private int status_id;
    private String status_type;
    private String status_pic;

    public MessageBean() {
    }

    public int getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(int head_pic) {
        this.head_pic = head_pic;
    }

    public String getMsg_content() {
        return msg_content;
    }

    public void setMsg_content(String msg_content) {
        this.msg_content = msg_content;
    }

    public int getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(int msg_id) {
        this.msg_id = msg_id;
    }

    public String getMsg_time() {
        return msg_time;
    }

    public void setMsg_time(String msg_time) {
        this.msg_time = msg_time;
    }

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public String getStatus_pic() {
        return status_pic;
    }

    public void setStatus_pic(String status_pic) {
        this.status_pic = status_pic;
    }

    public String getStatus_type() {
        return status_type;
    }

    public void setStatus_type(String status_type) {
        this.status_type = status_type;
    }

    public int getUtype() {
        return utype;
    }

    public void setUtype(int utype) {
        this.utype = utype;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getB_name() {
        return b_name;
    }

    public void setB_name(String b_name) {
        this.b_name = b_name;
    }

    public int getB_uid() {
        return b_uid;
    }

    public void setB_uid(int b_uid) {
        this.b_uid = b_uid;
    }

    public String getB_utype() {
        return b_utype;
    }

    public void setB_utype(String b_utype) {
        this.b_utype = b_utype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "b_name='" + b_name + '\'' +
                ", msg_id=" + msg_id +
                ", uid=" + uid +
                ", utype=" + utype +
                ", name='" + name + '\'' +
                ", b_utype='" + b_utype + '\'' +
                ", b_uid=" + b_uid +
                ", head_pic=" + head_pic +
                ", msg_time='" + msg_time + '\'' +
                ", msg_type='" + msg_type + '\'' +
                ", msg_content='" + msg_content + '\'' +
                ", status_id=" + status_id +
                ", status_type='" + status_type + '\'' +
                ", status_pic='" + status_pic + '\'' +
                '}';
    }
}
