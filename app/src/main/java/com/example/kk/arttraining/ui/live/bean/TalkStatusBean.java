package com.example.kk.arttraining.ui.live.bean;

import com.example.kk.arttraining.bean.NoDataResponseBean;

/**
 * 作者：wschenyongyin on 2017/2/7 17:37
 * 说明:
 */
public class TalkStatusBean extends NoDataResponseBean{
    private String is_talk;

    public String getIs_talk() {
        return is_talk;
    }

    public void setIs_talk(String is_talk) {
        this.is_talk = is_talk;
    }

    @Override
    public String toString() {
        return "TalkStatusBean{" +
                "is_talk='" + is_talk + '\'' +
                '}';
    }
}
