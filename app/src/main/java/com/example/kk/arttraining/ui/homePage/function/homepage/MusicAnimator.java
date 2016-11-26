package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.kk.arttraining.R;
import com.example.kk.arttraining.ui.homePage.prot.IMusic;

/**
 * Created by kanghuicong on 2016/11/20.
 * QQ邮箱:515849594@qq.com
 */
public class MusicAnimator {
    static IMusic iMusic;


    public MusicAnimator(IMusic iMusic) {
        this.iMusic = iMusic;
    }

    public void doMusicArtAnimator(ImageView ivMusicArt) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ivMusicArt, "rotation", 0f, 360f);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.INFINITE);//

        AnimatorSet MusicArtSet = new AnimatorSet();
        MusicArtSet.play(objectAnimator);
        MusicArtSet.setDuration(5000);
        MusicArtSet.start();

        iMusic.StopArtMusic(MusicArtSet);
    }

    public void doMusicCommandAnimator(ImageView ivMusicCommand) {
//        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ivMusicCommand, "rotation",  0f, 30f);
//
//        AnimatorSet MusicCommandSet = new AnimatorSet();
//        MusicCommandSet.play(objectAnimator);
//        MusicCommandSet.setDuration(1000);
//        MusicCommandSet.start();
        RotateAnimation ra = new RotateAnimation(0, 30, RotateAnimation.RELATIVE_TO_SELF,0.5F,RotateAnimation.RELATIVE_TO_SELF,0.5F);
        ra.setDuration(300);
        ra.setFillAfter(true);
        ivMusicCommand.startAnimation(ra);
        iMusic.StopCommandMusic(ra);
    }

}
