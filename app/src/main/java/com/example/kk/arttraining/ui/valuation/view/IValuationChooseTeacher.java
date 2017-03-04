package com.example.kk.arttraining.ui.valuation.view;

import com.example.kk.arttraining.bean.modelbean.TecInfoBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/15 11:18
 * 说明:选择老师
 */
public interface IValuationChooseTeacher {

    void SuccessSearch(List<TecInfoBean> tecInfoBeanList);

    void SuccessRefresh(List<TecInfoBean> tecInfoBeanList);

    void SuccessLoad(List<TecInfoBean> tecInfoBeanList);

    void FailureSearch(String error_msg);

    void FailureRefresh(String error_msg);

    void FailureLoad(int flag);

}
