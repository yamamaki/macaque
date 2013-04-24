package com.example.d2y;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

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

public class addActivity extends Activity {

	final iuOpenHelper iuHelper = new iuOpenHelper(this, iuOpenHelper.DB_TABLE,
			null, 1);
	final idusActivityHelper idusHelper = new idusActivityHelper();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.addactivity);
		Button add_back = (Button) findViewById(R.id.add_back);
		Button add_ok = (Button) findViewById(R.id.add_ok);

		Calendar cal = Calendar.getInstance();

		// 定义DatePicker
		DatePicker dp = (DatePicker) findViewById(R.id.add_datepicker);
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

		// 定义TimePicker
		TimePicker tp = (TimePicker) findViewById(R.id.add_timepicker);
		int hour = cal.get(Calendar.HOUR);
		int minute = cal.get(Calendar.MINUTE);
		tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker view, int hour, int minute) {
				flushTime(hour, minute);
			}
		});

		// 定义控件变量
		final EditText add_activity = (EditText) findViewById(R.id.add_activity02);
		final EditText add_tag = (EditText) findViewById(R.id.add_tag02);
		final EditText add_address = (EditText) findViewById(R.id.add_address02);
		final TextView add_date = (TextView) findViewById(R.id.add_date02);
		final TextView add_time = (TextView) findViewById(R.id.add_time02);
		final EditText add_remind = (EditText) findViewById(R.id.add_remind02);
		final EditText add_remark = (EditText) findViewById(R.id.add_remark02);

		// 添加add_date的点击事件
		add_date.setClickable(true);
		add_date.setFocusable(true);
		add_date.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		// 获取当前日期
		DateFormat date = DateFormat.getDateInstance();
		add_date.setText(date.format(new Date()));

		// 获取当前时间
		DateFormat time = DateFormat.getTimeInstance(DateFormat.SHORT);
		add_time.setText(time.format(new Date()));

		add_back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				setResult(RESULT_CANCELED, i);
				finish();
			}
		});
		add_ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				iuActivity addActivity = new iuActivity();
				addActivity._activity = add_activity.getText().toString();
				addActivity._tag = add_tag.getText().toString();
				addActivity._address = add_address.getText().toString();
				addActivity._date = add_date.getText().toString();
				addActivity._time = add_time.getText().toString();
//				 addActivity._remind =Integer.parseInt(add_remind.getText().toString());
				addActivity._remark = add_remark.getText().toString();

				if (addActivity._activity.equals("")) {
					Toast.makeText(addActivity.this, "对不起，请将活动填写完整",
							Toast.LENGTH_LONG).show();
				} 
				else {
					long res = idusHelper.insertActivity(iuHelper, addActivity);
					if (res == -1) {
						Toast.makeText(addActivity.this, "添加活动失败！",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(addActivity.this, "添加活动成功！",
								Toast.LENGTH_SHORT).show();
					}
					Intent i = new Intent();
					setResult(RESULT_OK, i);
					finish();
					}
			}
		});
	}

	// 刷新add_date所显示的内容
	public void flushDate(int year, int monthOfYear, int dayOfMonth) {
		final TextView add_date = (TextView) findViewById(R.id.add_date02);
		add_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
	}

	// 刷新add_time所显示的内容
	public void flushTime(int hour, int minute) {
		final TextView add_time = (TextView) findViewById(R.id.add_time02);
		add_time.setText(hour + ":" + minute);
	}
}
