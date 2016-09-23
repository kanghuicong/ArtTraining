package com.example.kk.arttraining.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ToolKits {
	public static SharedPreferences getSharedPreferences(Context context) {
		return context.getSharedPreferences("com.util", Context.MODE_PRIVATE);
	}

	public static void putBooble(Context context, String key, boolean value) {
		SharedPreferences SharedPreferences = getSharedPreferences(context);
		SharedPreferences.Editor editor = SharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static boolean fetchBooble(Context context, String key,
			boolean defaultValue) {
		return getSharedPreferences(context).getBoolean(key, defaultValue);
	}
}
