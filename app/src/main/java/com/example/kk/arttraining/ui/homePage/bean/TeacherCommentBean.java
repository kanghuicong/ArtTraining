package com.example.kk.arttraining.ui.homePage.bean;

import com.example.kk.arttraining.bean.modelbean.TecCommentsBean;
import com.example.kk.arttraining.bean.modelbean.TecInfoBean;

/**
 * Created by kanghuicong on 2016/12/2.
 * QQ邮箱:515849594@qq.com
 */
public class TeacherCommentBean {
    TecInfoBean tecInfo;
    TecCommentsBean tecComment;

    public TecInfoBean getTecInfo() {
        return tecInfo;
    }

    public void setTecInfo(TecInfoBean tecInfo) {
        this.tecInfo = tecInfo;
    }

    public TecCommentsBean getTecComment() {
        return tecComment;
    }

    public void setTecComment(TecCommentsBean tecComment) {
        this.tecComment = tecComment;
    }
}
