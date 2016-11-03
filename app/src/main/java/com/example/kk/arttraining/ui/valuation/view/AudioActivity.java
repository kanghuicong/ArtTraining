package com.example.kk.arttraining.ui.valuation.view;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.valuation.bean.AudioInfoBean;
import com.example.kk.arttraining.ui.valuation.presenter.AudioPresenter;
import com.example.kk.arttraining.utils.FileUtil;
import com.example.kk.arttraining.utils.UIUtil;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 作者：wschenyongyin on 2016/10/31 20:51
 * 说明:
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
    private int CHOSE_LOCAL_AUDIO = 001;
    private MediaPlayer mMediaPlayer;
    private String file_path;
    private Animation hyperspaceJumpAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valuation_audio_activity);
        ButterKnife.inject(this);
        init();

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
                        audioPresenter.startRecode();
                        iconRecodeBg.startAnimation(hyperspaceJumpAnimation);
                        flag = 1;
                        break;
                    case 1:
                        audioPresenter.stopRecode();
                        iconRecodeBg.clearAnimation();
                        break;
                }
                break;
            case R.id.valuation_audio_choselocal_recode:
//                Intent intent = new Intent(AudioActivity.this, MediaActivity.class);
//                intent.putExtra("media_type", "music");
//                startActivity(intent);
                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                        "audio/*");
                intent.setType("audio/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, CHOSE_LOCAL_AUDIO);

                break;

            case R.id.iv_title_back:
                finish();
                break;

            case R.id.recode_ok:
                Intent intent1 = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("audio_info", audioInfoBean);
                intent1.putExtras(bundle);
                setResult(ValuationMain.CHOSE_PRODUCTION, intent1);
                finish();
                break;

        }
    }

    //开始录音
    @Override
    public void startRecord() {

    }

    //停止录音
    @Override
    public void stopRecord() {

    }

    //录音完成
    @Override
    public void RecordOK(AudioInfoBean audioInfoBean) {
        this.audioInfoBean = audioInfoBean;
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
                audioPresenter.stopRecode();
            }
        }
    };

    private void play() {
        mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(file_path);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
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
            String file_size=FileUtil.getAutoFileOrFilesSize(file_path);
            UIUtil.showLog("file_path", file_path + "");
            UIUtil.showLog("文件大小：",file_size+"");
            play();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMediaPlayer.stop();
    }
}
