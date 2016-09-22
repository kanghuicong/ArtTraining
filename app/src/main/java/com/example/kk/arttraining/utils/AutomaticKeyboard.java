package com.example.kk.arttraining.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by kanghuicong on 2016/9/22.
 * QQ邮箱:515849594@qq.com
 * 说明：自动弹出键盘
 */
public class AutomaticKeyboard {

    public static void GetClick(EditText et) {
        // 获取编辑框焦点
        et.setFocusable(true);
        et.setFocusableInTouchMode(true);
        et.requestFocus();
        //打开软键盘
        InputMethodManager imm = (InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
