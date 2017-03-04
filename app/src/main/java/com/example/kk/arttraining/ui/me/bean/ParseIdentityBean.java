package com.example.kk.arttraining.ui.me.bean;

import com.example.kk.arttraining.bean.modelbean.NoDataResponseBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/13 10:40
 * 说明:
 */
public class ParseIdentityBean extends NoDataResponseBean {

    List<IdentityBean> identitys;

    public List<IdentityBean> getIdentitys() {
        return identitys;
    }

    public void setIdentitys(List<IdentityBean> identitys) {
        this.identitys = identitys;
    }
}
