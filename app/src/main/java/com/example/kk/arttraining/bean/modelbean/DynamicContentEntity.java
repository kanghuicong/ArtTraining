package com.example.kk.arttraining.bean.modelbean;

import java.io.Serializable;

/**
 * Created by kanghuicong on 2016/10/18.
 * QQ邮箱:515849594@qq.com
 */
public class DynamicContentEntity implements Serializable {
    String content;
    String like_state;

    public String getLike_state() {
        return like_state;
    }

    public void setLike_state(String like_state) {
        this.like_state = like_state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
