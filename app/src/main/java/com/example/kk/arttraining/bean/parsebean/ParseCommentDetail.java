package com.example.kk.arttraining.bean.parsebean;

import com.example.kk.arttraining.bean.modelbean.TecCommentsBean;
import com.example.kk.arttraining.bean.modelbean.TecInfoBean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/6 16:30
 * 说明:获取帖子详情中的老师评论详情
 */
public class ParseCommentDetail implements Serializable{
    private TecInfoBean tec;
    private List<TecCommentsBean> tec_comments;

    public ParseCommentDetail() {
    }

    public TecInfoBean getTec() {
        return tec;
    }

    public void setTec(TecInfoBean tec) {
        this.tec = tec;
    }

    public List<TecCommentsBean> getTec_comments() {
        return tec_comments;
    }

    public void setTec_comments(List<TecCommentsBean> tec_comments) {
        this.tec_comments = tec_comments;
    }

    @Override
    public String toString() {
        return "ParseCommentDetail{" +
                "tec=" + tec +
                ", tec_comments=" + tec_comments +
                '}';
    }
}
