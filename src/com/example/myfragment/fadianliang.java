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

		// ��ȡ��վ�б�������id
//		intent=getIntent();
//		String id=intent.getStringExtra("id");
		//intent.putExtra("id", id);


		intent = new Intent().setClass(this, fadianliang_gl.class);
		spec = tabHost.newTabSpec("����").setIndicator("����").setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, fadianliang_yfdl.class);
		spec = tabHost.newTabSpec("�·�����").setIndicator("�·�����").setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, fadianliang_nfdl.class);
		spec = tabHost.newTabSpec("�귢����").setIndicator("�귢����").setContent(intent);
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
				case R.id.main_tab_gonglv:// ��ӿ���
					tabHost.setCurrentTabByTag("����");
					break;
				case R.id.main_tab_yfdl:// �ҵĿ���
					tabHost.setCurrentTabByTag("�·�����");
					break;
				case R.id.main_tab_nfdl:// �ҵ�֪ͨ
					tabHost.setCurrentTabByTag("�귢����");
					break;
				default:
					// tabHost.setCurrentTabByTag("�ҵĿ���");
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
//        tabHost.addTab(tabHost.newTabSpec("����").setIndicator("����", getResources().getDrawable(R.drawable.ic_launcher))  
//                .setContent(R.id.view1));  
//        tabHost.addTab(tabHost.newTabSpec("�·�����").setIndicator("�·�����")  
//                .setContent(R.id.view2));  
//        tabHost.addTab(tabHost.newTabSpec("�귢����").setIndicator("�귢����")  
//                .setContent(R.id.view3));
//        TabWidget tabWidget=tabHost.getTabWidget();
//        for (int i = 0; i < tabWidget.getChildCount(); i++) {//tabWidget.getChildCount()�õ�tab����
//        	TextView tv = (TextView) tabWidget.getChildAt(i).findViewById(android.R.id.title);
//            tv.setTextSize(18); 	
//		}//����tab�ϵ������С
//         
//          
//         //��ǩ�л��¼�����setOnTabChangedListener  
//        tabHost.setOnTabChangedListener(new OnTabChangeListener(){    
//            @Override  
//            public void onTabChanged(String tabId) {  
//                if (tabId.equals("����")) {   //��һ����ǩ 
////                   	ChartView myView=new ChartView(fadianliang.this);
////                	myView.SetInfo(
////                	        new String[]{"7-11","7-12","7-13","7-14","7-15","7-16","7-17"},   //X��̶�
////                	        new String[]{"","50","100","150","200","250"},   //Y��̶�
////                	        new String[]{"15","23","10","36","45","40","12"},  //����
////                	        "ͼ��ı���"
////                	);
//                }  
//                if (tabId.equals("�·�����")) {   //�ڶ�����ǩ  
//                }  
//                if (tabId.equals("�귢����")) {   //��������ǩ  
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
