package com.example.kk.arttraining.download.ui;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.kk.arttraining.R;
import com.example.kk.arttraining.download.bean.FileInfo;
import com.example.kk.arttraining.download.service.SignleDownloadService;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 作者：wschenyongyin on 2016/9/24 09:03
 * 说明:
 */
public class downloadDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private int theme;
    private Button btn_start;
    private Button btn_stop;
    private dialogListener listener;

    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.progressBar)
    ProgressBar progressBar;

    String url;
    FileInfo fileInfo;
    @InjectView(R.id.pro_text)
    TextView proText;

    public downloadDialog(Context context) {
        super(context);
        this.context = context;
//        this.listener=listener;
    }

    // 传入布局，activity
    public downloadDialog(Context context, int layout) {

        super(context, layout);
        this.context = context;

    }

    // 传入布局，activity，主题
    public downloadDialog(Context context, int layout, int theme) {
        super(context, theme);
        this.context = context;
        this.theme = theme;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        init();

        //注册广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction(SignleDownloadService.ACTION_UPDATE);
        context.registerReceiver(mReceiver, filter);

        btn_start = (Button) findViewById(R.id.start);
        btn_stop = (Button) findViewById(R.id.pause);
        btn_start.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
    }


    public interface dialogListener {
        public void onClick(View view);
    }

    @Override
    public void onClick(View v) {
//        listener.onClick(v);
        final Intent intent = new Intent(context, SignleDownloadService.class);
        switch (v.getId()) {

            case R.id.start:
                intent.setAction(SignleDownloadService.ACTION_START);
                intent.putExtra("fileinfo", fileInfo);
                context.startService(intent);
                break;
            case R.id.pause:
                intent.setAction(SignleDownloadService.ACTION_PAUSE);
                intent.putExtra("fileinfo", fileInfo);
                context.startService(intent);
                break;
        }
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
}
