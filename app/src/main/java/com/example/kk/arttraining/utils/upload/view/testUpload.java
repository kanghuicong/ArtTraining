package com.example.kk.arttraining.utils.upload.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.UIUtil;
import com.example.kk.arttraining.utils.upload.bean.TokenBean;
import com.example.kk.arttraining.utils.upload.service.UploadQiNiuService;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 作者：wschenyongyin on 2016/11/2 16:48
 * 说明:
 */
public class testUpload extends Activity {

    @InjectView(R.id.tv_test)
    TextView tvTest;
    @InjectView(R.id.bt_test_start)
    Button btTestStart;
    @InjectView(R.id.bt_test_pause)
    Button btTestPause;

    private TokenBean tokenBean;
    private Intent intent;
    private String file_path = "/storage/emulated/0/FinalAudio.wav";
    private String error_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_upload);
        ButterKnife.inject(this);
        UIUtil.showLog("請求地址：", Config.BASE_URL + Config.URL_UPLOAD_QINIU_GETTOKEN);
        if (Config.QINIUYUN_TOKEN == null) getToken();


        intent = new Intent(testUpload.this, UploadQiNiuService.class);
        btTestStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUpload();
            }
        });

        btTestPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseUpload();
            }
        });

    }

    //获取token
    void getToken() {

        Callback<TokenBean> callback = new Callback<TokenBean>() {
            @Override
            public void onResponse(Call<TokenBean> call, Response<TokenBean> response) {
                if (response.body() != null) {
                    tokenBean = response.body();
                    if (tokenBean.getError_code().equals("0")) {
                        Config.QINIUYUN_TOKEN = tokenBean.getQiniu_token();
                        UIUtil.showLog("token", Config.QINIUYUN_TOKEN + "");
                    } else {
                        error_code = tokenBean.getError_code();
                        handler.sendEmptyMessage(0);
                    }
                } else {
                    error_code = "500";
                    handler.sendEmptyMessage(0);
                }
            }

            @Override
            public void onFailure(Call<TokenBean> call, Throwable t) {
                error_code = "500";
                handler.sendEmptyMessage(0);
            }
        };
        Map<String, String> map = new HashMap<String, String>();
        map.put("access_token", "bbbbb");
        map.put("uid", "111111");
//        map.put("index", "111111");
//        map.put("flag", "2222");


        Call<TokenBean> call = HttpRequest.getUserApi().getQiNiuToken(map);
        call.enqueue(callback);
    }


    //开始传
    void startUpload() {

        intent.setAction(UploadQiNiuService.ACTION_START);
        intent.putExtra("file_path", file_path);
        intent.putExtra("token", Config.QINIUYUN_TOKEN);
        startService(intent);
    }

    //暂停上传
    void pauseUpload() {
        intent.setAction(UploadQiNiuService.ACTION_PAUSE);
        intent.putExtra("file_path", file_path);
        intent.putExtra("token", Config.QINIUYUN_TOKEN);

//                intent.putExtra("index", "111111");
//        intent.putExtra("flag", "sssssssss");
        startService(intent);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (error_code) {
                case "500":
                    UIUtil.ToastshowShort(testUpload.this, "ssssssssssssss");
                    break;
            }
        }
    };
}
