package com.example.d2y;

import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.DatePicker.OnDateChangedListener;

;

public class updateActivity extends Activity {
	final iuOpenHelper iuHelper = new iuOpenHelper(this, iuOpenHelper.DB_TABLE,
			null, 1);
	final idusActivityHelper idusHelper = new idusActivityHelper();
	String[] updateActivityArray = new String[4];
	String _activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.updateactivity);

		Intent gi = getIntent();
		_activity = gi.getStringExtra("_ACTIVITY");

		updateActivityArray = idusHelper.searchActivity(iuHelper, _activity);

		Button update_back = (Button) findViewById(R.id.update_back);
		Button update_ok = (Button) findViewById(R.id.update_ok);
		DatePicker dp = (DatePicker) findViewById(R.id.datepicker);
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int monthOfYear = cal.get(Calendar.MONTH);
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		dp.init(year, monthOfYear, dayOfMonth, new OnDateChangedListener() {
			@Override
			public void onDateChanged(DatePicker view, int year,
					int monthOfYear, int dayOfMonth) {
				flushDate(year, monthOfYear, dayOfMonth);
			}
		});
		
		TimePicker tp = (TimePicker) findViewById(R.id.timepicker);
		int hour = cal.get(Calendar.HOUR);
		int minute = cal.get(Calendar.MINUTE);
		tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker view, int hour, int minute) {
				flushTime(hour, minute);
			}
		});

		final EditText update_item = (EditText) findViewById(R.id.update_item02);
		final EditText update_tag = (EditText) findViewById(R.id.update_tag02);
		final TextView update_date = (TextView) findViewById(R.id.update_date02);
		final TextView update_time = (TextView) findViewById(R.id.update_time02);
		
		update_item.setText(updateActivityArray[0]);
		update_tag.setText(updateActivityArray[1]);
		update_date.setText(updateActivityArray[2]);
		update_time.setText(updateActivityArray[3]);
		
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
				String[] newActivityArray = new String[4];
				newActivityArray[0] = update_item.getText().toString();
				newActivityArray[1] = update_tag.getText().toString();
				newActivityArray[2] = update_date.getText().toString();
				newActivityArray[3] = update_time.getText().toString();
				if (newActivityArray[0].equals("")) {
					Toast.makeText(updateActivity.this, "对不起，请将活动填写完整",
							Toast.LENGTH_SHORT).show();
				} else {
					long res = idusHelper.updateActivity(iuHelper,
							newActivityArray, _activity);
					if (res == -1) {
						Toast.makeText(updateActivity.this, "更新活动失败！",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(updateActivity.this, "更新活动成功！",
								Toast.LENGTH_SHORT).show();
					}
					Intent i = new Intent();
					setResult(RESULT_OK, i);
					finish();
				}
			}
		});
	}

	// 刷新EditText所显示的内容
	public void flushDate(int year, int monthOfYear, int dayOfMonth) {
		final TextView update_date = (TextView) findViewById(R.id.update_date02);
		update_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
	}

	// 刷新add_time所显示的内容
	public void flushTime(int hour, int minute) {
		final TextView add_time = (TextView) findViewById(R.id.update_time02);
		add_time.setText(hour + ":" + minute);
	}

}
