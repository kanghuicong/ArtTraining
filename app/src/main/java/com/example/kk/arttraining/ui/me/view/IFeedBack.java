package com.example.kk.arttraining.ui.me.view;

import java.util.Map;

/**
 * 作者：wschenyongyin on 2016/11/17 11:27
 * 说明:反馈
 */
public interface IFeedBack {

    void commitFeedBack();

    void Success();

    void OnFailure(String error_code, String error_msg);


}
