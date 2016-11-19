package com.example.kk.arttraining.ui.me.bean;

import com.example.kk.arttraining.bean.NoDataResponseBean;

import java.io.Serializable;

/**
 * 作者：wschenyongyin on 2016/11/9 09:56
 * 说明:获取我的页面的统计
 */
public class UserCountBean extends NoDataResponseBean implements Serializable {

    private int bbs_num;
    private int group_num;
    private int favorite_num;
    private int comment_num;
    private int follow_num;
    private int fans_num;


    public UserCountBean() {
    }

    public int getBbs_num() {
        return bbs_num;
    }

    public void setBbs_num(int bbs_num) {
        this.bbs_num = bbs_num;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public int getFans_num() {
        return fans_num;
    }

    public void setFans_num(int fans_num) {
        this.fans_num = fans_num;
    }

    public int getFavorite_num() {
        return favorite_num;
    }

    public void setFavorite_num(int favorite_num) {
        this.favorite_num = favorite_num;
    }

    public int getFollow_num() {
        return follow_num;
    }

    public void setFollow_num(int follow_num) {
        this.follow_num = follow_num;
    }

    public int getGroup_num() {
        return group_num;
    }

    public void setGroup_num(int group_num) {
        this.group_num = group_num;
    }
}
