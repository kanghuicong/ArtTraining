package com.example.kk.arttraining.ui.me.view;

/**
 * 作者：wschenyongyin on 2016/11/11 21:01
 * 说明:修改密码
 */
public interface IChangePwdActivity {


    void changePwd();



    void Success();


    void Failure(String error_code,String error_msg);

    void ShowLoading();

    void HideLoading();

}
