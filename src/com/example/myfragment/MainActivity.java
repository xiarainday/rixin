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
        
		/* ��ʾApp icon����back�� */
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		
		//��ȡ����		
		String url = Constant.url + "datareport";
		asynctask task = new asynctask();
		task.execute(url);
		Constant.handler=new Handler(){
			 @Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					String result = msg.obj.toString();
					JSONObject jsonObject;//��������json����
					try {
						jsonObject = new JSONObject(result);
						// resultpassword = jsonObject.getString("PassWord");
						String jrfdl=jsonObject.getString("powerToday");
						tv_jrfdl.setText(jrfdl+"kWh");
						String zcdz=jsonObject.getString("numberOfGoodPlants");
						String dz=jsonObject.getString("numberOfPlants");
						tv_dzzt.setText("����   "+zcdz+"/"+dz+" ����");
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
					Toast.makeText(MainActivity.this, "��ʱû������",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}			
		};
		

        
    }
    
    //actionbar�������ûд���Զ���Ķ����˵���û��
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
        	//�������ξ��˳�
        	if ((System.currentTimeMillis() - exitTime) > 2000) {
    			Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����",
    					Toast.LENGTH_SHORT).show();
    			exitTime = System.currentTimeMillis();
    		} else {
    			// android.os.Process.killProcess(android.os.Process.myPid());
    			finish();
    			System.exit(0);

    		}
            return true;
        case R.id.xxzx:
			Toast.makeText(this, "�����ˡ���Ϣ���ġ�������", Toast.LENGTH_SHORT).show();
			Intent intent=new Intent(MainActivity.this,xxzx.class);
			MainActivity.this.startActivity(intent);
			return true;
        case R.id.tcdqyh:
			Toast.makeText(this, "�����ˡ��л��û���������", Toast.LENGTH_SHORT).show();
			Intent intent2=new Intent();
			intent2.putExtra("panduan", 1);//��ͬactivity֮��Ĵ�ֵ
			intent2.setClass(MainActivity.this,LoginActivity.class);
			MainActivity.this.startActivity(intent2);
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
    
   
	// ���η��ؾ��˳�
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
			Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����",
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
//			//�൱��ҳ����ת
//			Intent intent=new Intent(this,MainActivity2.class);
//			startActivity(intent);
//			break;
//		}
//		case R.id.second: {
//			myfragment2 fragment2=new myfragment2();//��ʼ��fragment
//			FragmentManager fragmentManager = getFragmentManager();//��ȡfragment������
//			//��������
//			FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
//			beginTransaction.add(R.id.frame, fragment2);
//			beginTransaction.addToBackStack(null);//���ӷ���
//			beginTransaction.commit();//�ύ����
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
