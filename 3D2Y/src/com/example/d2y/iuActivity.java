package com.example.d2y;

public class iuActivity {
	private String _activity = "";
	private String _tag = "";
	private String _address = "";
	private String _date = "";
	private String _time = "";
	private int _remind = 0;
	private String _remark = "";
	
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
	
	public void setIUActivity(iuActivity i) {
		_activity = i._activity;
		_tag = i._tag;
		_address = i._address;
		_date = i._date;
		_time = i._time;
		_remind = i._remind;
		_remark = i._remark;
	}
	
	public void setIUActivity(String a, String tg, String ad, String d, 
			String t, boolean rd, String rk) {
		_activity = a;
		_tag = tg;
		_address = ad;
		_date = d;
		_time = t;
		if (rd) {
			_remind = 1;
		}
		else _remind = 0;
		_remark = rk;
	}
	
	public void setIUActivity(String a, String tg, String ad, String d, 
			String t, int rd, String rk) {
		_activity = a;
		_tag = tg;
		_address = ad;
		_date = d;
		_time = t;
		_remind = rd;
		_remark = rk;
	}
	
	public String getActivity() {
		return _activity;
	}
	
	public String getTag() {
		return _tag;
	}
	
	public String getAddress() {
		return _address;
	}
	
	public String getDate() {
		return _date;
	}
	
	public String getTime() {
		return _time;
	}
	
	public int getRemind() {
		return _remind;
	}
	
	public String getRemark() {
		return _remark;
	}
	
	public void setActivity(String a) {
		_activity = a;
	}
	
	public void setTag(String tg) {
		_tag = tg;
	}
	
	public void setAddress(String ad) {
		_address = ad;
	}
	
	public void setDate(String d) {
		_date = d;
	}
	
	public void setTime(String t) {
		_time = t;
	}
	
	public void setRemind(int rd) {
		_remind = rd;
	}
	
	public void setRemind(boolean rd) {
		if (rd) {
			_remind = 1;
		}
		else _remind = 0;
	}
	
	public void setRemark(String rk) {
		_remark = rk;
	}
}

