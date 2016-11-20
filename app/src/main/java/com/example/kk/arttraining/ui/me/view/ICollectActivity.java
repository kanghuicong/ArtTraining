package com.example.kk.arttraining.ui.me.view;

import com.example.kk.arttraining.ui.me.bean.CollectBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/9 17:04
 * 说明:收藏activity接口
 */
public interface ICollectActivity {
    //获取收藏数据
    void getCollectData();

    //上拉加载
    void getLoadData();

    //成功
    void Success(List<CollectBean> collectBeanList);

    //上拉加载成功

    void SuccessLoadData(List<CollectBean> collectBeanList);

    //失败
    void Failure(String error_code,String error_msg);

    //显示加载dialog
    void showLoading();

    //隐藏加载的dialog
    void hideLoading();
}
