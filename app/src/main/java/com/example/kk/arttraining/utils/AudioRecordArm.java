package com.example.kk.arttraining.utils;

import android.media.MediaRecorder;
import android.os.Environment;

import com.example.kk.arttraining.ui.valuation.bean.AudioInfoBean;

import java.io.File;
import java.io.IOException;

/**
 * 作者：wschenyongyin on 2016/10/31 11:46
 * 说明:
 */
public class AudioRecordArm {
    private boolean isRecord = false;
    private MediaRecorder mMediaRecorder;
    private String file_path;

    public AudioRecordArm() {
    }

    private static AudioRecordArm mInstance;

    public synchronized static AudioRecordArm getInstance() {
        if (mInstance == null)
            mInstance = new AudioRecordArm();
        return mInstance;
    }

    public int startRecordAndFile() {
        //判断是否有外部存储设备sdcard
        if (AudioFileFunc.isSdcardExit()) {
            if (isRecord) {
                return ErrorCode.E_STATE_RECODING;
            } else {
                if (mMediaRecorder == null)
                    createMediaRecord();

                try {
                    mMediaRecorder.prepare();
                    mMediaRecorder.start();
                    // 让录制状态为true
                    isRecord = true;
                    return ErrorCode.SUCCESS;
                } catch (Exception e) {
                    e.printStackTrace();
                    return ErrorCode.E_UNKOWN;
                }
            }

        } else {
            return ErrorCode.E_NOSDCARD;
        }
    }


    public void stopRecordAndFile() {
        close();
    }

    public long getRecordFileSize() {
        return AudioFileFunc.getFileSize(AudioFileFunc.getAMRFilePath());
    }

    //获取录取文件的信息
    public AudioInfoBean getRecodeInfo() {

        AudioInfoBean audioInfoBean = new AudioInfoBean();
        audioInfoBean.setAudio_path(file_path);
        audioInfoBean.setAudio_size(AudioFileFunc.getFileSize(file_path));

        return audioInfoBean;
    }

    private void createMediaRecord() {
         /* ①Initial：实例化MediaRecorder对象 */
        mMediaRecorder = new MediaRecorder();

        /* setAudioSource/setVedioSource*/
        mMediaRecorder.setAudioSource(AudioFileFunc.AUDIO_INPUT);//设置麦克风

        /* 设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default
         * THREE_GPP(3gp格式，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
         */
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
         /* 设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default */
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

         /* 设置输出文件的路径 */
        file_path = AudioFileFunc.getAMRFilePath();
        File file = new File(file_path);
        if (file.exists()) {
            file.delete();
        }
        mMediaRecorder.setOutputFile(AudioFileFunc.getAMRFilePath());
    }


    private void close() {
        if (mMediaRecorder != null) {
            System.out.println("stopRecord");
            isRecord = false;
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
    }
}
