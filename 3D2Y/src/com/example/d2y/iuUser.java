package com.example.d2y;

public class iuUser {
	private long _id = 0;
	private String _username = "";
	private String _email = "";
	private String _password = "";
	private String _sessionid = "";
	
	public iuUser() {
		_id = 0;
		_username = "";
		_password = "";
		_email = "";
		_sessionid = "";
	} 
	
	public long getId() {
		return _id;
	}
	
	public String getUsername() {
		return _username;
	}
	
	public String getPassword() {
		return _password;
	}
	
	public String getEmail() {
		return _email;
	}
	
	public String getSessionid() {
		return _sessionid;
	}
	
	public void setUser(iuUser u) {
		_id = u.getId();
		_username = u.getUsername();
		_password = u.getPassword();
		_email = u.getEmail();
		_sessionid = u.getSessionid();
	}
	
	public void setUsername(String sn) {
		_username = sn;
	}
	
	public void setPassword(String pw) {
		_password = pw;
	}
	
	public void setEmail(String email) {
		_email = email;
	}	
	
	public void setSessionid(String sessionid) {
		_sessionid = sessionid;
	}
}
	
	