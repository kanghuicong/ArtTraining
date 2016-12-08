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
public class TeacherSearchData {
    ITeacherSearch iTeacherSearch ;

    public TeacherSearchData(ITeacherSearch iTeacherSearch) {
        this.iTeacherSearch = iTeacherSearch;
    }


    public void getTeacherListData(String identity,String type) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", "");
        map.put("identity", identity);
        if (!type.equals("")) {
            map.put("spec", type);
        }

        Callback<TecherList> callback = new Callback<TecherList>() {
            @Override
            public void onResponse(Call<TecherList> call, Response<TecherList> response) {
                TecherList techerList = response.body();
                if (response.body() != null) {
                    if (techerList.getError_code().equals("0")) {
                        iTeacherSearch.getTeacher(techerList.getTec());
                    }else {
                        iTeacherSearch.OnTeacherFailure(techerList.getError_msg());
                    }
                } else {
                    iTeacherSearch.OnTeacherFailure("OnFailure");
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
    public void loadTeacherListData(int self, String identity,String type) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", "");
        map.put("self", self);
        map.put("identity", identity);
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
                        iTeacherSearch.OnLoadTeacherFailure(0);
                    }
                } else {
                    iTeacherSearch.OnLoadTeacherFailure(1);
                }
            }

            @Override
            public void onFailure(Call<TecherList> call, Throwable t) {
                iTeacherSearch.OnLoadTeacherFailure(2);
            }
        };

        Call<TecherList> call = HttpRequest.getCommonApi().techerList(map);
        call.enqueue(callback);
    }
}
