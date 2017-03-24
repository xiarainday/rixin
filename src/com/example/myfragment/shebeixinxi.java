package com.example.myfragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.rain.db.Constant;
import com.rain.db.asynctask;
import com.rain.kongjian.MyAdspter;
import com.rain.kongjian.nbq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class shebeixinxi extends Activity implements OnItemClickListener{
	private ListView listView;
	final List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
	nbq arr_adapter;
	Map<String, Object> map = null;
	
	TextView tv_nbq_dzmc;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shebeixinxi);
		
		listView=(ListView) findViewById(R.id.list_nbq);
//		List<Map<String, Object>> list=getData();
//		listView.setAdapter(new nbq(this, list));
		
		Constant.sharedata=getSharedPreferences("rixin", 0);
		String dzmc=Constant.sharedata.getString("dzmc",null);
		tv_nbq_dzmc=(TextView) findViewById(R.id.tv_nbq_dzmc);
		tv_nbq_dzmc.setText(dzmc);
		
		// 获取id
		Intent intent = getParent().getIntent();
		String id = intent.getStringExtra("id");
		// 获取数据
		String url = Constant.url + "converters/"+id;
		asynctask task = new asynctask();
		task.execute(url);
		Constant.handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					String result = msg.obj.toString();

					try {
						// //截取API返回的数据 Message传过来的数据加了首尾信息文
						// int begin=msg.toString().indexOf("[");
						// int end=msg.toString().indexOf("]");
						// String request=msg.toString().substring(begin,
						// end+1);

						JSONArray jsonArray = new JSONArray(result);
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject item = jsonArray.getJSONObject(i); // 每条记录又由几个Object对象组成
//							String plantName = item.getString("plantName"); // 获取对象对应的值{"plantId":"10000","converterId":"1001","rifadianliang":"34.0000","zongfadianliang":"70.0000","yougonggonglv":"20.0000","zongzhiliugonglv":"400.0000","jilushijian":"12:33:01"}
							String plantId = item.getString("plantId");
							String converterId = item.getString("converterId");
							String rifadianliang = item.getString("rifadianliang");
							String zongfadianliang = item.getString("zongfadianliang");
							String yougonggonglv = item.getString("yougonggonglv");
							String zongzhiliugonglv = item.getString("zongzhiliugonglv");
							String jilushijian = item.getString("jilushijian");

							// name_id.put(plantName, plantId);//留给点击事件传值用

							map = new HashMap<String, Object>(); // 存放到MAP里面
							map.put("bm", converterId);
							map.put("fdl", rifadianliang);
//							if (state.equals("1") || state == "1") {
//								map.put("zhuangtai", R.drawable.yuan);
//							} else {
//								map.put("zhuangtai", R.drawable.yuan_yc);
//							}					
							list.add(map);
							arr_adapter = new nbq(shebeixinxi.this, list);
							arr_adapter.notifyDataSetChanged();// 实时listview更新数据
							listView.setAdapter(arr_adapter);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break;
				default:
					Toast.makeText(shebeixinxi.this, "暂时没有数据",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		};
		
		
		listView.setOnItemClickListener(this);//单个条目点击的监听器
	}
	
//	private List<Map<String,Object>> getData(){
//		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>(); 
//		for (int i = 0; i <5; i++) {
//			Map<String,Object> map=new HashMap<String,Object>();
//			map.put("bm", "编号..."+i);
//			map.put("fdl", "发电量..."+i);
//			map.put("zhuangtai", R.drawable.yuan);
//			list.add(map);
//		}
//		return list;
//	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Intent intent=new Intent(shebeixinxi.this, nibianqi_zt.class);
		shebeixinxi.this.startActivity(intent);
	}
}