package com.example.d2y;

public class iuActivity {
	private String _activity = "";
	private int _tag = 0;
	private String _address = "";
	private int _address_type = 0;
	private String _date = "";
	private String _time = "";
	private int _remind = 0;
	private String _remark = "";
	
	public iuActivity() {
		_activity = "";
		_tag = 0;
		_address = "";
		_address_type = 0;
		_date = "";
		_time = "";
		_remind = 0;
		_remark = "";
	}
	
	public iuActivity(String activity, int tag, String address,int ad_type,
			String date, String time, int remind, String remark) {
		_activity = activity;
		_tag = tag;
		_address = address;
		_address_type = ad_type;
		_date = date;
		_time = time;
		_remind = remind;
		_remark = remark;
	}
	
	public void setIUActivity(iuActivity i) {
		_activity = i._activity;
		_tag = i._tag;
		_address = i._address;
		_address_type = i._address_type;
		_date = i._date;
		_time = i._time;
		_remind = i._remind;
		_remark = i._remark;
	}
	
	public void setIUActivity(String a, int tg, String ad,int ad_type, String d, 
			String t, boolean rd, String rk) {
		_activity = a;
		_tag = tg;
		_address = ad;
		_address_type = ad_type;
		_date = d;
		_time = t;
		if (rd) {
			_remind = 1;
		}
		else _remind = 0;
		_remark = rk;
	}
	
	public void setIUActivity(String a, int tg, String ad, int ad_type,String d, 
			String t, int rd, String rk) {
		_activity = a;
		_tag = tg;
		_address = ad;
		_address_type = ad_type;
		_date = d;
		_time = t;
		_remind = rd;
		_remark = rk;
	}
	
	public String getActivity() {
		return _activity;
	}
	
	public int getTag() {
		return _tag;
	}
	
	public String getAddress() {
		return _address;
	}
	
	public int getAddress_type(){
		return _address_type;
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
	
	public void setTag(int tg) {
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

