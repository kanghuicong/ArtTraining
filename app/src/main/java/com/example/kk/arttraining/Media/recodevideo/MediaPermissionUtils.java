package com.example.kk.arttraining.media.recodevideo;

import android.content.Context;
import android.hardware.Camera;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

/**
 * 作者：wschenyongyin on 2016/11/30 15:01
 * 说明:针对6.0以下5.0以上系统拍照录音权限处理
 */
public class MediaPermissionUtils {

    public static final int STATE_NO_PERMISSION = -1;
    public static final int STATE_RECORDING = 0;
    public static final int STATE_SUCCESS = 1;


    private int vocAuthority[] = new int[10];

    private int vocNum = 0;

    private boolean check = true;

    //针对魅族手机权限（拍照）
    public static boolean hasVideoPermission() {
        boolean isCanUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            Camera.Parameters mParameters = mCamera.getParameters(); //针对魅族手机
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            isCanUse = false;
        }

        if (mCamera != null) {
            try {
                mCamera.release();
            } catch (Exception e) {
                e.printStackTrace();
                return isCanUse;
            }
        }
        return isCanUse;
    }

    //针对魅族手机权限（录音）
    public static boolean hasRecordPermission() {
        boolean hasPermission = false;
        if (getRecordState() == STATE_SUCCESS) {
            hasPermission = true;
        } else {
            hasPermission = false;
        }

        return hasPermission;
    }

    public static int getRecordState() {
        int minBuffer = AudioRecord.getMinBufferSize(44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
        short[] point = new short[minBuffer];
        int readSize = 0;

        AudioRecord audioRecord = null;
        try {
            audioRecord = new AudioRecord(MediaRecorder.AudioSource.DEFAULT, 44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT,
                    (minBuffer * 100));
// 开始录音
            audioRecord.startRecording();// 检测是否可以进入初始化状态

        } catch (Exception e) {

            if (audioRecord != null) {
                audioRecord.release();
                audioRecord = null;
            } else {
            }
        }
        return STATE_SUCCESS;
    }

    //检测MediaRecorder是否有权限
    public static boolean isHasAudioRecordPermission(Context context) {
// 音频获取源
        int audioSource = MediaRecorder.AudioSource.MIC;
        // 设置音频采样率，44100是目前的标准，但是某些设备仍然支持22050，16000，11025
        int sampleRateInHz = 44100;
        // 设置音频的录制的声道CHANNEL_IN_STEREO为双声道，CHANNEL_CONFIGURATION_MONO为单声道
        int channelConfig = AudioFormat.CHANNEL_IN_STEREO;
        // 音频数据格式:PCM 16位每个样本。保证设备支持。PCM 8位每个样本。不一定能得到设备支持。
        int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
        // 缓冲区字节大小
        int bufferSizeInBytes = 0;
        bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz,
                channelConfig, audioFormat);
        AudioRecord audioRecord =  new AudioRecord(audioSource, sampleRateInHz,
                channelConfig, audioFormat, bufferSizeInBytes);
        //开始录制音频
        try{
            // 防止某些手机崩溃，例如联想
            audioRecord.startRecording();
        }catch (IllegalStateException e){
            e.printStackTrace();
        }
        /**
         * 根据开始录音判断是否有录音权限
         */
        if (audioRecord.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING) {
            return false;
        }
        audioRecord.stop();
        audioRecord.release();
        audioRecord = null;
        return true;

    }


}
