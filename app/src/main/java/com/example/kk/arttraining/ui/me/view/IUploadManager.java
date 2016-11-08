package com.example.kk.arttraining.ui.me.view;

/**
 * 作者：wschenyongyin on 2016/11/7 11:45
 * 说明:
 */
public interface IUploadManager {

    void startUpload(String file_path, String order_id);

    void stopUpload(String file_path, String order_id);
}
