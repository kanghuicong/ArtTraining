package com.example.kk.arttraining.ui.me.presenter;

import android.content.Context;
import android.os.Handler;


import com.example.kk.arttraining.bean.UserLoginBean;
import com.example.kk.arttraining.ui.me.view.IUserLoginView;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.StringUtils;
import com.example.kk.arttraining.utils.UIUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/10/15 17:04
 * 说明:
 */
public class UserLoginPresenter {
    private IUserLoginView iUserLoginView;
    private Handler handler = new Handler();
    private Context context;
    private static final int MSG_SET_ALIAS = 1001;

    public UserLoginPresenter(Context context,IUserLoginView iUserLoginView) {
        this.iUserLoginView = iUserLoginView;
        this.context=context;

    }

    //请求后台验证
    public void loginRequest(String name, String pwd) {


        Map<String, String> map = new HashMap<String, String>();
        map.put("name", name);
        map.put("pwd", pwd);

        Callback<UserLoginBean> callback = new Callback<UserLoginBean>() {
            @Override
            public void onResponse(Call<UserLoginBean> call, Response<UserLoginBean> response) {
                if (response.body() != null) {
                    UserLoginBean userBean = response.body();

                    if (userBean.getError_code().equals("0")) {
                        // TODO: 2016/10/17 将用户信息存到本地数据库
                        UIUtil.showLog("后台获取用户信息---》", userBean.toString());
                        iUserLoginView.SaveUserInfo(userBean);
                        iUserLoginView.ToMainActivity(userBean);
                        //登陆成功后将access_token赋值到全局变量
                        Config.ACCESS_TOKEN = userBean.getAccess_token();
                        Config.UID = userBean.getUid();
                    } else {
                        iUserLoginView.showFailedError(userBean.getError_code());
                    }
                } else {
                    iUserLoginView.showFailedError(Config.Connection_Failure);
                }
                iUserLoginView.hideLoading();
            }

            @Override
            public void onFailure(Call<UserLoginBean> call, Throwable t) {
                iUserLoginView.showFailedError(Config.Connection_Failure);
                iUserLoginView.hideLoading();
            }
        };


        Call<UserLoginBean> call = HttpRequest.getUserApi().Login(map);
        call.enqueue(callback);

    }

    //登陆
    public void Login() {
        iUserLoginView.showLoading();
        String name = iUserLoginView.getUserName();
        String pwd = iUserLoginView.getPassword();
        if (name.equals("")) {
            iUserLoginView.showFailedError("101");
        } else if (!StringUtils.isPhone(name)) {
            iUserLoginView.showFailedError("103");
        } else if (pwd.equals("")) {
            iUserLoginView.showFailedError("102");
        } else if (pwd.length() < 6 || pwd.length() > 16) {
            iUserLoginView.showFailedError("104");

        } else {
            loginRequest(name, pwd);
        }
    }



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
                            JPushInterface.setAliasAndTags(context,
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
            UIUtil.showLog("设置jpush别名---》",code+"");
            String logs;
            switch (code) {
                case 0:
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    UIUtil.showLog("设置别名成功------->","true");
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
