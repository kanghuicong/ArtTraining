package com.example.kk.arttraining.utils.upload.service;

/**
 * 作者：wschenyongyin on 2016/11/8 14:44
 * 说明:小文件上传接口
 */
public interface ISignleUpload {
    //上传成功
    void uploadSuccess(String file_path);

    //上传视频文件
    void uploadVideoPic(String video_pic);

    //上传失败
    void uploadFailure(String error_code);
}
