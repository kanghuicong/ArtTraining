package com.example.kk.arttraining.ui.valuation.view;

import com.example.kk.arttraining.ui.valuation.bean.AudioInfoBean;

/**
 * 作者：wschenyongyin on 2016/11/1 10:16
 * 说明:
 */
public interface IAudioActivity {

    //开始录音
    void startRecord();

    //停止录音
    void stopRecord();

void RecordOK(AudioInfoBean audioInfoBean);

    //从本地选择文件
    void choseLocalRecord();

    //开始计时
    void timer(int minutes,int seconds);


}
