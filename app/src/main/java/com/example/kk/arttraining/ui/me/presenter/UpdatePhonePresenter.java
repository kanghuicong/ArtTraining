package com.example.kk.arttraining.ui.me.presenter;

import com.example.kk.arttraining.bean.GeneralBean;
import com.example.kk.arttraining.bean.NoDataResponseBean;
import com.example.kk.arttraining.bean.UpdateBean;
import com.example.kk.arttraining.ui.me.view.IUpdatePhone;
import com.example.kk.arttraining.ui.me.view.UpdatePhone;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/11/13 11:56
 * 说明:
 */
public class UpdatePhonePresenter {
    IUpdatePhone iUpdatePhone;

    public UpdatePhonePresenter(IUpdatePhone iUpdatePhone) {
        this.iUpdatePhone = iUpdatePhone;
    }


    //检查手机号码是否注册
    public void checkIsRegister(Map<String, String> map) {
        Callback<NoDataResponseBean> callback = new Callback<NoDataResponseBean>() {
            @Override
            public void onResponse(Call<NoDataResponseBean> call, Response<NoDataResponseBean> response) {
                if (response.body() != null) {
                    NoDataResponseBean responseBean = response.body();
                    if (responseBean.getError_code().equals("0")) {
                        iUpdatePhone.SuccessPhoneReg();
                    } else {
                        iUpdatePhone.Failure(responseBean.getError_msg());
                    }
                } else {
                    iUpdatePhone.Failure(Config.REQUEST_FAILURE);
                }
            }

            @Override
            public void onFailure(Call<NoDataResponseBean> call, Throwable t) {
                iUpdatePhone.Failure(Config.REQUEST_FAILURE);
            }
        };
        Call<NoDataResponseBean> call = HttpRequest.getUserApi().checkMobile(map);
        call.enqueue(callback);
    }

    //获取验证码
    public void getVerificatioCode(Map<String, String> map) {
        Callback<GeneralBean> callback = new Callback<GeneralBean>() {
            @Override
            public void onResponse(Call<GeneralBean> call, Response<GeneralBean> response) {
                if (response.body() != null) {
                    GeneralBean responseBean = response.body();
                    UIUtil.showLog("返回码", responseBean.getError_code() + "");
                    if (responseBean.getError_code().equals("0")) {
                        iUpdatePhone.SuccessVerifyCode();
                    } else {
                        iUpdatePhone.Failure(responseBean.getError_code());

                    }
                } else {
                    iUpdatePhone.Failure(Config.Connection_Failure);
                }
            }

            @Override
            public void onFailure(Call<GeneralBean> call, Throwable t) {
                iUpdatePhone.Failure(Config.Connection_Failure);
            }

        };
        Call<GeneralBean> call = HttpRequest.getUserApi().sendSMS(map);
        call.enqueue(callback);
    }

    //校验验证码

    public void checkVerificatioCode(Map<String, String> map) {
        Callback<GeneralBean> callback = new Callback<GeneralBean>() {
            @Override
            public void onResponse(Call<GeneralBean> call, Response<GeneralBean> response) {

                if (response.body() != null) {
                    GeneralBean responseBean = response.body();

                    if (responseBean.getError_code().equals("0")) {
                        iUpdatePhone.SuccessVerify();
                    } else {
                        iUpdatePhone.Failure(responseBean.getError_msg());
                    }
                } else {
                    iUpdatePhone.Failure(Config.REQUEST_FAILURE);
                }
            }

            @Override
            public void onFailure(Call<GeneralBean> call, Throwable t) {
                iUpdatePhone.Failure(Config.REQUEST_FAILURE);
            }
        };
        Call<GeneralBean> call = HttpRequest.getUserApi().verifySMS(map);
        call.enqueue(callback);
    }


    //更改手机号码
    public void savePhone(Map<String, Object> map) {
        Callback<UpdateBean> callback = new Callback<UpdateBean>() {
            @Override
            public void onResponse(Call<UpdateBean> call, Response<UpdateBean> response) {

                if (response.body() != null) {
                    UpdateBean responseBean = response.body();

                    if (responseBean.getError_code().equals("0")) {
                        iUpdatePhone.SuccessChangePhone();
                    } else {
                        iUpdatePhone.Failure(responseBean.getError_msg());
                    }
                } else {
                    iUpdatePhone.Failure(Config.REQUEST_FAILURE);
                }
            }

            @Override
            public void onFailure(Call<UpdateBean> call, Throwable t) {
                iUpdatePhone.Failure(Config.REQUEST_FAILURE);
            }
        };
        Call<UpdateBean> call = HttpRequest.getUserApi().updateMobile(map);
        call.enqueue(callback);
    }
}
