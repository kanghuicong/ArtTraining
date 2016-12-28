package com.example.kk.arttraining.utils;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kk.arttraining.R;

/**
 * 作者：wschenyongyin on 2016/9/21 10:32
 * 说明:提示信息工具类
 */
public class UIUtil {

    //短的toast提示
    public static void ToastshowShort(Context context, String message) {
        if (TimeDelayClick.isFastClick(1000)) {
            return;
        } else {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        }
    }

    //长的toast提示
    public static void ToastshowLong(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    //自定义时长的dialog
    public static void ToastshowDuration(Context context, CharSequence message, int duration) {
        Toast.makeText(context, message, duration).show();
    }

    //等待的dialog
    public static Dialog showWaitDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.loading_dialog, null);
        ImageView blow = (ImageView) view.findViewById(R.id.dialog_imgv_blow);
        Animation rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.loading);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        blow.startAnimation(rotateAnimation);
        Dialog loadingDialog = new Dialog(context, R.style.LoadingDialog);
        loadingDialog.show();
        loadingDialog.setContentView(view);
        return loadingDialog;
    }
    //Log信息打印
    public static void showLog(String classStr, String contextStr) {
//       Log.i(classStr, contextStr);
    }
    public static void IntentActivity(Activity fromActivity, Activity toActivity) {
        Intent intent = new Intent(fromActivity, toActivity.getClass());
        fromActivity.startActivity(intent);
    }
}
