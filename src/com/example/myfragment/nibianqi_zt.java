package com.example.myfragment;

import com.rain.db.Constant;
import com.rain.kongjian.ChartView;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.MenuItem;

public class nibianqi_zt extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.nibianqi_zt);		

		/* 显示App icon左侧的back键 */
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		 Constant.point = new Point();  
		 getWindowManager().getDefaultDisplay().getSize(Constant.point);  
				
       	ChartView myView=new ChartView(nibianqi_zt.this);
		setContentView(myView);
		myView.SetInfo(new String[] { "7-11", "7-12", "7-13", "7-14", "7-15",
				"7-16", "7-17" }, // X轴刻度
				new String[] { "", "50", "100", "150", "200", "250" }, // Y轴刻度
				new String[] { "15", "23", "10", "36", "45", "40", "12" }, // 数据
				"图标的标题");

	}

	// 顶部返回键
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
