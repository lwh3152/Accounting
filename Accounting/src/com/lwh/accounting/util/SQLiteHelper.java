package com.lwh.accounting.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper{

	/**
	 * 构造方法,传递一些数据库的信息
	 * @param context
	 * @param name	数据库的名称
	 * @param factory
	 * @param version 数据库的版本
	 */
	public SQLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	/**
	 * 创建数据库时会调用onCreate方法,所以在该方法中,创建一些数据库的表(即创建完数据库又创建数据库表)
	 * getReadableDatabase()和getWritableDatabase()都会创建或打开一个只读/只写的数据库
	 * 所以调用这两个方法的时候,一样会执行onCreate方法,该方法只执行一次
	 * 
	 * 所以在使用的时候,可以实例化这个类,并调用getReadableDatabase或则getWritableDatabase方法返回一个只读/只写的数据库
	 * SQLiteHelper helper = new SQLiteHelper();
	 * helper.getReadableDatabase();只读
	 * helper.getWritableDatabase();只写
	 * 接下来就可以进行增删改查的操作了
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		String createTableSql = "create table if not exists " + DBHelper.DB_TABLENAME +
				" (id integer primary key autoincrement,price int,type int,status int," +
				"year int,month int,day int,note text,ctm datetime,utm timestamp)";
		db.execSQL(createTableSql);
	}

	/**
	 * 数据库版本更新时调用
	 */
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		
	}
	
}
