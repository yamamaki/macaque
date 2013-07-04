package com.example.d2y;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends Activity {
	iuHttpHelper iuHttp = new iuHttpHelper();
	final iuOpenHelper iuHelper = new iuOpenHelper(this,
			iuOpenHelper.DB_TABLE_USER, null, 1);
	final idusUserHelper idusHelper = new idusUserHelper();
	iuUser localUser = new iuUser();
	int localOrNot = 0; // 0表示local，1表示online

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		final Button login = (Button) findViewById(R.id.login);
		login.getBackground().setAlpha(100);
		final Button register = (Button) findViewById(R.id.reg);
		register.getBackground().setAlpha(100);
		final EditText login_sn = (EditText) findViewById(R.id.login_sn02);
		login_sn.getBackground().setAlpha(100);
		final EditText login_pw = (EditText) findViewById(R.id.login_pw02);
		login_pw.getBackground().setAlpha(100);
		
		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				localUser.setUsername(login_sn.getText().toString());
				localUser.setPassword(login_pw.getText().toString());
				iuHttpPostTask postTask = new iuHttpPostTask();
				postTask.execute(new String[] { login_sn.getText().toString(),
						login_pw.getText().toString() });
			}
		});
		register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(login.this, register.class);
				startActivity(i);
				login.this.overridePendingTransition(R.anim.zoomin,
						R.anim.zoomout);
			}
		});
//		localLogin.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				localUser.setUsername(login_sn.getText().toString());
//				localUser.setPassword(login_pw.getText().toString());
//				System.out.println("l"+localUser.getUsername());
//				System.out.println("l"+localUser.getPassword());
//				iuUser validUser = new iuUser();
//
//				validUser.setUser(idusHelper.searchUser(iuHelper,
//						localUser.getUsername()));
//				System.out.println("v"+validUser.getUsername());
//				System.out.println("v"+validUser.getPassword());
//				if (validUser.getUsername().equals("") || !validUser.getPassword().equals(localUser.getPassword())) {
//					Toast.makeText(login.this, "本地登录失败!", Toast.LENGTH_SHORT)
//							.show();
//				} else {
//					Intent i = new Intent(login.this, toDoList.class);
//					i.putExtra("_USERNAME",
//							localUser.getUsername());
//					startActivity(i);
//					login.this.overridePendingTransition(R.anim.zoomin,
//							R.anim.zoomout);
//				}
//			}
//		});
	}

	public class iuHttpPostTask extends AsyncTask<String, String, String> {
		String[] resBundle = { "", "", ""};

		@Override
		protected String doInBackground(String... params) {
			try {
				resBundle = iuHttp.iuLogin(
						new String[] { "email", "password" }, new String[] {
								params[0], params[1] });
				Log.v("tag", resBundle[0]);
			} catch (Exception e) {
				resBundle[0] = "";
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
			if (resBundle[0].equals("true")) {
				Toast.makeText(login.this, "登录成功!", Toast.LENGTH_SHORT).show();
				localOrNot = 1;
				iuUser validUser = new iuUser();
				validUser = idusHelper.searchUser(iuHelper,
						localUser.getUsername());
				localUser.setSessionid(resBundle[2]);
				if (validUser.getUsername().equals("")) {
					if (idusHelper.insertUser(iuHelper, localUser) > 0) {
						System.out.println("i"+localUser.getUsername());
						System.out.println("i"+localUser.getPassword());
						System.out.println("insert");
					}
				} 
				else {
					idusHelper.updateUser(iuHelper, localUser,
							localUser.getUsername());
					System.out.println("u"+localUser.getUsername());
					System.out.println("u"+localUser.getPassword());
					System.out.println("update");
				}
				System.out.println(resBundle[1]);
				System.out.println(resBundle[2]);
				
				Intent i = new Intent(login.this, newsFeed.class);
				i.putExtra("_USERNAME",
						resBundle[1]);
				i.putExtra("_SESSIONID",
						resBundle[2]);
				i.putExtra("_LOCALORNOT", 1);
				startActivity(i);
				login.this.overridePendingTransition(R.anim.zoomin,
						R.anim.zoomout);
			} else if (resBundle[0].equals("nouser")) {
				Toast.makeText(login.this, "该用户不存在！", Toast.LENGTH_SHORT)
						.show();
			} else if (resBundle[0].equals("passworderror")) {
				Toast.makeText(login.this, "密码错误！", Toast.LENGTH_SHORT).show();
			} else {
//				Toast.makeText(login.this, "网络连接失败！", Toast.LENGTH_SHORT)
//						.show();
				iuUser validUser = new iuUser();
				validUser.setUser(idusHelper.searchUser(iuHelper,
						localUser.getUsername()));
				if (validUser.getUsername().equals("") || !validUser.getPassword().equals(localUser.getPassword())) {
					Toast.makeText(login.this, "登录失败!", Toast.LENGTH_SHORT)
							.show();
				} else {
					Intent i = new Intent(login.this, toDoList.class);
					i.putExtra("_USERNAME",
							localUser.getUsername());
					i.putExtra("_LOCALORNOT", 0);
					startActivity(i);
					login.this.overridePendingTransition(R.anim.zoomin,
							R.anim.zoomout);
				}
			}
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onProgressUpdate(String... values) {
		}
	}

}
