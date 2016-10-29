package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.example.kk.arttraining.R;

/**
 * Created by kanghuicong on 2016/10/19.
 * QQ邮箱:515849594@qq.com
 * 说明：点赞动画
 */
public class LikeAnimatorSet {

    public static void likeAnimatorSet(final Context context, final TextView tv_like, final int image) {
//        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(tv_like, "scaleX", 1f, 0f, 0f, 1f);
//        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(tv_like, "scaleY", 1f, 0f, 0f, 1f);
//
//        objectAnimator1.addListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animator) {
//                Drawable drawable = context.getResources().getDrawable(image);
//                tv_like.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
//            }
//        });
//        AnimatorSet like_set = new AnimatorSet();
//        like_set.play(objectAnimator1).with(objectAnimator2);
//        like_set.setDuration(500);
//        like_set.start();

        final ScaleAnimation sa = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        final ScaleAnimation sa1 = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(200);
        sa1.setDuration(10);
        tv_like.startAnimation(sa);
        sa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                sa.setFillAfter(true);
                Drawable drawable = context.getResources().getDrawable(image);
                tv_like.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                tv_like.setText(Integer.valueOf(tv_like.getText().toString()) + 1 + "");
                tv_like.startAnimation(sa1);
                sa1.setFillAfter(true);
            }
        });
    }

    public static void noLikeAnimatorSet(final Context context, final TextView tv_like, final int image) {
        Drawable drawable = context.getResources().getDrawable(image);
        tv_like.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
        tv_like.setText(Integer.valueOf(tv_like.getText().toString()) - 1 + "");
    }

    public static void setLikeImage(final Context context, final TextView tv_like, final int image) {
        Drawable drawable = context.getResources().getDrawable(image);
        tv_like.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }
}
