package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.app.Activity;

import com.example.kk.arttraining.bean.StatusesDetailBean;
import com.example.kk.arttraining.bean.parsebean.HeadNewsListBean;
import com.example.kk.arttraining.bean.parsebean.TecherList;
import com.example.kk.arttraining.custom.view.HorizontalListView;
import com.example.kk.arttraining.ui.homePage.prot.IHomePageMain;
import com.example.kk.arttraining.ui.homePage.prot.ITeacherComment;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.yixia.camera.util.Log;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2016/11/5.
 * QQ邮箱:515849594@qq.com
 */
public class DynamicContentTeacher {

    ITeacherComment iTeacherComment;

    public DynamicContentTeacher(ITeacherComment iTeacherComment){
        this.iTeacherComment = iTeacherComment;
    }

    public void getDynamicContentTeacher(final Activity activity,int status_id) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", "");
        map.put("status_id", status_id+"");

        Callback<StatusesDetailBean> callback = new Callback<StatusesDetailBean>() {
            @Override
            public void onResponse(Call<StatusesDetailBean> call, Response<StatusesDetailBean> response) {
                StatusesDetailBean statusesDetailBean = response.body();
                Log.i("response", "response");
                if (response.body() != null) {
                    if (statusesDetailBean.getError_code().equals("0")) {
                        iTeacherComment.getTeacherComment(statusesDetailBean);
                    }else {
                        iTeacherComment.OnFailure(statusesDetailBean.getError_code());
                    }
                }
            }

            @Override
            public void onFailure(Call<StatusesDetailBean> call, Throwable t) {
                iTeacherComment.OnFailure("onfailure");
            }
        };

        Call<StatusesDetailBean> call = HttpRequest.getStatusesApi().statusesDetail(map);
        call.enqueue(callback);
    }

}
