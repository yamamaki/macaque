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

public class register extends Activity {
	iuHttpHelper iuHttp = new iuHttpHelper();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register);
		Button nex = (Button) findViewById(R.id.reg_next);
		final EditText reg_email = (EditText) findViewById(R.id.reg_email02);
		final EditText reg_pw = (EditText) findViewById(R.id.reg_pw02);
		final EditText reg_rpw = (EditText) findViewById(R.id.reg_pw04);
		nex.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				iuHttpPostTask postTask = new iuHttpPostTask();
				postTask.execute(new String[] {
						"http://iiyouyou.com/i/signUpDb.php",
						reg_email.getText().toString(),
						reg_pw.getText().toString(),
						reg_rpw.getText().toString() });
			}
		});
	}

	public class iuHttpPostTask extends AsyncTask<String, String, String> {
		String res = "";

		@Override
		protected String doInBackground(String... params) {
			try {
				res = iuHttp.requestByPost(params[0], new String[] { "email",
						"password", "repeatpassword" }, new String[] {
						params[1], params[2], params[3] });
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
				Toast.makeText(register.this, "注册成功!", Toast.LENGTH_SHORT)
						.show();
				Intent i = new Intent(register.this, login.class);
				startActivity(i);
			} else {
				Toast.makeText(register.this, "注册失败!", Toast.LENGTH_SHORT)
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