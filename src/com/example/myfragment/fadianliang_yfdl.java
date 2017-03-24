package com.example.myfragment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.rain.db.Constant;
import com.rain.db.asynctask;
import com.rain.kongjian.ChartView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

public class fadianliang_yfdl extends Activity{
	List<String> days=new ArrayList<String>();
	List<String> days_day=new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.fadianliang_yfdl);
		
		 Constant.point = new Point();  
		 getWindowManager().getDefaultDisplay().getSize(Constant.point);  
		
		// 获取id
		Intent intent = getParent().getIntent();
//		String id = intent.getStringExtra("id");
		Constant.sharedata=getSharedPreferences("rixin", 0);
		String id=Constant.sharedata.getString("id",null);
		
		// 获取数据
		String url = Constant.url + "plantdetailfordays/" + id;
		asynctask task = new asynctask();
		task.execute(url);
		Constant.handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					String result = msg.obj.toString();					

					JSONObject jsonObject = null;
					try {
						jsonObject = new JSONObject(result);
						for (int i = 0; i < jsonObject.length(); i++) {
							String d="d"+(i+1);
							String day=jsonObject.getString(d);
							days.add(day);
							days_day.add("第"+(i+1)+"天");
						}
//						String d="d1";
//						String wb = jsonObject.getString(d);
//						String s=wb;
				       	ChartView myView=new ChartView(fadianliang_yfdl.this);
				       	String[] days1 = (String[])days.toArray(new String[days.size()]);
				       	String[] days_day1 = (String[])days_day.toArray(new String[days_day.size()]);
						myView.SetInfo(days_day1, // X轴刻度
								new String[] { "", "50", "100", "150", "200", "250" }, // Y轴刻度
								days1, // 数据
								"日发电量");						
						setContentView(myView);
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// try {
					// JSONArray jsonArray = new JSONArray(result);
					// for (int i = 0; i < jsonArray.length(); i++) {
					// JSONObject item = jsonArray.getJSONObject(i); //
					// 每条记录又由几个Object对象组成
					// String dianzhanmingcheng =
					// item.getString("dianzhanmingcheng"); //
					// 获取对象对应的值{"plantName":"婀栧寳澶у","plantId":"10000","power":"2100.0000","state":"1","time":"21:14:20"}
					// String dianzhanbianma = item.getString("dianzhanbianma");
					// String huanjingwendu = item
					// .getString("huanjingwendu");
					// String dianchibanwendu =
					// item.getString("dianchibanwendu");
					// String rizhaoqiangdu = item
					// .getString("rizhaoqiangdu");
					// String fengsu = item
					// .getString("fengsu");
					// String huanjingyaqiang =
					// item.getString("huanjingyaqiang");
					// String huanjingshidu = item.getString("huanjingshidu");
					// String taiyangnengfusheliang = item
					// .getString("taiyangnengfusheliang");
					// String time = item.getString("jilushijian");
					// }
					// } catch (JSONException e) {
					// // TODO Auto-generated catch block
					// e.printStackTrace();
					// }

					break;
				default:
					Toast.makeText(fadianliang_yfdl.this, "暂时没有数据",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		};
		
		
		

		
	}
	
}
