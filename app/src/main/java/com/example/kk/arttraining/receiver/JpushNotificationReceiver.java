package com.example.kk.arttraining.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.kk.arttraining.receiver.bean.JpushBean;
import com.example.kk.arttraining.utils.JsonTools;
import com.example.kk.arttraining.utils.UIUtil;

import cn.jpush.android.api.JPushInterface;

/**
 * 作者：wschenyongyin on 2016/12/5 14:39
 * 说明:notification推送消息
 */
public class JpushNotificationReceiver extends BroadcastReceiver {
    private String Jpush_Content_Type;
    private String extras;
    private JpushBean jpushBean;
    private String type;
    private String value;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        UIUtil.showLog("JpushNotificationReceiver---->", extras + "");
        jpushBean = JsonTools.ParseJpushExtras("alert", extras);
        UIUtil.showLog("JpushNotificationReceiver---->jpushBean--", jpushBean.toString() + "");
//        type = jpushBean.getType();
//        value = jpushBean.getValue();
//        switch (type) {
//            //帖子动态评论
//            case "comment_bbs":
//                bundle.getString(JPushInterface.EXTRA_CONTENT_TYPE);
//                break;
//            //作品评论
//            case "comment_work":
//                break;
//            //小组动态评论
//            case "comment_gstus":
//                break;
//            //帖子回复
//            case "reply_bbs":
//                break;
//            //作品回复
//            case "reply_work":
//                break;
//            //小组动态评论
//            case "reply_gstus":
//                break;
//            //老师评论
//            case "tec_comment":
//                bundle.getString(JPushInterface.EXTRA_CONTENT_TYPE);
//                break;
//            //老师回复
//            case "tec_reply":
//                break;
//            //学生测评
//            case "stu_ass":
//                break;
//            //帖子点赞
//            case "like_bbs":
//                break;
//            //作品点赞
//            case "like_work":
//                break;
//            //小组动态点赞
//            case "like_gstus":
//                break;
//            //关注
//            case "follow":
//                break;
//        }
    }
}
