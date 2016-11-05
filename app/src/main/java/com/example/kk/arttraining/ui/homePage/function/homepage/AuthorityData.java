package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.app.Activity;

import com.example.kk.arttraining.bean.TecInfoBean;
import com.example.kk.arttraining.bean.parsebean.TecherList;
import com.example.kk.arttraining.custom.view.HorizontalListView;
import com.example.kk.arttraining.ui.homePage.prot.IHomePageMain;
import com.example.kk.arttraining.ui.homePage.adapter.AuthorityAdapter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.yixia.camera.util.Log;

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
    public static void getAuthorityData(final HorizontalListView lvAuthority, final Activity activity, final IHomePageMain iHomePageMain) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", "");
        map.put("uid", Config.User_Id);

        Callback<TecherList> callback = new Callback<TecherList>() {
            @Override
            public void onResponse(Call<TecherList> call, Response<TecherList> response) {
                TecherList teacherList = response.body();
                Log.i("response", "response");
                if (response.body() != null) {
                    if (teacherList.getError_code().equals("0")) {
                        List<TecInfoBean> tecInfoBeanList = teacherList.getTec();
                        AuthorityAdapter authorityAdapter = new AuthorityAdapter(activity, tecInfoBeanList);
                        lvAuthority.setAdapter(authorityAdapter);
                    } else {
                        iHomePageMain.OnFailure(teacherList.getError_code());
                    }
                } else {
                    iHomePageMain.OnFailure("failure");
                }
            }

            @Override
            public void onFailure(Call<TecherList> call, Throwable t) {
                iHomePageMain.OnFailure("failure");
            }

        };
        Call<TecherList> call = HttpRequest.getCommonApi().techerList(map);
        call.enqueue(callback);
    }
}
