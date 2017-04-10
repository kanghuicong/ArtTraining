package com.example.kk.arttraining.ui.homePage.function.shadow;

import java.io.Serializable;

/**
 * Author: wangjie
 * Email: tiantian.china.2@gmail.com
 * Date: 5/25/15.
 */
public class ShadowProperty implements Serializable {
    public static final int ALL = 0x1111;
    public static final int LEFT = 0x0001;
    public static final int TOP = 0x0010;
    public static final int RIGHT = 0x0100;
    public static final int BOTTOM = 0x1000;

    /**
     * 阴影颜色
     */
    private int shadowColor;
    /**
     * 阴影半径
     */
    private int shadowRadius;
    /**
     * 阴影x偏移
     */
    private int shadowDx;
    /**
     * 阴影y偏移
     */
    private int shadowDy;

    /**
     * 阴影边
     */
    private int shadowSide = ALL;

    public int getShadowSide() {
        return shadowSide;
    }

    public ShadowProperty setShadowSide(int shadowSide) {
        this.shadowSide = shadowSide;
        return this;
    }

    public int getShadowOffset() {
        return getShadowOffsetHalf() * 2;
    }

    public int getShadowOffsetHalf() {
        return 0 >= shadowRadius ? 0 : Math.max(shadowDx, shadowDy) + shadowRadius;
    }

    public int getShadowColor() {
        return shadowColor;
    }

    public ShadowProperty setShadowColor(int shadowColor) {
        this.shadowColor = shadowColor;
        return this;
    }

    public int getShadowRadius() {
        return shadowRadius;
    }

    public ShadowProperty setShadowRadius(int shadowRadius) {
        this.shadowRadius = shadowRadius;
        return this;
    }

    public int getShadowDx() {
        return shadowDx;
    }

    public ShadowProperty setShadowDx(int shadowDx) {
        this.shadowDx = shadowDx;
        return this;
    }

    public int getShadowDy() {
        return shadowDy;
    }

    public ShadowProperty setShadowDy(int shadowDy) {
        this.shadowDy = shadowDy;
        return this;
    }


//    ShadowProperty sp = new ShadowProperty()
//            .setShadowColor(0x77000000)
//            .setShadowDy(dp2px(this, 0.5f))
//            .setShadowRadius(dp2px(this, 3))
//            .setShadowSide(ShadowProperty.ALL);
//    ShadowViewDrawable sd = new ShadowViewDrawable(sp, Color.WHITE, 0, 0);
//    ViewCompat.setBackground(TV, sd);
//    ViewCompat.setLayerType(TV, ViewCompat.LAYER_TYPE_SOFTWARE, null);


//        ShadowProperty sp = new ShadowProperty()
//                .setShadowColor(0x770000FF)
//                .setShadowDy(dp2px(this, 0.5f))
//                .setShadowRadius(dp2px(this, 3))
//                .setShadowSide(ShadowProperty.LEFT | ShadowProperty.RIGHT | ShadowProperty.BOTTOM);
//        ShadowViewDrawable sd = new ShadowViewDrawable(sp, Color.TRANSPARENT, 0, 0);
//        ViewCompat.setBackground(TV, sd);
//        ViewCompat.setLayerType(TV, ViewCompat.LAYER_TYPE_SOFTWARE, null);
}
