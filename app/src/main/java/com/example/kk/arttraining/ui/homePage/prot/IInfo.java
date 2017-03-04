package com.example.kk.arttraining.ui.homePage.prot;

import com.example.kk.arttraining.bean.modelbean.InfoBean;

import java.util.List;

/**
 * Created by kanghuicong on 2017/1/6.
 * QQ邮箱:515849594@qq.com
 */
public interface IInfo {
    void getInfoList(List<InfoBean> infoList);

    void OnInfoListFailure();

    void loadInfoList(List<InfoBean> infoList);

    void loadInfoListFailure(int Flag);
}
