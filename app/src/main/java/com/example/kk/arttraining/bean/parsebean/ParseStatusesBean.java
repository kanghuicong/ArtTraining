package com.example.kk.arttraining.bean.parsebean;

import com.example.kk.arttraining.bean.AttachmentBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/20 19:11
 * 说明:小组动态列表
 */
public class ParseStatusesBean {
    private int stus_id;
    private String stus_type;
    private int owner;
    private String owner_type;
    private String owner_name;
    private String owner_head_pic;
    private String create_time;
    private String city;
    private String tag;
    private String identity;
    private int theme_id;
    private String theme_name;
    private String content;
    private int browse_num;
    private int comment_num;
    private int like_num;
    private String is_like;
    private String comment_tec;
    private String comment_tec_uni;
    private List<AttachmentBean> att;

    public ParseStatusesBean() {
    }

    public String getOwner_head_pic() {
        return owner_head_pic;
    }

    public void setOwner_head_pic(String owner_head_pic) {
        this.owner_head_pic = owner_head_pic;
    }

    public List<AttachmentBean> getAtt() {
        return att;
    }

    public void setAtt(List<AttachmentBean> att) {
        this.att = att;
    }

    public int getBrowse_num() {
        return browse_num;
    }

    public void setBrowse_num(int browse_num) {
        this.browse_num = browse_num;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public String getComment_tec() {
        return comment_tec;
    }

    public void setComment_tec(String comment_tec) {
        this.comment_tec = comment_tec;
    }

    public String getComment_tec_uni() {
        return comment_tec_uni;
    }

    public void setComment_tec_uni(String comment_tec_uni) {
        this.comment_tec_uni = comment_tec_uni;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getIs_like() {
        return is_like;
    }

    public void setIs_like(String is_like) {
        this.is_like = is_like;
    }

    public int getLike_num() {
        return like_num;
    }

    public void setLike_num(int like_num) {
        this.like_num = like_num;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getOwner_type() {
        return owner_type;
    }

    public void setOwner_type(String owner_type) {
        this.owner_type = owner_type;
    }

    public int getStus_id() {
        return stus_id;
    }

    public void setStus_id(int stus_id) {
        this.stus_id = stus_id;
    }

    public String getStus_type() {
        return stus_type;
    }

    public void setStus_type(String stus_type) {
        this.stus_type = stus_type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getTheme_id() {
        return theme_id;
    }

    public void setTheme_id(int theme_id) {
        this.theme_id = theme_id;
    }

    public String getTheme_name() {
        return theme_name;
    }

    public void setTheme_name(String theme_name) {
        this.theme_name = theme_name;
    }
}
