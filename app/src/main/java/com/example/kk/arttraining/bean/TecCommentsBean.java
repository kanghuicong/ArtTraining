package com.example.kk.arttraining.bean;

/**
 * 作者：wschenyongyin on 2016/10/19 19:54
 * 说明:老师评论bean
 */
public class TecCommentsBean {
    int tec_id;
    int comm_id;
    private String type;
    private String content;
    private String comm_time;
    private String duration;
    private String attr;
    private String comm_type;
    private String thumbnail;

    public TecCommentsBean() {

    }

    public int getTec_id() {
        return tec_id;
    }

    public void setTec_id(int tec_id) {
        this.tec_id = tec_id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getComm_id() {
        return comm_id;
    }

    public void setComm_id(int comm_id) {
        this.comm_id = comm_id;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getComm_time() {
        return comm_time;
    }

    public void setComm_time(String comm_time) {
        this.comm_time = comm_time;
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

    @Override
    public String toString() {
        return "TecCommentsBean{" +
                "comm_type='" + comm_type + '\'' +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
