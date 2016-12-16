package com.example.kk.arttraining.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.UriMatcher;
import android.os.Bundle;

import com.example.kk.arttraining.receiver.bean.JpushBean;
import com.example.kk.arttraining.ui.homePage.activity.DynamicContent;
import com.example.kk.arttraining.utils.JsonTools;
import com.example.kk.arttraining.utils.UIUtil;

import cn.jpush.android.api.JPushInterface;

/**
 * 作者：wschenyongyin on 2016/12/6 20:07
 * 说明:监听点击状态栏机关推送消息
 */
public class JpushOpenReceiver extends BroadcastReceiver {
    //推送的原始数据
    private String extras;
    //装推送的数据
    private JpushBean jpushBean;
    //推送的类型
    private String type;
    //推送的值
    private String value;

    private Intent skipIntent;
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        UIUtil.showLog("JpushOpenReceiver---->", extras + "");
        jpushBean = JsonTools.ParseJpushExtras("alert", extras);
        UIUtil.showLog("JpushOpenReceiver---->jpushBean--", jpushBean.toString() + "");
        type = jpushBean.getType();
        value = jpushBean.getValue();

        switch (type) {
            //帖子动态评论
            case "comment_bbs":
                skipIntent = new Intent(context, DynamicContent.class);
                skipIntent.putExtra("status_id", value);
                skipIntent.putExtra("stus_type", "status");
                skipIntent.putExtra("type", value);
                context.startActivity(skipIntent);
                break;
            //作品评论
            case "comment_work":
                skipIntent = new Intent(context, DynamicContent.class);
                skipIntent.putExtra("status_id", value);
                skipIntent.putExtra("stus_type", "work");
                skipIntent.putExtra("type", value);
                context.startActivity(skipIntent);
                break;
            //小组动态评论
            case "comment_gstus":
                break;
            //帖子回复
            case "reply_bbs":
                skipIntent = new Intent(context, DynamicContent.class);
                skipIntent.putExtra("status_id", value);
                skipIntent.putExtra("stus_type", "status");
                skipIntent.putExtra("type", value);
                context.startActivity(skipIntent);
                break;
            //作品回复
            case "reply_work":
                skipIntent = new Intent(context, DynamicContent.class);
                skipIntent.putExtra("status_id", value);
                skipIntent.putExtra("stus_type", "status");
                skipIntent.putExtra("type", value);
                context.startActivity(skipIntent);
                break;
            //小组动态评论
            case "reply_gstus":
                break;
            //老师评论
            case "tec_comment":
                break;
            //老师回复
            case "tec_reply":
                break;
            //学生测评
            case "stu_ass":
                break;
            //帖子点赞
            case "like_bbs":
                break;
            //作品点赞
            case "like_work":
                break;
            //小组动态点赞
            case "like_gstus":
                break;
            //关注
            case "follow":
                break;
        }
    }
}
