package com.example.d2y;

import java.util.Calendar;

import com.example.d2y.register.iuHttpPostTask;

import me.imid.view.SwitchButton;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
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

public class addActivity extends Activity {

	final iuOpenHelper iuHelper = new iuOpenHelper(this,
			iuOpenHelper.DB_TABLE_ACTIVITY, null, 1);
	final idusActivityHelper idusHelper = new idusActivityHelper();
	final iuHttpHelper iuHttp = new iuHttpHelper();
	String Address;
	int add_tag = 0;
	int add_address_type = 0;
	int requestcode = 0;
	String position = "";

	String _username;
	String _sessionid;
	int _localOrNot;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.addactivity);

		final Button add_back = (Button) findViewById(R.id.add_back);
		add_back.getBackground().setAlpha(100);
		final Button add_ok = (Button) findViewById(R.id.add_ok);
		add_ok.getBackground().setAlpha(100);
		final EditText add_activity = (EditText) findViewById(R.id.add_activity02);
		final EditText add_address = (EditText) findViewById(R.id.add_address02);
		final Button add_map = (Button) findViewById(R.id.add_address03);
		final TextView add_date = (TextView) findViewById(R.id.add_date02);
		final TextView add_time = (TextView) findViewById(R.id.add_time02);
		final SwitchButton add_remind = (SwitchButton) findViewById(R.id.add_remind02);
		final EditText add_remark = (EditText) findViewById(R.id.add_remark02);

		Intent gi = getIntent();
		_localOrNot = gi.getExtras().getInt("_LOCALORNOT");
		_username = gi.getStringExtra("_USERNAME");
		if (_localOrNot == 1) {
			_sessionid = gi.getStringExtra("_SESSIONID");
		}

		// set activity tag

		final Button add_tagDefault = (Button) findViewById(R.id.add_tagDefault);
		final Button add_tagA = (Button) findViewById(R.id.add_tagA);
		final Button add_tagB = (Button) findViewById(R.id.add_tagB);
		final Button add_tagC = (Button) findViewById(R.id.add_tagC);
		final Button add_tagD = (Button) findViewById(R.id.add_tagD);
		add_tagDefault.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				add_tagDefault.setBackgroundResource(R.drawable.selector_activity_all);
				add_tagA.setBackgroundResource(R.color.A);
				add_tagB.setBackgroundResource(R.color.B);
				add_tagC.setBackgroundResource(R.color.C);
				add_tagD.setBackgroundResource(R.color.D);
				add_tag = 0;
			}
		});
		add_tagA.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				add_tagDefault.setBackgroundResource(R.color.All);
				add_tagA.setBackgroundResource(R.drawable.selector_activity_a);
				add_tagB.setBackgroundResource(R.color.B);
				add_tagC.setBackgroundResource(R.color.C);
				add_tagD.setBackgroundResource(R.color.D);
				add_tag = 1;
			}
		});
		add_tagB.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				add_tagDefault.setBackgroundResource(R.color.All);
				add_tagA.setBackgroundResource(R.color.A);
				add_tagB.setBackgroundResource(R.drawable.selector_activity_b);
				add_tagC.setBackgroundResource(R.color.C);
				add_tagD.setBackgroundResource(R.color.D);
				add_tag = 2;
			}
		});
		add_tagC.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				add_tagDefault.setBackgroundResource(R.color.All);
				add_tagA.setBackgroundResource(R.color.A);
				add_tagB.setBackgroundResource(R.color.B);
				add_tagC.setBackgroundResource(R.drawable.selector_activity_c);
				add_tagD.setBackgroundResource(R.color.D);
				add_tag = 3;
			}
		});
		add_tagD.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				add_tagDefault.setBackgroundResource(R.color.All);
				add_tagA.setBackgroundResource(R.color.A);
				add_tagB.setBackgroundResource(R.color.B);
				add_tagC.setBackgroundResource(R.color.C);
				add_tagD.setBackgroundResource(R.drawable.selector_activity_d);
				add_tag = 4;
			}
		});

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
				i.putExtra("_USERNAME", _username);
				i.putExtra("_LOCALORNOT", _localOrNot);
				if (_localOrNot == 1) {
					i.putExtra("_SESSIONID", _sessionid);
				}
				setResult(RESULT_CANCELED, i);
				addActivity.this.overridePendingTransition(R.anim.zoomin,
						R.anim.zoomout);
				finish();
			}
		});

		add_ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				iuActivity newActivity = new iuActivity();
				if (position == "") {
					Address = add_address.getText().toString();
					add_address_type = 0;
				} else {
					Address = position;
					add_address_type = 1;
				}
				Log.v("ok", "" + add_address_type);
				newActivity
						.setIUActivity(add_activity.getText().toString(),
								add_tag, Address, add_address_type, add_date
										.getText().toString(), add_time
										.getText().toString(), add_remind
										.isChecked(), add_remark.getText()
										.toString());
				if (newActivity.getActivity().equals("")) {
					Toast.makeText(addActivity.this, "对不起，请将活动填写完整",
							Toast.LENGTH_LONG).show();
				} else {
					long res = idusHelper.insertActivity(iuHelper, newActivity);
					if (res == -1) {
						Toast.makeText(addActivity.this, "添加活动失败！",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(addActivity.this, "添加活动成功！",
								Toast.LENGTH_SHORT).show();
					}
					iuHttpPostTask postTask = new iuHttpPostTask();
					postTask.execute(new String[] {
							add_activity.getText().toString(),
							add_address.getText().toString(), "",
							add_remark.getText().toString(), "", "2013", "2013", "07",
							"07", "02", "10", _sessionid });

/*					Intent i = new Intent();
					i.putExtra("_USERNAME", _username);
					i.putExtra("_LOCALORNOT", _localOrNot);
					if (_localOrNot == 1) {
						i.putExtra("_SESSIONID", _sessionid);
					}
					setResult(RESULT_OK, i);
					addActivity.this.overridePendingTransition(R.anim.zoomin,
							R.anim.zoomout);
					finish();*/
				}
			}
		});

		add_map.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(addActivity.this, Map.class);
				startActivityForResult(i, requestcode);
				addActivity.this.overridePendingTransition(R.anim.zoomin,
						R.anim.zoomout);
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
							public void onDateSet(DatePicker view, int y,
									int m, int d) {
								add_date.setText(y + "-" + (m + 1) + "-" + d);
							}
						}, year, monthOfYear, dayOfMonth);
				add_datedialog.show();
			}
		});

		// 添加add_time的点击事件
		add_time.setText(hourOfDay + ":" + minute);
		add_time.setClickable(true);
		add_time.setFocusable(true);
		add_time.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Dialog add_timedialog = new TimePickerDialog(addActivity.this,
						new OnTimeSetListener() {
							@Override
							public void onTimeSet(TimePicker view, int h, int m) {
								add_time.setText(h + ":" + m);
							}
						}, hourOfDay, minute, true);
				add_timedialog.show();
			}
		});
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data != null) {
			position = data.getStringExtra("position");
			TextView tv1 = (TextView) findViewById(R.id.add_address01);
			EditText et = (EditText) findViewById(R.id.add_address02);
			Button bn = (Button) findViewById(R.id.add_address03);
			final RelativeLayout rl = (RelativeLayout) findViewById(R.id.rladdinfo);

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
					Intent i = new Intent(addActivity.this, Map.class);
					startActivityForResult(i, requestcode);
					rl.removeView(tv);
				}
			});
			rl.removeView(et);
			rl.removeView(bn);
			rl.addView(tv);
			RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.ALIGN_PARENT_LEFT,
					RelativeLayout.TRUE);
			tv1.setPadding(15, 10, 0, 0);
			tv1.setLayoutParams(params);
			params = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.WRAP_CONTENT,
					RelativeLayout.LayoutParams.WRAP_CONTENT);
			params.addRule(RelativeLayout.RIGHT_OF, tv1.getId());
			tv.setLayoutParams(params);
		}
	}

	public class iuHttpPostTask extends AsyncTask<String, String, String> {
		String resBundle = "";
		
		// @Override
		protected String doInBackground(String... params) {
			try {
				resBundle = iuHttp.iuPushActivity(new String[] { "atyname",
						"atyaddress", "atytype", "atycontent", "atymaxmember",
						"beginyear", "endyear", "beginmonth", "endmonth",
						"beginday", "endday", "sessionid" },
						new String[] { params[0], params[1], params[2],
								params[3], params[4], params[5], params[6],
								params[7], params[8], params[9], params[10], params[11] });
				Log.v("tag", "beiju "+resBundle);
			} catch (Exception e) {
				resBundle = "";
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(String res) {
			System.out.println(resBundle);
			if (resBundle.equals("true")) {
				Toast.makeText(addActivity.this, "发布成功!", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(addActivity.this, "发布失败!", Toast.LENGTH_SHORT)
						.show();
			}
			Intent i = new Intent();
			i.putExtra("_USERNAME", _username);
			i.putExtra("_LOCALORNOT", _localOrNot);
			if (_localOrNot == 1) {
				i.putExtra("_SESSIONID", _sessionid);
			}
			setResult(RESULT_OK, i);
			addActivity.this.overridePendingTransition(R.anim.zoomin,
					R.anim.zoomout);
			finish();
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onProgressUpdate(String... values) {
		}
	}

}
