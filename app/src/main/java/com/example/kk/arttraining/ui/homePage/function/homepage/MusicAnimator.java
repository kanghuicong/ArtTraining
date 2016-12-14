package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.BaseInterpolator;
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
    IMusic iMusic;

    public MusicAnimator(IMusic iMusic) {
        this.iMusic = iMusic;
    }

    public void doMusicArtAnimator(ImageView ivMusicArt) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ivMusicArt, "rotation", 0f, 360);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setRepeatMode(ValueAnimator.INFINITE);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setRepeatCount(-1);

        AnimatorSet MusicArtSet = new AnimatorSet();
        MusicArtSet.play(objectAnimator);
        MusicArtSet.setDuration(5000);


        MusicArtSet.start();

        iMusic.StopArtMusic(MusicArtSet);
    }

    public void doMusicAnimator(ImageView iv) {
        AnimationDrawable anim = (AnimationDrawable)iv.getBackground();// 获取到动画资源
        anim.setOneShot(false); // 设置是否重复播放
        anim.start();// 开始动画
        iMusic.StopMusic(anim);
    }
}
