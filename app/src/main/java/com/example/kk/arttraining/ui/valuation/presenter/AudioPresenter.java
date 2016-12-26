package com.example.kk.arttraining.ui.valuation.presenter;

import com.example.kk.arttraining.ui.valuation.bean.AudioInfoBean;
import com.example.kk.arttraining.media.recodevoice.IAudioActivity;
import com.example.kk.arttraining.utils.AudioRecordArm;
import com.example.kk.arttraining.utils.AudioRecordWav;
import com.example.kk.arttraining.utils.UIUtil;

/**
 * 作者：wschenyongyin on 2016/11/1 10:40
 * 说明:
 */
public class AudioPresenter {
    private IAudioActivity iAudioActivity;
    private AudioRecordWav audioRecordWav;
    AudioRecordArm audioRecordArm;
    private Runnable runnable;
    private AudioInfoBean audioInfoBean;
//    Timer timer = new Timer();

    public AudioPresenter(IAudioActivity iAudioActivity) {
        this.iAudioActivity = iAudioActivity;
        audioRecordWav = new AudioRecordWav();
        audioRecordArm=new AudioRecordArm();

    }

    //开始录制
    public void startWavRecode() {
        audioRecordWav.startRecordAndFile();

    }

    //停止录制
    public void stopWavRecode() {
        audioRecordWav.stopRecordAndFile();
        UIUtil.showLog("AudioPresenter.class","录音文件大小："+audioRecordWav.getRecodeInfo().getAudio_size()+",文件地址："+audioRecordWav.getRecodeInfo().getAudio_path());
        iAudioActivity.RecordOK(audioRecordWav.getRecodeInfo());

    }

    //开始录制
    public void startArmRecode() {
        audioRecordArm.startRecordAndFile();

    }

    //停止录制
    public void stopArmRecode() {
        audioRecordArm.stopRecordAndFile();
        UIUtil.showLog("AudioPresenter.class","录音文件大小："+audioRecordArm.getRecodeInfo().getAudio_size()+",文件地址："+audioRecordArm.getRecodeInfo().getAudio_path());
        iAudioActivity.RecordOK(audioRecordArm.getRecodeInfo());

    }



}
