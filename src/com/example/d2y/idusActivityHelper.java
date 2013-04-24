package com.example.d2y;

import static com.example.d2y.iuOpenHelper.*;
import static com.example.d2y.iuActivity.*;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class idusActivityHelper {
	//构造函数
	public idusActivityHelper() {
	}
	
	//获取所有活动
	
    /*public int getActivityNum(iuOpenHelper helper) {
    	int num;
    	SQLiteDatabase _db = helper.getWritableDatabase();
    	Cursor c = _db.query(DB_TABLE, new String[] {KEY_ACTIVITY, KEY_TAG, 
    												KEY_ADDRESS, KEY_DATE,
    												KEY_TIME, KEY_REMIND,
    												KEY_REMARK},
    			null, null, null, null, KEY_ACTIVITY);
    	num = c.getCount();
    	c.close();
    	_db.close();
    	return num;
    }*/
	
    public iuActivity[] getActivities(iuOpenHelper helper) {
    	SQLiteDatabase _db = helper.getWritableDatabase();
    	Cursor c = _db.query(DB_TABLE, new String[] {KEY_ACTIVITY, KEY_TAG, 
    												KEY_ADDRESS, KEY_DATE,
    												KEY_TIME, KEY_REMIND,
    												KEY_REMARK},
    			null, null, null, null, KEY_ACTIVITY);
    	int activityIndex = c.getColumnIndex(KEY_ACTIVITY);
    	int tagIndex = c.getColumnIndex(KEY_TAG);
    	int addressIndex = c.getColumnIndex(KEY_ADDRESS);
    	int dateIndex = c.getColumnIndex(KEY_DATE);
    	int timeIndex = c.getColumnIndex(KEY_TIME);
    	int remindIndex = c.getColumnIndex(KEY_REMIND);
    	int remarkIndex = c.getColumnIndex(KEY_REMARK);
    	iuActivity[] iuActivity = new iuActivity[c.getCount()];
    	int i = 0;
    	for (c.moveToFirst(); !(c.isAfterLast()); c.moveToNext()) {
    		iuActivity[i] = new iuActivity();
    		iuActivity[i]._activity = c.getString(activityIndex);
    		iuActivity[i]._tag = c.getString(tagIndex);
    		iuActivity[i]._address = c.getString(addressIndex);
    		iuActivity[i]._date = c.getString(dateIndex);
    		iuActivity[i]._time = c.getString(timeIndex);
    		iuActivity[i]._remind = c.getInt(remindIndex);
    		iuActivity[i]._remark = c.getString(remarkIndex);
    		++i;
    	}
    	c.close();
    	_db.close();
    	return iuActivity;
    }

	//插入
	public long insertActivity(iuOpenHelper iuHelper, iuActivity iuActivity) {
		long res;
		SQLiteDatabase _db = iuHelper.getWritableDatabase();
		ContentValues v = new ContentValues();
		v.put(KEY_ACTIVITY, iuActivity._activity);
		v.put(KEY_TAG, iuActivity._tag);
		v.put(KEY_ADDRESS, iuActivity._address);
		v.put(KEY_DATE, iuActivity._date);
		v.put(KEY_TIME, iuActivity._time);
		v.put(KEY_REMIND, iuActivity._remind);
		v.put(KEY_REMARK, iuActivity._remark);
		res = _db.insert(DB_TABLE, null, v);
		_db.close();
		return res;
	}

	//删除
	public long deleteActivity(iuOpenHelper iuHelper, String activity) {
		long res;
		SQLiteDatabase _db = iuHelper.getWritableDatabase();
		res = _db.delete(DB_TABLE, KEY_ACTIVITY + "=?", new String[] {activity});
		_db.close();
		return res;
	}

	//更新
	public long updateActivity(iuOpenHelper iuHelper, iuActivity iuActivity,
			String activity) {
		long res;
		SQLiteDatabase _db = iuHelper.getWritableDatabase();
		ContentValues v = new ContentValues();
		v.put(KEY_ACTIVITY, iuActivity._activity);
		v.put(KEY_TAG, iuActivity._tag);
		v.put(KEY_ADDRESS, iuActivity._address);
		v.put(KEY_DATE, iuActivity._date);
		v.put(KEY_TIME, iuActivity._time);
		v.put(KEY_REMIND, iuActivity._remind);
		v.put(KEY_REMARK, iuActivity._remark);
		res = _db.update(DB_TABLE, v, KEY_ACTIVITY + "=?", new String[] {activity});
		_db.close();
		return res; 
	}

	//查找
	public iuActivity searchActivity(iuOpenHelper iuHelper, String activity) {
		iuActivity iuActivity = new iuActivity();
		SQLiteDatabase _db = iuHelper.getWritableDatabase();
		Cursor res = _db.query(DB_TABLE, null, KEY_ACTIVITY + "=?",
				new String[] {activity}, null, null, null);
		while (res.moveToNext()) {
			iuActivity._activity = res.getString(res.getColumnIndex(KEY_ACTIVITY));
			iuActivity._tag = res.getString(res.getColumnIndex(KEY_TAG));
			iuActivity._address = res.getString(res.getColumnIndex(KEY_ADDRESS));
			iuActivity._date = res.getString(res.getColumnIndex(KEY_DATE));
			iuActivity._time = res.getString(res.getColumnIndex(KEY_TIME));
			iuActivity._remind = res.getInt(res.getColumnIndex(KEY_REMIND));
			iuActivity._remark = res.getString(res.getColumnIndex(KEY_REMARK));
		}
		_db.close();
		return iuActivity;
	}
}