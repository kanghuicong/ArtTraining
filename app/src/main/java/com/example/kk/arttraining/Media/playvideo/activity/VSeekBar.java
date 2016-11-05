package com.example.kk.arttraining.Media.playvideo.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.SeekBar;

/**
 * 作者：wschenyongyin on 2016/9/21 14:06
 * 说明:视频播放  seekbar
 */
public class VSeekBar extends SeekBar {
    public VSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VSeekBar(Context context) {
        super(context);
    }

    public VSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VSeekBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        canvas.rotate(-90);
        canvas.translate(-getHeight(),0);
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, w, oldw, oldh);
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec);
        setMeasuredDimension(getMeasuredHeight(),getMeasuredWidth());
    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
    }
}
