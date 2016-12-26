package com.example.kk.arttraining.media.recodevideo;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.prot.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PlayActivity extends BaseActivity {

    public final static String DATA = "URL";
    @InjectView(R.id.playView)
    PlayView playView;
    //    @InjectView(R.id.playBtn)
//    Button playBtn;
    @InjectView(R.id.activity_play)
    RelativeLayout activityPlay;
    @InjectView(R.id.recrod_ok)
    TextView recrodOk;
    @InjectView(R.id.play_video_back)
    ImageView playVideoBack;

//    @BindView(R.id.playView)
//    PlayView playView;
//    @BindView(R.id.playBtn)
//    Button playBtn;
//    @BindView(R.id.activity_play)
//    RelativeLayout activityPlay;

    private long playPostion = -1;
    private long duration = -1;
    String uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.inject(this);
//        ButterKnife.bind(this);

        uri = getIntent().getStringExtra(DATA);

        playView.setVideoURI(Uri.parse(uri));

        playView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                playView.seekTo(1);
                startVideo();
            }
        });

        playView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //获取视频资源的宽度
                int videoWidth = mp.getVideoWidth();
                //获取视频资源的高度
                int videoHeight = mp.getVideoHeight();
                playView.setSizeH(videoHeight);
                playView.setSizeW(videoWidth);
                playView.requestLayout();
                duration = mp.getDuration();
                play();
            }
        });

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        boolean isScreenOn = pm.isScreenOn();//如果为true，则表示屏幕“亮”了，否则屏幕“暗”了。
        if (!isScreenOn) {
            pauseVideo();
        }
    }

    @Override
    public void init() {

    }

    @OnClick({R.id.recrod_ok, R.id.play_video_back})
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.play_video_back:
                Intent intent = new Intent();
                intent.putExtra("tag", "cancel");
                setResult(RecordActivity.PLAY_VIDEO_CODE, intent);
                finish();
                break;
            case R.id.recrod_ok:
                Intent intentOk = new Intent();
                intentOk.putExtra("tag", "ok");
                setResult(RecordActivity.PLAY_VIDEO_CODE, intentOk);
                finish();
                break;
        }
        play();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (playPostion > 0) {
            pauseVideo();
        }
        playView.seekTo((int) ((playPostion > 0 && playPostion < duration) ? playPostion : 1));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playView.stopPlayback();
    }

    @Override
    protected void onPause() {
        super.onPause();
        playView.pause();
        playPostion = playView.getCurrentPosition();
        pauseVideo();

    }

    @Override
    public void onBackPressed() {
//        FileUtils.deleteFile(uri);
        Intent intent = new Intent();
        intent.putExtra("tag", "cancel");
        setResult(RecordActivity.PLAY_VIDEO_CODE, intent);
        finish();
//        overridePendingTransition(R.anim.fab_in, R.anim.fab_out);
    }


    private void pauseVideo() {
        playView.pause();
//        playBtn.setText("播放");
    }

    private void startVideo() {
        playView.start();
//        playBtn.setText("停止");
    }

    /**
     * 播放
     */
    private void play() {
        if (playView.isPlaying()) {
            pauseVideo();
        } else {
            if (playView.getCurrentPosition() == playView.getDuration()) {
                playView.seekTo(0);
            }
            startVideo();
        }
    }


}
