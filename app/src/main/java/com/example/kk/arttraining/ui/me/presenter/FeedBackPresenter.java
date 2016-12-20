package com.example.kk.arttraining.ui.me.presenter;

import com.example.kk.arttraining.bean.GeneralBean;
import com.example.kk.arttraining.ui.homePage.bean.General;
import com.example.kk.arttraining.ui.me.view.IFeedBack;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/11/17 11:26
 * 说明:
 */
public class FeedBackPresenter {

    private IFeedBack iFeedBack;

    public FeedBackPresenter(IFeedBack iFeedBack) {
        this.iFeedBack = iFeedBack;
    }

    public void commitFeedBack(String user_phone, String feedback_content) {
        if (user_phone.equals("")){
            if (  feedback_content.length()>5&&feedback_content.length()<201) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("access_token", Config.ACCESS_TOKEN);
                map.put("uid", Config.UID);
                map.put("content", feedback_content);
                map.put("phone", user_phone);
                commitRequest(map);
            } else {
                if (!StringUtils.isPhone(user_phone)) {
                    iFeedBack.OnFailure(Config.Connection_Failure, "您的联系号码有误！");
                } else if (feedback_content.length()<6){
                    iFeedBack.OnFailure(Config.Connection_Failure, "反馈内容不能少于五个字哦！");
                }
                else if (feedback_content.length()>200){
                    iFeedBack.OnFailure(Config.Connection_Failure, "反馈内容超出限制！");
                }
            }
        }else {
            if (StringUtils.isPhone(user_phone)&&feedback_content.length()>5&&feedback_content.length()<201) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("access_token", Config.ACCESS_TOKEN);
                map.put("uid", Config.UID);
                map.put("content", feedback_content);
                map.put("phone", user_phone);
                commitRequest(map);
            } else {
                if (!StringUtils.isPhone(user_phone)) {
                    iFeedBack.OnFailure(Config.Connection_Failure, "您的联系号码有误！");
                } else if (feedback_content.length()<6){
                    iFeedBack.OnFailure(Config.Connection_Failure, "反馈内容不能少于五个字哦！");
                }
                else if (feedback_content.length()>200){
                    iFeedBack.OnFailure(Config.Connection_Failure, "反馈内容超出限制！");
                }
            }
        }




    }

    public void commitRequest(Map<String, Object> map) {
        Callback<GeneralBean> callback = new Callback<GeneralBean>() {
            @Override
            public void onResponse(Call<GeneralBean> call, Response<GeneralBean> response) {
                GeneralBean generalBean = response.body();
                if (generalBean != null) {
                    if (generalBean.getError_code().equals("0")) {
                        iFeedBack.Success();
                    } else {
                        iFeedBack.OnFailure(generalBean.getError_code(), generalBean.getError_msg());
                    }

                } else {
                    iFeedBack.OnFailure(Config.Connection_Failure, Config.Connection_ERROR_TOAST);
                }
            }

            @Override
            public void onFailure(Call<GeneralBean> call, Throwable t) {
                iFeedBack.OnFailure(Config.Connection_Failure, Config.Connection_ERROR_TOAST);
            }
        };

        Call<GeneralBean> call = HttpRequest.getCommonApi().recommendCreate(map);
        call.enqueue(callback);

    }

}
