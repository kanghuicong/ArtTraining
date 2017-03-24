package com.example.kk.arttraining.ui.live.view;

/**
 * Created by kanghuicong on 2017/3/14.
 * QQ邮箱:515849594@qq.com
 */
public interface ILiveBuy {

    void getCloud(Double aDouble,int position);

    void getPayCloud();

    void onFailure(String code,String msg);

}
