package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.bean.parsebean.TecherList;
import com.example.kk.arttraining.ui.homePage.prot.IHomePageMain;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2016/11/3.
 * QQ邮箱:515849594@qq.com
 */
public class AuthorityData {
    IHomePageMain iHomePageMain;

    public AuthorityData(IHomePageMain iHomePageMain) {
        this.iHomePageMain = iHomePageMain;
    }

    public void getAuthorityData(int self) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        if (Config.ACCESS_TOKEN != null)
            map.put("access_token", Config.ACCESS_TOKEN);
        if (Config.UID != 0)
            map.put("uid", Config.UID);
        map.put("self", self);

        Callback<TecherList> callback = new Callback<TecherList>() {
            @Override
            public void onResponse(Call<TecherList> call, Response<TecherList> response) {
                TecherList teacherList = response.body();
                UIUtil.showLog("测评response", response.body() + "");
                if (response.body() != null) {
                    UIUtil.showLog("测评Error", teacherList.getError_code() + "");
                    if (teacherList.getError_code().equals("0")) {
                        final List<TecInfoBean> tecInfoBeanList = teacherList.getTec();
                        UIUtil.showLog("测评teacherList", teacherList + "----");
                        iHomePageMain.getTeacherData(tecInfoBeanList);
                    } else {
                        iHomePageMain.OnTeacherFailure();
                    }
                } else {
                    iHomePageMain.OnTeacherFailure();
                }
            }

            @Override
            public void onFailure(Call<TecherList> call, Throwable t) {
                iHomePageMain.OnTeacherFailure();
            }
        };

        Call<TecherList> call = HttpRequest.getCommonApi().techerListIndex(map);
        call.enqueue(callback);
    }
}
