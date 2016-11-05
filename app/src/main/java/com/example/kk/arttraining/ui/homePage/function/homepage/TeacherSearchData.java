package com.example.kk.arttraining.ui.homePage.function.homepage;

import com.example.kk.arttraining.bean.parsebean.SearchBean;
import com.example.kk.arttraining.ui.homePage.prot.ITeacherSearch;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.yixia.camera.util.Log;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2016/11/4.
 * QQ邮箱:515849594@qq.com
 */
public class TeacherSearchData {

    static ITeacherSearch iTeacherSearch;
    static int flag=0;

    public TeacherSearchData(ITeacherSearch iTeacherSearch) {
        this.iTeacherSearch = iTeacherSearch;
    }

    public void getTeacherSearchData(String type) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", "");
        map.put("uid", Config.User_Id);
        map.put("key", "");//搜索关键字
        map.put("hot", "");//热度
        map.put("college", "");//院校
        map.put("spec", "");//专业
        map.put("city", "");//城市

        Callback<SearchBean> callback = new Callback<SearchBean>() {
            @Override
            public void onResponse(Call<SearchBean> call, Response<SearchBean> response) {
                SearchBean searchBean = response.body();
                Log.i("response", "response");
                if (response.body() != null) {
                    if (searchBean.getError_code().equals("0")) {
                        if (flag == 0) {
                            iTeacherSearch.getTeacher(searchBean.getTec());
                            flag++;
                        }else {
                            iTeacherSearch.updateTeacher(searchBean.getTec());
                        }
                    }else {
                        iTeacherSearch.OnFailure(searchBean.getError_code());
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchBean> call, Throwable t) {
                iTeacherSearch.OnFailure("onfailure");
            }
        };

        Call<SearchBean> call = HttpRequest.getCommonApi().searchTec(map);
        call.enqueue(callback);
    }


}
