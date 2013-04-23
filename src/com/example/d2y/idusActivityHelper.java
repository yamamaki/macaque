package com.example.d2y;

import static com.example.d2y.iuOpenHelper.*;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class idusActivityHelper {
	//构造函数
	public idusActivityHelper() {
	}
	
	//获取所有活动
    public String[][] getActivities(iuOpenHelper helper) {
    	String [][] activityArrayList; 
    	SQLiteDatabase _db = helper.getWritableDatabase();
    	Cursor c = _db.query(DB_TABLE, new String[] {KEY_ACTIVITY, KEY_TAG, KEY_DATE, KEY_TIME},
    			null, null, null, null, KEY_ACTIVITY);
    	int activityIndex = c.getColumnIndex(KEY_ACTIVITY);
    	int tagIndex = c.getColumnIndex(KEY_TAG);
    	int dateIndex = c.getColumnIndex(KEY_DATE);
    	int timeIndex = c.getColumnIndex(KEY_TIME);
    	activityArrayList = new String[4][c.getCount()];
    	int i = 0;
    	for (c.moveToFirst(); !(c.isAfterLast()); c.moveToNext()) {
    		activityArrayList[0][i] = c.getString(activityIndex);
    		activityArrayList[1][i] = c.getString(tagIndex);
    		activityArrayList[2][i] = c.getString(dateIndex);
    		activityArrayList[3][i] = c.getString(timeIndex);
    		++i;
    	}
    	c.close();
    	_db.close();
    	return activityArrayList;
    }

	//插入
	public long insertActivity(iuOpenHelper iuHelper, String[] activityArray) {
		long res;
		SQLiteDatabase _db = iuHelper.getWritableDatabase();
		ContentValues v = new ContentValues();
		v.put(KEY_ACTIVITY, activityArray[0]);
		v.put(KEY_TAG, activityArray[1]);
		v.put(KEY_DATE, activityArray[2]);
		v.put(KEY_TIME, activityArray[3]);
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
	public long updateActivity(iuOpenHelper iuHelper, String[] activityArray,
			String activity) {
		long res;
		SQLiteDatabase _db = iuHelper.getWritableDatabase();
		ContentValues v = new ContentValues();
		v.put(KEY_ACTIVITY, activityArray[0]);
		v.put(KEY_TAG, activityArray[1]);
		v.put(KEY_DATE, activityArray[2]);
		v.put(KEY_TIME, activityArray[3]);
		res = _db.update(DB_TABLE, v, KEY_ACTIVITY + "=?", new String[] {activity});
		_db.close();
		return res; // -1失败
	}

	//查找
	public String[] searchActivity(iuOpenHelper iuHelper, String activity) {
		String[] activityArray = new String[4];
		SQLiteDatabase _db = iuHelper.getWritableDatabase();
		Cursor res = _db.query(DB_TABLE, null, KEY_ACTIVITY + "=?",
				new String[] {activity}, null, null, null);
		while (res.moveToNext()) {
			activityArray[0] = res.getString(res.getColumnIndex(KEY_ACTIVITY));
			activityArray[1] = res.getString(res.getColumnIndex(KEY_TAG));
			activityArray[2] = res.getString(res.getColumnIndex(KEY_DATE));
			activityArray[3] = res.getString(res.getColumnIndex(KEY_TIME));
		}
		_db.close();
		return activityArray;
	}
}