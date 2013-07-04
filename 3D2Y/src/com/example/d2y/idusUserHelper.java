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
	public iuUser searchUser(iuOpenHelper iuHelper, String username) {
		iuUser iuUser = new iuUser();
		SQLiteDatabase _db = iuHelper.getWritableDatabase();
		Cursor res = _db.query(DB_TABLE_USER, null, KEY_USERNAME + "=?",
				new String[] {username}, null, null, null);
		while (res.moveToNext()) {
			iuUser.setUsername(res.getString(res.getColumnIndex(KEY_USERNAME)));
			iuUser.setPassword(res.getString(res.getColumnIndex(KEY_PASSWORD)));
		}
		_db.close();
		return iuUser;
	}
	

}