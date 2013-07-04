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

public class register extends Activity {
	iuHttpHelper iuHttp = new iuHttpHelper();
	final iuOpenHelper iuHelper = new iuOpenHelper(this,
			iuOpenHelper.DB_TABLE_USER, null, 1);
	final idusUserHelper idusHelper = new idusUserHelper();
	iuUser localUser = new iuUser();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register);
		Button nex = (Button) findViewById(R.id.reg_next);
		nex.getBackground().setAlpha(100);
		final EditText reg_email = (EditText) findViewById(R.id.reg_email02);
		reg_email.getBackground().setAlpha(100);
		final EditText reg_pw = (EditText) findViewById(R.id.reg_pw02);
		reg_pw.getBackground().setAlpha(100);
		final EditText reg_rpw = (EditText) findViewById(R.id.reg_pw04);
		reg_rpw.getBackground().setAlpha(100);
		nex.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				iuHttpPostTask postTask = new iuHttpPostTask();
				postTask.execute(new String[] {
						reg_email.getText().toString(),
						reg_pw.getText().toString(),
						reg_rpw.getText().toString() });
			}
		});
	}

	public class iuHttpPostTask extends AsyncTask<String, String, String> {
		String[] resBundle = {"", ""};
		
		// @Override
		protected String doInBackground(String... params) {
			try {
				resBundle = iuHttp.iuRegister(new String[] { "email", "password",
						"repeatpassword" }, new String[] { params[0],
						params[1], params[2] });
				Log.v("tag",resBundle[0]);
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
				Toast.makeText(register.this, "注册成功!", Toast.LENGTH_SHORT)
						.show();
				String validUsername = idusHelper.searchUser(iuHelper,
						localUser.getUsername()).getUsername();
				if (validUsername.equals("")) {
					if (idusHelper.insertUser(iuHelper, localUser) > 0) {
						System.out.println("insert");
					}
				} else {
					idusHelper.updateUser(iuHelper, localUser,
							localUser.getUsername());
					System.out.println("update");
				}
				Intent i = new Intent(register.this, login.class);
				startActivity(i);
				register.this.overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			} else if (resBundle[0].equals("sqlerror")) {
				Toast.makeText(register.this, "该Email已被注册!", Toast.LENGTH_SHORT)
						.show();
			} else if (resBundle[0].equals("passwordinvaild")) {
				Toast.makeText(register.this, "两次输入密码不一样", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(register.this, "网络连接失败！", Toast.LENGTH_SHORT)
						.show();
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