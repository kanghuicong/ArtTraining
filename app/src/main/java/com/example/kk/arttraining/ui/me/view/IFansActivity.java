package com.example.kk.arttraining.ui.me.view;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.ui.me.bean.FansBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/8 21:07
 * 说明:粉丝view的接口
 */
public interface IFansActivity {
    //获取粉丝数据
    void getFansData();

    //获取关注数据
    void getFocusData();

    //成功
    void Success(List<FansBean> fansBeanList);

    //失败
    void Failure(String error_code);

    //显示加载dialog
    void showLoading();

    //隐藏加载dialog
    void hideLoading();


}
