package com.example.d2y;

import static com.example.d2y.iuOpenHelper.*;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class idusActivityHelper {
	// 构造函数
	public idusActivityHelper() {
	}

	// 根据标签获取所有活动
	public iuActivity[] getActivities(iuOpenHelper helper, int tag) {
		SQLiteDatabase _db = helper.getWritableDatabase();
		Cursor c;
		if (tag != 0) {
			c = _db.query(DB_TABLE_ACTIVITY, new String[] { KEY_ACTIVITY,
					KEY_TAG, KEY_ADDRESS, KEY_ADDRESS_TYPE,KEY_DATE, KEY_TIME, KEY_REMIND,
					KEY_REMARK }, "_tag=?",
					new String[] { String.valueOf(tag) }, null, null, null);
		} else {
			c = _db.query(DB_TABLE_ACTIVITY, new String[] { KEY_ACTIVITY,
					KEY_TAG, KEY_ADDRESS, KEY_ADDRESS_TYPE,KEY_DATE, KEY_TIME, KEY_REMIND,
					KEY_REMARK }, null, null, null, null, null);
		}
		int activityIndex = c.getColumnIndex(KEY_ACTIVITY);
		int tagIndex = c.getColumnIndex(KEY_TAG);
		int addressIndex = c.getColumnIndex(KEY_ADDRESS);
		int addresstypeIndex = c.getColumnIndex(KEY_ADDRESS_TYPE);
		int dateIndex = c.getColumnIndex(KEY_DATE);
		int timeIndex = c.getColumnIndex(KEY_TIME);
		int remindIndex = c.getColumnIndex(KEY_REMIND);
		int remarkIndex = c.getColumnIndex(KEY_REMARK);
		iuActivity[] iuActivity = new iuActivity[c.getCount()];
		int i = 0;
		for (c.moveToFirst(); !(c.isAfterLast()); c.moveToNext()) {
			iuActivity[i] = new iuActivity();
			iuActivity[i].setIUActivity(c.getString(activityIndex),
					c.getInt(tagIndex), c.getString(addressIndex),
					c.getInt(addresstypeIndex),
					c.getString(dateIndex), c.getString(timeIndex),
					c.getInt(remindIndex), c.getString(remarkIndex));
			++i;
		}
		c.close();
		_db.close();
		return iuActivity;
	}

	// 插入
	public long insertActivity(iuOpenHelper iuHelper, iuActivity iuActivity) {
		long res;
		SQLiteDatabase _db = iuHelper.getWritableDatabase();
		ContentValues v = new ContentValues();
		v.put(KEY_ACTIVITY, iuActivity.getActivity());
		v.put(KEY_TAG, iuActivity.getTag());
		v.put(KEY_ADDRESS, iuActivity.getAddress());
		v.put(KEY_DATE, iuActivity.getDate());
		v.put(KEY_TIME, iuActivity.getTime());
		v.put(KEY_REMIND, iuActivity.getRemind());
		v.put(KEY_REMARK, iuActivity.getRemark());
		res = _db.insert(DB_TABLE_ACTIVITY, null, v);
		_db.close();
		return res;
	}

	// 删除
	public long deleteActivity(iuOpenHelper iuHelper, String activity) {
		long res;
		SQLiteDatabase _db = iuHelper.getWritableDatabase();
		res = _db.delete(DB_TABLE_ACTIVITY, KEY_ACTIVITY + "=?",
				new String[] { activity });
		_db.close();
		return res;
	}

	// 更新
	public long updateActivity(iuOpenHelper iuHelper, iuActivity iuActivity,
			String activity) {
		long res;
		SQLiteDatabase _db = iuHelper.getWritableDatabase();
		ContentValues v = new ContentValues();
		v.put(KEY_ACTIVITY, iuActivity.getActivity());
		v.put(KEY_TAG, iuActivity.getTag());
		v.put(KEY_ADDRESS, iuActivity.getAddress());
		v.put(KEY_DATE, iuActivity.getDate());
		v.put(KEY_TIME, iuActivity.getTime());
		v.put(KEY_REMIND, iuActivity.getRemind());
		v.put(KEY_REMARK, iuActivity.getRemark());
		res = _db.update(DB_TABLE_ACTIVITY, v, KEY_ACTIVITY + "=?",
				new String[] { activity });
		_db.close();
		return res;
	}

	// 查找
	public iuActivity searchActivity(iuOpenHelper iuHelper, String activity) {
		iuActivity iuActivity = new iuActivity();
		SQLiteDatabase _db = iuHelper.getWritableDatabase();
		Cursor res = _db.query(DB_TABLE_ACTIVITY, null, KEY_ACTIVITY + "=?",
				new String[] { activity }, null, null, null);
		while (res.moveToNext()) {
			iuActivity.setIUActivity(
					res.getString(res.getColumnIndex(KEY_ACTIVITY)),
					res.getInt(res.getColumnIndex(KEY_TAG)),
					res.getString(res.getColumnIndex(KEY_ADDRESS)),
					res.getInt(res.getColumnIndex(KEY_ADDRESS_TYPE)),
					res.getString(res.getColumnIndex(KEY_DATE)),
					res.getString(res.getColumnIndex(KEY_TIME)),
					res.getInt(res.getColumnIndex(KEY_REMIND)),
					res.getString(res.getColumnIndex(KEY_REMARK)));
		}
		_db.close();
		return iuActivity;
	}
}