package com.example.d2y;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class iuOpenHelper extends SQLiteOpenHelper{
	//声明了数据库的基本信息
	//数据库文件的名称
	public static final String DB_NAME = "macaquedb.db";
	//数据库表格名称和数据库版本
	public static final String DB_TABLE = "personal_info";
	public static final int DB_VERSION = 1;
	//数据库表中的属性名称
//	public static final String KEY_ID = "_id";
//	public static final String KEY_USERNAME = "_username";
	public static final String KEY_ACTIVITY = "_activity";
	public static final String KEY_TAG = "_tag";
	public static final String KEY_DATE = "_date";
	public static final String KEY_TIME = "_time";
	
	
	public iuOpenHelper(Context context, String name, 
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	private static final String DB_CREATE = 
			"create table " + DB_TABLE +"("+
//			KEY_ID+" integer primary key autoincrement, "+
//			KEY_USERNAME+" text, "+
			KEY_ACTIVITY+" text, "+
			KEY_TAG+" text, "+
			KEY_DATE+" text, "+
			KEY_TIME+" text);";
		
	@Override
	public void onCreate(SQLiteDatabase _db) {
		_db.execSQL(DB_CREATE);
	}
	@Override
	public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
		_db.execSQL("DROP TABLE IF EXTISTS " + DB_TABLE);
		onCreate(_db);
	}
}