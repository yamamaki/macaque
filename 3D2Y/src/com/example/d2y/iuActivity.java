package com.example.d2y;

public class iuActivity {
	public String _activity = "";
	public String _tag = "";
	public String _address = "";
	public String _date = "";
	public String _time = "";
	public int _remind = 0;
	public String _remark = "";
	
	public iuActivity() {
		_activity = "";
		_tag = "";
		_address = "";
		_date = "";
		_time = "";
		_remind = 0;
		_remark = "";
	}
	
	public iuActivity(String activity, String tag, String address, 
			String date, String time, int remind, String remark) {
		_activity = activity;
		_tag = tag;
		_address = address;
		_date = date;
		_time = time;
		_remind = remind;
		_remark = remark;
	}
}