package com.example.myfragment;

import com.rain.db.Constant;
import com.rain.kongjian.ChartView;


import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;

public class fadianliang extends TabActivity {
		
	private RadioGroup group;
	private TabHost tabHost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fadianliang);

		tabHost = this.getTabHost();
		TabHost.TabSpec spec;
		Intent intent;

		// 获取电站列表传过来的id
//		intent=getIntent();
//		String id=intent.getStringExtra("id");
		//intent.putExtra("id", id);


		intent = new Intent().setClass(this, fadianliang_gl.class);
		spec = tabHost.newTabSpec("功率").setIndicator("功率").setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, fadianliang_yfdl.class);
		spec = tabHost.newTabSpec("月发电量").setIndicator("月发电量").setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, fadianliang_nfdl.class);
		spec = tabHost.newTabSpec("年发电量").setIndicator("年发电量").setContent(intent);
		tabHost.addTab(spec);

		tabHost.addTab(spec);
		tabHost.setCurrentTab(1);

		RadioGroup radioGroup = (RadioGroup) this
				.findViewById(R.id.main_tab_group_fdl);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.main_tab_gonglv:// 添加考试
					tabHost.setCurrentTabByTag("功率");
					break;
				case R.id.main_tab_yfdl:// 我的考试
					tabHost.setCurrentTabByTag("月发电量");
					break;
				case R.id.main_tab_nfdl:// 我的通知
					tabHost.setCurrentTabByTag("年发电量");
					break;
				default:
					// tabHost.setCurrentTabByTag("我的考试");
					break;
				}
			}
		});

	}

//    @Override  
//    protected void onCreate(Bundle savedInstanceState) {  
//        super.onCreate(savedInstanceState);   
//        TabHost tabHost = getTabHost();  
//        LayoutInflater.from(this).inflate(R.layout.fadianliang,  
//                tabHost.getTabContentView(), true);  
//        tabHost.addTab(tabHost.newTabSpec("功率").setIndicator("功率", getResources().getDrawable(R.drawable.ic_launcher))  
//                .setContent(R.id.view1));  
//        tabHost.addTab(tabHost.newTabSpec("月发电量").setIndicator("月发电量")  
//                .setContent(R.id.view2));  
//        tabHost.addTab(tabHost.newTabSpec("年发电量").setIndicator("年发电量")  
//                .setContent(R.id.view3));
//        TabWidget tabWidget=tabHost.getTabWidget();
//        for (int i = 0; i < tabWidget.getChildCount(); i++) {//tabWidget.getChildCount()得到tab总数
//        	TextView tv = (TextView) tabWidget.getChildAt(i).findViewById(android.R.id.title);
//            tv.setTextSize(18); 	
//		}//设置tab上的字体大小
//         
//          
//         //标签切换事件处理，setOnTabChangedListener  
//        tabHost.setOnTabChangedListener(new OnTabChangeListener(){    
//            @Override  
//            public void onTabChanged(String tabId) {  
//                if (tabId.equals("功率")) {   //第一个标签 
////                   	ChartView myView=new ChartView(fadianliang.this);
////                	myView.SetInfo(
////                	        new String[]{"7-11","7-12","7-13","7-14","7-15","7-16","7-17"},   //X轴刻度
////                	        new String[]{"","50","100","150","200","250"},   //Y轴刻度
////                	        new String[]{"15","23","10","36","45","40","12"},  //数据
////                	        "图标的标题"
////                	);
//                }  
//                if (tabId.equals("月发电量")) {   //第二个标签  
//                }  
//                if (tabId.equals("年发电量")) {   //第三个标签  
//                }  
//            }              
//        });   
//          
//        
//        TabWidget tw=tabHost.getTabWidget();
//        tw.setBackgroundColor(getResources().getColor(R.color.txt_blue));
//          
//    } 
//    
//	public void onTabChanged(String tabId) {
//		Activity activity = getLocalActivityManager().getActivity(tabId);
//		if (activity != null) {       	
//			activity.onWindowFocusChanged(true);
//		}
//	}
  
}  
