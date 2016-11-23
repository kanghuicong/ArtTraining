package com.example.kk.arttraining.ui.homePage.function.teacher;

import com.example.kk.arttraining.bean.parsebean.TecherList;
import com.example.kk.arttraining.bean.parsebean.TecherShow;
import com.example.kk.arttraining.ui.homePage.prot.ITeacherContent;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2016/11/15.
 * QQ邮箱:515849594@qq.com
 */
public class TeacherContentData {
    ITeacherContent iTeacherContent;
    public TeacherContentData(ITeacherContent iTeacherContent) {
        this.iTeacherContent = iTeacherContent;
    }

    public void getTeacherContentData(int teacher_id) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("login_id", Config.UID);
        map.put("tec_id", teacher_id);

        Callback<TecherShow> callback = new Callback<TecherShow>() {
            @Override
            public void onResponse(Call<TecherShow> call, Response<TecherShow> response) {
                TecherShow techerShow = response.body();
                if (response.body() != null) {
                    if (techerShow.getError_code().equals("0")) {
                        iTeacherContent.getTeacherContent(techerShow);
                    }
                } else {
                    iTeacherContent.OnTeacherContentFailure(techerShow.getError_code());
                }
            }

            @Override
            public void onFailure(Call<TecherShow> call, Throwable t) {
                iTeacherContent.OnTeacherContentFailure("onfailure");
            }
        };

        Call<TecherShow> call = HttpRequest.getCommonApi().techerDetail(map);
        call.enqueue(callback);
    }
}
