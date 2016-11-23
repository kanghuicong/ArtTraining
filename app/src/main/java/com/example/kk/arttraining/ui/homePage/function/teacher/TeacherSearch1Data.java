package com.example.kk.arttraining.ui.homePage.function.teacher;

import com.example.kk.arttraining.bean.parsebean.TecherList;
import com.example.kk.arttraining.ui.homePage.prot.ITeacherSearch;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2016/11/22.
 * QQ邮箱:515849594@qq.com
 */
public class TeacherSearch1Data {
    static ITeacherSearch iTeacherSearch ;

    public TeacherSearch1Data(ITeacherSearch iTeacherSearch) {
        this.iTeacherSearch = iTeacherSearch;
    }

    //获取声乐老师列表
    public void getTeacherListData(String type) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", "");
        map.put("spec", type);


        Callback<TecherList> callback = new Callback<TecherList>() {
            @Override
            public void onResponse(Call<TecherList> call, Response<TecherList> response) {
                TecherList techerList = response.body();
                if (response.body() != null) {
                    if (techerList.getError_code().equals("0")) {
                        iTeacherSearch.getTeacher(techerList.getTec());
                    }
                } else {
                    iTeacherSearch.OnFailure(techerList.getError_code());
                }
            }

            @Override
            public void onFailure(Call<TecherList> call, Throwable t) {
                iTeacherSearch.OnTeacherFailure("OnFailure");
            }
        };
        Call<TecherList> call = HttpRequest.getCommonApi().techerList(map);
        call.enqueue(callback);
    }

    //上拉加载
    public void loadTeacherListData(int self, String type) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", "");
        map.put("self", self);
        map.put("spec", type);


        Callback<TecherList> callback = new Callback<TecherList>() {
            @Override
            public void onResponse(Call<TecherList> call, Response<TecherList> response) {
                TecherList techerList = response.body();
                UIUtil.showLog("名师列表response", response.body() + "----" + techerList.getError_code());
                if (response.body() != null) {
                    if (techerList.getError_code().equals("0")) {
                        UIUtil.showLog("loadTeacherResponse", techerList.getTec() + "");
                        iTeacherSearch.loadTeacher(techerList.getTec());
                    } else {
                        iTeacherSearch.OnLoadTeacherFailure(techerList.getError_msg());
                    }
                } else {
                    iTeacherSearch.OnLoadTeacherFailure(techerList.getError_msg());
                }
            }

            @Override
            public void onFailure(Call<TecherList> call, Throwable t) {
                iTeacherSearch.OnLoadTeacherFailure("OnFailure");
            }
        };

        Call<TecherList> call = HttpRequest.getCommonApi().techerList(map);
        call.enqueue(callback);
    }
}
