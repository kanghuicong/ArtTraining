package com.example.kk.arttraining.utils;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.SeekBar;

import com.example.kk.arttraining.Media.recodevideo.PlayAudioListenter;

import java.io.IOException;

/**
 * 作者：wschenyongyin on 2016/10/18 18:12
 * 说明:用于播放在线音频
 * 调用方法：
 * PlayAudioUtil playAudioUtil=new PlayAudioUtil();
 * String url = "http://121.43.172.150:8080/LeRun/piaoxue.mp3";
 * playAudioUtil.playUrl(url);
 */
public class PlayAudioUtil implements MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    public MediaPlayer mediaPlayer;
    PlayAudioListenter playAudioListenter;
    //初始化

    private static PlayAudioUtil playAudioUtil = null;

    private PlayAudioUtil() {
    }

    public static PlayAudioUtil getPlayAudioUtil(PlayAudioListenter playAudioListenter) {
        if (playAudioUtil == null) {
            playAudioUtil = new PlayAudioUtil(playAudioListenter);
        }
        return playAudioUtil;
    }

    public PlayAudioUtil(PlayAudioListenter playAudioListenter) {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
        } catch (Exception e) {
            Log.e("mediaPlayer", "error", e);
        }
        this.playAudioListenter = playAudioListenter;
    }


    //继续播放
    public void play() {
        mediaPlayer.start();
    }

    //第一次加载音频
    public void playUrl(String videoUrl) {
        try {
            UIUtil.showLog("PlayAudioUtil-->playUrl","videoUrl--->"+videoUrl);
            mediaPlayer.reset();
            mediaPlayer.setDataSource(videoUrl);
            mediaPlayer.prepare();//prepare之后自动播放
        } catch (IllegalArgumentException e) {
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

    //暂停播放
    public void pause() {
        mediaPlayer.pause();
    }

    //停止播放
    public void stop(int Flag) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            if (Flag == 0) {
                Config.playAudioUtil=null;
                mediaPlayer.release();
                mediaPlayer = null;
            }
        }
    }
    @Override
    /**
     * 通过onPrepared播放
     */
    public void onPrepared(MediaPlayer arg0) {
        arg0.start();
        Log.e("mediaPlayer", "onPrepared");
    }

    @Override
    public void onCompletion(MediaPlayer arg0) {
        Log.e("mediaPlayer", "播放完成");
        playAudioListenter.playCompletion();
    }

    @Override
    public void onBufferingUpdate(MediaPlayer arg0, int bufferingProgress) {
    }
}