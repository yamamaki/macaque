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
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class updateActivity extends Activity {
	final iuOpenHelper iuHelper = new iuOpenHelper(this, iuOpenHelper.DB_TABLE_ACTIVITY, null, 1);
	final idusActivityHelper idusHelper = new idusActivityHelper();
	iuActivity oldActivity = new iuActivity();
	String _activity;
	String update_address;
	int update_tag = 0;
	int update_address_type = 0;
	int requestcode = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.updateactivity);

		Intent gi = getIntent();
		_activity = gi.getStringExtra("_ACTIVITY");

		oldActivity = idusHelper.searchActivity(iuHelper, _activity);

		final Button update_back = (Button) findViewById(R.id.update_back);
		update_back.getBackground().setAlpha(100);
		final Button update_ok = (Button) findViewById(R.id.update_ok);
		update_ok.getBackground().setAlpha(100);
		final EditText update_activity = (EditText) findViewById(R.id.update_activity02);
		final EditText update_address_et = (EditText) findViewById(R.id.update_address02);
		final Button update_map = (Button) findViewById(R.id.update_address03);
		final TextView update_date = (TextView) findViewById(R.id.update_date02);
		final TextView update_time = (TextView) findViewById(R.id.update_time02);
		final SwitchButton update_remind = (SwitchButton) findViewById(R.id.update_remind02);
		final EditText update_remark = (EditText) findViewById(R.id.update_remark02);
		
		// set activity tag
		final Button update_tagDefault = (Button)findViewById(R.id.update_tagDefault);
		final Button update_tagA = (Button) findViewById(R.id.update_tagA);
		final Button update_tagB = (Button) findViewById(R.id.update_tagB);
		final Button update_tagC = (Button) findViewById(R.id.update_tagC);
		final Button update_tagD = (Button) findViewById(R.id.update_tagD);
		update_tagDefault.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				update_tagDefault.setBackgroundResource(R.drawable.selector_activity_all);
				update_tagA.setBackgroundResource(R.color.A);
				update_tagB.setBackgroundResource(R.color.B);
				update_tagC.setBackgroundResource(R.color.C);
				update_tagD.setBackgroundResource(R.color.D);
				update_tag = 0;
			}
		});
		update_tagA.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				update_tagDefault.setBackgroundResource(R.color.All);
				update_tagA.setBackgroundResource(R.drawable.selector_activity_a);
				update_tagB.setBackgroundResource(R.color.B);
				update_tagC.setBackgroundResource(R.color.C);
				update_tagD.setBackgroundResource(R.color.D);
				update_tag = 1;
			}
		});
		update_tagB.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				update_tagDefault.setBackgroundResource(R.color.All);
				update_tagA.setBackgroundResource(R.color.A);
				update_tagB.setBackgroundResource(R.drawable.selector_activity_b);
				update_tagC.setBackgroundResource(R.color.C);
				update_tagD.setBackgroundResource(R.color.D);
				update_tag = 2;
			}
		});
		update_tagC.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				update_tagDefault.setBackgroundResource(R.color.All);
				update_tagA.setBackgroundResource(R.color.A);
				update_tagB.setBackgroundResource(R.color.B);
				update_tagC.setBackgroundResource(R.drawable.selector_activity_c);
				update_tagD.setBackgroundResource(R.color.D);
				update_tag = 3;
			}
		});
		update_tagD.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				update_tagDefault.setBackgroundResource(R.color.All);
				update_tagA.setBackgroundResource(R.color.A);
				update_tagB.setBackgroundResource(R.color.B);
				update_tagC.setBackgroundResource(R.color.C);
				update_tagD.setBackgroundResource(R.drawable.selector_activity_d);
				update_tag = 4;
			}
		});

		Calendar cal = Calendar.getInstance();
		final int year = cal.get(Calendar.YEAR);
		final int monthOfYear = cal.get(Calendar.MONTH);
		final int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		final int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);
		final int minute = cal.get(Calendar.MINUTE);

		update_activity.setText(oldActivity.getActivity());
		// update_tag.setText(oldActivity.getTag());
		update_tag = oldActivity.getTag();
		update_address_type =oldActivity.getAddress_type();
		update_address = oldActivity.getAddress();
		Log.v("ok",""+update_address_type);
		update_map.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(updateActivity.this, Map.class);
					startActivityForResult(i, requestcode);
					updateActivity.this.overridePendingTransition(R.anim.zoomin,
							R.anim.zoomout);
				}
			});
		update_address_et.setText(oldActivity.getAddress());
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
				updateActivity.this.overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
				finish();
			}
		});
		
		update_ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				iuActivity newActivity = new iuActivity();
				newActivity.setIUActivity(update_activity.getText().toString(), 
						update_tag, update_address,update_address_type,
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
					updateActivity.this.overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
					finish();
				}
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
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data != null) {
			String position = data.getStringExtra("position");
			update_address = position;
			TextView tv1 = (TextView)findViewById(R.id.update_address01);
			EditText et = (EditText) findViewById(R.id.update_address02);
			Button bn = (Button) findViewById(R.id.update_address03);
			final RelativeLayout rl = (RelativeLayout) findViewById(R.id.update_layout);
			
			final TextView tv = new TextView(this);
			tv.setText(position);
			tv.setTextSize(18f);
			tv.setPadding(10, 10, 10, 0);
			tv.setGravity(Gravity.RIGHT);
			tv.setWidth(600);
			tv.setClickable(true);
			tv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent i = new Intent(updateActivity.this, Map.class);			
					startActivityForResult(i, requestcode);
					rl.removeView(tv);
				}
			});
			rl.removeView(et);
			rl.removeView(bn);
			rl.addView(tv);	
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
			tv1.setPadding(15, 10, 0, 0);
			tv1.setLayoutParams(params);
			params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.RIGHT_OF, tv1.getId());
			tv.setLayoutParams(params);
		}
	}
}
