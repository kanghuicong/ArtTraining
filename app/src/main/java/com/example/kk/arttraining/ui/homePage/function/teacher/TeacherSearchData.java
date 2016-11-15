package com.example.kk.arttraining.ui.homePage.function.teacher;

import com.example.kk.arttraining.bean.parsebean.SearchBean;
import com.example.kk.arttraining.bean.parsebean.TecherList;
import com.example.kk.arttraining.ui.homePage.prot.ITeacherSearch;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

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

    public TeacherSearchData(ITeacherSearch iTeacherSearch) {
        this.iTeacherSearch = iTeacherSearch;
    }

    public void getTeacherListData() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", Config.ACCESS_TOKEN);

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
                iTeacherSearch.OnFailure("onfailure");
            }
        };

        Call<TecherList> call = HttpRequest.getCommonApi().techerList(map);
        call.enqueue(callback);
    }


    public void getTeacherSearchData(String type, String content) {
        HashMap<String, String> map = new HashMap<String, String>();
        switch (type) {
            case "key":
                map.clear();
                map.put("access_token", "");
                map.put("uid", Config.User_Id);
                map.put("key", "");//搜索关键字
                map.put("hot", "");//热度
                map.put("college", "");//院校
                map.put("spec", "");//专业
                map.put("city", "");//城市
                break;
            case "college":
                map.clear();
                map.put("access_token", "");
                map.put("uid", Config.User_Id);
                map.put("key", "");//搜索关键字
                map.put("hot", "");//热度
                map.put("college", content);//院校
                map.put("spec", "");//专业
                map.put("city", "");//城市
                break;
            case "spec":
                map.clear();
                map.put("access_token", "");
                map.put("uid", Config.User_Id);
                map.put("key", "");//搜索关键字
                map.put("hot", "");//热度
                map.put("college", "");//院校
                map.put("spec", content);//专业
                map.put("city", "");//城市
                break;
        }

        Callback<SearchBean> callback = new Callback<SearchBean>() {
            @Override
            public void onResponse(Call<SearchBean> call, Response<SearchBean> response) {
                SearchBean searchBean = response.body();
                UIUtil.showLog("searchBean", searchBean + "----");
                if (response.body() != null) {
                    if (searchBean.getError_code().equals("0")) {
                        iTeacherSearch.updateTeacher(searchBean.getTec());
                    } else {
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
