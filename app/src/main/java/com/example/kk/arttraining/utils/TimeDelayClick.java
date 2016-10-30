package com.example.kk.arttraining.utils;

/**
 * Created by kanghuicong on 2016/9/22.
 * QQ邮箱:515849594@qq.com
 * 说明：按钮的延时操作
 */
public class TimeDelayClick {
    private static long lastClickTime;
    public synchronized static boolean isFastClick(int wait_time) {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < wait_time) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}

//if (TimeDelayClick.isFastClick(500)) {
//        return;
//        } else{
//        }