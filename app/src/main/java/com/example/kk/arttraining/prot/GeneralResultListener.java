package com.example.kk.arttraining.prot;

/**
 * 作者：wschenyongyin on 2016/12/8 14:22
 * 说明:通用失败或成功的接口
 */
public interface GeneralResultListener {

    void GeneralSuccess();

    void GeneralFailure(String error_code,String error_msg);
}
