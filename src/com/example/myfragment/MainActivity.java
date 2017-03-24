package com.example.myfragment;

import org.json.JSONException;
import org.json.JSONObject;

import com.rain.db.Constant;
import com.rain.db.asynctask;
import com.rain.start.LoginActivity;
import com.rain.start.logoActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
////	private RadioGroup group;
//	EditText etUserName,etUserPass;
//	CheckBox chk;
//	SharedPreferences pref;
//	Editor editor;
	private TextView tv_jrfdl,tv_dzzt,tv_yfdl,tv_nfdl,tv_lj,tv_fdsrl;
	private Button btn;
	
	private long exitTime = 0;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fadianliangtongji);
        
        tv_jrfdl=(TextView) findViewById(R.id.tv_jrfdl);
        tv_dzzt=(TextView) findViewById(R.id.tv_dzzt);
        tv_yfdl=(TextView) findViewById(R.id.tv_yfdl);
        tv_nfdl=(TextView) findViewById(R.id.tv_nfdl);
        tv_lj=(TextView) findViewById(R.id.tv_lj);
        tv_fdsrl=(TextView) findViewById(R.id.tv_fdsrl);
        
        btn =(Button) findViewById(R.id.btn_dzlb);
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,dianzhanliebiao.class);
				MainActivity.this.startActivity(intent);
			}
		});
        
		/* 显示App icon左侧的back键 */
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		
		//获取数据		
		String url = Constant.url + "datareport";
		asynctask task = new asynctask();
		task.execute(url);
		Constant.handler=new Handler(){
			 @Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					String result = msg.obj.toString();
					JSONObject jsonObject;//解析单条json数据
					try {
						jsonObject = new JSONObject(result);
						// resultpassword = jsonObject.getString("PassWord");
						String jrfdl=jsonObject.getString("powerToday");
						tv_jrfdl.setText(jrfdl+"kWh");
						String zcdz=jsonObject.getString("numberOfGoodPlants");
						String dz=jsonObject.getString("numberOfPlants");
						tv_dzzt.setText("正常   "+zcdz+"/"+dz+" 总数");
						String yfdl=jsonObject.getString("powerThisMonth");
						tv_yfdl.setText(yfdl+"MWh");
						String nfdl=jsonObject.getString("powerThisYear");
						tv_nfdl.setText(nfdl+"MWh");
						String lj=jsonObject.getString("powerSum");
						tv_lj.setText(lj+"MWh");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				default:
					Toast.makeText(MainActivity.this, "暂时没有数据",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}			
		};
		

        
    }
    
    //actionbar如果这里没写，自定义的顶部菜单就没有
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
        case android.R.id.home:  
            //finish();
        	//连续两次就退出
        	if ((System.currentTimeMillis() - exitTime) > 2000) {
    			Toast.makeText(getApplicationContext(), "再按一次退出程序",
    					Toast.LENGTH_SHORT).show();
    			exitTime = System.currentTimeMillis();
    		} else {
    			// android.os.Process.killProcess(android.os.Process.myPid());
    			finish();
    			System.exit(0);

    		}
            return true;
        case R.id.xxzx:
			Toast.makeText(this, "你点击了“消息中心”按键！", Toast.LENGTH_SHORT).show();
			Intent intent=new Intent(MainActivity.this,xxzx.class);
			MainActivity.this.startActivity(intent);
			return true;
        case R.id.tcdqyh:
			Toast.makeText(this, "你点击了“切换用户”按键！", Toast.LENGTH_SHORT).show();
			Intent intent2=new Intent();
			intent2.putExtra("panduan", 1);//不同activity之间的传值
			intent2.setClass(MainActivity.this,LoginActivity.class);
			MainActivity.this.startActivity(intent2);
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
    
   
	// 两次返回就退出
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void exit() {
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			Toast.makeText(getApplicationContext(), "再按一次退出程序",
					Toast.LENGTH_SHORT).show();
			exitTime = System.currentTimeMillis();
		} else {
			// android.os.Process.killProcess(android.os.Process.myPid());
			finish();
			System.exit(0);

		}
	}

//	@Override
//	public void onCheckedChanged(RadioGroup group, int checkedId) {
//		// TODO Auto-generated method stub
//		switch (checkedId) {
//		case R.id.first: {
//			//相当于页面跳转
//			Intent intent=new Intent(this,MainActivity2.class);
//			startActivity(intent);
//			break;
//		}
//		case R.id.second: {
//			myfragment2 fragment2=new myfragment2();//初始化fragment
//			FragmentManager fragmentManager = getFragmentManager();//获取fragment管理者
//			//开启事务
//			FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
//			beginTransaction.add(R.id.frame, fragment2);
//			beginTransaction.addToBackStack(null);//增加返回
//			beginTransaction.commit();//提交事务
//			break;
//		}
//		case R.id.third: {
//
//			break;
//		}
//		case R.id.fourth: {
//
//			break;
//		}
//		}
//	}
    
}
