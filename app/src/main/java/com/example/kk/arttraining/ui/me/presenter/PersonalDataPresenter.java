package com.example.kk.arttraining.ui.me.presenter;

import com.example.kk.arttraining.bean.parsebean.OrgListBean;
import com.example.kk.arttraining.ui.me.bean.ParseCitysBean;
import com.example.kk.arttraining.ui.me.view.IOrgSchoolShowActivity;
import com.example.kk.arttraining.ui.homePage.bean.ParseProvinceListBean;
import com.example.kk.arttraining.ui.homePage.bean.ParseSchoolListBean;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/11/11 14:08
 * 说明:个人设置中的数据处理处理类
 */
public class PersonalDataPresenter {
    private IOrgSchoolShowActivity iOrgSchoolShowActivity;

    public PersonalDataPresenter(IOrgSchoolShowActivity iOrgSchoolShowActivity) {
        this.iOrgSchoolShowActivity = iOrgSchoolShowActivity;

    }

    //获取省份数据
    public void getProvinceData(Map<String, Object> map) {
        Callback<ParseProvinceListBean> callback = new Callback<ParseProvinceListBean>() {
            @Override
            public void onResponse(Call<ParseProvinceListBean> call, Response<ParseProvinceListBean> response) {
                ParseProvinceListBean provinceListBean = response.body();

                if (provinceListBean != null) {
                    if (provinceListBean.getError_code().equals("0")) {
                        iOrgSchoolShowActivity.SuccessProvince(provinceListBean.getProvince());
                    } else {
                        iOrgSchoolShowActivity.Failure(provinceListBean.getError_msg());
                    }

                } else {
                    iOrgSchoolShowActivity.Failure("网络连接失败");
                }

            }

            @Override
            public void onFailure(Call<ParseProvinceListBean> call, Throwable t) {
                UIUtil.showLog("PersonalDataPresenter.class_getProvinceData","onFailure-->"+t.getMessage()+"-->"+t.getCause());
                iOrgSchoolShowActivity.Failure("网络连接失败");
            }
        };
        Call<ParseProvinceListBean> call = HttpRequest.getCommonApi().locationProvince(map);
        call.enqueue(callback);
    }

    //获取机构数据
    public void getOrgData(Map<String, Object> map) {
        Callback<OrgListBean> callback = new Callback<OrgListBean>() {
            @Override
            public void onResponse(Call<OrgListBean> call, Response<OrgListBean> response) {
                UIUtil.showLog("PersonalDataPresenter.class_getOrgData","onResponse-->"+response.code()+"-->"+response.message());

                OrgListBean orgListBean = response.body();
                if (orgListBean != null) {
                    if (orgListBean.getError_code().equals("0")) {
                        iOrgSchoolShowActivity.SuccessOrg(orgListBean.getOrg());
                    } else {
                        iOrgSchoolShowActivity.Failure(orgListBean.getError_msg());
                    }

                } else {
                    iOrgSchoolShowActivity.Failure("网络连接失败");
                }

            }

            @Override
            public void onFailure(Call<OrgListBean> call, Throwable t) {
                UIUtil.showLog("PersonalDataPresenter.class_getOrgData","onFailure-->"+t.getMessage()+"-->"+t.getCause());
                iOrgSchoolShowActivity.Failure("网络连接失败");
            }
        };
        Call<OrgListBean> call = HttpRequest.getCommonApi().orgList(map);
        call.enqueue(callback);

    }

    //获取城市数据
    public void getCityData(Map<String, Object> map) {
        Callback<ParseCitysBean> callback = new Callback<ParseCitysBean>() {
            @Override
            public void onResponse(Call<ParseCitysBean> call, Response<ParseCitysBean> response) {
                UIUtil.showLog("PersonalDataPresenter.class_getCityData","onResponse-->"+response.code()+"-->"+response.message());

                ParseCitysBean citysBean = response.body();
                if (citysBean != null) {
                    if (citysBean.getError_code().equals("0")) {
                        UIUtil.showLog("PersonalDataPresenter.class_getCityData","onResponse-->"+citysBean.getCitys().toString());
                        iOrgSchoolShowActivity.SuccessCity(citysBean.getCitys());
                    } else {
                        iOrgSchoolShowActivity.Failure(citysBean.getError_msg());
                    }
                } else {
                    iOrgSchoolShowActivity.Failure("网络连接失败");
                }
            }

            @Override
            public void onFailure(Call<ParseCitysBean> call, Throwable t) {
                iOrgSchoolShowActivity.Failure("网络连接失败");
                UIUtil.showLog("PersonalDataPresenter.class_getCityData","onFailure-->"+t.getMessage()+"-->"+t.getCause());
            }
        };
        Call<ParseCitysBean> call = HttpRequest.getCommonApi().locationMeCity(map);
        call.enqueue(callback);
    }

    //获取院校数据
    public void getSchoolData(Map<String, Object> map) {

        Callback<ParseSchoolListBean> callback = new Callback<ParseSchoolListBean>() {
            @Override
            public void onResponse(Call<ParseSchoolListBean> call, Response<ParseSchoolListBean> response) {
                UIUtil.showLog("PersonalDataPresenter.class_getSchoolData","onResponse-->"+response.code()+"-->"+response.message());

                ParseSchoolListBean schoolListBean = response.body();
                if (schoolListBean != null) {
                    if (schoolListBean.getError_code().equals("0")) {
                        iOrgSchoolShowActivity.SuccessSchool(schoolListBean.getInstitutions());
                    } else {
                        iOrgSchoolShowActivity.Failure(schoolListBean.getError_msg());
                    }
                } else {
                    iOrgSchoolShowActivity.Failure("网络连接失败");
                }
            }

            @Override
            public void onFailure(Call<ParseSchoolListBean> call, Throwable t) {
                iOrgSchoolShowActivity.Failure("网络连接失败");
                UIUtil.showLog("PersonalDataPresenter.class_getSchoolData","onFailure-->"+t.getMessage()+"-->"+t.getCause());

            }
        };
        Call<ParseSchoolListBean> call = HttpRequest.getSchoolApi().schoolList(map);
        call.enqueue(callback);
    }

}
