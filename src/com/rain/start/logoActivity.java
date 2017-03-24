package com.rain.start;

import java.util.Timer;
import java.util.TimerTask;

import com.example.myfragment.MainActivity;
import com.example.myfragment.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

public class logoActivity extends Activity{
	private ProgressBar progressBar;
    private Button backButton;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//È¥³ý±êÌâ  
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
        setContentView(R.layout.logo);
        
        
        progressBar=(ProgressBar) findViewById(R.id.pgBar);
        backButton=(Button) findViewById(R.id.btn_back);
        
        Intent intent=new Intent(logoActivity.this,MainActivity.class);
        logoActivity.this.startActivity(intent);
        finish();
        backButton.setOnClickListener(new OnClickListener() {
        	  
            @Override  
            public void onClick(View v) {
                finish(); 
            }
 
        });
		
	}
	
						

}
