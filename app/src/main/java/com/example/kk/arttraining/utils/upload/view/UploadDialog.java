package com.example.kk.arttraining.utils.upload.view;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.utils.upload.service.UploadQiNiuService;

/**
 * 作者：wschenyongyin on 2016/11/2 20:36
 * 说明:
 */
public class UploadDialog extends Dialog implements View.OnClickListener {

    private ProgressBar progressBar;
    private TextView progressBar_Num;
    private Button btn_close;
    private int layout;
    private Context context;
    private UploadListener listener;

    public UploadDialog(Context context) {
        super(context);
        this.context = context;
    }

    public UploadDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    // 传入布局，activity，主题
    public UploadDialog(Context context, int layout, int theme, UploadListener uploadListener) {
        super(context, theme);
        this.layout = layout;
        this.context = context;
        this.listener = uploadListener;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout);

        IntentFilter filter = new IntentFilter();
        filter.addAction(UploadQiNiuService.ACTION_UPDATE);
        context.registerReceiver(mReceiver, filter);
        progressBar = (ProgressBar) findViewById(R.id.dialog_progress);
        progressBar_Num = (TextView) findViewById(R.id.tv_progress_num);
        btn_close = (Button) findViewById(R.id.dialog_upload_close);
        btn_close.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        listener.onClick(v);
    }

    public interface UploadListener {
        public void onClick(View view);
    }

    /**
     * 更新UI的广播接收器
     */
    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int finshed = intent.getIntExtra("progress", 0);
            Message msg = new Message();
            msg.obj = finshed;
            mHandler.sendMessage(msg);
        }
    };
    //更新进度条
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int finshed = (int) msg.obj;
            progressBar.setProgress(finshed);
            progressBar_Num.setText(finshed + "%");
        }
    };
}
