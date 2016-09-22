

package com.example.kk.arttraining.playvideo.media;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import tv.danmaku.ijk.media.player.IMediaPlayer;
/**
 * 作者：wschenyongyin on 2016/9/21 14:06
 * 说明:
 */
public class MediaPlayerService extends Service {
    private static IMediaPlayer sMediaPlayer;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MediaPlayerService.class);
        return intent;
    }

    public static void intentToStart(Context context) {
        context.startService(newIntent(context));
    }

    public static void intentToStop(Context context) {
        context.stopService(newIntent(context));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void setMediaPlayer(IMediaPlayer mp) {
        if (sMediaPlayer != null && sMediaPlayer != mp) {
            if (sMediaPlayer.isPlaying())
                sMediaPlayer.stop();
            sMediaPlayer.release();
            sMediaPlayer = null;
        }
        sMediaPlayer = mp;
    }

    public static IMediaPlayer getMediaPlayer() {
        return sMediaPlayer;
    }
}
