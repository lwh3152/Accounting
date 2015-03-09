package com.lwh.accounting.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesUtil {
	
	public static final String FILE_NAME = "account_sp";
	
	public static void putValue(Context context, long value, String key) {
		SharedPreferences sp = getPreferences(context, FILE_NAME);
		sp.edit().putLong(key, value).commit();
	}

	public static long getValue(Context context, String key) {
		SharedPreferences sp = getPreferences(context, FILE_NAME);
		return sp.getLong(key, 0);
	}
	
	public static void putDateValue(Context context, String value, String key){
		SharedPreferences sp = getPreferences(context, FILE_NAME);
		sp.edit().putString(key, value).commit();
	}
	
	public static String getDateValue(Context context, String key){
		SharedPreferences sp = getPreferences(context, FILE_NAME);
		return sp.getString(key, "");
	}
	
	@SuppressLint("WorldWriteableFiles")
	public static SharedPreferences getPreferences(Context context,String fileName){
		SharedPreferences sp = context.getSharedPreferences(fileName,Context.MODE_WORLD_WRITEABLE);
		return sp;
	}
}
