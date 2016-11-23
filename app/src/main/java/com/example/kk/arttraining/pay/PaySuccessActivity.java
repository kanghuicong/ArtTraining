package com.example.kk.arttraining.pay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.utils.ActivityManage;
import com.example.kk.arttraining.utils.Config;
import com.example.kk.arttraining.utils.UIUtil;
import com.example.kk.arttraining.utils.upload.service.UploadQiNiuService;
import com.example.kk.arttraining.utils.upload.view.UploadDialog;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2016/11/20 16:08
 * 说明:
 */
public class PaySuccessActivity extends BaseActivity {

    @InjectView(R.id.tv_title_back)
    TextView tvTitleBack;
    @InjectView(R.id.tv_title_bar)
    TextView tvTitleBar;
    @InjectView(R.id.rl_title)
    RelativeLayout rlTitle;

    private UploadDialog uploadDialog;
    private String file_path;
    private String token;
    private String order_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.me_order_detail);
        ButterKnife.inject(this);
        init();
    }

    @Override
    public void init() {
        Intent intent = getIntent();
        file_path = intent.getStringExtra("file_path");
        token = intent.getStringExtra("token");
        order_id = intent.getStringExtra("order_id");
        tvTitleBar.setText("支付成功");
        startUpload();
    }

    @OnClick(R.id.tv_title_back)
    public void onClick(View v) {
        ActivityManage.getAppManager().finishAllActivity();
        finish();
    }

    //开始传
    void startUpload() {
        UIUtil.showLog("payactivity-->", "startUpload");
        Intent intent = new Intent(this, UploadQiNiuService.class);
        intent.setAction(UploadQiNiuService.ACTION_START);
        intent.putExtra("file_path", file_path);
        intent.putExtra("token", Config.QINIUYUN_WORKS_TOKEN);
        intent.putExtra("order_id", order_id);
        startService(intent);

        uploadDialog = new UploadDialog(this, R.layout.dialog_upload, R.style.Dialog, new UploadDialog.UploadListener() {
            @Override
            public void onClick(View view) {
                uploadDialog.dismiss();
            }
        });
        uploadDialog.show();
    }
}
