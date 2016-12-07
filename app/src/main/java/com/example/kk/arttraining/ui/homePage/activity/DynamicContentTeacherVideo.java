package com.example.kk.arttraining.ui.homePage.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.utils.FileUtil;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by kanghuicong on 2016/12/5.
 * QQ邮箱:515849594@qq.com
 */
public class DynamicContentTeacherVideo extends Activity {
    @InjectView(R.id.custom_video_player_standard)
    JCVideoPlayerStandard customVideoPlayerStandard;
    @InjectView(R.id.iv_teacher_video_fork)
    ImageView ivTeacherVideoFork;
    @InjectView(R.id.ll_teacher_video)
    LinearLayout llTeacherVideo;

    private Bitmap video_pic;
    String path, thumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage_dynamic_teacher_comment_video);
        ButterKnife.inject(this);

        llTeacherVideo.getBackground().setAlpha(100);
        path = getIntent().getStringExtra("path");
        thumbnail = getIntent().getStringExtra("thumbnail");

        customVideoPlayerStandard.setUp(path, "");

        File file = new File(path);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    video_pic = FileUtil.returnBitmap(thumbnail);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            customVideoPlayerStandard.thumbImageView.setImageBitmap(video_pic);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @OnClick(R.id.iv_teacher_video_fork)
    public void onClick() {
        finish();
    }
}
