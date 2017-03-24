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
		
		//去除标题
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
							
		//获取实例化对象
		sp=this.getSharedPreferences("userinfo", Context.MODE_WORLD_READABLE);
		userName=(EditText) findViewById(R.id.et_zh);
		password = (EditText) findViewById(R.id.et_mima);
		rem_pw = (CheckBox) findViewById(R.id.cb_mima);
		auto_login = (CheckBox) findViewById(R.id.cb_auto);
		btn_login = (Button) findViewById(R.id.btn_login);
		btnQuit = (ImageButton)findViewById(R.id.img_btn);
		
		//得到从MainActivity传过来的值
		Intent intent2=getIntent();
		int value=intent2.getIntExtra("panduan",0);
		if (value==1) {
			//auto_login.setChecked(false);
			sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
		}
		
		//判断记住多选框的状态
		if (sp.getBoolean("ISCHECK", false)) {//假设SharedPreferences里面还没有ISCHECK的值，此时默认返回false
			//设置默认是记录密码
			rem_pw.setChecked(true);
			userName.setText(sp.getString("USER_NAME",""));
			password.setText(sp.getString("PASSWORD", ""));
			
	          //判断自动登陆多选框状态  
			if (sp.getBoolean("AUTO_ISCHECK", false)) {
				// 设置默认是自动登录状态
				auto_login.setChecked(true);
				// 跳转界面
				Intent intent = new Intent(LoginActivity.this,
						logoActivity.class);
				LoginActivity.this.startActivity(intent);
				finish();
			}		
		}
		
		//登录监听事件  现在默认为用户名为：rain 密码：123
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
								Toast.makeText(LoginActivity.this,"登录成功", Toast.LENGTH_SHORT).show();
								//登录成功和记住密码框为选中状态才保存用户信息
								if (rem_pw.isChecked()) {
									//记住用户名、密码
									Editor editor=sp.edit();
									editor.putString("USER_NAME", userNameValue);
									editor.putString("PASSWORD", passwordValue);
									editor.commit();
								}
								//跳转界面
								Intent intent =new Intent(LoginActivity.this,logoActivity.class);
								LoginActivity.this.startActivity(intent);
								finish();
							}else {
								Toast.makeText(LoginActivity.this,"用户名或密码错误，请重新登录", Toast.LENGTH_LONG).show();
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
			            	Toast.makeText(LoginActivity.this, "暂时没有数据", Toast.LENGTH_SHORT).show();
			                break;
			            }
			        }			         
			    };
				
				
				//如果在这里判断第一次执行的handler在判断之后，所以会出现第一次密码错误，而第二次判断用的却是第一次的数据
//				//if (userNameValue.equals("rain")&&passwordValue.equals("123")) {
//				if (passwordValue.equals(resultpassword)) {
//					Toast.makeText(LoginActivity.this,"登录成功", Toast.LENGTH_SHORT).show();
//					//登录成功和记住密码框为选中状态才保存用户信息
//					if (rem_pw.isChecked()) {
//						//记住用户名、密码
//						Editor editor=sp.edit();
//						editor.putString("USER_NAME", userNameValue);
//						editor.putString("PASSWORD", passwordValue);
//						editor.commit();
//					}
//					//跳转界面
//					Intent intent =new Intent(LoginActivity.this,logoActivity.class);
//					LoginActivity.this.startActivity(intent);
//					finish();
//				}else {
//					Toast.makeText(LoginActivity.this,"用户名或密码错误，请重新登录", Toast.LENGTH_LONG).show();
//				}				
				
			}
		});
		
		//监听记住密码多选框按钮事件
		rem_pw.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (rem_pw.isChecked()) {
					Toast.makeText(LoginActivity.this,"记住密码已选中", Toast.LENGTH_SHORT).show();
					sp.edit().putBoolean("ISCHECK", true).commit();
				} else {
					Toast.makeText(LoginActivity.this,"记住密码没有选中", Toast.LENGTH_SHORT).show();
					sp.edit().putBoolean("ISCHECK", false).commit();
				}
			}
		});
		
		//监听自动登录多选框事件
		auto_login.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if (auto_login.isChecked()) {
					Toast.makeText(LoginActivity.this, "自动登录已选中", Toast.LENGTH_SHORT).show();
					sp.edit().putBoolean("AUTO_ISCHECK", true).commit();
					boolean b = sp.getBoolean("AUTO_ISCHECK", false);
				} else {
					Toast.makeText(LoginActivity.this, "自动登录未选中", Toast.LENGTH_SHORT).show();
					sp.edit().putBoolean("AUTO_ISCHECK", false).commit();
				}
			}
		});
		
		//退出
		btnQuit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		
	}
	
	
	

}

