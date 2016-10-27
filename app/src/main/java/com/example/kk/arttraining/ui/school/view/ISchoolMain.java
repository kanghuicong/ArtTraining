package com.example.kk.arttraining.ui.school.view;

import com.example.kk.arttraining.ui.school.bean.ParseProvinceListBean;
import com.example.kk.arttraining.ui.school.bean.ParseSchoolListBean;
import com.example.kk.arttraining.ui.school.bean.ProvinceBean;
import com.example.kk.arttraining.ui.school.bean.SchoolBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/10/26 11:32
 * 说明:
 */
public interface ISchoolMain {
    //获取省份列表信息
    public void getProvinceList(List<ProvinceBean> provinceBeanList);

    //获取院校列表信息
    public void getSchoolList(List<SchoolBean> schoolBeanList);

    //设置默认的学院列表
    public void DefaultSchoolList(List<SchoolBean> schoolBeanList);

    //显示加载dialog
    public void showLoading();

    //隐藏加载dialog
    public void hideLoading();

    //请求失败
    public void onFailure();

}
