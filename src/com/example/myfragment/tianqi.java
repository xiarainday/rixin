package com.example.myfragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rain.db.Constant;
import com.rain.db.asynctask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

public class tianqi extends Activity {
	TextView tv_wd,tv_fs,tv_sd,tv_dzmc;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tianqi);
		
		tv_wd=(TextView) findViewById(R.id.tv_wd);
		tv_fs=(TextView) findViewById(R.id.tv_fs);
		tv_sd=(TextView) findViewById(R.id.tv_sd);
		tv_dzmc=(TextView) findViewById(R.id.tv_tianqi_dzmc);

		// 获取id
		Intent intent = getParent().getIntent();
		String id = intent.getStringExtra("id");
		// 获取数据
		String url = Constant.url + "weather/" + id;
		asynctask task = new asynctask();
		task.execute(url);
		Constant.handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					String result = msg.obj.toString();

					try {
						JSONArray jsonArray = new JSONArray(result);
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject item = jsonArray.getJSONObject(i); // 每条记录又由几个Object对象组成
							String dianzhanmingcheng = item.getString("dianzhanmingcheng"); // 获取对象对应的值{"plantName":"婀栧寳澶у","plantId":"10000","power":"2100.0000","state":"1","time":"21:14:20"}
							tv_dzmc.setText(dianzhanmingcheng);																					
							String dianzhanbianma = item.getString("dianzhanbianma");
							String huanjingwendu = item
									.getString("huanjingwendu");
							tv_wd.setText(huanjingwendu+" ℃");
							String dianchibanwendu = item.getString("dianchibanwendu");							
							String rizhaoqiangdu = item
									.getString("rizhaoqiangdu");
							String fengsu = item
									.getString("fengsu");
							tv_fs.setText(fengsu+" m/s");
							String huanjingyaqiang = item.getString("huanjingyaqiang");
							String huanjingshidu = item.getString("huanjingshidu");
							tv_sd.setText(huanjingshidu+" %RH");
							String taiyangnengfusheliang = item
									.getString("taiyangnengfusheliang");

							String time = item.getString("jilushijian");
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break;
				default:
					Toast.makeText(tianqi.this, "暂时没有数据", Toast.LENGTH_SHORT)
							.show();
					break;
				}
			}
		};

	}
}
