package com.example.kk.arttraining.ui.discover.function.circle;

import com.example.kk.arttraining.bean.GroupBean;
import com.example.kk.arttraining.bean.parsebean.GroupListBean;
import com.example.kk.arttraining.ui.discover.prot.IMyGroup;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by kanghuicong on 2016/11/13.
 * QQ邮箱:515849594@qq.com
 */
public class CircleMyGroupData {
    IMyGroup iMyGroup;

    public CircleMyGroupData(IMyGroup iMyGroup) {
        this.iMyGroup = iMyGroup;
    }

    public void getCircleMyGroup(){
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", "");
        map.put("uid", Config.UID);

        Callback<GroupListBean> callback = new Callback<GroupListBean>() {
            @Override
            public void onResponse(Call<GroupListBean> call, Response<GroupListBean> response) {
                GroupListBean groupListBean = response.body();
                if (response.body() != null) {
                    if (groupListBean.getError_code().equals("0")) {
                        List<GroupBean> tecInfoBeanList = groupListBean.getGroups();
                        iMyGroup.getMyGroupData(tecInfoBeanList);
                    } else {
                        iMyGroup.OnMyGroupFailure(groupListBean.getError_code());
                    }
                } else {
                    iMyGroup.OnMyGroupFailure("failure");
                }
            }
            @Override
            public void onFailure(Call<GroupListBean> call, Throwable t) {
                iMyGroup.OnMyGroupFailure("failure");
            }
        };
        Call<GroupListBean> call = HttpRequest.getGroupApi().groupMyList(map);
        call.enqueue(callback);
    }
}
