package com.example.myfragment;

import com.rain.db.Constant;

import android.app.ActionBar;
import android.app.TabActivity;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class zhuyemian extends TabActivity {// implements
											// OnCheckedChangeListener
	private RadioGroup group;
	private TabHost tabHost;
	private TextView main_tab_new_message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// group=(RadioGroup) findViewById(R.id.radioGroup);
		// group.setOnCheckedChangeListener(this);

		/* ��ʾApp icon����back�� */
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		main_tab_new_message = (TextView) findViewById(R.id.main_tab_new_message);
		main_tab_new_message.setVisibility(View.VISIBLE);
		main_tab_new_message.setText("10");

		tabHost = this.getTabHost();
		TabHost.TabSpec spec;
		Intent intent;

		// ��ȡ��վ�б�������id
		intent=getIntent();
		String id=intent.getStringExtra("id");
		//intent.putExtra("id", id);
//		Editor sharedata = getSharedPreferences("rixin", 0).edit();
//		sharedata.putString("id", id);
		Constant.sharedata =getSharedPreferences("rixin", 0);
		Editor editor=Constant.sharedata.edit();
		editor.putString("id", id);
		editor.commit();

		intent = new Intent().setClass(this, dianzhangaikuang.class);
		spec = tabHost.newTabSpec("�ſ�").setIndicator("�ſ�").setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, tianqi.class);
		spec = tabHost.newTabSpec("����").setIndicator("����").setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, fadianliang.class);
		spec = tabHost.newTabSpec("������").setIndicator("������").setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, shebeixinxi.class);
		spec = tabHost.newTabSpec("�����").setIndicator("�����").setContent(intent);
		tabHost.addTab(spec);
		tabHost.setCurrentTab(0);

		RadioGroup radioGroup = (RadioGroup) this
				.findViewById(R.id.main_tab_group);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.main_tab_gaikuang:// ��ӿ���
					tabHost.setCurrentTabByTag("�ſ�");
					actionBar.setTitle("�ſ�");// ���ñ�����
					break;
				case R.id.main_tab_tianqi:// �ҵĿ���
					tabHost.setCurrentTabByTag("����");
					actionBar.setTitle("����");
					break;
				case R.id.main_tab_fadianliang:// �ҵ�֪ͨ
					tabHost.setCurrentTabByTag("������");
					actionBar.setTitle("������");
					break;
				case R.id.main_tab_nibianqi:// ����
					tabHost.setCurrentTabByTag("�����");
					actionBar.setTitle("�����");
					break;
				default:
					// tabHost.setCurrentTabByTag("�ҵĿ���");
					break;
				}
			}
		});

	}

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

	// @Override
	// public void onCheckedChanged(RadioGroup group, int checkedId) {
	// // TODO Auto-generated method stub
	// switch (checkedId) {
	// case R.id.first: {
	// //�൱��ҳ����ת
	// Intent intent=new Intent(this,MainActivity2.class);
	// startActivity(intent);
	// break;
	// }
	// case R.id.second: {
	// // myfragment2 fragment2=new myfragment2();//��ʼ��fragment
	// // FragmentManager fragmentManager = getFragmentManager();//��ȡfragment������
	// // //��������
	// // FragmentTransaction beginTransaction =
	// fragmentManager.beginTransaction();
	// // beginTransaction.add(R.id.frame, fragment2);
	// // beginTransaction.addToBackStack(null);//���ӷ���
	// // beginTransaction.commit();//�ύ����
	//
	// break;
	// }
	// case R.id.third: {
	// Intent intent=new Intent(this,shebeixinxi.class);
	// startActivity(intent);
	// break;
	// }
	// case R.id.fourth: {
	// Intent intent=new Intent(this,tianqi.class);
	// startActivity(intent);
	// break;
	// }
	// }
	// }

}
