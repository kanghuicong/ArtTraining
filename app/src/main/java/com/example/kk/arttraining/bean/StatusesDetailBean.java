package com.example.kk.arttraining.bean;

import com.example.kk.arttraining.bean.parsebean.CommentsBean;
import com.example.kk.arttraining.bean.parsebean.ParseCommentDetail;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/20 08:42
 * 说明:获取动态详情
 */
public class StatusesDetailBean {
    private String error_code;
    private String error_msg;
    private String type;
    private int stus_id;
    private int owner;
    private String owner_type;
    private String owner_name;
    private String owner_head_pic;
    private String create_time;
    private String city;
    private String tag;
    private String identity;
    private String title;
    private int theme_id;
    private String thteme_name;
    private String content;
    private int browse_num;
    private int comment_num;
    private int like_num;
    private String is_like;
    private String is_comment;
    private String comment_tec;
    private String comment_tec_uni;
    private List<AttachmentBean> att;
    private int tec_comment_num;
    private TecInfoBean tec;
    private String remarks;
    private List<ParseCommentDetail> tec_comments_list;
//    private List<TecCommentsBean> tec_comments;
    private List<CommentsBean> comments;
    private AdvertisBean ad;

    public int getStus_id() {
        return stus_id;
    }

    public void setStus_id(int stus_id) {
        this.stus_id = stus_id;
    }

    public String getIs_comment() {
        return is_comment;
    }

    public void setIs_comment(String is_comment) {
        this.is_comment = is_comment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOwner_head_pic() {
        return owner_head_pic;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void setOwner_head_pic(String owner_head_pic) {
        this.owner_head_pic = owner_head_pic;
    }

    public StatusesDetailBean() {
    }

    public AdvertisBean getAd() {
        return ad;
    }

    public void setAd(AdvertisBean ad) {
        this.ad = ad;
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

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
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

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public TecInfoBean getTec() {
        return tec;
    }

    public void setTec(TecInfoBean tec) {
        this.tec = tec;
    }

    public int getTec_comment_num() {
        return tec_comment_num;
    }

    public void setTec_comment_num(int tec_comment_num) {
        this.tec_comment_num = tec_comment_num;
    }

    public List<ParseCommentDetail> getTec_comments_list() {
        return tec_comments_list;
    }

    public void setTec_comments_list(List<ParseCommentDetail> tec_comments_list) {
        this.tec_comments_list = tec_comments_list;
    }

    public int getTheme_id() {
        return theme_id;
    }

    public void setTheme_id(int theme_id) {
        this.theme_id = theme_id;
    }

    public String getThteme_name() {
        return thteme_name;
    }

    public void setThteme_name(String thteme_name) {
        this.thteme_name = thteme_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "StatusesDetailBean{" +
                "ad=" + ad +
                ", error_code='" + error_code + '\'' +
                ", error_msg='" + error_msg + '\'' +
                ", type='" + type + '\'' +
                ", stus_id=" + stus_id +
                ", owner=" + owner +
                ", owner_type='" + owner_type + '\'' +
                ", owner_name='" + owner_name + '\'' +
                ", owner_head_pic='" + owner_head_pic + '\'' +
                ", create_time='" + create_time + '\'' +
                ", city='" + city + '\'' +
                ", tag='" + tag + '\'' +
                ", identity='" + identity + '\'' +
                ", title='" + title + '\'' +
                ", theme_id=" + theme_id +
                ", thteme_name='" + thteme_name + '\'' +
                ", content='" + content + '\'' +
                ", browse_num=" + browse_num +
                ", comment_num=" + comment_num +
                ", like_num=" + like_num +
                ", is_like='" + is_like + '\'' +
                ", is_comment='" + is_comment + '\'' +
                ", comment_tec='" + comment_tec + '\'' +
                ", comment_tec_uni='" + comment_tec_uni + '\'' +
                ", att=" + att +
                ", tec_comment_num=" + tec_comment_num +
                ", tec=" + tec +
                ", remarks='" + remarks + '\'' +
                ", tec_comments_list=" + tec_comments_list +
                ", comments=" + comments +
                '}';
    }
}
