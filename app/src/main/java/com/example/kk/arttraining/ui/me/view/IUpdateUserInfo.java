package com.example.kk.arttraining.ui.me.view;

import com.example.kk.arttraining.bean.UpdateBean;

/**
 * 作者：wschenyongyin on 2016/11/15 20:10
 * 说明:用户修改保存用户信息到服务器
 */
public interface IUpdateUserInfo {

    //更新用户信息
    void updateInfo();

    //更新成功
    void SuccessUpdate(UpdateBean updateBean);

    //更新失败
    void FailureUpdate(String error_code, String error_msg);

}
