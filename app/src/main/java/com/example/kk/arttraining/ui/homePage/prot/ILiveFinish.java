package com.example.kk.arttraining.ui.homePage.prot;

import com.example.kk.arttraining.ui.homePage.bean.LiveFinishBean;

/**
 * Created by kanghuicong on 2017/1/18.
 * QQ邮箱:515849594@qq.com
 */
public interface ILiveFinish {
    void getLiveFinish(LiveFinishBean liveFinishBean);

    void OnLiveFinishFailure(String result);
}
