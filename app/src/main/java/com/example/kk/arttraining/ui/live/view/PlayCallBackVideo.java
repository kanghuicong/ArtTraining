package com.example.kk.arttraining.ui.live.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.custom.dialog.LoadingDialog;
import com.example.kk.arttraining.custom.view.CustomVideoView;
import com.example.kk.arttraining.utils.UIUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayCallBackVideo extends Activity {

    private Bitmap video_pic;
    String path, thumbnail;
    CustomVideoView customVideoView;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_dynamic_teacher_comment_video);
        ButterKnife.inject(this);

        loadingDialog = LoadingDialog.getInstance(this);
        loadingDialog.show();

        path = getIntent().getStringExtra("path");
        thumbnail = getIntent().getStringExtra("thumbnail");
        customVideoView = (CustomVideoView) findViewById(R.id.custom_videoview);

        MediaController mc = new MediaController(this);
//        mc.setVisibility(View.INVISIBLE);  //隐藏VideoView自带的进度条

        if (path != null && !path.equals("")) {
            customVideoView.setMediaController(mc);
            customVideoView.setVideoURI(Uri.parse(path));//播放网络视频

            customVideoView.requestFocus();
            customVideoView.start();//自动开始播放
        } else {
            UIUtil.ToastshowShort(getApplicationContext(), "播放地址无效");

        }


        customVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            // 播放结束后的动作
            public void onCompletion(MediaPlayer mp) {
                UIUtil.ToastshowShort(getApplicationContext(), "播放完成");
            }
        });

        customVideoView.setPlayPauseListener(new CustomVideoView.PlayPauseListener() {
            @Override
            //开始播放监听
            public void onPlay() {
                UIUtil.showLog("onPlay-->", "true");
                loadingDialog.dismiss();
            }

            // 暂停播放监听
            @Override
            public void onPause() {
                UIUtil.showLog("onPause-->", "true");

            }
        });
        //视频无法播放监听
        customVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return false;
            }
        });
        //缓冲监听
        customVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                return false;
            }
        });


        customVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                UIUtil.showLog("onPrepared-->", "true");
            }
        });
    }

    @OnClick(R.id.iv_teacher_video_fork)
    public void onClick() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (customVideoView != null) {
            customVideoView.suspend();  //将VideoView所占用的资源释放掉
        }
    }
}