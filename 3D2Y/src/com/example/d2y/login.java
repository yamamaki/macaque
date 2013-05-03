package com.example.d2y;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends Activity {
	iuHttpHelper iuHttp = new iuHttpHelper();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		final Button login = (Button) findViewById(R.id.login);
		final Button register = (Button) findViewById(R.id.reg);
		final EditText login_sn = (EditText) findViewById(R.id.login_sn02);
		final EditText login_pw = (EditText) findViewById(R.id.login_pw02);
		
		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				iuHttpPostTask postTask = new iuHttpPostTask(); 
				postTask.execute(new String[] {"http://iiyouyou.com/i/loginDb.php",
					login_sn.getText().toString(),
					login_pw.getText().toString()});
			}
		});
		register.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent(login.this, register.class);
				startActivity(i);
			}
		});
	}
	
	public class iuHttpPostTask extends AsyncTask<String, String, String>{
		String res = "";  
		@Override
		protected String doInBackground(String... params) {
			try {
				res = iuHttp.requestByPost(params[0], new String[] {"email", "password" }, 
						new String[] {params[1], params[2]});
				return res;
			} catch (Exception e) {
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
			if (res.equals("true")) {
				Toast.makeText(login.this, "登录成功!", Toast.LENGTH_SHORT)
						.show();
				Intent i = new Intent(login.this, toDoList.class);
				startActivity(i);
			}
			if (res.equals("nouser")) {
				Toast.makeText(login.this, "该用户不存在！", Toast.LENGTH_SHORT)
						.show();
			}
			if (res.equals("passworderror")) {
				Toast.makeText(login.this, "密码错误！", Toast.LENGTH_SHORT)
						.show();
			}
			if (res.equals("weberror")) {
				Toast.makeText(login.this, "网络连接失败！", Toast.LENGTH_SHORT)
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
