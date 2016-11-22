package com.example.kk.arttraining.ui.valuation.view;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.bean.MusicInfoBean;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.homePage.activity.PostingMain;
import com.example.kk.arttraining.ui.valuation.bean.AudioInfoBean;
import com.example.kk.arttraining.ui.valuation.presenter.AudioPresenter;
import com.example.kk.arttraining.utils.AudioFileFunc;
import com.example.kk.arttraining.utils.FileUtil;
import com.example.kk.arttraining.utils.UIUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2016/10/31 20:51
 * 说明:选择音频
 */
public class AudioActivity extends BaseActivity implements IAudioActivity {


    @InjectView(R.id.valuation_audio_start_recode)
    ImageView valuationAudioStartRecode;
    @InjectView(R.id.valuation_audio_choselocal_recode)
    ImageView valuationAudioChoselocalRecode;
    @InjectView(R.id.tv_minutes)
    TextView tvMinutes;
    @InjectView(R.id.tv_seconds)
    TextView tvSeconds;
    @InjectView(R.id.recode_ok)
    TextView recode_ok;
    @InjectView(R.id.iv_title_back)
    ImageView recode_back;
    @InjectView(R.id.rl_title)
    RelativeLayout rlTitle;
    @InjectView(R.id.icon_recode_bg)
    ImageView iconRecodeBg;

    private int minutes;
    private int seconds;
    private AudioPresenter audioPresenter;
    private int flag = 0;
    private AudioInfoBean audioInfoBean;
    public static int CHOSE_LOCAL_AUDIO = 001;
    private MediaPlayer mMediaPlayer;
    private String file_path;
    private Animation hyperspaceJumpAnimation;
    //判断从哪里进来的
    private String from;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valuation_audio_activity);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        from = intent.getStringExtra("fromIntent");
        init();
        UIUtil.showLog("AudioActivity11--->from", from + "");
        hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                AudioActivity.this, R.anim.loading_animation);


    }

    @Override
    public void init() {
        audioPresenter = new AudioPresenter(this);
    }

    @OnClick({R.id.valuation_audio_start_recode, R.id.valuation_audio_choselocal_recode, R.id.iv_title_back, R.id.recode_ok})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.valuation_audio_start_recode:
                switch (flag) {
                    case 0:
                        iconRecodeBg.startAnimation(hyperspaceJumpAnimation);
                        valuationAudioStartRecode.setImageResource(R.mipmap.stop_recode);
                        UIUtil.showLog("AudioActivity22--->from", from + "");
                        if (from.equals("postingMain")) {
                            audioPresenter.startArmRecode();
                        } else if (from.equals("production")) {
                            audioPresenter.startWavRecode();
                        }
                        flag = 1;
                        break;
                    case 1:
                        valuationAudioStartRecode.setImageResource(R.mipmap.stop_recode);
                        iconRecodeBg.clearAnimation();
                        if (from.equals("postingMain")) {
                            audioPresenter.stopArmRecode();
                        } else if (from.equals("production")) {
                            audioPresenter.stopWavRecode();
                            recode_ok.setVisibility(View.VISIBLE);
                        }
                        flag = 2;
                        break;
                    case 2:
                        break;
                }
                break;
            case R.id.valuation_audio_choselocal_recode:

                Intent intent = new Intent(this, MediaActivity.class);
                intent.putExtra("media_type", "music");
                startActivityForResult(intent, CHOSE_LOCAL_AUDIO);

                break;

            case R.id.iv_title_back:
                finish();
                break;

            case R.id.recode_ok:
                Intent intent1 = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("media_info", audioInfoBean);
                bundle.putString("type", "music");
                intent1.putExtras(bundle);
                if (from.equals("postingMain")) {
                    setResult(PostingMain.POST_MAIN_AUDIO_CODE, intent1);
                } else if (from.equals("production")) {
                    setResult(ValuationMain.CHOSE_PRODUCTION, intent1);
                }

                finish();
                break;

        }
    }


    //录音完成
    @Override
    public void RecordOK(AudioInfoBean audioInfoBean) {
        this.audioInfoBean = audioInfoBean;
        play();
    }


    //从本地文件选取文件
    @Override
    public void choseLocalRecord() {

    }

    //计时
    @Override
    public void timer(int minutes, int seconds) {
        this.minutes = minutes;
        this.seconds = seconds;
        handler.sendEmptyMessage(0);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tvMinutes.setText("0" + minutes + ":");
            if (seconds < 10) {
                tvSeconds.setText("0" + seconds + "");
            } else {
                tvSeconds.setText(seconds + "");
            }
            if (minutes == 5) {

                if (from.equals("postingMain")) {
                    audioPresenter.stopArmRecode();
                } else if (from.equals("production")) {
                    audioPresenter.stopWavRecode();
                }

            }
        }
    };

    private void play() {
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource((new FileInputStream(new File(audioInfoBean.getAudio_path())).getFD()));
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            //播放音频监听
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    iconRecodeBg.clearAnimation();
                }
            });
            iconRecodeBg.startAnimation(hyperspaceJumpAnimation);

        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == CHOSE_LOCAL_AUDIO) {
            Uri uri = data.getData();

            file_path = FileUtil.getFilePath(AudioActivity.this, uri);
            UIUtil.showLog("uri", uri + "");
            UIUtil.showLog("file_path", file_path + "");
            long file_size = AudioFileFunc.getFileSize(file_path);
            UIUtil.showLog("file_size", file_size + "");
            AudioInfoBean audioInfoBean = new AudioInfoBean();
            audioInfoBean.setAudio_path(file_path);
            audioInfoBean.setAudio_size(file_size);
            RecordOK(audioInfoBean);

        } else if (requestCode == CHOSE_LOCAL_AUDIO) {
            try {
                file_path = data.getStringExtra("file_path");
                String music_length = data.getStringExtra("file_length");
                long file_size = AudioFileFunc.getFileSize(file_path);
                UIUtil.showLog("file_size", file_size + "");
                AudioInfoBean audioInfoBean = new AudioInfoBean();
                audioInfoBean.setAudio_path(file_path);
                audioInfoBean.setAudio_size(file_size);
                audioInfoBean.setAudio_length(music_length);
                audioInfoBean.setMedia_type("audio");
                RecordOK(audioInfoBean);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) mMediaPlayer.stop();

    }
}
