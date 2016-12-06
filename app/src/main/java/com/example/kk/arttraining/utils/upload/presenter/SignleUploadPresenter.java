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
    private String QIUNIU_TOKEN;
    private String video_pic = null;
    int buket_type;

    public SignleUploadPresenter(ISignleUpload iSignleUpload) {
        this.iSignleUpload = iSignleUpload;

    }

    public void upload(List<String> fileList, int buket_type) {
        this.fileList = fileList;
        upload_count = fileList.size();
        attBeanList = new ArrayList<AttBean>();
        this.buket_type = buket_type;
        //判断七牛云token是否为空
        switch (buket_type) {
            case 1:
                if (Config.QINIUYUN_BBS_TOKEN == null) {
                    getToken(1);
                } else {
                    QIUNIU_TOKEN = Config.QINIUYUN_BBS_TOKEN;
                    forUpload(1);
                }
                ;
                break;
            case 2:
                if (Config.QINIUYUN_COURSE_TOKEN == null) {
                    getToken(2);
                } else {
                    QIUNIU_TOKEN = Config.QINIUYUN_COURSE_TOKEN;
                    forUpload(2);
                }
                ;
                break;
            case 3:
                if (Config.QINIUYUN_GOURP_TOKEN == null) {
                    getToken(3);
                } else {
                    QIUNIU_TOKEN = Config.QINIUYUN_GOURP_TOKEN;
                    forUpload(3);
                }
                ;
                break;
            case 4:
                if (Config.QINIUYUN_ADVERT_TOKEN == null) {
                    getToken(4);
                } else {
                    QIUNIU_TOKEN = Config.QINIUYUN_ADVERT_TOKEN;
                    forUpload(4);
                }
                ;
                break;
            case 5:
                if (Config.QINIUYUN_USER_HEADER_TOKEN == null) {
                    getToken(5);
                } else {
                    QIUNIU_TOKEN = Config.QINIUYUN_USER_HEADER_TOKEN;
                    forUpload(5);
                }
                ;
                break;
            case 6:
                if (Config.QINIUYUN_WORKS_TOKEN == null) {
                    getToken(6);
                } else {
                    QIUNIU_TOKEN = Config.QINIUYUN_WORKS_TOKEN;
                    forUpload(6);
                }

        }
    }

    void forUpload(int buket_type) {
        for (int i = 0; i < upload_count; i++) {
            signleUpload(fileList.get(i), buket_type);
        }
    }

    //上传文件
    void signleUpload(String file_path, int buket_type) {
        UIUtil.showLog("file_path", "---------->" + file_path);
        File file = new File(file_path);
        //生成文件名
        String upkey = RandomUtils.RandomFileName() + "." + FileUtil.getFileType(file_path);
        UIUtil.showLog("upkey", "---------->" + upkey);
        //执行上传
        uploadManager = new UploadManager();
        uploadManager.put(file, upkey, QIUNIU_TOKEN, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                UIUtil.showLog("上传返回结果", "------》" + info.toString());
                if (info.isOK()) {

                    if (video_pic != null) {
                        iSignleUpload.uploadVideoPic(key);
                        UIUtil.showLog("上传视频缩略图成功", "------》");
                        video_pic = null;
                    } else {
                        AttBean attBean = new AttBean();
                        attBean.setStore_path(key);
                        attBeanList.add(attBean);
                        uploadSuccess(attBeanList);
                        UIUtil.showLog("上传成功", "------》");
                    }
                } else {
                    iSignleUpload.uploadFailure(info.error, Config.Connection_ERROR_TOAST);
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


    public void uploadVideoPic(String video_pic, int buket_type) {
        this.video_pic = video_pic;
        if (Config.QINIUYUN_PIC_TOKEN == null) {
            getToken(buket_type);
        } else {
            signleUpload(video_pic, buket_type);
        }
    }


    //获取token
    void getToken(final int buket_type) {
        UIUtil.showLog("执行getToken()", "-------》");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        map.put("buket_type", buket_type);
        Callback<TokenBean> callback = new Callback<TokenBean>() {
            @Override
            public void onResponse(Call<TokenBean> call, Response<TokenBean> response) {
                UIUtil.showLog("获取上传tokenonResponse--->",response.code()+"--->"+response.message());
                if (response.body() != null) {
                    TokenBean tokenBean = response.body();
                    UIUtil.showLog("获取上传tokenon--->tokenBean",tokenBean.toString());
                    if (tokenBean.getError_code().equals("0")) {
                        QIUNIU_TOKEN = tokenBean.getQiniu_token();
                        switch (buket_type) {
                            case 1:
                                Config.QINIUYUN_BBS_TOKEN = QIUNIU_TOKEN;
                                break;
                            case 2:
                                Config.QINIUYUN_COURSE_TOKEN = QIUNIU_TOKEN;
                                break;
                            case 3:
                                Config.QINIUYUN_GOURP_TOKEN = QIUNIU_TOKEN;
                                break;
                            case 4:
                                Config.QINIUYUN_ADVERT_TOKEN = QIUNIU_TOKEN;
                                break;
                            case 5:
                                Config.QINIUYUN_USER_HEADER_TOKEN = QIUNIU_TOKEN;
                                break;
                            case 6:
                                Config.QINIUYUN_WORKS_TOKEN = QIUNIU_TOKEN;
                                break;
                        }
                        if (video_pic != null) {
                            signleUpload(video_pic, buket_type);
                        } else {
                            forUpload(buket_type);
                        }
                        UIUtil.showLog("token", QIUNIU_TOKEN + "");
                    } else {
                        iSignleUpload.uploadFailure(tokenBean.getError_code(), tokenBean.getError_msg());
                    }
                } else {
                    iSignleUpload.uploadFailure(response.code() + "", Config.Connection_ERROR_TOAST);
                }
            }

            @Override
            public void onFailure(Call<TokenBean> call, Throwable t) {
                UIUtil.showLog("获取上传token失败--->",t.getMessage()+"--->"+t.getCause());
                iSignleUpload.uploadFailure(Config.Connection_Failure, Config.Connection_ERROR_TOAST);
            }
        };
        Call<TokenBean> call = HttpRequest.getUserApi().getQiNiuToken(map);
        call.enqueue(callback);
    }

}
