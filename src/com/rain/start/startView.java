package com.rain.start;


import com.example.myfragment.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class startView extends Activity {
	 @Override  
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.splash);
		setContentView(R.layout.startview);
		Handler x = new Handler();
		x.postDelayed(new splashhandler(), 2000);
		
	}
 
	    class splashhandler implements Runnable{  
	  
	        public void run() {  
	            startActivity(new Intent(getApplication(),LoginActivity.class));  
	            startView.this.finish();  
	        }  
	          
	    }  
}
