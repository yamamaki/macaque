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
    	activityArrayList = new String[7][c.getCount()];
    	int i = 0;
    	for (c.moveToFirst(); !(c.isAfterLast()); c.moveToNext()) {
    		activityArrayList[0][i] = c.getString(activityIndex);
    		activityArrayList[1][i] = c.getString(tagIndex);
    		activityArrayList[2][i] = c.getString(addressIndex);
    		activityArrayList[3][i] = c.getString(dateIndex);
    		activityArrayList[4][i] = c.getString(timeIndex);
    		activityArrayList[5][i] = c.getString(remindIndex);
    		activityArrayList[6][i] = c.getString(remarkIndex);
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
		v.put(KEY_ADDRESS, activityArray[2]);
		v.put(KEY_DATE, activityArray[3]);
		v.put(KEY_TIME, activityArray[4]);
		v.put(KEY_REMIND, activityArray[5]);
		v.put(KEY_REMARK, activityArray[6]);
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
		v.put(KEY_ADDRESS, activityArray[2]);
		v.put(KEY_DATE, activityArray[3]);
		v.put(KEY_TIME, activityArray[4]);
		v.put(KEY_REMIND, activityArray[5]);
		v.put(KEY_REMARK, activityArray[6]);
		res = _db.update(DB_TABLE, v, KEY_ACTIVITY + "=?", new String[] {activity});
		_db.close();
		return res; // -1失败
	}

	//查找
	public String[] searchActivity(iuOpenHelper iuHelper, String activity) {
		String[] activityArray = new String[7];
		SQLiteDatabase _db = iuHelper.getWritableDatabase();
		Cursor res = _db.query(DB_TABLE, null, KEY_ACTIVITY + "=?",
				new String[] {activity}, null, null, null);
		while (res.moveToNext()) {
			activityArray[0] = res.getString(res.getColumnIndex(KEY_ACTIVITY));
			activityArray[1] = res.getString(res.getColumnIndex(KEY_TAG));
			activityArray[2] = res.getString(res.getColumnIndex(KEY_ADDRESS));
			activityArray[3] = res.getString(res.getColumnIndex(KEY_DATE));
			activityArray[4] = res.getString(res.getColumnIndex(KEY_TIME));
			activityArray[5] = res.getString(res.getColumnIndex(KEY_REMIND));
			activityArray[6] = res.getString(res.getColumnIndex(KEY_REMARK));
		}
		_db.close();
		return activityArray;
	}
}