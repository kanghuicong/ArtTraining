package com.example.kk.arttraining.ui.homePage.bean;

import com.example.kk.arttraining.bean.modelbean.NoDataResponseBean;
import com.example.kk.arttraining.custom.view.NoPreloadViewPager;

import java.util.List;

/**
 * Created by kanghuicong on 2017/3/13.
 * QQ邮箱:515849594@qq.com
 */
public class ExamineBean extends NoDataResponseBean {
    List<ExamineListBean> list;

    public List<ExamineListBean> getList() {
        return list;
    }

    public void setList(List<ExamineListBean> list) {
        this.list = list;
    }
}
