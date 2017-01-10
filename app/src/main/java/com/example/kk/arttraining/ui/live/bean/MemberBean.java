package com.example.kk.arttraining.ui.live.bean;

/**
 * 作者：wschenyongyin on 2017/1/9 15:32
 * 说明:直播间用户信息bean
 */
public class MemberBean {

    private int uid;
    private String name;
    private String utype;
    private String head_pic;
    //owner房主  member成员
    private String member_type;

    private int member_id;


    public MemberBean() {
    }

    public String getHead_pic() {
        return head_pic;
    }

    public void setHead_pic(String head_pic) {
        this.head_pic = head_pic;
    }

    public String getMember_type() {
        return member_type;
    }

    public void setMember_type(String member_type) {
        this.member_type = member_type;
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

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    @Override
    public String toString() {
        return "MemberBean{" +
                "head_pic='" + head_pic + '\'' +
                ", uid=" + uid +
                ", name='" + name + '\'' +
                ", utype='" + utype + '\'' +
                ", member_type='" + member_type + '\'' +
                ", member_id=" + member_id +
                '}';
    }
}
