package com.example.kk.arttraining.utils;

import android.util.Log;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * 作者：wschenyongyin on 2016/9/21 10:32
 * 说明:
 */
public class UploadUtils {

  void uploadFile( File file,Callback callback){
      // 创建 RequestBody，用于封装 请求RequestBody
      RequestBody requestFile =
              RequestBody.create(MediaType.parse("multipart/form-data"), file);

      // MultipartBody.Part is used to send also the actual file name
      MultipartBody.Part body =
              MultipartBody.Part.createFormData("image", file.getName(), requestFile);

      // 添加描述
      String descriptionString = "hello, 这是文件描述";
      RequestBody description =
              RequestBody.create(
                      MediaType.parse("multipart/form-data"), descriptionString);

      // 执行请求
      Call<ResponseBody> call = HttpRequest.getApiService().upload(description, body);
      call.enqueue(callback);
  }
}
