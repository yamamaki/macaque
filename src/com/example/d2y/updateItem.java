package com.example.d2y;

import static com.example.d2y.iuOpenHelper.*;

import java.util.Calendar;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.DatePicker.OnDateChangedListener;;

public class updateItem extends Activity {
	iuOpenHelper iuHelper;
	String [] updateInfo = new String[3];
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.updateitem);
        iuHelper = new iuOpenHelper(this, iuOpenHelper.DB_TABLE, null, 1);
        Intent gi = getIntent();
        final String activity = gi.getStringExtra("_ACTIVITY");
        updateInfo = searchActivity(iuHelper, activity);
        
        Button update_back = (Button)findViewById(R.id.update_back);
        Button update_ok = (Button)findViewById(R.id.update_ok);
        DatePicker dp = (DatePicker)findViewById(R.id.datepicker);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int monthOfYear = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        dp.init(year, monthOfYear, dayOfMonth, new OnDateChangedListener() {
        	@Override
        	public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        		flushDate(year, monthOfYear, dayOfMonth);
        	}
        });
        
        final EditText update_item = (EditText)findViewById(R.id.update_item02);
        update_item.setText(updateInfo[0]);
        final TextView update_date = (TextView)findViewById(R.id.update_date02);
        update_date.setText(updateInfo[1]);
        final EditText update_tag = (EditText)findViewById(R.id.update_tag02);
        update_tag.setText(updateInfo[2]);
        
        update_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				setResult(RESULT_CANCELED, i);
				finish();
			}
		});
        update_ok.setOnClickListener(new View.OnClickListener() {
        	@Override
        	public void onClick(View v) {
        		String [] activityArray = new String[3];
        		activityArray[0] = update_item.getText().toString();
        		activityArray[1] = update_date.getText().toString();
        		activityArray[2] = update_tag.getText().toString();
        		if (activityArray[0].equals("")) {
        			Toast.makeText(updateItem.this, "对不起，请将活动填写完整",
        			Toast.LENGTH_LONG).show();
        		}
        		else {
        			updateActivity(activityArray, activity);
        			Intent i = new Intent();
        			setResult(RESULT_OK, i);
        			finish();
        		}
			}
		});
    } 
    
    //更新活动详细信息到数据库
    public void updateActivity(String [] activityArray, String activity) {
    	SQLiteDatabase _db = iuHelper.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(KEY_ACTIVITY, activityArray[0]);
    	values.put(KEY_DATE, activityArray[1]);
    	values.put(KEY_TAG, activityArray[2]);
    	long count = _db.update(DB_TABLE, values, KEY_ACTIVITY+"=?", new String[] {activity});
    	_db.close();
    	if (count == -1) {
    		Toast.makeText(this, "更新活动失败！", Toast.LENGTH_LONG).show();
    	}
    	else {
    		Toast.makeText(this, "更新活动成功！", Toast.LENGTH_LONG).show();
    	}
    }
    
    //刷新EditText所显示的内容
    public void flushDate(int year, int monthOfYear, int dayOfMonth) {
    	final TextView update_date = (TextView)findViewById(R.id.update_date02);
    	update_date.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
    }
    
    public String[] searchActivity(iuOpenHelper helper, String activity) {
    	SQLiteDatabase _db = helper.getWritableDatabase();
    	String []updateInfo = new String[3];
    	Cursor res = _db.query(DB_TABLE, null, KEY_ACTIVITY + "=?", new String[] {activity}, null, null, null);
    	while(res.moveToNext()) {
    		updateInfo[0] = res.getString(res.getColumnIndex(KEY_ACTIVITY));
    		updateInfo[1] = res.getString(res.getColumnIndex(KEY_DATE));
    		updateInfo[2] = res.getString(res.getColumnIndex(KEY_TAG));
    	}
    	_db.close();
    	return updateInfo;
    }
}
