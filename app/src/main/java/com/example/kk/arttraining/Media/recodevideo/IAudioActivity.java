package com.example.kk.arttraining.Media.recodevideo;

import com.example.kk.arttraining.ui.valuation.bean.AudioInfoBean;

/**
 * 作者：wschenyongyin on 2016/11/1 10:16
 * 说明:
 */
public interface IAudioActivity {


    void RecordOK(AudioInfoBean audioInfoBean);

    //从本地选择文件
    void choseLocalRecord();


}
