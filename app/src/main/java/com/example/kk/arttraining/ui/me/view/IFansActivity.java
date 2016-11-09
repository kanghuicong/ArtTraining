package com.example.kk.arttraining.ui.me.view;

import com.bumptech.glide.Glide;

/**
 * 作者：wschenyongyin on 2016/11/8 21:07
 * 说明:粉丝view的接口
 */
public interface IFansActivity {

    void getData();

    void Success();

    void Failure();

    void showLoading();

    void hideLoading();


}
