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

public class regNext extends Activity {
	iuHttpHelper iuHttp = new iuHttpHelper();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.regnext);
		Button fin = (Button) findViewById(R.id.reg_fin);
		final EditText reg_name = (EditText) findViewById(R.id.reg_name02);
		final EditText reg_place = (EditText) findViewById(R.id.reg_place02);
		final EditText reg_phone = (EditText) findViewById(R.id.reg_phone02);
		fin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				iuHttpPostTask postTask = new iuHttpPostTask();
				postTask.execute(new String[] {
						"http://iiyouyou.com/i/setting.php",
						reg_name.getText().toString(),
						reg_place.getText().toString(),
						reg_phone.getText().toString() });
			}
		});
	}

	public class iuHttpPostTask extends AsyncTask<String, String, String> {
		String[] resBundle = new String[2];

		@Override
		protected String doInBackground(String... params) {
			try {
				resBundle = iuHttp.iuSetting(new String[] { "name",
						"place", "phone" }, new String[] {
						params[0], params[1], params[2] });
				return resBundle[0];
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
				Toast.makeText(regNext.this, "填写信息成功!", Toast.LENGTH_SHORT)
						.show();
				Intent i = new Intent(regNext.this, login.class);
				startActivity(i);
				regNext.this.overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
			} else {
				Toast.makeText(regNext.this, "填写信息失败!", Toast.LENGTH_SHORT)
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