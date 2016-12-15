package com.example.kk.arttraining.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.UriMatcher;
import android.os.Bundle;

import com.example.kk.arttraining.utils.UIUtil;

import cn.jpush.android.api.JPushInterface;

/**
 * 作者：wschenyongyin on 2016/12/6 20:07
 * 说明:
 */
public class JpushOpenReceiver extends BroadcastReceiver {
    private String Jpush_Content_Type;
    @Override
    public void onReceive(Context context, Intent intent) {
        UIUtil.showLog("JpushOpenReceiver------>","true");
        Bundle bundle = intent.getExtras();
        Jpush_Content_Type = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        UIUtil.showLog("EXTRA_NOTIFICATION_TITLE------>",Jpush_Content_Type);
        switch (Jpush_Content_Type) {
            case "ass_no":
                bundle.getString(JPushInterface.EXTRA_CONTENT_TYPE);
                break;
            case "ass_yes":
                break;
            case "master_msg":
                break;
            case "money_msg":
                break;
            case "info_msg":
                break;
            case "course_msg":
                break;
        }
    }
}
