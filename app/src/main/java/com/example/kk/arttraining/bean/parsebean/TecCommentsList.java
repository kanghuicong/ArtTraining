package com.example.kk.arttraining.bean.parsebean;

import com.example.kk.arttraining.bean.modelbean.AdvertisBean;
import com.example.kk.arttraining.bean.modelbean.TecCommentsBean;
import com.example.kk.arttraining.bean.modelbean.TecInfoBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/20 11:25
 * 说明:名师点评列表
 */
public class TecCommentsList {
    private String error_code;
    private String error_msg;
    private int id;
    private String comment_tec;
    private String comment_tec_uni;
    private int tec_comment_num;
    private TecInfoBean tec;
    private List<TecCommentsBean> tec_comments;
    private AdvertisBean ad;

    public TecCommentsList() {
    }

    public AdvertisBean getAd() {
        return ad;
    }

    public void setAd(AdvertisBean ad) {
        this.ad = ad;
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

    public List<TecCommentsBean> getTec_comments() {
        return tec_comments;
    }

    public void setTec_comments(List<TecCommentsBean> tec_comments) {
        this.tec_comments = tec_comments;
    }
}
