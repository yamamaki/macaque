package com.example.d2y;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.Menu;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

public class iu extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.init);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        LinearInterpolator lir = new LinearInterpolator();  
        anim.setInterpolator(lir);
        findViewById(R.id.init_anim).startAnimation(anim);
        new Thread() {
        	@Override
        	public void run() {
        		SystemClock.sleep(2000);
        		mHandler.sendEmptyMessage(0);
        	}
        }.start();
    }
    
    Handler mHandler = new Handler() {
	    public void handleMessage(Message msg) {
	    	if (msg.what == 0) {
	    		Intent i = new Intent(iu.this, login.class);
	    		startActivity(i);
	    		iu.this.overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
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
