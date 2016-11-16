package com.example.kk.arttraining.ui.homePage.function.teacher;

import com.example.kk.arttraining.bean.parsebean.ParseMajorBean;
import com.example.kk.arttraining.bean.parsebean.SearchBean;
import com.example.kk.arttraining.ui.homePage.prot.ITeacher;
import com.example.kk.arttraining.ui.school.bean.ParseProvinceListBean;
import com.example.kk.arttraining.ui.school.bean.ParseSchoolListBean;
import com.example.kk.arttraining.ui.school.bean.ProvinceBean;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2016/11/7.
 * QQ邮箱:515849594@qq.com
 */
public class TeacherThemeData {
    ITeacher iTeacher;

    public TeacherThemeData(ITeacher iTeacher) {
        this.iTeacher = iTeacher;
    }

    public void getTeacherMajorData() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token",Config.ACCESS_TOKEN);
        UIUtil.showLog("MajorLeftData", "map");
        Callback<ParseMajorBean> callback = new Callback<ParseMajorBean>() {
            @Override
            public void onResponse(Call<ParseMajorBean> call, Response<ParseMajorBean> response) {
                ParseMajorBean parseMajorBean = response.body();
                UIUtil.showLog("MajorLeftData", "response");
                if (response.body() != null) {
                    UIUtil.showLog("MajorLeftData",parseMajorBean.toString());
                    iTeacher.getMajor(parseMajorBean.getMajors());
                } else {
                    iTeacher.OnFailure(parseMajorBean.getError_code());
                }
            }
            @Override
            public void onFailure(Call<ParseMajorBean> call, Throwable t) {
                iTeacher.OnFailure("onFailure");
            }
        };
        Call<ParseMajorBean> call = HttpRequest.getSchoolApi().majorList(map);
        call.enqueue(callback);
    }

//    public void getTeacherMajorRightData(final int majorFlag,String fatherName) {
//        HashMap<String, String> map = new HashMap<String, String>();
//        map.put("access_token", "");
//        map.put("father_name", fatherName);
//
//        Callback<ParseMajorBean> callback = new Callback<ParseMajorBean>() {
//            @Override
//            public void onResponse(Call<ParseMajorBean> call, Response<ParseMajorBean> response) {
//                ParseMajorBean parseMajorBean = response.body();
//                if (response.body() != null) {
//                    if (majorFlag == 0) {
//                        iTeacher.getMajorRight(parseMajorBean.getMajors());
//                    }else {
//                        iTeacher.getUpdateMajorRight(parseMajorBean.getMajors());
//                    }
//                } else {
//                    iTeacher.OnFailure(parseMajorBean.getError_code());
//                }
//            }
//            @Override
//            public void onFailure(Call<ParseMajorBean> call, Throwable t) {
//                iTeacher.OnFailure("onFailure");
//            }
//        };
//        Call<ParseMajorBean> call = HttpRequest.getSchoolApi().majorList(map);
//        call.enqueue(callback);
//    }

    public void getTeacherSchoolLeftData() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", "");

        Callback<ParseProvinceListBean> callback = new Callback<ParseProvinceListBean>() {
            @Override
            public void onResponse(Call<ParseProvinceListBean> call, Response<ParseProvinceListBean> response) {
                ParseProvinceListBean parseProvinceListBean = response.body();
                if (response.body() != null) {
                    if (parseProvinceListBean.getError_code().equals("0")) {
                        iTeacher.getSchoolLeft(parseProvinceListBean.getProvince());
                    } else {
                        iTeacher.OnFailure(parseProvinceListBean.getError_code());
                    }
                }else {
                    iTeacher.OnFailure(parseProvinceListBean.getError_code());
                }
            }
            @Override
            public void onFailure(Call<ParseProvinceListBean> call, Throwable t) {
                iTeacher.OnFailure("onFailure");
            }
        };

        Call<ParseProvinceListBean> call = HttpRequest.getCommonApi().locationProvince(map);
        call.enqueue(callback);
    }

    public void getTeacherSchoolRightData( final int schoolFlag,String province) {
        final HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", "");
        map.put("uid", Config.User_Id);
        map.put("city_name", province);

        UIUtil.showLog("SchoolRightData", "SchoolRightData");
        final Callback<ParseSchoolListBean> callback = new Callback<ParseSchoolListBean>() {
            @Override
            public void onResponse(Call<ParseSchoolListBean> call, Response<ParseSchoolListBean> response) {
                ParseSchoolListBean parseSchoolListBean = response.body();
                UIUtil.showLog("SchoolRightData", response.body()+"");
                if (response.body() != null) {
                    if (parseSchoolListBean.getError_code().equals("0")) {
                        if (schoolFlag == 0) {
                            UIUtil.showLog("SchoolRightData", "SchoolRightData1");
                            iTeacher.getSchoolRight(parseSchoolListBean.getInstitutions());
                        } else {
                            iTeacher.getUpdateSchoolRight(parseSchoolListBean.getInstitutions());
                        }
                    } else {
                        iTeacher.OnFailure(parseSchoolListBean.getError_code());
                    }
                }
            }

            @Override
            public void onFailure(Call<ParseSchoolListBean> call, Throwable t) {
                iTeacher.OnFailure("onFailure");
            }
        };
        Call<ParseSchoolListBean> call = HttpRequest.getSchoolApi().schoolList(map);
        call.enqueue(callback);
    }

}
