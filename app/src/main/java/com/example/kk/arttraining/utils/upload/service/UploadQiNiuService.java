package com.example.kk.arttraining.utils.upload.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;
import com.example.kk.arttraining.utils.upload.bean.TokenBean;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.KeyGenerator;
import com.qiniu.android.storage.Recorder;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.qiniu.android.storage.persistent.FileRecorder;
import com.qiniu.android.utils.UrlSafeBase64;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/11/2 11:46
 * 说明:七牛云断点上传
 */
public class UploadQiNiuService extends Service {
    private UploadManager uploadManager;
    private volatile boolean isCancelled = false;
    //Token
    private String token;
    //文件路径
    private String file_path;
    //订单id
    private String order_id;
    //开始下载
    public static final String ACTION_START = "ACTION_START";
    //暂停下载
    public static final String ACTION_PAUSE = "ACTION_PAUSE";
    //更新UI
    public static final String ACTION_UPDATE = "ACTION_UPDATE";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        token = intent.getStringExtra("token");
        file_path = intent.getStringExtra("file_path");
        order_id = intent.getStringExtra("order_id");

        UIUtil.showLog("onStartCommand", token + "--->" + file_path + "-->" + order_id);
        switch (intent.getAction()) {
            //开始下载
            case ACTION_START:
                if (Config.QINIUYUN_TOKEN == null) {
                    getToken();
                } else {
                    initService();
                }
                UIUtil.showLog("执行下载","-------》");
                break;
            //暂停下载
            case ACTION_PAUSE:
                isCancelled = true;
                UIUtil.showLog("执行暂停下载","-------》");
                break;
        }

        return super.onStartCommand(intent, flags, startId);
    }

    void initService() {
        String dirPath = "/storage/emulated/0/Download";
        Recorder recorder = null;
        try {
            File f = File.createTempFile("qiniu_xxxx", ".tmp");
            Log.d("qiniu", f.getAbsolutePath().toString());
            dirPath = f.getParent();
            //设置记录断点的文件的路径
            recorder = new FileRecorder(dirPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String dirPath1 = dirPath;
        //默认使用 key 的url_safe_base64编码字符串作为断点记录文件的文件名。
        //避免记录文件冲突（特别是key指定为null时），也可自定义文件名(下方为默认实现)：
        KeyGenerator keyGen = new KeyGenerator() {
            public String gen(String key, File file) {
                // 不必使用url_safe_base64转换，uploadManager内部会处理
                // 该返回值可替换为基于key、文件内容、上下文的其它信息生成的文件名
                String path = key + "_._" + new StringBuffer(file.getAbsolutePath()).reverse();
                Log.d("qiniu########", path);
                File f = new File(dirPath1, UrlSafeBase64.encodeToString(path));
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader(f));
                    String tempString = null;
                    int line = 1;
                    try {
                        while ((tempString = reader.readLine()) != null) {
//                          System.out.println("line " + line + ": " + tempString);
                            Log.d("qiniu", "line " + line + ": " + tempString);
                            line++;
                        }

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } finally {
                        try {
                            reader.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                return path;
            }
        };

        Configuration config = new Configuration.Builder()
                // recorder 分片上传时，已上传片记录器
                // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .recorder(recorder, keyGen)
                .build();
        // 实例化一个上传的实例
        uploadManager = new UploadManager(config);
        upLoad();
    }


    void upLoad() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("x:phone", "12345678");

        Log.d("qiniu", "click upload");
        isCancelled = false;
        uploadManager.put(file_path, null, Config.QINIUYUN_TOKEN,
                new UpCompletionHandler() {
                    public void complete(String key,
                                         ResponseInfo info, JSONObject res) {
                        try {
//                            Log.i("qiniu------->", key + ",\r\n " + info
//                                    + ",\r\n " + res.toString());
                            Log.i("qiniu返回状态码------->", info.toString() + "s");

                            if (info.isOK() == true) {
//                           textview.setText(res.toString());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new UploadOptions(map, null, false,
                        new UpProgressHandler() {
                            public void progress(String key, double percent) {
                                Log.i("qiniu*********", key + ": " + percent);
//                               progressbar.setVisibility(View.VISIBLE);
                                int progress = (int) (percent * 100);


                                Intent intent = new Intent();
                                intent.setAction(ACTION_UPDATE);
                                intent.putExtra("progress", progress);
                                intent.putExtra("order_id", order_id);
                                sendBroadcast(intent);
                            }

                        }, new UpCancellationSignal() {
                    @Override
                    public boolean isCancelled() {
                        return isCancelled;
                    }
                }));
    }


    //获取token
    void getToken() {
        UIUtil.showLog("执行getToken()",  "-------》");
        Callback<TokenBean> callback = new Callback<TokenBean>() {
            @Override
            public void onResponse(Call<TokenBean> call, Response<TokenBean> response) {
                if (response.body() != null) {
                    TokenBean tokenBean = response.body();
                    if (tokenBean.getError_code().equals("0")) {
                        Config.QINIUYUN_TOKEN = tokenBean.getQiniu_token();
                        initService();
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
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.TEST_ACCESS_TOKEN);
        map.put("uid", "111111");


        Call<TokenBean> call = HttpRequest.getUserApi().getQiNiuToken(map);
        call.enqueue(callback);
    }
}
