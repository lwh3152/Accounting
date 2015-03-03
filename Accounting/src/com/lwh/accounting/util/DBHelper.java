package com.lwh.accounting.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lwh.accounting.entity.Accounting;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper {
	public static final String DB_NAME = "account.db";
	public static final String DB_TABLENAME = "account";
	public static final int VERSION = 1;
	public static SQLiteDatabase dbInstance;
	private SQLiteHelper sqlHelper;
	private Context context;
	
	// 构造方法
	public DBHelper(Context mContext){
		this.context = mContext;
	}
	// 初始化数据库，并返回只写的数据库
	public void openDatabase() {
		if(dbInstance == null){
			sqlHelper = new SQLiteHelper(context, DB_NAME, null, VERSION);
			dbInstance = sqlHelper.getWritableDatabase();
		}
	}
	
	
	/**
	 * 插入数据
	 * @param price	价格
	 * @param type	类型1-收入，2-支出
	 * @param status状态1-有效，2-无效
	 * @param year	年
	 * @param month	月
	 * @param day	日
	 * @param note	备注
	 * @param ctm	创建时间
	 * @param utm	更改时间
	 * @return
	 */
	public static long insertData(int price,int type,int status,
			int year,int month,int day,String note,String ctm,String utm){
		ContentValues content = getContent(price, type, status, year, month, day, note, ctm, utm);
		long result = dbInstance.insert(DB_TABLENAME, null, content);
		dbInstance.close();
		return result;
	}
	// type:1-收入,2-支出;status:1-有效,2-无效
	public static ContentValues getContent(int price,int type,int status,
			int year,int month,int day,String note,String ctm,String utm){
		ContentValues content = new ContentValues();
		content.put("price", price);
		content.put("type", type);
		content.put("status", status);
		content.put("year", year);
		content.put("month", month);
		content.put("day", day);
		content.put("note", note);
		content.put("ctm", ctm);
		content.put("utm", utm);
		return content;
	}
	
	public static List<Accounting> getAccounting(){
		String sql = "select * from " + DB_TABLENAME ;
		//dbInstance.execSQL(sql, null);
		Cursor cursor = dbInstance.rawQuery(sql, null);
		List<Accounting> list = new ArrayList<Accounting>();
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			int price = cursor.getInt(cursor.getColumnIndex("price"));
			int type = cursor.getInt(cursor.getColumnIndex("type"));
			int status = cursor.getInt(cursor.getColumnIndex("status"));
			int year = cursor.getInt(cursor.getColumnIndex("year"));
			int month = cursor.getInt(cursor.getColumnIndex("month"));
			int day = cursor.getInt(cursor.getColumnIndex("day"));
			String note = cursor.getString(cursor.getColumnIndex("note"));
			String ctm = cursor.getString(cursor.getColumnIndex("ctm"));
			String utm = cursor.getString(cursor.getColumnIndex("utm"));
			Accounting selectAccount = getAccount(id, price, type, status, year, month, day, note, null, null);
			list.add(selectAccount);
		}
		dbInstance.close();
		return list;
	}
	
	public static Accounting getAccount(int id,int price,int type,int status,int year,int month,int day,String note,String ctm,String utm){
		Accounting account = new Accounting();
		account.setId(id);
		account.setPrice(price);
		account.setType(type);
		account.setStatus(status);
		account.setYear(year);
		account.setMonth(month);
		account.setDay(day);
		account.setNote(note);
		account.setCtm(ctm);
		account.setUtm(utm);
		return account;
	}
}
