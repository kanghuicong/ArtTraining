package com.example.kk.arttraining.ui.homePage.bean;

import com.example.kk.arttraining.bean.NoDataResponseBean;

import java.util.List;

/**
 * Created by kanghuicong on 2016/11/11.
 * QQ邮箱:515849594@qq.com
 */
public class SearchHomepagerBean extends NoDataResponseBean {

    private List<Object> search;

    public List<Object> getSearch() {
        return search;
    }

    public void setSearch(List<Object> search) {
        this.search = search;
    }
}
