package com.example.kk.arttraining.ui.live.bean;

import com.example.kk.arttraining.bean.modelbean.NoDataResponseBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2017/1/9 15:56
 * 说明:解析直播间评论列表
 */
public class ParseCommentListBean extends NoDataResponseBean {

    private List<LiveCommentBean> comment_list;


    public List<LiveCommentBean> getComment_list() {
        return comment_list;
    }

    public void setComment_list(List<LiveCommentBean> comment_list) {
        this.comment_list = comment_list;
    }

    public ParseCommentListBean() {
    }

    @Override
    public String toString() {
        return "ParseCommentListBean{" +
                "comment_list=" + comment_list +
                '}';
    }
}
