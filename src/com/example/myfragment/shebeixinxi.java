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
		
		// ��ȡid
		Intent intent = getParent().getIntent();
		String id = intent.getStringExtra("id");
		// ��ȡ����
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
						// //��ȡAPI���ص����� Message�����������ݼ�����β��Ϣ��
						// int begin=msg.toString().indexOf("[");
						// int end=msg.toString().indexOf("]");
						// String request=msg.toString().substring(begin,
						// end+1);

						JSONArray jsonArray = new JSONArray(result);
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject item = jsonArray.getJSONObject(i); // ÿ����¼���ɼ���Object�������
//							String plantName = item.getString("plantName"); // ��ȡ�����Ӧ��ֵ{"plantId":"10000","converterId":"1001","rifadianliang":"34.0000","zongfadianliang":"70.0000","yougonggonglv":"20.0000","zongzhiliugonglv":"400.0000","jilushijian":"12:33:01"}
							String plantId = item.getString("plantId");
							String converterId = item.getString("converterId");
							String rifadianliang = item.getString("rifadianliang");
							String zongfadianliang = item.getString("zongfadianliang");
							String yougonggonglv = item.getString("yougonggonglv");
							String zongzhiliugonglv = item.getString("zongzhiliugonglv");
							String jilushijian = item.getString("jilushijian");

							// name_id.put(plantName, plantId);//��������¼���ֵ��

							map = new HashMap<String, Object>(); // ��ŵ�MAP����
							map.put("bm", converterId);
							map.put("fdl", rifadianliang);
//							if (state.equals("1") || state == "1") {
//								map.put("zhuangtai", R.drawable.yuan);
//							} else {
//								map.put("zhuangtai", R.drawable.yuan_yc);
//							}					
							list.add(map);
							arr_adapter = new nbq(shebeixinxi.this, list);
							arr_adapter.notifyDataSetChanged();// ʵʱlistview��������
							listView.setAdapter(arr_adapter);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break;
				default:
					Toast.makeText(shebeixinxi.this, "��ʱû������",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		};
		
		
		listView.setOnItemClickListener(this);//������Ŀ����ļ�����
	}
	
//	private List<Map<String,Object>> getData(){
//		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>(); 
//		for (int i = 0; i <5; i++) {
//			Map<String,Object> map=new HashMap<String,Object>();
//			map.put("bm", "���..."+i);
//			map.put("fdl", "������..."+i);
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