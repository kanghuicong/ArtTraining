package com.example.kk.arttraining.ui.homePage.prot;

import com.example.kk.arttraining.bean.BannerBean;

import java.util.List;

/**
 * Created by kanghuicong on 2016/11/8.
 * QQ邮箱:515849594@qq.com
 */
public interface IShuffling {
    void getShuffling(List<BannerBean> list);

    void OnFailure(String failure);
}
