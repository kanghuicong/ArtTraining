package com.example.kk.arttraining.ui.homePage.prot;

import com.example.kk.arttraining.ui.homePage.bean.LiveWaitBean;

/**
 * Created by kanghuicong on 2017/1/18.
 * QQ邮箱:515849594@qq.com
 */
public interface ILiveWait {
    void getLiveWait(LiveWaitBean liveWaitBean);

    void OnLiveWaitFailure(String result);
}
