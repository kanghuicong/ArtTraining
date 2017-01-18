package com.example.kk.arttraining.ui.homePage.bean;

import com.example.kk.arttraining.bean.NoDataResponseBean;

import java.util.List;

/**
 * Created by kanghuicong on 2017/1/17.
 * QQ邮箱:515849594@qq.com
 */
public class LiveList extends NoDataResponseBean {
    List<LiveListBean> openclass_list;

    public List<LiveListBean> getOpenclass_list() {
        return openclass_list;
    }

    public void setOpenclass_list(List<LiveListBean> openclass_list) {
        this.openclass_list = openclass_list;
    }
}
