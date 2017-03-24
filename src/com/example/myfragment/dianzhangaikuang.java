package com.example.myfragment;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rain.db.Constant;
import com.rain.db.asynctask;
import com.rain.kongjian.MyAdspter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class dianzhangaikuang extends Activity {
	private TextView tv_zjrl, tv_gl, tv_dwfdl, tv_jrfdl, tv_jrsy, tv_ljfdl,
			tv_ljsy,tv_dzmc,tv_map;
	String jingdu;
	String weidu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main2);
		//获取id
		Intent intent = getParent().getIntent();
		String id = intent.getStringExtra("id");
		
		tv_dzmc=(TextView) findViewById(R.id.tv_dzmc);
		tv_zjrl=(TextView) findViewById(R.id.tv_zjrl);
		tv_gl=(TextView) findViewById(R.id.tv_gl);
		tv_dwfdl=(TextView) findViewById(R.id.tv_dwfdl);
		tv_jrfdl=(TextView) findViewById(R.id.tv_jrfdl);
		tv_jrsy=(TextView) findViewById(R.id.tv_jrsy);
		tv_ljfdl=(TextView) findViewById(R.id.tv_ljfdl);
		tv_ljsy=(TextView) findViewById(R.id.tv_ljsy);
		
		tv_map=(TextView) findViewById(R.id.tv_map);
		tv_map.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
//				Intent intent=new Intent(dianzhangaikuang.this,map.class);
				intent.putExtra("jingdu", jingdu);
				intent.putExtra("weidu", weidu);
				intent.setClass(dianzhangaikuang.this,map.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				dianzhangaikuang.this.startActivity(intent);
			}
		});

		// 获取数据
		String url = Constant.url + "plantdetail/" + id;
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
							String plantName = item.getString("plantName"); // 获取对象对应的值{"plantName":"婀栧寳澶у","plantId":"10000","power":"2100.0000","state":"1","time":"21:14:20"}
							tv_dzmc.setText(plantName);
							
							Constant.sharedata =getSharedPreferences("rixin", 0);
							Editor editor=Constant.sharedata.edit();
							editor.putString("dzmc", plantName);
							editor.commit();
							
							String plantId = item.getString("plantId");
							String zhuangtai = item
									.getString("yunxingzhuangtai");
							String gonglv = item.getString("dangqiangonglv");
							tv_gl.setText(gonglv);
							String rifadianliang = item
									.getString("rifadianliang");
							tv_jrfdl.setText(rifadianliang);
							String leijifadianliang = item
									.getString("leijifadianliang");
							tv_ljfdl.setText(leijifadianliang);
							String zongshouyi = item.getString("zongshouyi");
							tv_ljsy.setText(zongshouyi);
							String zhanzhang = item.getString("zhanzhang");
							String lianxidianhua = item
									.getString("lianxidianhua");
							String xiangxidizhi = item
									.getString("xiangxidizhi");
							tv_map.setText(xiangxidizhi);
							//目标经纬度
							jingdu = item.getString("jingdu");
							weidu = item.getString("weidu");

							String time = item.getString("jilushijian");

							// map = new HashMap<String, Object>(); // 存放到MAP里面
							// map.put("name", plantName);
							// map.put("plantId", plantId);
							// map.put("gonglv", power);
							// if (state.equals("1")||state=="1") {
							// map.put("zhuangtai", R.drawable.yuan);
							// }else{
							// map.put("zhuangtai", R.drawable.yuan_yc);
							// }
							// map.put("time", time);
							// list.add(map);
							// arr_adapter=new MyAdspter(dianzhangaikuang.this,
							// list);
							// arr_adapter.notifyDataSetChanged();//实时listview更新数据
							// listView.setAdapter(arr_adapter);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break;
				default:
					Toast.makeText(dianzhangaikuang.this, "暂时没有数据",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		};

		// Editor sharedata = (Editor) this.getSharedPreferences("rixin", 0);
		// String id = sharedata.getString("id", null);

	}

}
