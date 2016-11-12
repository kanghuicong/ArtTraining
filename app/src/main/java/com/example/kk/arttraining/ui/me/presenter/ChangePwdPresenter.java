package com.example.kk.arttraining.ui.me.presenter;

import com.example.kk.arttraining.bean.UpdateBean;
import com.example.kk.arttraining.ui.me.view.IChangePwdActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/11/11 21:15
 * 说明:修改密码处理类
 */
public class ChangePwdPresenter {
    IChangePwdActivity iChangePwdActivity;

    public ChangePwdPresenter(IChangePwdActivity iChangePwdActivity) {
        this.iChangePwdActivity = iChangePwdActivity;

    }

    public void changePwd(String pwd1, String pwd2) {
        //判断密码是否为空
        if (!pwd1.equals("") && !pwd2.equals("")) {
            //判断密码是否符合规范
            if (pwd1.length() > 5 && pwd1.length() < 17 && pwd2.length() > 5 && pwd2.length() < 17) {
                //判断两次输入的密码是否相同
                if (pwd1.equals(pwd2)) {

                    Map<String, Object> map=new HashMap<String,Object>();
                    map.put("access_token", Config.ACCESS_TOKEN);
                    map.put("uid", Config.UID);
                    map.put("new_pwd",pwd1);
                    ChangePwdRequest(map);
                } else {
                    iChangePwdActivity.Failure("您两次输入的密码不同哦！");
                }
            } else {
                iChangePwdActivity.Failure("您输入的密码不符合规范！");
            }

        } else {
            iChangePwdActivity.Failure("请输入您要修改的密码！");
        }

    }

    void ChangePwdRequest(Map<String, Object> map) {
        Callback<UpdateBean> callback = new Callback<UpdateBean>() {
            @Override
            public void onResponse(Call<UpdateBean> call, Response<UpdateBean> response) {
                UpdateBean updateBean = response.body();
                if (updateBean != null) {
                    if (updateBean.getError_code().equals("0")) {
                        iChangePwdActivity.Success();
                    } else {
                        iChangePwdActivity.Failure(updateBean.getError_msg());
                    }

                } else {
                    iChangePwdActivity.Failure("连接服务器超时");
                }

            }

            @Override
            public void onFailure(Call<UpdateBean> call, Throwable t) {
                iChangePwdActivity.Failure("连接服务器超时");
            }
        };
        Call<UpdateBean> call = HttpRequest.getUserApi().updatePwd(map);
        call.enqueue(callback);

    }
}
