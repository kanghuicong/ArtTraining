package com.example.kk.arttraining.ui.homePage.function.teacher;

import com.example.kk.arttraining.bean.parsebean.TecherList;
import com.example.kk.arttraining.ui.course.bean.ArtTeacherListBean;
import com.example.kk.arttraining.ui.course.bean.ArtTypeListBean;
import com.example.kk.arttraining.ui.homePage.prot.ITeacherArtSearch;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2016/12/23.
 * QQ邮箱:515849594@qq.com
 */
public class TeacherArtSearchData {

    ITeacherArtSearch iTeacherArtSearch;

    public TeacherArtSearchData(ITeacherArtSearch iTeacherArtSearch) {
        this.iTeacherArtSearch = iTeacherArtSearch;
    }

    public void getArtType() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("user_name", Config.ArtName);
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("area_id", 1);

        Callback<ArtTypeListBean> callback = new Callback<ArtTypeListBean>() {
            @Override
            public void onResponse(Call<ArtTypeListBean> call, Response<ArtTypeListBean> response) {
                ArtTypeListBean artTypeListBean = response.body();
                if (response.body() != null) {
                    if (artTypeListBean.getCode() == 0) {
                        iTeacherArtSearch.getArtType(artTypeListBean.getType_list());
                    } else {

                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<ArtTypeListBean> call, Throwable t) {

            }
        };

        Call<ArtTypeListBean> call = HttpRequest.getCourseApi().getArtTypeList(map);
        call.enqueue(callback);
    }



    //artSchool老师
    public void getArtSchoolData(String nation_type,int art_type_id) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("user_name", Config.ArtName);
        if(Config.ACCESS_TOKEN!=null) map.put("access_token", Config.ACCESS_TOKEN);
        map.put("area_id", 1);
        if (nation_type.equals("国内名师")) {
            map.put("nation_type", 1);
        } else if (nation_type.equals("海外华人艺术家")) {
            map.put("nation_type", 2);
        } else if (nation_type.equals("国际名师")) {
            map.put("nation_type", 3);
        }
        map.put("get_count", 20);
        map.put("art_type_id", art_type_id);

        Callback<ArtTeacherListBean> callback = new Callback<ArtTeacherListBean>() {
            @Override
            public void onResponse(Call<ArtTeacherListBean> call, Response<ArtTeacherListBean> response) {
                ArtTeacherListBean artTeacherList = response.body();
                if (response.body() != null) {
                    if (artTeacherList.getCode()== 0) {
                        iTeacherArtSearch.getArtTeacher(artTeacherList.getTeacher_list());
                    } else {
                        iTeacherArtSearch.OnTeacherFailure("数据加载失败");
                    }
                } else {
                    iTeacherArtSearch.OnTeacherFailure("数据加载失败");
                }
            }

            @Override
            public void onFailure(Call<ArtTeacherListBean> call, Throwable t) {
                iTeacherArtSearch.OnTeacherFailure("网络连接失败");
            }
        };

        Call<ArtTeacherListBean> call = HttpRequest.getCourseApi().getArtTeacherList(map);
        call.enqueue(callback);
    }

    //上拉加载
    public void loadTeacherListData(String nation_type,int start_index,int art_type_id) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("user_name", Config.ArtName);
        if(Config.ACCESS_TOKEN!=null) map.put("access_token", Config.ACCESS_TOKEN);
        map.put("area_id", 1);
        if (nation_type.equals("国内名师")) {
            map.put("nation_type", 1);
        } else if (nation_type.equals("海外华人艺术家")) {
            map.put("nation_type", 2);
        } else if (nation_type.equals("国际名师")) {
            map.put("nation_type", 3);
        }
        map.put("art_type_id", art_type_id);
        map.put("start_index", start_index);
        map.put("get_count", 20);

        Callback<ArtTeacherListBean> callback = new Callback<ArtTeacherListBean>() {
            @Override
            public void onResponse(Call<ArtTeacherListBean> call, Response<ArtTeacherListBean> response) {
                ArtTeacherListBean artTeacherList = response.body();
                if (response.body() != null) {
                    if (artTeacherList.getCode()== 0) {
                        iTeacherArtSearch.loadArtTeacher(artTeacherList.getTeacher_list());
                    } else {
                        iTeacherArtSearch.OnLoadTeacherFailure("OnFailure1");
                    }
                } else {
                    iTeacherArtSearch.OnLoadTeacherFailure("OnFailure2");
                }
            }

            @Override
            public void onFailure(Call<ArtTeacherListBean> call, Throwable t) {
                iTeacherArtSearch.OnLoadTeacherFailure("OnFailure3");
            }
        };

        Call<ArtTeacherListBean> call = HttpRequest.getCourseApi().getArtTeacherList(map);
        call.enqueue(callback);
    }
}
