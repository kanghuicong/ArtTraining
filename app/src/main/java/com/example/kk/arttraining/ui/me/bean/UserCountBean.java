package com.example.kk.arttraining.ui.me.bean;

import com.example.kk.arttraining.bean.NoDataResponseBean;

import java.io.Serializable;

/**
 * 作者：wschenyongyin on 2016/11/9 09:56
 * 说明:获取我的页面的统计
 */
public class UserCountBean extends NoDataResponseBean implements Serializable {
    private int ufans_num;
    private int ufocus_num;
    private int ugroup_num;
    private int utopic_num;
    private int ucollect_num;
    private int ucomment_num;
    private int usenior_num;
    private int utransfor_num;

    public UserCountBean() {
    }

    public int getUcollect_num() {
        return ucollect_num;
    }

    public void setUcollect_num(int ucollect_num) {
        this.ucollect_num = ucollect_num;
    }

    public int getUcomment_num() {
        return ucomment_num;
    }

    public void setUcomment_num(int ucomment_num) {
        this.ucomment_num = ucomment_num;
    }

    public int getUfans_num() {
        return ufans_num;
    }

    public void setUfans_num(int ufans_num) {
        this.ufans_num = ufans_num;
    }

    public int getUfocus_num() {
        return ufocus_num;
    }

    public void setUfocus_num(int ufocus_num) {
        this.ufocus_num = ufocus_num;
    }

    public int getUgroup_num() {
        return ugroup_num;
    }

    public void setUgroup_num(int ugroup_num) {
        this.ugroup_num = ugroup_num;
    }

    public int getUsenior_num() {
        return usenior_num;
    }

    public void setUsenior_num(int usenior_num) {
        this.usenior_num = usenior_num;
    }

    public int getUtopic_num() {
        return utopic_num;
    }

    public void setUtopic_num(int utopic_num) {
        this.utopic_num = utopic_num;
    }

    public int getUtransfor_num() {
        return utransfor_num;
    }

    public void setUtransfor_num(int utransfor_num) {
        this.utransfor_num = utransfor_num;
    }
}
