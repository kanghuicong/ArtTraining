package com.example.kk.arttraining.ui.valuation.view;

import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.ui.school.bean.SchoolBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/29 09:42
 * 说明:
 */
public interface IChoseTeacher {
    //获取老师列表
    void getTeacherData(List<TecInfoBean> tecInfoBeanList);

    //获取院校列表
    void getSchoolData(List<SchoolBean> schoolBeanList);

    void showLoading();

    void hideLoading();

    void onFailure();
}
