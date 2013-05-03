package com.example.d2y;

import static com.example.d2y.iuOpenHelper.*;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class idusUserHelper {
	//构造函数
	public idusUserHelper() {
	}
	
	//添加新的用户
	public long insertUser(iuOpenHelper iuHelper, iuUser u) {
		long res;
		SQLiteDatabase _db = iuHelper.getWritableDatabase();
		ContentValues v = new ContentValues();
		v.put(KEY_ID, u.getId());
		v.put(KEY_USERNAME, u.getUsername());
		v.put(KEY_PASSWORD, u.getPassword());
		v.put(KEY_EMAIL, u.getEmail());
		res = _db.insert(DB_TABLE_USER, null, v);
		_db.close();
		return res;
	}

	//删除本地已有用户
	public long deleteUser(iuOpenHelper iuHelper, iuUser u) {
		long res;
		SQLiteDatabase _db = iuHelper.getWritableDatabase();
		res = _db.delete(DB_TABLE_USER, u.getUsername() + "=?", new String[] {u.getUsername()});
		_db.close();
		return res;
	}

	//更新已有用户信息
	public long updateUser(iuOpenHelper iuHelper, iuUser u,
			String username) {
		long res;
		SQLiteDatabase _db = iuHelper.getWritableDatabase();
		ContentValues v = new ContentValues();
		v.put(KEY_ID, u.getId());
		v.put(KEY_USERNAME, u.getUsername());
		v.put(KEY_PASSWORD, u.getPassword());
		v.put(KEY_EMAIL, u.getEmail());
		res = _db.update(DB_TABLE_USER, v, KEY_USERNAME + "=?", new String[] {username});
		_db.close();
		return res; 
	}

	//查找
	public iuActivity searchActivity(iuOpenHelper iuHelper, String activity) {
		iuActivity iuActivity = new iuActivity();
		SQLiteDatabase _db = iuHelper.getWritableDatabase();
		Cursor res = _db.query(DB_TABLE_ACTIVITY, null, KEY_ACTIVITY + "=?",
				new String[] {activity}, null, null, null);
		while (res.moveToNext()) {
			iuActivity.setIUActivity(res.getString(res.getColumnIndex(KEY_ACTIVITY)),
					res.getString(res.getColumnIndex(KEY_TAG)), 
					res.getString(res.getColumnIndex(KEY_ADDRESS)), 
					res.getString(res.getColumnIndex(KEY_DATE)), 
					res.getString(res.getColumnIndex(KEY_TIME)), 
					res.getInt(res.getColumnIndex(KEY_REMIND)), 
					res.getString(res.getColumnIndex(KEY_REMARK)));
		}
		_db.close();
		return iuActivity;
	}
}