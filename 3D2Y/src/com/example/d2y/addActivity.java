package com.example.d2y;

import java.util.Calendar;
import me.imid.view.SwitchButton;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class addActivity extends Activity {

	final iuOpenHelper iuHelper = new iuOpenHelper(this, iuOpenHelper.DB_TABLE, null, 1);
	final idusActivityHelper idusHelper = new idusActivityHelper();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.addactivity);
		
		final Button add_back = (Button) findViewById(R.id.add_back);
		final Button add_ok = (Button) findViewById(R.id.add_ok);
		final EditText add_activity = (EditText) findViewById(R.id.add_activity02);
		final EditText add_tag = (EditText) findViewById(R.id.add_tag02);
		final EditText add_address = (EditText) findViewById(R.id.add_address02);
		final Button add_map = (Button) findViewById(R.id.add_address03);
		final TextView add_date = (TextView) findViewById(R.id.add_date02);
		final TextView add_time = (TextView) findViewById(R.id.add_time02);
		final SwitchButton add_remind = (SwitchButton) findViewById(R.id.add_remind02);
		final EditText add_remark = (EditText) findViewById(R.id.add_remark02);
		
		final Calendar cal = Calendar.getInstance();
		final int year = cal.get(Calendar.YEAR);
		final int monthOfYear = cal.get(Calendar.MONTH);
		final int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		final int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
		final int minute = cal.get(Calendar.MINUTE);

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
				if (add_remind.isChecked()) addActivity._remind = 1;
				else addActivity._remind = 0;
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
		
		add_map.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(addActivity.this, mapdemo.class);
				startActivity(i);
			}
		});
		
		// 添加add_date的点击事件
		add_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
		add_date.setClickable(true);
		add_date.setFocusable(true);
		add_date.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Dialog add_datedialog = new DatePickerDialog(addActivity.this,
						new DatePickerDialog.OnDateSetListener() {
							@Override
							public void onDateSet(DatePicker view, int y, int m, int d) {
								add_date.setText(y + "-" + (m + 1) + "-" + d);
							}
						}, year, monthOfYear, dayOfMonth);
				add_datedialog.show();
			}				
		});
		
		//添加add_time的点击事件
		add_time.setText(hourOfDay+":"+minute);
		add_time.setClickable(true);
		add_time.setFocusable(true);
		add_time.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Dialog add_timedialog = new TimePickerDialog(addActivity.this, new OnTimeSetListener() {
					@Override
					public void onTimeSet(TimePicker view, int h, int m) {
						add_time.setText(h+":"+m);
					}
				}, hourOfDay, minute, true);
				add_timedialog.show();
			}				
		});
	}
}
