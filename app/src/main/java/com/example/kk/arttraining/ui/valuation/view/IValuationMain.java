package com.example.kk.arttraining.ui.valuation.view;

import com.example.kk.arttraining.bean.TecInfoBean;

import java.util.List;

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

    //获取作品名称
    String getProductionName();

    //获取作品描述
    String getProductionDescribe();

    //获取老师信息
    List<TecInfoBean> getTeacherInfo();

    //获取作品文件
    String getProductionPath();

    //提交订单
    void CommitOrder();

    void OnFailure(String error_code);

    void showLoading();

    void hideLoading();


}
