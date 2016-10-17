package com.example.kk.arttraining.download.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.kk.arttraining.R;
import com.example.kk.arttraining.download.bean.FileInfo;
import com.example.kk.arttraining.download.service.SignleDownloadService;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SignleDownloadActivity extends AppCompatActivity {


    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.progressBar)
    ProgressBar progressBar;

    String url;
    FileInfo fileInfo;
    @InjectView(R.id.pro_text)
    TextView proText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        init();

        //注册广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction(SignleDownloadService.ACTION_UPDATE);
        registerReceiver(mReceiver, filter);
    }

    private void init() {
        proText.setVisibility(View.VISIBLE);
        progressBar.setMax(100);
        //创建文件信息对象
        url = "http://121.43.172.150:8080/LeRun/colorrun.apk";
        fileInfo = new FileInfo(0, url, "WeChat", 0, 0);
        name.setText(fileInfo.getFileName());
    }

    /**
     * 更新UI的广播接收器
     */
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (SignleDownloadService.ACTION_UPDATE.equals(intent.getAction())) {
                int finished = intent.getIntExtra("finished", 0);
                progressBar.setProgress(finished);
                proText.setText(new StringBuffer().append(finished).append("%"));
            }
        }
    };


    @OnClick({R.id.start, R.id.pause})
    public void onClick(View view) {
        Intent intent = new Intent(SignleDownloadActivity.this, SignleDownloadService.class);
        switch (view.getId()) {
            case R.id.start:
                intent.setAction(SignleDownloadService.ACTION_START);
                intent.putExtra("fileinfo", fileInfo);
                startService(intent);
                break;
            case R.id.pause:
                intent.setAction(SignleDownloadService.ACTION_PAUSE);
                intent.putExtra("fileinfo", fileInfo);
                startService(intent);
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
    }
}
