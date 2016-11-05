package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.app.Activity;
import android.widget.ListView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.parsebean.StatusesBean;
import com.example.kk.arttraining.Media.playvideo.activity.VideoListLayout;
import com.example.kk.arttraining.ui.homePage.activity.IHomePageMain;
import com.example.kk.arttraining.ui.homePage.adapter.DynamicAdapter;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.JsonTools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2016/10/28.
 * QQ邮箱:515849594@qq.com
 */
public class DynamicData {



    public static void getDynamicData(final ListView lvHomepageDynamic, final Activity activity,final IHomePageMain iHomePageMain) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("access_token", "");
        map.put("uid", Config.User_Id);
        map.put("type", "all");

        Callback<StatusesBean> callback = new Callback<StatusesBean>() {
            @Override
            public void onResponse(Call<StatusesBean> call, Response<StatusesBean> response) {
                StatusesBean statusesBean = response.body();
                if (response.body() != null) {
                    if (statusesBean.getError_code().equals("0")) {
                        List<Map<String, Object>> mapList = JsonTools.ParseStatuses(statusesBean.getStatuses());
                        DynamicAdapter dynamicadapter = new DynamicAdapter(activity, mapList);
                        lvHomepageDynamic.setAdapter(dynamicadapter);
                        lvHomepageDynamic.setOnItemClickListener(new DynamicItemClick(activity));//Item点击事件
                    } else {
                        iHomePageMain.OnFailure(statusesBean.getError_code());
                    }
                } else {
                    iHomePageMain.OnFailure("failure");
                }
            }

            @Override
            public void onFailure(Call<StatusesBean> call, Throwable t) {
                String data = VideoListLayout.readTextFileFromRawResourceId(activity, R.raw.statuses);
                List<Map<String, Object>> mapList = JsonTools.ParseStatuses(data);
                DynamicAdapter dynamicadapter = new DynamicAdapter(activity, mapList);
                lvHomepageDynamic.setAdapter(dynamicadapter);
                lvHomepageDynamic.setOnItemClickListener(new DynamicItemClick(activity));
                iHomePageMain.OnFailure("failure");
            }
        };

        Call<StatusesBean> call = HttpRequest.getStatusesApi().statusesGoodList(map);
        call.enqueue(callback);
    }


//listView数据
//    private void getDynamicData() {
//        HashMap<String, String> map = new HashMap<String, String>();
//        map.put("access_token", "");
//        map.put("uid", Config.User_Id);
//        map.put("type", "all");
//
//        Callback<StatusesBean> callback = new Callback<StatusesBean>() {
//            @Override
//            public void onResponse(Call<StatusesBean> call, Response<StatusesBean> response) {
//                StatusesBean statusesBean = response.body();
//                if (response.body() != null) {
//                    if (statusesBean.getError_code().equals("0")) {
//                        List<Map<String, Object>> mapList = JsonTools.ParseStatuses(statusesBean.getStatuses());
//                        DynamicAdapter dynamicadapter = new DynamicAdapter(activity, mapList);
//                        lvHomepageDynamic.setAdapter(dynamicadapter);
//                        lvHomepageDynamic.setOnItemClickListener(new DynamicItemClick(activity));//Item点击事件
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<StatusesBean> call, Throwable t) {
//                String data = VideoListLayout.readTextFileFromRawResourceId(activity, R.raw.statuses);
//                List<Map<String, Object>> mapList = JsonTools.ParseStatuses(data);
//                DynamicAdapter dynamicadapter = new DynamicAdapter(activity, mapList);
//                lvHomepageDynamic.setAdapter(dynamicadapter);
//                lvHomepageDynamic.setOnItemClickListener(new DynamicItemClick(activity));
//            }
//        };
//
//        Call<StatusesBean> call = HttpRequest.getStatusesApi().statusesGoodList(map);
//        call.enqueue(callback);
//    }

}
