package com.example.kk.arttraining.utils.upload.bean;

import com.example.kk.arttraining.bean.NoDataResponseBean;

/**
 * 作者：wschenyongyin on 2016/11/2 11:36
 * 说明:
 */
public class TokenBean extends NoDataResponseBean {
    private String qiniu_token;

    public TokenBean() {
    }

    public String getQiniu_token() {
        return qiniu_token;
    }

    public void setQiniu_token(String qiniu_token) {
        this.qiniu_token = qiniu_token;
    }
}
