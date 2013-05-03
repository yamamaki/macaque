package com.example.d2y;

import java.util.Calendar;

import me.imid.view.SwitchButton;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class updateActivity extends Activity {
	final iuOpenHelper iuHelper = new iuOpenHelper(this, iuOpenHelper.DB_TABLE_ACTIVITY, null, 1);
	final idusActivityHelper idusHelper = new idusActivityHelper();
	iuActivity oldActivity = new iuActivity();
	String _activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.updateactivity);

		Intent gi = getIntent();
		_activity = gi.getStringExtra("_ACTIVITY");

		oldActivity = idusHelper.searchActivity(iuHelper, _activity);

		final Button update_back = (Button) findViewById(R.id.update_back);
		final Button update_ok = (Button) findViewById(R.id.update_ok);
		final EditText update_activity = (EditText) findViewById(R.id.update_activity02);
		final EditText update_tag = (EditText) findViewById(R.id.update_tag02);
		final EditText update_address = (EditText) findViewById(R.id.update_address02);
		final Button update_map = (Button) findViewById(R.id.update_address03);
		final TextView update_date = (TextView) findViewById(R.id.update_date02);
		final TextView update_time = (TextView) findViewById(R.id.update_time02);
		final SwitchButton update_remind = (SwitchButton) findViewById(R.id.update_remind02);
		final EditText update_remark = (EditText) findViewById(R.id.update_remark02);
		
		Calendar cal = Calendar.getInstance();
		final int year = cal.get(Calendar.YEAR);
		final int monthOfYear = cal.get(Calendar.MONTH);
		final int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		final int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
		final int minute = cal.get(Calendar.MINUTE);

		update_activity.setText(oldActivity.getActivity());
		update_tag.setText(oldActivity.getTag());
		update_address.setText(oldActivity.getAddress());
		update_date.setText(oldActivity.getDate());
		update_time.setText(oldActivity.getTime());
		if (oldActivity.getRemind() == 1) update_remind.setChecked(true);
		else update_remind.setChecked(false);
		update_remark.setText(oldActivity.getRemark());
		
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
				iuActivity newActivity = new iuActivity();
				newActivity.setIUActivity(update_activity.getText().toString(), 
						update_tag.getText().toString(), update_address.getText().toString(),
						update_date.getText().toString(), update_time.getText().toString(), 
						update_remind.isChecked(), update_remark.getText().toString());
				
				if (newActivity.getActivity().equals("")) {
					Toast.makeText(updateActivity.this, "对不起，请将活动填写完整",
							Toast.LENGTH_SHORT).show();
				} else {
					long res = idusHelper.updateActivity(iuHelper, newActivity, _activity);
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
		
		update_map.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(updateActivity.this, mapdemo.class);
				startActivity(i);
			}
		});
		
		// 添加update_date的点击事件
		update_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
		update_date.setClickable(true);
		update_date.setFocusable(true);
		update_date.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Dialog update_datedialog = new DatePickerDialog(updateActivity.this,
						new DatePickerDialog.OnDateSetListener() {
							@Override
							public void onDateSet(DatePicker view, int y, int m, int d) {
								update_date.setText(y + "-" + (m + 1) + "-" + d);
							}
						}, year, monthOfYear, dayOfMonth);
				update_datedialog.show();
			}				
		});
		
		//添加update_time的点击事件
		update_time.setText(hourOfDay+":"+minute);
		update_time.setClickable(true);
		update_time.setFocusable(true);
		update_time.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Dialog update_timedialog = new TimePickerDialog(updateActivity.this, new OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker view, int h, int m) {
						update_time.setText(h+":"+m);
					}
				}, hourOfDay, minute, true);
				update_timedialog.show();
			}				
		});
	}
}
