package com.example.d2y;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class iuOpenHelper extends SQLiteOpenHelper{
	//声明了数据库的基本信息
	//数据库文件的名称
	public static final String DB_NAME = "macaquedb.db";
	
	//数据库表格名称和数据库版本
	public static final String DB_TABLE_USER = "user_info";
	public static final String DB_TABLE_ACTIVITY = "activity_info";
	public static final String DB_TABLE_MAP = "address_info";
	public static final int DB_VERSION = 1;
	
	//数据表 user 中的属性名称
	public static final String KEY_ID = "_id";
	public static final String KEY_USERNAME = "_username";
	public static final String KEY_PASSWORD = "_password";
	public static final String KEY_EMAIL = "_email";
	
	//数据库表 activity 中的属性名称
	public static final String KEY_ACTIVITY = "_activity";
	public static final String KEY_TAG = "_tag";
	public static final String KEY_ADDRESS = "_address";
	public static final String KEY_DATE = "_date";
	public static final String KEY_TIME = "_time";
	public static final String KEY_REMIND = "_remind";
	public static final String KEY_REMARK = "_remark";
	
	
	public iuOpenHelper(Context context, String name, 
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}
	
	//新建 user 数据表
	private static final String DB_CREATE_USER =
			"create table " + DB_TABLE_USER +"("+
			KEY_ID+" integer, "+
			KEY_USERNAME+" text, "+
			KEY_PASSWORD+" text, "+
			KEY_EMAIL+" text);";
	
	//新建 activity 数据表
	private static final String DB_CREATE_ACTIVITY = 
			"create table " + DB_TABLE_ACTIVITY +"("+
			KEY_ACTIVITY+" text, "+
			KEY_TAG+" text, "+
			KEY_ADDRESS+" text, "+
			KEY_DATE+" text, "+
			KEY_TIME+" text, "+
			KEY_REMIND+" integer, "+
			KEY_REMARK+" text);";
		
	@Override
	public void onCreate(SQLiteDatabase _db) {
		_db.execSQL(DB_CREATE_USER);
		_db.execSQL(DB_CREATE_ACTIVITY);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
		_db.execSQL("DROP TABLE IF EXSISTS " + DB_TABLE_USER);
		_db.execSQL("DROP TABLE IF EXSISTS " + DB_TABLE_ACTIVITY);
		onCreate(_db);
	}
}