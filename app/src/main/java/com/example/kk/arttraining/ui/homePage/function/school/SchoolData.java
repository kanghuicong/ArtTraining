package com.example.kk.arttraining.ui.homePage.function.school;

import com.example.kk.arttraining.bean.modelbean.ConditionBean;
import com.example.kk.arttraining.bean.modelbean.ConditionListBean;
import com.example.kk.arttraining.ui.homePage.bean.ParseSchoolListBean;
import com.example.kk.arttraining.ui.homePage.bean.SchoolBean;
import com.example.kk.arttraining.ui.homePage.prot.ISchool;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/10/26 11:51
 * 说明:院校数据获取类
 */
public class SchoolData {
    private ISchool iSchoolMain;

    public SchoolData(ISchool iSchoolMain) {
        this.iSchoolMain = iSchoolMain;
    }

    //获取省份信息
    public void getProvinceData(Map<String, Object> map) {

        Callback<ConditionListBean> callback = new Callback<ConditionListBean>() {
            @Override
            public void onResponse(Call<ConditionListBean> call, Response<ConditionListBean> response) {
                if (response.body() != null) {
                    ConditionListBean bean = response.body();
                    UIUtil.showLog("getProvinceData",response.message()+"-----3");
                    if (bean.getError_code().equals("0")) {
                        List<ConditionBean> beanList = bean.getConditions();
                        UIUtil.showLog("getProvinceData",beanList.size()+"-----2");
                        iSchoolMain.getProvinceList(beanList);
                    } else {
                        iSchoolMain.onFailure(bean.getError_code(), bean.getError_msg());
                    }
                } else {
                    iSchoolMain.onFailure(Config.Connection_Failure, Config.Connection_ERROR_TOAST);
                }
            }

            @Override
            public void onFailure(Call<ConditionListBean> call, Throwable t) {
                iSchoolMain.onFailure(Config.Connection_Failure, Config.Connection_ERROR_TOAST);
            }
        };

        Call<ConditionListBean> call = HttpRequest.getSchoolApi().conditionList(map);
        call.enqueue(callback);
    }

    //根据省份获取学校列表
    public void getSchoolData(Map<String, Object> map) {
        Callback<ParseSchoolListBean> callback = new Callback<ParseSchoolListBean>() {
            @Override
            public void onResponse(Call<ParseSchoolListBean> call, Response<ParseSchoolListBean> response) {
                UIUtil.showLog("SchoolMainPresenter_getSchoolData", response.code() + "---->" + response.message());
                if (response.body() != null) {
                    ParseSchoolListBean bean = response.body();
                    if (bean.getError_code().equals("0")) {
                        List<SchoolBean> schoolBeanList = bean.getInstitutions();
                        UIUtil.showLog("SchoolMainPresenter_schoolBeanList", schoolBeanList.size()+"");
                        iSchoolMain.getSchoolList(schoolBeanList);
                    } else {
                        iSchoolMain.onFailure(bean.getError_code(), bean.getError_msg());
                    }
                } else {
                    iSchoolMain.onFailure(Config.Connection_Failure, Config.Connection_ERROR_TOAST);
                }
            }

            @Override
            public void onFailure(Call<ParseSchoolListBean> call, Throwable t) {
                UIUtil.showLog("SchoolMainPresenter_getSchoolData", "onFailure-->" + t.getMessage() + "---->" + t.getCause());
                iSchoolMain.onFailure(Config.Connection_Failure, Config.Connection_ERROR_TOAST);
            }
        };

        Call<ParseSchoolListBean> call = HttpRequest.getSchoolApi().schoolList(map);
        call.enqueue(callback);
    }
}
