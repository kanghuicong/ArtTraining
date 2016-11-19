package com.example.kk.arttraining.ui.me.view;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.ui.homePage.bean.Follow;
import com.example.kk.arttraining.ui.me.bean.FansBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/8 21:07
 * 说明:粉丝view的接口
 */
public interface IFansActivity {

    void RefreshData();

    void LoadData();

    //成功
    void SuccessRefresh(List<Follow> followList);

    void SuccessLoad(List<Follow> followList);

    //失败
    void Failure(String error_code,String error_msg);



}
