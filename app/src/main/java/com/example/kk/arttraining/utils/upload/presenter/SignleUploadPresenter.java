package com.example.kk.arttraining.utils.upload.presenter;

import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.FileUtil;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.RandomUtils;
import com.example.kk.arttraining.utils.UIUtil;
import com.example.kk.arttraining.utils.upload.bean.AttBean;
import com.example.kk.arttraining.utils.upload.bean.TokenBean;
import com.example.kk.arttraining.utils.upload.service.ISignleUpload;
import com.google.gson.Gson;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/11/8 14:30
 * 说明:普通文件上传
 */
public class SignleUploadPresenter {

    private UploadManager uploadManager;
    private ISignleUpload iSignleUpload;
    //上传成功后返回的地址list
    List<String> completeList;
    //上传文件的地址list
    List<String> fileList;
    //上传文件的个数
    int upload_count;
    //用于封装上传的附件地址
    List<AttBean> attBeanList;

    public SignleUploadPresenter(ISignleUpload iSignleUpload) {
        this.iSignleUpload = iSignleUpload;

    }

    public void upload(List<String> fileList) {
        this.fileList = fileList;
        upload_count = fileList.size();
        attBeanList = new ArrayList<AttBean>();
        //判断七牛云token是否为空
        if (Config.QINIUYUN_TOKEN == null) {
            getToken();
        } else {
            forUpload();
        }
    }

    void forUpload() {
        for (int i = 0; i < upload_count; i++) {
            signleUpload(fileList.get(i));
        }
    }

    //上传文件
    void signleUpload(String file_path) {

        File file = new File(file_path);
        //生成文件名
        String upkey = RandomUtils.RandomFileName() + "." + FileUtil.getFileType(file_path);
        //执行上传
        uploadManager = new UploadManager();
        uploadManager.put(file, upkey, Config.QINIUYUN_TOKEN, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                UIUtil.showLog("上传返回结果", "------》" + info.toString());
                if (info.isOK()) {
                    AttBean attBean = new AttBean();
                    attBean.setStore_path(key);
                    attBeanList.add(attBean);
                    uploadSuccess(attBeanList);
                    UIUtil.showLog("上传成功", "------》");
                } else {
                    iSignleUpload.uploadFailure(info.error);
                    UIUtil.showLog("上传失败", "------》");
                }
                return;
            }
        }, null);
    }

    //全部附件上传成功后
    void uploadSuccess(List<AttBean> attBeanList) {
        UIUtil.showLog("uploadSuccess11", "------》");
        if (attBeanList.size() == upload_count) {
            UIUtil.showLog("uploadSuccess2", "------》");
            Gson gson = new Gson();
            String jsonString = gson.toJson(attBeanList);
            iSignleUpload.uploadSuccess(jsonString);
        }
    }


    //获取token
    void getToken() {
        UIUtil.showLog("执行getToken()", "-------》");
        Map<String, String> map = new HashMap<String, String>();
        map.put("access_token", Config.TEST_ACCESS_TOKEN);
        map.put("uid", "111111");
        Callback<TokenBean> callback = new Callback<TokenBean>() {
            @Override
            public void onResponse(Call<TokenBean> call, Response<TokenBean> response) {
                if (response.body() != null) {
                    TokenBean tokenBean = response.body();
                    if (tokenBean.getError_code().equals("0")) {
                        Config.QINIUYUN_TOKEN = tokenBean.getQiniu_token();
                        forUpload();
                        UIUtil.showLog("token", Config.QINIUYUN_TOKEN + "");
                    } else {
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<TokenBean> call, Throwable t) {
            }
        };
        Call<TokenBean> call = HttpRequest.getUserApi().getQiNiuToken(map);
        call.enqueue(callback);
    }

}
