package com.example.kk.arttraining;

import com.example.kk.arttraining.bean.modelbean.AppInfoBean;

/**
 * 作者：wschenyongyin on 2016/11/30 16:26
 * 说明:
 */
public interface IUpdateApp {
    void getAppVersion();

    void SuccessAppVersion(AppInfoBean appInfoBean);

    void FailureAppVersion(String error_code, String error_msg);


}
