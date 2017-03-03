package com.example.kk.arttraining.ui.startpager;

/**
 * 作者：wschenyongyin on 2016/12/20 09:45
 * 说明:
 */

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;

import com.chechezhi.ui.guide.SingleElement;
import com.chechezhi.ui.guide.SinglePage;

import java.util.ArrayList;
import java.util.List;

public class GuideView extends View implements OnPageChangeListener {
    private static final int DOT_SPACE_DIVIDE = 3;
    private static final float DOT_Y_POSITION = 0.8F;
    private List<SinglePage> mGuideContent;
    private int mPosition;
    private float mOffset;
    private boolean mDrawDot;
    private List<SingleElement> mDotList;
    private int mDotXStart;
    private int mDotXPlus;
    private int mDotY;
    private SingleElement mSelectedDot;

    public GuideView(Activity activity, List<SinglePage> guideContent, boolean drawDot, Bitmap dotDefault, Bitmap dotSelected) {
        super(activity);
        this.mGuideContent = guideContent;
        this.mDrawDot = drawDot;
        if (guideContent != null && guideContent.size() > 1 && drawDot) {
            this.mDotList = new ArrayList();
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            Rect frame = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            int statusBarHeight = frame.top;
            int mScreenWidth = dm.widthPixels;
            int mScreenHeight = dm.heightPixels - statusBarHeight;
            this.mDotXStart = mScreenWidth / 3 - dotDefault.getWidth() / 2;
            this.mDotXPlus = mScreenWidth / 3 / (guideContent.size() - 1);
            this.mDotY = (int) ((float) mScreenHeight * 0.92F);

            for (int i = 0; i < guideContent.size(); ++i) {
                SingleElement e = new SingleElement((float) (this.mDotXStart + this.mDotXPlus * i), (float) this.mDotY, (float) (this.mDotXStart + this.mDotXPlus * i), (float) this.mDotY, 1.0F, 1.0F, dotDefault);
                this.mDotList.add(e);
            }

            this.mSelectedDot = new SingleElement((float) (this.mDotXStart + this.mDotXPlus * this.mPosition), (float) this.mDotY, (float) (this.mDotXStart + this.mDotXPlus * this.mPosition), (float) this.mDotY, 1.0F, 1.0F, dotSelected);
        }

    }

    protected void onDraw(Canvas canvas) {
        if (this.mDrawDot) {
            int singlePage;
            for (singlePage = 0; singlePage < this.mDotList.size(); ++singlePage) {
                SingleElement i = (SingleElement) this.mDotList.get(singlePage);
                this.drawElement(canvas, i);
            }

            singlePage = this.mDotXStart + this.mDotXPlus * this.mPosition;
            this.mSelectedDot.xStart = (float) singlePage;
            this.mSelectedDot.xEnd = (float) singlePage;
            this.drawElement(canvas, this.mSelectedDot);
        }

        SinglePage var5 = (SinglePage) this.mGuideContent.get(this.mPosition);
        if (var5.mElementsList == null) {
            super.onDraw(canvas);
        } else {
            for (int var6 = 0; var6 < var5.mElementsList.size(); ++var6) {
                SingleElement e = (SingleElement) var5.mElementsList.get(var6);
                this.drawElement(canvas, e);
            }

            super.onDraw(canvas);
        }
    }

    private void drawElement(Canvas canvas, SingleElement e) {
        Bitmap bitmap = e.contentBitmap;
        Matrix m = e.m;
        Paint p = e.p;
        float dx = e.xStart + (e.xEnd - e.xStart) * this.mOffset;
        float dy = e.yStart + (e.yEnd - e.yStart) * this.mOffset;
        float alpha = e.alphaStart + (e.alphaEnd - e.alphaStart) * this.mOffset;
        m.setTranslate(dx, dy);
        p.setAlpha((int) (255.0F * alpha));
        canvas.drawBitmap(bitmap, m, p);
    }

    public void onPageScrollStateChanged(int state) {
    }

    public void onPageScrolled(int index, float offset, int offsetPixel) {
        this.mPosition = index;
        this.mOffset = offset;
        this.invalidate();
    }

    public void onPageSelected(int index) {
    }
}