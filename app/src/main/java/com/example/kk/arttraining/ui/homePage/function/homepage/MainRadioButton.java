package com.example.kk.arttraining.ui.homePage.function.homepage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.RadioButton;

import com.example.kk.arttraining.R;

/**
 * Created by kanghuicong on 2016/11/3.
 * QQ邮箱:515849594@qq.com
 */
public class MainRadioButton {

    public static void initColor(Context context,RadioButton rb) {
        if (rb.isChecked()) {
            rb.setTextColor(context.getResources().getColor(R.color.title_color));
        } else {
            rb.setTextColor(context.getResources().getColor(R.color.rb_text));
        }
    }

    public static void initImage(Context context,RadioButton rb,int imageChecked,int imageNormal){
        if (rb.isChecked()) {
            Drawable drawable = context.getResources().getDrawable(imageChecked);
            rb.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
        }else {
            Drawable drawable = context.getResources().getDrawable(imageNormal);
            rb.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
        }
    }
}
