package com.example.d2y;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.Menu;
import android.view.Window;

public class iu extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.init);
        new Thread() {
        	@Override
        	public void run() {
        		SystemClock.sleep(1000);
        		mHandler.sendEmptyMessage(0);
        	}
        }.start();
    }
    
    Handler mHandler = new Handler() {
	    public void handleMessage(Message msg) {
	    	if (msg.what == 0) {
	    		Intent i = new Intent(iu.this, login.class);
	    		startActivity(i);
	    		finish();
	    	}
	    }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
