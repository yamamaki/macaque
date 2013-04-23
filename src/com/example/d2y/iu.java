package com.example.d2y;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;

public class iu extends Activity {
	private boolean loadLoginLayout = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (!loadLoginLayout) {
        	setContentView(R.layout.init);
        }
        else {
        	setContentView(R.layout.login);
        }
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
