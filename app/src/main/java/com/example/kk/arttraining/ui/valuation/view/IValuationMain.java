package com.example.kk.arttraining.ui.valuation.view;

/**
 * 作者：wschenyongyin on 2016/10/29 09:41
 * 说明:
 */
public interface IValuationMain {
    //获取输入的作品
    String getValuationName();

    //获取输入的作品描述
    String getValuationDescribe();

    //设置测评费用
    void setCostPay();

    //设置实付费用
    void setRealCostPay();

    //设置选择的老师
    void setTeacher();

    //设置选择回来作品路径
    void setProductionPath();

    void showLoading();

    void hideLoading();


}
