package com.example.kk.arttraining.ui.me.view;

/**
 * 作者：wschenyongyin on 2016/11/13 11:42
 * 说明:修改手机号码处理类
 */
public interface IUpdatePhone {
    //校验手机号码是否注册
    void verifyPhoneReg();

    //获取验证码
    void getVerifyCode();

    //校验验证码
    void VerifyCode();

    void savePhone();

    //判断手机号码是否注册 成功
    void SuccessPhoneReg();

    //获取校验码成功
    void SuccessVerifyCode();

    //校验验证码成功
    void SuccessVerify();
    //更改手机号码成功
    void SuccessChangePhone();

    //失败
    void Failure(String error_msg);


}
