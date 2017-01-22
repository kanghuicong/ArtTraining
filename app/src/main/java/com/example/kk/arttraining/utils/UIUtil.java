package com.example.kk.arttraining.utils;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.kk.arttraining.MyApplication;
import com.example.kk.arttraining.R;

/**
 * 作者：wschenyongyin on 2016/9/21 10:32
 * 说明:UI,提示信息工具类
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
       Log.i(classStr, contextStr);
    }
    public static void IntentActivity(Activity fromActivity, Activity toActivity) {
        Intent intent = new Intent(fromActivity, toActivity.getClass());
        fromActivity.startActivity(intent);
    }


    public static Context getContext() {
        return MyApplication.getInstance();
    }
    /**
     * 获取资源对象
     */
    public static Resources getResources() {
        return getContext().getResources();
    }
    /**
     * 获取资源文件字符串
     *
     * @param id
     * @return
     */
    public static String getString(int id) {
        return getResources().getString(id);
    }
    /**
     * 获取资源文件字符串数组
     *
     * @param id
     * @return
     */
    public static String[] getStringArray(int id) {
        return getResources().getStringArray(id);
    }
    /**
     * 获取资源文件图片
     *
     * @param id
     * @return
     */
    public static Drawable getDrawable(int id) {
        return ContextCompat.getDrawable(getContext(), id);
    }
    /**
     * 获取资源文件颜色
     *
     * @param id
     * @return
     */
    public static int getColor(int id) {
        return ContextCompat.getColor(getContext(), id);
    }
    /**
     * 根据id获取颜色的状态选择器
     *
     * @param id
     * @return
     */
    public static ColorStateList getColorStateList(int id) {
        return ContextCompat.getColorStateList(getContext(), id);
    }
    /**
     * 获取尺寸
     *
     * @param id
     * @return
     */
    public static int getDimen(int id) {
        return getResources().getDimensionPixelSize(id); // 返回具体像素值
    }
    /**
     * dp -> px
     *
     * @param dp
     * @return
     */
    public static int dp2px(float dp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }
    /**
     * px -> dp
     *
     * @param px
     * @return
     */
    public static float px2dp(int px) {
        float density = getResources().getDisplayMetrics().density;
        return px / density;
    }
    /**
     * 加载布局文件
     *
     * @param id
     * @return
     */
    public static View inflate(int id) {
        return View.inflate(getContext(), id, null);
    }
    /**
     * 把自身从父View中移除
     * @param view
     */
    public static void removeSelfFromParent(View view) {
        if (view != null) {
            ViewParent parent = view.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) parent;
                group.removeView(view);
            }
        }
    }
    /**
     * 请求View树重新布局，用于解决中层View有布局状态而导致上层View状态断裂
     * @param view
     * @param isAll
     */
    public static void requestLayoutParent(View view, boolean isAll) {
        ViewParent parent = view.getParent();
        while (parent != null && parent instanceof View) {
            if (!parent.isLayoutRequested()) {
                parent.requestLayout();
                if (!isAll) {
                    break;
                }
            }
            parent = parent.getParent();
        }
    }
    /**
     * 判断触点是否落在该View上
     * @param ev
     * @param v
     * @return
     */
    public static boolean isTouchInView(MotionEvent ev, View v) {
        int[] vLoc = new int[2];
        v.getLocationOnScreen(vLoc);
        float motionX = ev.getRawX();
        float motionY = ev.getRawY();
        return motionX >= vLoc[0] && motionX <= (vLoc[0] + v.getWidth())
                && motionY >= vLoc[1] && motionY <= (vLoc[1] + v.getHeight());
    }
    /**
     * findViewById的泛型封装，减少强转代码
     * @param layout
     * @param id
     * @param <T>
     * @return
     */
    public static <T extends View> T findViewById(View layout, int id) {
        return (T) layout.findViewById(id);
    }
    /**
     * 获取屏幕的比例
     * @param context
     * @return
     */
    public static float getScaledDensity(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float value = dm.scaledDensity;
        return value;
    }
    /**
     * 获取控件的高度，如果获取的高度为0，则重新计算尺寸后再返回高度
     * @param view
     * @return
     */
    public static int getViewMeasuredHeight(View view) {
        calcViewMeasure(view);
        return view.getMeasuredHeight();
    }
    /**
     * 获取控件的宽度，如果获取的宽度为0，则重新计算尺寸后再返回宽度
     * @param view
     * @return
     */
    public static int getViewMeasuredWidth(View view) {
        calcViewMeasure(view);
        return view.getMeasuredWidth();
    }
    /**
     * 测量控件的尺寸
     * @param view
     */
    public static void calcViewMeasure(View view) {
        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int expandSpec = View.MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        view.measure(width, expandSpec);
    }
    /**
     * 设置textview指定文字为某一颜色
     *
     * @param content 显示的文字
     * @param color   需要转换成的颜色值
     * @param start   需要变色文字开始位置
     * @param end     需要变色文字结束位置
     */
    public static SpannableStringBuilder changeTextColor(String content, int color, int start, int end) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(content);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder;
    }

}
