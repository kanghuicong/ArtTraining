package com.example.kk.arttraining.ui.me;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.download.service.SignleDownloadService;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.HttpRequest;
import com.example.kk.arttraining.utils.upload.bean.TokenBean;
import com.example.kk.arttraining.utils.upload.service.UploadQiNiuService;

import org.w3c.dom.Text;

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
    private FragmentManager fManager;
    private UploadFragment uploadFragment;
    private DownloadFragment downloadFragment;
    private int gray = 0xFF7597B3;
    private int whirt = 0xFFFFFFFF;
    @InjectView(R.id.fl_transfor)
    FrameLayout fl_transfor;
    @InjectView(R.id.tv_download)
    TextView tv_download;
    @InjectView(R.id.tv_upload)
    TextView tv_upload;
    @InjectView(R.id.title_back)
    ImageView img_back;
    @InjectView(R.id.title_barr)
    TextView title_barr;

    private TokenBean tokenBean;
    private Intent intent;
    private String file_path;
    private String error_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_transfor_activity);
        init();

    }

    @Override
    public void init() {

        Intent fromIntent = getIntent();
        file_path = fromIntent.getStringExtra("file_path");
        //如果跳转过来没有携带文件地址，不执行获取七牛云token
        if (Config.QINIUYUN_TOKEN == null) {
            getToken();
        }
        intent = new Intent(TransforListActivity.this, UploadQiNiuService.class);

        fManager = getFragmentManager();
        DefaultShow();
        ButterKnife.inject(this);
        title_barr.setText("传输列表");
        tv_download.setOnClickListener(this);
        tv_upload.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_download:
                setChioceItem(0);
                break;
            case R.id.tv_upload:
                setChioceItem(1);
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
                        Config.QINIUYUN_TOKEN = tokenBean.getQiniu_token();
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
        map.put("access_token", Config.ACCESS_TOKEN);
        map.put("uid", Config.UID);
        Call<TokenBean> call = HttpRequest.getUserApi().getQiNiuToken(map);
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
        clearChioce();
        hideFragments(transaction);
        switch (index) {
            case 0:
//                img_homepage.setImageResource(R.drawable.tab_run_yes);
                tv_download.setBackgroundColor(Color.GREEN);
                // rv_lesson.setBackgroundResource(R.drawable.);
                if (downloadFragment == null) {

                    downloadFragment = new DownloadFragment();
                    transaction.add(R.id.fl_transfor, downloadFragment);
                } else {

                    transaction.show(downloadFragment);
                }
                break;

            case 1:
                tv_upload.setBackgroundColor(Color.GREEN);
                // rv_lesson.setBackgroundResource(R.drawable.);
                if (uploadFragment == null) {

                    uploadFragment = new UploadFragment();
                    transaction.add(R.id.fl_transfor, uploadFragment);
                } else {

                    transaction.show(uploadFragment);
                }
                break;
        }
        transaction.commit();
    }

    // 隐藏fragment
    private void hideFragments(FragmentTransaction transaction) {
        if (downloadFragment != null) {
            transaction.hide(downloadFragment);
        }
        if (uploadFragment != null) {
            transaction.hide(uploadFragment);
        }

    }

    public void clearChioce() {
        tv_download.setBackgroundColor(whirt);
        tv_upload.setBackgroundColor(whirt);
    }

    private void DefaultShow() {
        downloadFragment = new DownloadFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fl_transfor, downloadFragment);
        ft.commit();
    }
}
