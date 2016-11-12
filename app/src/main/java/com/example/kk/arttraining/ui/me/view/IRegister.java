package com.example.kk.arttraining.ui.me.view;

/**
 * 作者：wschenyongyin on 2016/11/5 19:28
 * 说明:
 */
public interface IRegister {


    //成功
    void onSuccess();

    void checkRecommendSuccess();

    void checkIsRegisterSuccess();

    //失败
    void onFailure(String error_code);

    //显示加载
    void showLoading();

    //隐藏加载
    void hideLoading();


}
