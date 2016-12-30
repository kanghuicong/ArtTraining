package com.example.kk.arttraining.ui.me.view;

import android.os.Message;

import com.example.kk.arttraining.ui.me.bean.MessageBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/12/30 09:27
 * 说明:消息列表
 */
public interface IMessageListView {

    //获取新的消息列表
    void getMessageNewData();

    //获取全部消息列表
    void getMessageAll();

    //获取新的消息成功
    void SuccessNew(List<MessageBean> messageBeanList);

    //获取全部消息成功
    void SuccessAll(List<MessageBean> messageBeanList);

    //获取新消息列表失败
    void FailureNew(String error_code, String error_msg);

    //获取全部消息失败
    void FailureAll(String error_code, String error_msg);

}
