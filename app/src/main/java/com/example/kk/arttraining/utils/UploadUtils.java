package com.example.kk.arttraining.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.kk.arttraining.bean.modelbean.UpdateHeadBean;
import com.example.kk.arttraining.custom.other.UploadFileRequestBody;
import com.example.kk.arttraining.prot.UploadListener;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;

/**
 * 作者：wschenyongyin on 2016/9/21 10:32
 * 说明:上传文件
 */
public class UploadUtils {


    //单文件上传
    public static void uploadFile(File file, Callback<UpdateHeadBean> callback) {
        // 创建 RequestBody，用于封装 请求RequestBody
//        RequestBody requestFile =
//                RequestBody.create(MediaType.parse("multipart/form-data;charset=utf-8"), file);

        UploadFileRequestBody uploadFileRequestBody = new UploadFileRequestBody(file, new UploadListener() {
            @Override
            public void uploadListener(long hasWrittenLen, long totalLen, boolean hasFinish) {

                UIUtil.showLog("hasWrittenLen:------->", hasWrittenLen + "");
                UIUtil.showLog("totalLen:------->", totalLen + "");
            }
        });

        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), uploadFileRequestBody);

        // 添加描述
        String descriptionString = "hello, 这是文件描述";

        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data;charset=utf-8"), descriptionString);

        // 执行请求
//        Call<UpdateHeadBean> call = HttpRequest.getUserApi().updateHead(Config.ACCESS_TOKEN, Config.UID, description, uploadFileRequestBody);
//        call.enqueue(callback);

        Log.i("upload", "------------->");
    }

    //多文件上传
    public static void uploadFiles(List<File> listfile, Callback callback) {
        // 创建 RequestBody，用于封装 请求RequestBody

        Map<String, RequestBody> map = new HashMap<String, RequestBody>();
        for (int i = 0; i < listfile.size(); i++) {
            File file = listfile.get(i);

            UploadFileRequestBody uploadFileRequestBody = new UploadFileRequestBody(file, new UploadListener() {
                @Override
                public void uploadListener(long hasWrittenLen, long totalLen, boolean hasFinish) {

                    UIUtil.showLog("hasWrittenLen:------->", hasWrittenLen + "");
                    UIUtil.showLog("totalLen:------->", totalLen + "");
                    UIUtil.showLog("hasFinish:------->", hasFinish + "");
                }
            });
            map.put(("wenjian\"; filename=\"" + file.getName() + "" + i), uploadFileRequestBody);

        }
        String descriptionString = "hello, 这是文件描述";
        RequestBody description =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"), descriptionString);
        // 执行请求
//        Call<ResponseBody> call = HttpRequest.getApiService().uploadFiles("1", "2", "3", description, map);
//
//        call.enqueue(callback);


    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.arg1) {
                case 1:
                    if (msg.what > 0) {

//                        uploadImageView.updatePercent(msg.what);
                    }
                    break;
            }
        }
    };

}
