package com.example.kk.arttraining.bean.parsebean;

import com.example.kk.arttraining.bean.AdvertisBean;
import com.example.kk.arttraining.bean.parsebean.CommentsBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/20 09:16
 * 说明:获取动态的评论列表bean
 */
public class CommentsListBean {
    private String error_code;
    private String error_msg;
    private int id;
    private int comment_num;
    private List<CommentsBean> comments;
    private AdvertisBean ad;

    public CommentsListBean() {
    }

    public AdvertisBean getAd() {
        return ad;
    }

    public void setAd(AdvertisBean ad) {
        this.ad = ad;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
