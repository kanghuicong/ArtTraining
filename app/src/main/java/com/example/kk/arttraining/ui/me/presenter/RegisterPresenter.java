package com.example.kk.arttraining.ui.me.presenter;

import com.example.kk.arttraining.bean.GeneralBean;
import com.example.kk.arttraining.bean.NoDataResponseBean;
import com.example.kk.arttraining.bean.UserLoginBean;
import com.example.kk.arttraining.ui.me.view.IRegister;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/11/5 19:35
 * 说明:
 */
public class RegisterPresenter {
    private IRegister iRegister;

    public RegisterPresenter(IRegister iRegister) {
        this.iRegister = iRegister;
    }

    //获取验证码
    public void getVerificatioCode(Map<String, String> map) {
        iRegister.hideLoading();
        Callback<NoDataResponseBean> callback = new Callback<NoDataResponseBean>() {
            @Override
            public void onResponse(Call<NoDataResponseBean> call, Response<NoDataResponseBean> response) {
                if (response.body() != null) {
                    NoDataResponseBean responseBean = response.body();
                    UIUtil.showLog("返回码", responseBean.getError_code() + "");
                    if (responseBean.getError_code().equals("0")) {
                        iRegister.hideLoading();
                        iRegister.onSuccess();
                    } else {
                        iRegister.onFailure(responseBean.getError_code());

                    }
                } else {
                    iRegister.onFailure(Config.Connection_Failure);
                }
            }

            @Override
            public void onFailure(Call<NoDataResponseBean> call, Throwable t) {
                iRegister.onFailure(Config.Connection_Failure);
            }

        };
        Call<NoDataResponseBean> call = HttpRequest.getUserApi().sendSMS(map);
        call.enqueue(callback);
    }

    //校验验证码

    public void checkVerificatioCode(Map<String, String> map) {
        iRegister.hideLoading();
        Callback<NoDataResponseBean> callback = new Callback<NoDataResponseBean>() {
            @Override
            public void onResponse(Call<NoDataResponseBean> call, Response<NoDataResponseBean> response) {

                if (response.body() != null) {
                    NoDataResponseBean responseBean = response.body();

                    if (responseBean.getError_code().equals("0")) {
                        iRegister.onSuccess();
                    } else {
                        iRegister.onFailure(responseBean.getError_msg());
                    }
                } else {
                    iRegister.onFailure(Config.REQUEST_FAILURE);
                }
            }

            @Override
            public void onFailure(Call<NoDataResponseBean> call, Throwable t) {
                iRegister.onFailure(Config.REQUEST_FAILURE);
            }
        };
        Call<NoDataResponseBean> call = HttpRequest.getUserApi().verifySMS(map);
        call.enqueue(callback);
    }

    //注册设置密码
    public void setPwd(String pwd1, String pwd2, String mobile) {
        if (checkPwd(pwd1, pwd2).equals("true")) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("mobile", mobile);
            map.put("psw", pwd1);
            setPwdRequest(map);
        } else {
            iRegister.hideLoading();
            iRegister.onFailure(checkPwd(pwd1, pwd2));
        }
    }

    //注册设置密码
    public void setForgotPwd(String pwd1, String pwd2, String mobile) {
        if (checkPwd(pwd1, pwd2).equals("true")) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("mobile", mobile);
            map.put("new_pwd", pwd1);
            ForgetPwdRequest(map);
        } else {
            iRegister.hideLoading();
            iRegister.onFailure(checkPwd(pwd1, pwd2));
        }
    }

    //请求后台设置密码
    public void setPwdRequest(Map<String, String> map) {
        iRegister.hideLoading();
        Callback<UserLoginBean> callback = new Callback<UserLoginBean>() {
            @Override
            public void onResponse(Call<UserLoginBean> call, Response<UserLoginBean> response) {
                if (response.body() != null) {
                    UserLoginBean responseBean = response.body();
                    if (responseBean.getError_code().equals("0")) {
                        iRegister.RegisterSuccess(responseBean);
                    } else {
                        iRegister.onFailure(responseBean.getError_msg());
                    }
                } else {
                    iRegister.onFailure(Config.REQUEST_FAILURE);
                }
            }

            @Override
            public void onFailure(Call<UserLoginBean> call, Throwable t) {
                iRegister.onFailure(Config.REQUEST_FAILURE);
            }
        };
        Call<UserLoginBean> call = HttpRequest.getUserApi().register(map);
        call.enqueue(callback);

    }

    //请求后台设置密码
    public void ForgetPwdRequest(Map<String, String> map) {
        iRegister.hideLoading();
        Callback<GeneralBean> callback = new Callback<GeneralBean>() {
            @Override
            public void onResponse(Call<GeneralBean> call, Response<GeneralBean> response) {
                UIUtil.showLog("RegisterPresenter.class_onResponse", response.code() + "---->" + response.message());
                if (response.body() != null) {
                    GeneralBean responseBean = response.body();
                    if (responseBean.getError_code().equals("0")) {
                        iRegister.onSuccess();
                    } else {
                        iRegister.onFailure(responseBean.getError_msg());
                    }
                } else {
                    iRegister.onFailure(Config.REQUEST_FAILURE);
                }
            }

            @Override
            public void onFailure(Call<GeneralBean> call, Throwable t) {
                iRegister.onFailure(Config.REQUEST_FAILURE);
                UIUtil.showLog("RegisterPresenter.class_onResponse", t.getMessage() + "---->" + t.getCause());
            }
        };
        Call<GeneralBean> call = HttpRequest.getUserApi().forgotPWD(map);
        call.enqueue(callback);

    }


    //检查用户密码
    String checkPwd(String pwd1, String pwd2) {
        //判断密码是否为空
        if (!pwd1.equals("") && !pwd2.equals("")) {
            //判断密码长度
            if ((pwd1.length() > 6) || (pwd2.length() > 6)) {
                //判断两次输入的密码是否相同
                if (pwd1.equals(pwd2)) {
                    return "true";
                } else {
                    return "101";
                }
            } else {
                return "102";
            }
        } else {
            return "103";
        }
    }

    //验证手机号码是否注册过

    public void checkIsRegister(Map<String, String> map) {
        iRegister.showLoading();
        Callback<NoDataResponseBean> callback = new Callback<NoDataResponseBean>() {
            @Override
            public void onResponse(Call<NoDataResponseBean> call, Response<NoDataResponseBean> response) {
                if (response.body() != null) {
                    NoDataResponseBean responseBean = response.body();
                    UIUtil.showLog("resisterPresenter", responseBean.getError_code() + "");
                    if (responseBean.getError_code().equals("0")) {
                        iRegister.checkIsRegisterSuccess();
                    } else {
                        iRegister.onFailure(responseBean.getError_code());
                    }
                } else {
                    iRegister.onFailure(Config.REQUEST_FAILURE);
                }
            }

            @Override
            public void onFailure(Call<NoDataResponseBean> call, Throwable t) {
                iRegister.onFailure(Config.REQUEST_FAILURE);
            }
        };
        Call<NoDataResponseBean> call = HttpRequest.getUserApi().checkMobile(map);
        call.enqueue(callback);
    }

    //验证推荐码
    public void checkRecommend(Map<String, String> map) {
        Callback<NoDataResponseBean> callback = new Callback<NoDataResponseBean>() {
            @Override
            public void onResponse(Call<NoDataResponseBean> call, Response<NoDataResponseBean> response) {
                if (response.body() != null) {

                    NoDataResponseBean responseBean = response.body();
                    UIUtil.showLog("checkRecommend_Code", responseBean.getError_code() + "");
                    if (responseBean.getError_code().equals("0")) {
                        iRegister.checkRecommendSuccess();
                    } else {
                        iRegister.onFailure(responseBean.getError_msg());
                    }
                } else {
                    iRegister.onFailure(Config.REQUEST_FAILURE);
                }
            }

            @Override
            public void onFailure(Call<NoDataResponseBean> call, Throwable t) {
                iRegister.onFailure(Config.REQUEST_FAILURE);
            }
        };
        Call<NoDataResponseBean> call = HttpRequest.getUserApi().inviteCode(map);
        call.enqueue(callback);
    }

}
