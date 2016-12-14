package com.example.kk.arttraining.ui.me.view;

import com.example.kk.arttraining.bean.LocationBean;
import com.example.kk.arttraining.bean.OrgBean;
import com.example.kk.arttraining.ui.homePage.bean.ProvinceBean;
import com.example.kk.arttraining.ui.homePage.bean.SchoolBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/11 14:18
 * 说明:
 */
public interface IOrgSchoolShowActivity {

    void getProvinceData();

    void getCityData();

    void getSchoolData();

    void getOrgData();

    void SuccessProvince(List<ProvinceBean> provinceBeanList);

    void SuccessCity(List<LocationBean> cityBeenList);

    void SuccessOrg( List<OrgBean> orgBeanList);

    void SuccessSchool(List<SchoolBean> schoolBeanList);

    void Failure(String error_code);

    void showLoading();

    void hideLoading();


}
