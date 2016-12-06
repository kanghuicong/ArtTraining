package com.example.kk.arttraining.ui.me.view;

import com.example.kk.arttraining.sqlite.bean.UploadBean;

import java.util.List;

/**
 * 作者：wschenyongyin on 2016/11/6 21:55
 * 说明:正在上传
 */
public interface IUploadFragment {
    //从本地数据获取上传列表信息
    void getLocalUploadData();

    //更新进度条
    void updateProgress();

    //查询成功
    void onSuccess(List<UploadBean> uploadBeanList);

    //更新订单上传附件的状态成功
    void UpdateOrderSuccess();

    //更新订单上传附件的状态失败
    void UpdateOrderFailure(String error_code,String error_msg);
}
