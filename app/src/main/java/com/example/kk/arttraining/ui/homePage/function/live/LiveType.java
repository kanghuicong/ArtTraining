package com.example.kk.arttraining.ui.homePage.function.live;

import android.content.Context;
import android.content.Intent;

import com.example.kk.arttraining.ui.live.view.LiveFinishActivity;
import com.example.kk.arttraining.ui.live.view.LiveWaitActivity;
import com.example.kk.arttraining.ui.live.view.PLVideoViewActivity;
import com.example.kk.arttraining.ui.live.view.PlayCallBackVideo;
import com.example.kk.arttraining.utils.UIUtil;

/**
 * Created by kanghuicong on 2017/3/27.
 * QQ邮箱:515849594@qq.com
 */
public class LiveType {

    public static void getLiveType(Context context,int type, int room_id, int chapter_id) {
        switch (type){
            //还未开始直播状态
            case 0:
                Intent intentBefore = new Intent(context, LiveWaitActivity.class);
                intentBefore.putExtra("room_id", room_id);
                intentBefore.putExtra("chapter_id", chapter_id);
                context.startActivity(intentBefore);
                break;
            //正在直播
            case 1:
                Intent intentBeing = new Intent(context, PLVideoViewActivity.class);
                intentBeing.putExtra("room_id", room_id);
                intentBeing.putExtra("chapter_id", chapter_id);
                context.startActivity(intentBeing);
                break;
            //直播结束
            case 2:
                Intent intentAfter = new Intent(context, LiveFinishActivity.class);
                intentAfter.putExtra("room_id", room_id);
                context.startActivity(intentAfter);
                break;
        }
    }

    public static void getLiveListType(Context context,int type,int room_id,int chapter_id,String path) {
        switch (type) {
            //未开播
            case 0:
                UIUtil.ToastshowShort(context, "亲，该章节还未开播！");
                break;
            //直播中
            case 1:
                Intent intentBeing = new Intent(context, PLVideoViewActivity.class);
                intentBeing.putExtra("room_id", room_id);
                intentBeing.putExtra("chapter_id", chapter_id);
                context.startActivity(intentBeing);
                break;
            //重播
            case 2:
                Intent intentRecord = new Intent(context, PlayCallBackVideo.class);
                intentRecord.putExtra("path", path);
                context.startActivity(intentRecord);
                break;
        }
    }
}
