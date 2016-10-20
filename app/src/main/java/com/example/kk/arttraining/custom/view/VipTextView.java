package com.example.kk.arttraining.custom.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by kanghuicong on 2016/10/12.
 * QQ邮箱:515849594@qq.com
 * 说明：VIP字体动画
 */
public class VipTextView extends TextView {
    Paint mPaint1;
    Paint mPaint2;
    Paint mPaint;
    int mViewWidth,mTranslate;
    LinearGradient mLinearGradient;
    Matrix mGradientMatrix;

    public VipTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        mPaint1 = new Paint();
//        mPaint1.setColor(getResources().getColor(android.R.color.holo_red_light));
//        mPaint1.setStyle(Paint.Style.FILL_AND_STROKE);
//        mPaint2 = new Paint();
//        mPaint2.setColor(Color.GREEN);
//        mPaint2.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawRect(
//                0,
//                0,
//                getMeasuredWidth(),
//                getMeasuredHeight(),
//                mPaint1
//        );
//        canvas.drawRect(
//                10,
//                10,
//                getMeasuredWidth() - 10,
//                getMeasuredHeight() - 10,
//                mPaint2
//        );
//        canvas.save();
//        canvas.translate(10, 0);
        super.onDraw(canvas);
//        canvas.restore();

        mGradientMatrix = new Matrix();
        if (mGradientMatrix != null) {
            mTranslate+=mViewWidth/5;
            if (mTranslate > 2 * mViewWidth) {
                mTranslate = -mViewWidth;
            }
            mGradientMatrix.setTranslate(mTranslate, 0);
            mLinearGradient.setLocalMatrix(mGradientMatrix);
            postInvalidateDelayed(100);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mViewWidth == 0) {
            mViewWidth = getMeasuredWidth();
            if (mViewWidth > 0) {
                mPaint = getPaint();
                mLinearGradient = new LinearGradient(
                        0,
                        0,
                        mViewWidth,
                        0,
                        new int[]{Color.RED, 0xffffffff, Color.RED},
                        null,
                        Shader.TileMode.CLAMP);
                mPaint.setShader(mLinearGradient);
            }
        }
    }
}
