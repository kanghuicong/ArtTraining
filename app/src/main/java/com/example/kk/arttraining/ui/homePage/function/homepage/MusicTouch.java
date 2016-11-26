package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.animation.AnimatorSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.example.kk.arttraining.utils.PlayAudioUtil;
import com.example.kk.arttraining.utils.UIUtil;

/**
 * Created by kanghuicong on 2016/11/25.
 * QQ邮箱:515849594@qq.com
 */
public class MusicTouch {

    public static void getMusicTouch(final ListView lv, final int MusicPosition, final PlayAudioUtil playAudioUtil, final AnimatorSet MusicArtSet) {
        lv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        // 触摸移动时的操作
                        UIUtil.showLog("触摸移动时的操作",lv.getFirstVisiblePosition()+"----=="+MusicPosition);
                        if (lv.getFirstVisiblePosition()-2 == MusicPosition ||lv.getLastVisiblePosition() ==MusicPosition ){
                            UIUtil.showLog("MusicStart","onScroll");
                            playAudioUtil.stop();
                            MusicArtSet.end();
                        }
                        break;
                }
                return false;
            }
        });
    }

    public static void stopMusicAnimator(PlayAudioUtil playAudioUtil , AnimatorSet MusicArtSet){
        if (playAudioUtil != null) {
            playAudioUtil.stop();
        }
        if (MusicArtSet != null) {
            MusicArtSet.end();
        }
    }
}
