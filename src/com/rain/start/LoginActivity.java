package com.rain.start;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.myfragment.R;
import com.rain.db.Constant;
import com.rain.db.asynctask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class LoginActivity extends Activity{
	private SharedPreferences sp;
	private EditText userName,password;
	private CheckBox rem_pw, auto_login;
	private Button btn_login;
	private ImageButton btnQuit;
	private String userNameValue,passwordValue; 
	String resultpassword="";
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		//ȥ������
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
							
		//��ȡʵ��������
		sp=this.getSharedPreferences("userinfo", Context.MODE_WORLD_READABLE);
		userName=(EditText) findViewById(R.id.et_zh);
		password = (EditText) findViewById(R.id.et_mima);
		rem_pw = (CheckBox) findViewById(R.id.cb_mima);
		auto_login = (CheckBox) findViewById(R.id.cb_auto);
		btn_login = (Button) findViewById(R.id.btn_login);
		btnQuit = (ImageButton)findViewById(R.id.img_btn);
		
		//�õ���MainActivity��������ֵ
		Intent intent2=getIntent();
		int value=intent2.getIntExtra("panduan",0);
		if (value==1) {
			//auto_login.setChecked(false);
			sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
		}
		
		//�жϼ�ס��ѡ���״̬
		if (sp.getBoolean("ISCHECK", false)) {//����SharedPreferences���滹û��ISCHECK��ֵ����ʱĬ�Ϸ���false
			//����Ĭ���Ǽ�¼����
			rem_pw.setChecked(true);
			userName.setText(sp.getString("USER_NAME",""));
			password.setText(sp.getString("PASSWORD", ""));
			
	          //�ж��Զ���½��ѡ��״̬  
			if (sp.getBoolean("AUTO_ISCHECK", false)) {
				// ����Ĭ�����Զ���¼״̬
				auto_login.setChecked(true);
				// ��ת����
				Intent intent = new Intent(LoginActivity.this,
						logoActivity.class);
				LoginActivity.this.startActivity(intent);
				finish();
			}		
		}
		
		//��¼�����¼�  ����Ĭ��Ϊ�û���Ϊ��rain ���룺123
		btn_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				userNameValue=userName.getText().toString();
				passwordValue=password.getText().toString();
				//resultpassword=sp.getString("PASSWORD", "");
				
				String url=Constant.url+"users/"+userNameValue;
				asynctask task = new asynctask();
				task.execute(url);
				Constant.handler = new Handler(){
					 
			        @Override
			        public void handleMessage(Message msg) {
			            switch (msg.what) {
			            case 1:
			            	resultpassword = msg.obj.toString();
							//if (userNameValue.equals("rain")&&passwordValue.equals("123")) {
							if (passwordValue.equals(resultpassword)) {
								Toast.makeText(LoginActivity.this,"��¼�ɹ�", Toast.LENGTH_SHORT).show();
								//��¼�ɹ��ͼ�ס�����Ϊѡ��״̬�ű����û���Ϣ
								if (rem_pw.isChecked()) {
									//��ס�û���������
									Editor editor=sp.edit();
									editor.putString("USER_NAME", userNameValue);
									editor.putString("PASSWORD", passwordValue);
									editor.commit();
								}
								//��ת����
								Intent intent =new Intent(LoginActivity.this,logoActivity.class);
								LoginActivity.this.startActivity(intent);
								finish();
							}else {
								Toast.makeText(LoginActivity.this,"�û�����������������µ�¼", Toast.LENGTH_LONG).show();
							}
			            	
			            			            			            	
//			                String result = msg.obj.toString();
//			                JSONObject jsonObject;
//							try {
//								jsonObject = new JSONObject(result);
//								resultpassword=jsonObject.getString("PassWord");
//							} catch (JSONException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}								
			                break;			 
			            default:
			            	Toast.makeText(LoginActivity.this, "��ʱû������", Toast.LENGTH_SHORT).show();
			                break;
			            }
			        }			         
			    };
				
				
				//����������жϵ�һ��ִ�е�handler���ж�֮�����Ի���ֵ�һ��������󣬶��ڶ����ж��õ�ȴ�ǵ�һ�ε�����
//				//if (userNameValue.equals("rain")&&passwordValue.equals("123")) {
//				if (passwordValue.equals(resultpassword)) {
//					Toast.makeText(LoginActivity.this,"��¼�ɹ�", Toast.LENGTH_SHORT).show();
//					//��¼�ɹ��ͼ�ס�����Ϊѡ��״̬�ű����û���Ϣ
//					if (rem_pw.isChecked()) {
//						//��ס�û���������
//						Editor editor=sp.edit();
//						editor.putString("USER_NAME", userNameValue);
//						editor.putString("PASSWORD", passwordValue);
//						editor.commit();
//					}
//					//��ת����
//					Intent intent =new Intent(LoginActivity.this,logoActivity.class);
//					LoginActivity.this.startActivity(intent);
//					finish();
//				}else {
//					Toast.makeText(LoginActivity.this,"�û�����������������µ�¼", Toast.LENGTH_LONG).show();
//				}				
				
			}
		});
		
		//������ס�����ѡ��ť�¼�
		rem_pw.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (rem_pw.isChecked()) {
					Toast.makeText(LoginActivity.this,"��ס������ѡ��", Toast.LENGTH_SHORT).show();
					sp.edit().putBoolean("ISCHECK", true).commit();
				} else {
					Toast.makeText(LoginActivity.this,"��ס����û��ѡ��", Toast.LENGTH_SHORT).show();
					sp.edit().putBoolean("ISCHECK", false).commit();
				}
			}
		});
		
		//�����Զ���¼��ѡ���¼�
		auto_login.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (auto_login.isChecked()) {
					Toast.makeText(LoginActivity.this, "�Զ���¼��ѡ��", Toast.LENGTH_SHORT).show();
					sp.edit().putBoolean("AUTO_ISCHECK", true).commit();
					boolean b = sp.getBoolean("AUTO_ISCHECK", false);
				} else {
					Toast.makeText(LoginActivity.this, "�Զ���¼δѡ��", Toast.LENGTH_SHORT).show();
					sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
				}
			}
		});
		
		//�˳�
		btnQuit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		
	}
	
	
	

}

