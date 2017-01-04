package com.example.kk.arttraining.ui.me.view;

import android.os.Message;

import com.example.kk.arttraining.ui.me.bean.MessageBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/12/30 09:27
 * 说明:消息列表
 */
public interface IMessageListView {


    //下拉刷新
    void SuccessRefresh(List<MessageBean> messageBeanList);
    //加载更多
    void SuccessLoad(List<MessageBean> messageBeanList);

    //获取全部消息失败
    void FailureRefrsh(String error_code, String error_msg);
    //上拉加载失败
    void FailureLoad(String error_code, String error_msg);

}
