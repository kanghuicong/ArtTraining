package com.example.kk.arttraining.prot;

/**
 * 作者：wschenyongyin on 2016/10/19 09:07
 * 说明:监听上传进度
 */
public interface UploadListener {
    void uploadListener(long hasWrittenLen, long totalLen, boolean hasFinish);
}
