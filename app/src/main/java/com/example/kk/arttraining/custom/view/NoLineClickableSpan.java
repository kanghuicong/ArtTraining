package com.example.kk.arttraining.custom.view;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by kanghuicong on 2017/1/12.
 * QQ邮箱:515849594@qq.com
 */
public class NoLineClickableSpan extends ClickableSpan {

    public NoLineClickableSpan() {
        super();
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(ds.linkColor);
        ds.setUnderlineText(false);
    }

    @Override
    public void onClick(View widget) {
    }
}