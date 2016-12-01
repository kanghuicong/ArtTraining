package com.example.kk.arttraining.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 * 作者：wschenyongyin on 2016/11/11 22:03
 * 说明:Activity管理类：用于管理Activity和退出程序
 */
public class ActivityManage {

    // Activity栈
    private static Stack<Activity> activityStack;
    // 单例模式
    private static ActivityManage instance;

    private ActivityManage() {
    }

    /**
     * 单一实例
     */
    public static ActivityManage getAppManager() {
        if (instance == null) {
            instance = new ActivityManage();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
//        for (Activity activity : activityStack) {
//            if (activity.getClass().equals(cls)) {
//                finishActivity(activity);
//            }
//        }
        UIUtil.showLog("关闭activity成功","true");
        for (int i = 0; i < activityStack.size(); i++) {
            if (cls.equals( activityStack.get(i))) {
                activityStack.get(i).finish();
                UIUtil.showLog("关闭activity成功","true");
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0; i < activityStack.size(); i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }
}