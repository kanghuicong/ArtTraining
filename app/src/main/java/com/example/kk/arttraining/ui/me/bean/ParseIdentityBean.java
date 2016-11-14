package com.example.kk.arttraining.ui.me.bean;

import com.example.kk.arttraining.bean.NoDataResponseBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/13 10:40
 * 说明:
 */
public class ParseIdentityBean extends NoDataResponseBean {

    List<IdentityBean> IdentityList;

    public List<IdentityBean> getIdentityList() {
        return IdentityList;
    }

    public void setIdentityList(List<IdentityBean> identityList) {
        IdentityList = identityList;
    }
}
