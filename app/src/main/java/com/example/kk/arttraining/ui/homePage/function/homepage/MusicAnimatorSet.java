package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.example.kk.arttraining.R;

/**
 * Created by kanghuicong on 2016/11/20.
 * QQ邮箱:515849594@qq.com
 */
public class MusicAnimatorSet {

    public static void doMusicAnimator(final ImageView imageView) {
        imageView.setBackgroundResource(R.mipmap.music_1);

        final ScaleAnimation sa1 = new ScaleAnimation(1, 1, 1, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        final ScaleAnimation sa2 = new ScaleAnimation(1, 1, 1, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        final ScaleAnimation sa3 = new ScaleAnimation(1, 1, 1, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        sa1.setDuration(300);
        sa2.setDuration(300);
        sa3.setDuration(300);
        imageView.startAnimation(sa1);
        sa1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                sa1.setFillAfter(true);
                imageView.setBackgroundResource(R.mipmap.music_2);
                imageView.startAnimation(sa2);
                sa2.setFillAfter(true);
                imageView.setBackgroundResource(R.mipmap.music_3);
                imageView.startAnimation(sa3);
                sa3.setFillAfter(true);
                doMusicAnimator(imageView);
            }
        });
    }
}
