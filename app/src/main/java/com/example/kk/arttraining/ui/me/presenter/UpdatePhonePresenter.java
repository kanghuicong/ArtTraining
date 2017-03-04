package com.example.kk.arttraining.ui.me.presenter;

import android.os.Handler;

import com.example.kk.arttraining.bean.modelbean.GeneralBean;
import com.example.kk.arttraining.bean.modelbean.NoDataResponseBean;
import com.example.kk.arttraining.bean.modelbean.UpdateBean;
import com.example.kk.arttraining.bean.modelbean.UserLoginBean;
import com.example.kk.arttraining.ui.me.view.IUpdatePhone;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
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
//第三方登陆验证手机号码是否绑定过
    public void UmVerifyPhoner(Map<String, String> map) {
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
        Call<NoDataResponseBean> call = HttpRequest.getUserApi().UmVerifyPhone(map);
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

    //更新手机号码校验验证码
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

    //第三方登录创建账号
    public void UmCreateUser(Map<String,Object> map){
        Callback<UserLoginBean> callback = new Callback<UserLoginBean>() {
            @Override
            public void onResponse(Call<UserLoginBean> call, Response<UserLoginBean> response) {
                if (response.body() != null) {
                    UserLoginBean responseBean = response.body();
                    if (responseBean.getError_code().equals("0")) {
                        iUpdatePhone.SuccessumBind(responseBean);
                    } else {
                        iUpdatePhone.Failure(responseBean.getError_msg());
                    }
                } else {
                    iUpdatePhone.Failure(Config.REQUEST_FAILURE);
                }
            }

            @Override
            public void onFailure(Call<UserLoginBean> call, Throwable t) {
                iUpdatePhone.Failure(Config.REQUEST_FAILURE);
            }
        };
        Call<UserLoginBean> call=HttpRequest.getUserApi().UmRegister(map);
        call.enqueue(callback);
    }

    private static final int MSG_SET_ALIAS = 1001;
    public void setJpushTag(String uid) {
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, uid));
    }

    //设置极光推送的别名
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(UIUtil.getContext(),
                            (String) msg.obj,
                            null,
                            mAliasCallback);
                    break;
                default:
            }
        }
    };

    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            UIUtil.showLog("设置jpush别名---》", code + "");
            String logs;
            switch (code) {
                case 0:
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    UIUtil.showLog("设置别名成功------->", "true");
                    break;
                case 6002:
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;

            }
        }

    };


}
