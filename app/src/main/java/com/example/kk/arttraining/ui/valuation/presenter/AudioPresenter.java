package com.example.kk.arttraining.ui.valuation.presenter;

import android.os.Handler;

import com.example.kk.arttraining.ui.valuation.bean.AudioInfoBean;
import com.example.kk.arttraining.ui.valuation.view.IAudioActivity;
import com.example.kk.arttraining.utils.AudioRecordWav;
import com.example.kk.arttraining.utils.UIUtil;

/**
 * 作者：wschenyongyin on 2016/11/1 10:40
 * 说明:
 */
public class AudioPresenter {
    private IAudioActivity iAudioActivity;
    private AudioRecordWav audioRecordWav;
    private Handler handler;
    private Runnable runnable;
    private int seconds;
    private int minutes;

    public AudioPresenter(IAudioActivity iAudioActivity) {
        this.iAudioActivity = iAudioActivity;
        audioRecordWav = new AudioRecordWav();

    }

    //开始录制
    public void startRecode() {
        audioRecordWav.startRecordAndFile();
        timer();
        handler.postDelayed(runnable, 1000);

    }

    //停止录制
    public void stopRecode() {
        audioRecordWav.stopRecordAndFile();

        UIUtil.showLog("AudioPresenter.class","录音文件大小："+audioRecordWav.getRecodeInfo().getAudio_size()+",文件地址："+audioRecordWav.getRecodeInfo().getAudio_path());
        iAudioActivity.RecordOK(audioRecordWav.getRecodeInfo());
        handler.removeCallbacks(runnable);

    }

    //计时器
    public void timer(){
         handler = new Handler();
         runnable = new Runnable() {
            @Override
            public void run() {
                seconds++;
                handler.postDelayed(this, 1000);
                if(seconds==60){
                    seconds=0;
                    minutes++;
                }
                iAudioActivity.timer(minutes,seconds);
            }
        };
    }

}
