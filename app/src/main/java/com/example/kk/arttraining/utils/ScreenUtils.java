package com.example.kk.arttraining.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * 作者：wschenyongyin on 2016/9/21 10:32
 * 说明:提示信息工具类
 */
public class ScreenUtils {
	private ScreenUtils() {
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	//获取屏幕宽度
	public static int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

//获取屏幕高度
	public static int getScreenHeight(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}

//获取状态栏的高度
	public static int getStatusHeight(Context context) {

		int statusHeight = -1;
		try {
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height")
					.get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusHeight;
	}

	//获取当前屏幕截图，包含状态栏
	public static Bitmap snapShotWithStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		view.destroyDrawingCache();
		return bp;

	}

	//获取当前屏幕截图，不包含状态栏
	public static Bitmap snapShotWithoutStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
				- statusBarHeight);
		view.destroyDrawingCache();
		return bp;
	}

	//根据屏幕宽度设置控件宽度
	public static void accordWidth(LinearLayout view, int width, int molecular, int denominator) {
		ViewGroup.LayoutParams ps = view.getLayoutParams();
		ps.width = (width/denominator * molecular);
		view.setLayoutParams(ps);
	}

	//根据屏幕高度设置控件高度
	public static void accordHeight(View view,int height, int molecular, int denominator) {
		ViewGroup.LayoutParams ps = view.getLayoutParams();
		ps.height = (height/denominator * molecular);
		Log.i("height", height + "----");
		Log.i("denominator", denominator + "----");
		Log.i("molecular", molecular + "----");
		Log.i("ps.height", ps.height + "----");

		view.setLayoutParams(ps);
	}

	/**
	 * �жϵ�ǰ�����Ƿ����
	 * @param context
	 * @return
	 */
	public static boolean isMultiPane(Context context) {
		return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
	}

}
