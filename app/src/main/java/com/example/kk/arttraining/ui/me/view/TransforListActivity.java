package com.example.kk.arttraining.ui.me.view;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.TitleBack;
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
 * 作者：wschenyongyin on 2016/9/23 14:58
 * 说明:传输列表页面
 */
public class TransforListActivity extends BaseActivity {
    @InjectView(R.id.rb_transfor_uploading)
    RadioButton rbTransforUploading;
    @InjectView(R.id.rb_transfor_upload_ok)
    RadioButton rbTransforUploadOk;
    private FragmentManager fManager;
    private UploadOkFragment uploadOkFragment;
    private UploadingFragment uploadingFragment;
    private int gray = 0xFF7597B3;
    private int whirt = 0xFFFFFFFF;
    @InjectView(R.id.fl_transfor)
    FrameLayout fl_transfor;


    private TokenBean tokenBean;
    private Intent intent;
    private String file_path;
    private String error_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_transfor_activity);
        ButterKnife.inject(this);
        TitleBack.TitleBackActivity(this,"传输列表");
        init();

    }

    @Override
    public void init() {
//
//        Intent fromIntent = getIntent();
//        file_path = fromIntent.getStringExtra("file_path");
        //如果跳转过来没有携带文件地址，不执行获取七牛云token
        if (Config.QINIUYUN_WORKS_TOKEN == null) {
            getToken();
        }
        intent = new Intent(TransforListActivity.this, UploadQiNiuService.class);
        fManager = getFragmentManager();
        DefaultShow();
        rbTransforUploading.setOnClickListener(this);
        rbTransforUploadOk.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rb_transfor_upload_ok:
                rbTransforUploading.setSelected(false);
                rbTransforUploadOk.setSelected(true);
                setChioceItem(1);
                break;
            //正在上传
            case R.id.rb_transfor_uploading:
                rbTransforUploading.setSelected(true);
                rbTransforUploadOk.setSelected(false);
                setChioceItem(0);
                break;
            case R.id.title_back:
                finish();
                break;
        }
    }

    //获取token
    void getToken() {
        Callback<TokenBean> callback = new Callback<TokenBean>() {
            @Override
            public void onResponse(Call<TokenBean> call, Response<TokenBean> response) {
                if (response.body() != null) {
                    tokenBean = response.body();
                    if (tokenBean.getError_code().equals("0")) {
                        Config.QINIUYUN_WORKS_TOKEN = tokenBean.getQiniu_token();
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
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        Call<TokenBean> call = HttpRequest.getUserApi().getQiNiuToken(map);
    }

    //开始传
    void startUpload() {
        intent.setAction(UploadQiNiuService.ACTION_START);
        intent.putExtra("file_path", file_path);
        intent.putExtra("token", Config.QINIUYUN_WORKS_TOKEN);
        startService(intent);
    }

    //暂停上传
    void pauseUpload() {
        intent.setAction(UploadQiNiuService.ACTION_PAUSE);
        intent.putExtra("file_path", file_path);
        intent.putExtra("token", Config.QINIUYUN_WORKS_TOKEN);
        startService(intent);
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (error_code) {
                case "500":
                    break;
            }
        }
    };


    public void setChioceItem(int index) {

        FragmentTransaction transaction = fManager.beginTransaction();
        hideFragments(transaction);
        switch (index) {
            case 0:
                if (uploadingFragment == null) {
                    uploadingFragment = new UploadingFragment();
                    transaction.add(R.id.fl_transfor, uploadingFragment);
                } else {
                    transaction.show(uploadingFragment);
                }
                break;

            case 1:
                if (uploadOkFragment == null) {
                    uploadOkFragment = new UploadOkFragment();
                    transaction.add(R.id.fl_transfor, uploadOkFragment);
                } else {
                    transaction.show(uploadOkFragment);
                }
                break;
        }
        transaction.commit();
    }

    // 隐藏fragment
    private void hideFragments(FragmentTransaction transaction) {
        if (uploadingFragment != null) {
            transaction.hide(uploadingFragment);
        }
        if (uploadOkFragment != null) {
            transaction.hide(uploadOkFragment);
        }
    }


    private void DefaultShow() {
        rbTransforUploading.setSelected(true);
        rbTransforUploadOk.setSelected(false);
        setChioceItem(0);
    }
}
