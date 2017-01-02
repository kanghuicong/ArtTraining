package com.example.kk.arttraining.receiver.bean;

/**
 * 作者：wschenyongyin on 2017/1/2 09:12
 * 说明:极光推送messageBean
 */
public class JpushMessageBean {

    int msg_num;
    int follow_num;
    int fans_num;
    int works_num;
    int bbs_num;
    String type;


    public JpushMessageBean() {
    }

    public int getBbs_num() {
        return bbs_num;
    }

    public void setBbs_num(int bbs_num) {
        this.bbs_num = bbs_num;
    }

    public int getFans_num() {
        return fans_num;
    }

    public void setFans_num(int fans_num) {
        this.fans_num = fans_num;
    }

    public int getFollow_num() {
        return follow_num;
    }

    public void setFollow_num(int follow_num) {
        this.follow_num = follow_num;
    }

    public int getMsg_num() {
        return msg_num;
    }

    public void setMsg_num(int msg_num) {
        this.msg_num = msg_num;
    }

    public int getWorks_num() {
        return works_num;
    }

    public void setWorks_num(int works_num) {
        this.works_num = works_num;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "JpushMessageBean{" +
                "bbs_num=" + bbs_num +
                ", msg_num=" + msg_num +
                ", follow_num=" + follow_num +
                ", fans_num=" + fans_num +
                ", works_num=" + works_num +
                '}';
    }
}
