package com.example.kk.arttraining.ui.school.presenter;

import com.example.kk.arttraining.ui.school.bean.ParseProvinceListBean;
import com.example.kk.arttraining.ui.school.bean.ParseSchoolListBean;
import com.example.kk.arttraining.ui.school.bean.ProvinceBean;
import com.example.kk.arttraining.ui.school.bean.SchoolBean;
import com.example.kk.arttraining.ui.school.view.ISchoolMain;
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
public class SchoolMainPresenter {
    private ISchoolMain iSchoolMain;

    public SchoolMainPresenter(ISchoolMain iSchoolMain) {
        this.iSchoolMain = iSchoolMain;
    }

    //获取省份信息
    public void getProvinceData(Map<String, Object> map) {
        Callback<ParseProvinceListBean> callback = new Callback<ParseProvinceListBean>() {
            @Override
            public void onResponse(Call<ParseProvinceListBean> call, Response<ParseProvinceListBean> response) {
                if (response.body() != null) {
                    ParseProvinceListBean bean = response.body();
                    if (bean.getError_code().equals("0")) {
                        List<ProvinceBean> beanList = bean.getProvince();
                        iSchoolMain.getProvinceList(beanList);
                    } else {
                        iSchoolMain.onFailure(bean.getError_code(), bean.getError_msg());
                    }
                } else {
                    iSchoolMain.onFailure(Config.Connection_Failure, Config.Connection_ERROR_TOAST);
                }
            }

            @Override
            public void onFailure(Call<ParseProvinceListBean> call, Throwable t) {
                iSchoolMain.onFailure(Config.Connection_Failure, Config.Connection_ERROR_TOAST);
            }
        };

        Call<ParseProvinceListBean> call = HttpRequest.getCommonApi().locationProvince(map);
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
