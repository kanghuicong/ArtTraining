package com.example.kk.arttraining.ui.school.presenter;

import com.example.kk.arttraining.ui.school.bean.ParseProvinceListBean;
import com.example.kk.arttraining.ui.school.bean.ParseSchoolListBean;
import com.example.kk.arttraining.ui.school.bean.ProvinceBean;
import com.example.kk.arttraining.ui.school.bean.SchoolBean;
import com.example.kk.arttraining.ui.school.view.ISchoolMain;
import com.example.kk.arttraining.utils.HttpRequest;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/10/26 11:51
 * 说明:
 */
public class SchoolMainPresenter {
    private ISchoolMain iSchoolMain;

    public SchoolMainPresenter(ISchoolMain iSchoolMain) {
        this.iSchoolMain = iSchoolMain;
    }

    //获取省份信息
    public void getProvinceData(Map<String, String> map) {
        Callback<ParseProvinceListBean> callback = new Callback<ParseProvinceListBean>() {
            @Override
            public void onResponse(Call<ParseProvinceListBean> call, Response<ParseProvinceListBean> response) {
                if (response.body() != null) {
                    ParseProvinceListBean bean = response.body();
                    if (bean.getError_code().equals("0")) {
                        List<ProvinceBean> beanList = bean.getProvince();
                        iSchoolMain.getProvinceList(beanList);
                    } else {
// TODO: 2016/10/26  获取数据失败
                    }
                } else {
// TODO: 2016/10/26  获取数据失败
                }


            }

            @Override
            public void onFailure(Call<ParseProvinceListBean> call, Throwable t) {
// TODO: 2016/10/26  请求网络失败
            }
        };

        Call<ParseProvinceListBean> call = HttpRequest.getSchoolApi().provinceList(map);

    }

    //根据省份获取学校列表
    public void getSchoolData(Map<String, String> map) {
        Callback<ParseSchoolListBean> callback = new Callback<ParseSchoolListBean>() {
            @Override
            public void onResponse(Call<ParseSchoolListBean> call, Response<ParseSchoolListBean> response) {
                if (response.body() != null) {
                    ParseSchoolListBean bean = response.body();
                    if (bean.getError_code().equals("0")) {
                        List<SchoolBean> schoolBeanList = bean.getInstitutions();
                        iSchoolMain.getSchoolList(schoolBeanList);

                    } else {
// TODO: 2016/10/26  获取数据失败
                    }
                } else {
// TODO: 2016/10/26  获取数据失败
                }
            }

            @Override
            public void onFailure(Call<ParseSchoolListBean> call, Throwable t) {
// TODO: 2016/10/26  请求网络失败
            }
        };

        Call<ParseSchoolListBean> call = HttpRequest.getSchoolApi().schoolList(map);
    }
}
