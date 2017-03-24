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

public class fadianliang_nfdl extends Activity {
	List<String> months = new ArrayList<String>();
	List<String> months_month = new ArrayList<String>();
	String[] kedu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.fadianliang_nfdl);
		Constant.point = new Point();
		getWindowManager().getDefaultDisplay().getSize(Constant.point);//获取屏幕分辨率

		// 获取id
		Intent intent = getParent().getIntent();
		// String id = intent.getStringExtra("id");
		Constant.sharedata = getSharedPreferences("rixin", 0);
		String id = Constant.sharedata.getString("id", null);

		// 获取数据
		String url = Constant.url + "plantdetailformonths/" + id;
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
							String d = "m" + (i + 1);
							String month = jsonObject.getString(d);
							months.add(month);
							months_month.add("第" + (i + 1) + "月");
						}
						// String d="d1";
						// String wb = jsonObject.getString(d);
						// String s=wb;
						ChartView myView = new ChartView(fadianliang_nfdl.this);
						String[] months1 = (String[]) months
								.toArray(new String[months.size()]);
						String[] months_month1 = (String[]) months_month
								.toArray(new String[months_month.size()]);
						//设置合适的横坐标
						int min = Integer.parseInt(months1[0], 10);// 字符串转换成int
						int max = Integer.parseInt(months1[0], 10);
						for (int i = 0; i < months1.length; i++) {
							if (Integer.parseInt(months1[i], 10) <= min) {
								min = Integer.parseInt(months1[i], 10);
							}
							if (Integer.parseInt(months1[i], 10) >= max) {
								max = Integer.parseInt(months1[i], 10);
							}
						}
						if (10 >=(max  / 10) && (max  / 10) > 0) {
							int i = max / 10 ;
							kedu = new String[] { "", String.valueOf(1 * i),
									String.valueOf(2 * i),
									String.valueOf(3 * i),
									String.valueOf(4 * i),
									String.valueOf(5 * i),
									String.valueOf(6 * i),
									String.valueOf(7 * i),
									String.valueOf(8 * i),
									String.valueOf(9 * i) };
						}else if (10 >=(max  / 100) && (max  / 100) > 0) {
							int i = max / 100;
							kedu = new String[] { "", String.valueOf(10 * i),
									String.valueOf(20 * i),
									String.valueOf(30 * i),
									String.valueOf(40 * i),
									String.valueOf(50 * i),
									String.valueOf(60 * i),
									String.valueOf(70 * i),
									String.valueOf(80 * i),
									String.valueOf(90 * i) };
						}else if (10 >=(max  / 1000) && (max  / 1000) > 0) {
							int i = max / 1000;
							kedu = new String[] { "", String.valueOf(100 * i),
									String.valueOf(200 * i),
									String.valueOf(300 * i),
									String.valueOf(400 * i),
									String.valueOf(500 * i),
									String.valueOf(600 * i),
									String.valueOf(700 * i),
									String.valueOf(800 * i),
									String.valueOf(900 * i) };
						}

						myView.SetInfo(months_month1, // X轴刻度
								kedu, // Y轴刻度
								months1, // 数据
								"月发电量");
						setContentView(myView);//这个不加上去会没有显示

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				default:
					Toast.makeText(fadianliang_nfdl.this, "暂时没有数据",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		};

	}

}
