package com.example.kk.arttraining.ui.homePage.bean;

import com.example.kk.arttraining.bean.modelbean.NoDataResponseBean;

import java.util.List;

/**
 * Created by kanghuicong on 2017/1/17.
 * QQ邮箱:515849594@qq.com
 */
public class LiveList extends NoDataResponseBean {
    int pre_page;
    int finish_page;
    int page_limit;

    List<LiveListBean> openclass_list;

    public int getPre_page() {
        return pre_page;
    }

    public void setPre_page(int pre_page) {
        this.pre_page = pre_page;
    }

    public int getFinish_page() {
        return finish_page;
    }

    public void setFinish_page(int finish_page) {
        this.finish_page = finish_page;
    }

    public int getPage_limit() {
        return page_limit;
    }

    public void setPage_limit(int page_limit) {
        this.page_limit = page_limit;
    }

    public List<LiveListBean> getOpenclass_list() {
        return openclass_list;
    }

    public void setOpenclass_list(List<LiveListBean> openclass_list) {
        this.openclass_list = openclass_list;
    }
}
