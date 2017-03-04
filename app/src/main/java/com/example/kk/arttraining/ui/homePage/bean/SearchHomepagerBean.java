package com.example.kk.arttraining.ui.homePage.bean;

import com.example.kk.arttraining.bean.modelbean.GroupBean;
import com.example.kk.arttraining.bean.modelbean.NoDataResponseBean;
import com.example.kk.arttraining.bean.modelbean.TecInfoBean;
import com.example.kk.arttraining.bean.parsebean.OrgListBean;

import java.util.List;

/**
 * Created by kanghuicong on 2016/11/11.
 * QQ邮箱:515849594@qq.com
 */
public class SearchHomepagerBean extends NoDataResponseBean {

    private List<OrgListBean> org;
    private List<TecInfoBean> tec;
    private List<GroupBean> groups;

    public List<OrgListBean> getOrg() {
        return org;
    }

    public void setOrg(List<OrgListBean> org) {
        this.org = org;
    }

    public List<TecInfoBean> getTec() {
        return tec;
    }

    public void setTec(List<TecInfoBean> tec) {
        this.tec = tec;
    }

    public List<GroupBean> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupBean> groups) {
        this.groups = groups;
    }
}
