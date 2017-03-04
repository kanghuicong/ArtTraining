package com.example.kk.arttraining.ui.homePage.prot;

import com.example.kk.arttraining.bean.modelbean.ConditionBean;
import com.example.kk.arttraining.ui.homePage.bean.SchoolBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/26 11:32
 * 说明:
 */
public interface ISchool {
    //获取省份列表信息
    public void getProvinceList(List<ConditionBean> provinceBeanList);

    //获取院校列表信息
    public void getSchoolList(List<SchoolBean> schoolBeanList);

    //设置默认的学院列表
    public void DefaultSchoolList(List<SchoolBean> schoolBeanList);

    //显示加载dialog
    public void showLoading();

    //隐藏加载dialog
    public void hideLoading();

    //请求失败
    public void onFailure(String error_code,String error_msg);

}
