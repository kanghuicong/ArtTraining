package com.example.kk.arttraining.ui.valuation.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kk.arttraining.R;
import com.example.kk.arttraining.prot.BaseActivity;
import com.example.kk.arttraining.ui.valuation.bean.AudioInfoBean;
import com.example.kk.arttraining.ui.valuation.presenter.AudioPresenter;

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

    private int minutes;
    private int seconds;
    private AudioPresenter audioPresenter;
    private int flag = 0;
    private AudioInfoBean audioInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.valuation_audio_activity);
        ButterKnife.inject(this);
        init();

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
                        flag = 1;
                        break;
                    case 1:
                        audioPresenter.stopRecode();
                        break;
                }
                break;
            case R.id.valuation_audio_choselocal_recode:
                Intent intent = new Intent(AudioActivity.this, MediaActivity.class);
                intent.putExtra("media_type", "music");
                startActivity(intent);
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
}
