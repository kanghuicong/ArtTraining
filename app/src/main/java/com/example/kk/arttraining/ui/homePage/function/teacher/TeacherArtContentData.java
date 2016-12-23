package com.example.kk.arttraining.ui.homePage.function.teacher;

import com.example.kk.arttraining.ui.course.bean.ArtTeacherContentBean;
import com.example.kk.arttraining.ui.course.bean.ArtTeacherListBean;
import com.example.kk.arttraining.ui.homePage.prot.ITeacherArtContent;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2016/12/23.
 * QQ邮箱:515849594@qq.com
 */
public class TeacherArtContentData {
    ITeacherArtContent iTeacherArtContent;

    public TeacherArtContentData(ITeacherArtContent iTeacherArtContent) {
        this.iTeacherArtContent = iTeacherArtContent;
    }

    public void getTeacherArtContentData(int tec_id) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("user_name", Config.ArtName);
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("teacher_id", tec_id);

        Callback<ArtTeacherContentBean> callback = new Callback<ArtTeacherContentBean>() {
            @Override
            public void onResponse(Call<ArtTeacherContentBean> call, Response<ArtTeacherContentBean> response) {
                ArtTeacherContentBean artTeacher = response.body();
                if (response.body() != null) {
                    if (artTeacher.getCode() == 0) {
                        iTeacherArtContent.getTeacherContent(artTeacher);
                    } else {
                        iTeacherArtContent.OnTeacherContentFailure();
                    }
                } else {
                    iTeacherArtContent.OnTeacherContentFailure();
                }
            }

            @Override
            public void onFailure(Call<ArtTeacherContentBean> call, Throwable t) {
                iTeacherArtContent.NoWifi();
            }
        };

        Call<ArtTeacherContentBean> call = HttpRequest.getCourseApi().getArtTeacherContent(map);
        call.enqueue(callback);
    }
}
