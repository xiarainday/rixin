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
import com.rain.xiaoguo.RefreshableView;
import com.rain.xiaoguo.RefreshableView.PullToRefreshListener;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;


public class dianzhanliebiao extends Activity implements OnItemClickListener,OnScrollListener{
	private ListView listView;
	MyAdspter arr_adapter;
	RefreshableView refreshableView;
	//private ArrayAdapter<String>arr_adapter;	
	//private SimpleAdapter simp_adapter;
	//private ArrayList<String> arr=new ArrayList<String>();//便于随时添加数据
	final List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
	Map<String, Object> map = null;
	Map<String,String> name_id=new HashMap<String, String>();
	
	public TextView tv_name;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dianzhanliebiao);
        
        listView=(ListView) findViewById(R.id.listView_arr);
        
        refreshableView=(RefreshableView) findViewById(R.id.refreshable_view_dzlb);
        refreshableView.setOnRefreshListener(new PullToRefreshListener() {
			
			@Override
			public void onRefresh() {//具体要实现的刷新逻辑就在这里面完成了
				// TODO Auto-generated method stub
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				refreshableView.finishRefreshing();//这一句要是不加上去，会一直显示正在刷新
			}
		},0);
        
        //1.新建一个数据适配器
        //ArrayAdapter（上下文，当前ListView加载的每一个列表项所对应的布局文件，）
        //2.适配器加载的数据源
/*        String[] arr_data={"酒品即人品","再喝多少次也改变不了","留下的吹逼印象","最真实的我","固我的存在着",
        		"融不进的圈子为何要将就","看不惯的人为何要看","不喜欢不迁就","我就是一个长不大的石头","哈哈哈","那又如何呢","我快乐","我依然在笑"};*/
//        String[] arr_data={"电站选择","电站一","电站二","电站三","电站四",
//        		"电站五","电站六","电站七","电站八","电站九","电站十","电站十一","电站十二"};
//        for (int i=0;i<arr_data.length;i++) {
//			arr.add(arr_data[i]);
//		}
        //simp_adapter=SimpleAdapter(this, getData(), R.layout.list_dzlb, new String[]{"name","gonglv","time","zhuangtai"}, new int[]{R.id.tv_name,R.id.tv_gonglv,R.id.tv_time,R.id.iv_zhuangtai});
        //arr_adapter=new ArrayAdapter<String>(this, R.layout.list_dzlb, arr);
        //3.视图ListView加载适配器
        //listView.setAdapter(new MyAdspter(this, list));
        
        //final List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();

		// 获取数据
		String url = Constant.url + "plants";
		asynctask task = new asynctask();
		task.execute(url);
		Constant.handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					String result = msg.obj.toString();
					
					try {
//						//截取API返回的数据   Message传过来的数据加了首尾信息文
//						int begin=msg.toString().indexOf("[");
//						int end=msg.toString().indexOf("]");
//						String request=msg.toString().substring(begin, end+1);
						
						JSONArray jsonArray = new JSONArray(result);
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject item = jsonArray.getJSONObject(i); // 每条记录又由几个Object对象组成
							String plantName = item.getString("plantName"); // 获取对象对应的值{"plantName":"婀栧寳澶у","plantId":"10000","power":"2100.0000","state":"1","time":"21:14:20"}
							String plantId = item.getString("plantId");
							String power=item.getString("power");
							String state = item.getString("state");
							String time = item.getString("time");
							
							name_id.put(plantName, plantId);//留给点击事件传值用

							map = new HashMap<String, Object>(); // 存放到MAP里面
							map.put("name", plantName);
							map.put("plantId", plantId);
							map.put("gonglv", power);
							if (state.equals("1")||state=="1") {
								map.put("zhuangtai", R.drawable.yuan);
							}else{
								map.put("zhuangtai", R.drawable.yuan_yc);	
							}
							map.put("time", time);
							list.add(map);
							arr_adapter=new MyAdspter(dianzhanliebiao.this, list);
							arr_adapter.notifyDataSetChanged();//实时listview更新数据
							listView.setAdapter(arr_adapter);							
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break;
				default:
					Toast.makeText(dianzhanliebiao.this, "暂时没有数据",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		};
        
        
        
        
        listView.setOnItemClickListener(this);//单个条目点击的监听器
        listView.setOnScrollListener(this);//滚动变化的监听器
        
        /* 显示App icon左侧的back键 */
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
    }
    
//	private List<Map<String,Object>> getData(){
//		final List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
//
//		// 获取数据
//		String url = Constant.url + "plants";
//		asynctask task = new asynctask();
//		task.execute(url);
//		Constant.handler = new Handler() {
//			@Override
//			public void handleMessage(Message msg) {
//				switch (msg.what) {
//				case 1:
//					String result = msg.obj.toString();
//					Map<String, Object> map = null;
//					try {
//						//截取API返回的数据   Message传过来的数据加了首尾信息文
//						int begin=msg.toString().indexOf("[");
//						int end=msg.toString().indexOf("]");
//						String request=msg.toString().substring(begin, end+1);
//						
//						JSONArray jsonArray = new JSONArray(request);
//						for (int i = 0; i < jsonArray.length(); i++) {
//							JSONObject item = jsonArray.getJSONObject(i); // 每条记录又由几个Object对象组成
//							String plantName = item.getString("plantName"); // 获取对象对应的值{"plantName":"婀栧寳澶у","plantId":"10000","power":"2100.0000","state":"1","time":"21:14:20"}
//							String plantId = item.getString("plantId");
//							String power=item.getString("power");
//							String state = item.getString("state");
//							String time = item.getString("time");
//
//							map = new HashMap<String, Object>(); // 存放到MAP里面
//							map.put("name", plantName);
//							//map.put("plantId", plantId);
//							map.put("gonglv", power);
//							if (state.equals("1")||state=="1") {
//								map.put("zhuangtai", R.drawable.yuan);
//							}else{
//								map.put("zhuangtai", R.drawable.yuan_yc);	
//							}
//							map.put("time", time);
//							list.add(map);							
//						}
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//
//					break;
//				default:
//					Toast.makeText(dianzhanliebiao.this, "暂时没有数据",
//							Toast.LENGTH_SHORT).show();
//					break;
//				}
//			}
//		};
//								
//		
////		for (int i = 0; i <20; i++) {
////			Map<String,Object> map=new HashMap<String,Object>();
////			map.put("name", "电站..."+i);
////			map.put("gonglv", "功率..."+i);
////			map.put("time", "时间..."+i);
////			map.put("zhuangtai", R.drawable.yuan);
////			list.add(map);
////		}
//		return list;
//	}
	   
       
    
    //actionbar如果这里没写，自定义的顶部菜单就没有
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.menu_sbztxx, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
        case android.R.id.home:  
            finish();
            return true;
        case R.id.sbztxx:
			Toast.makeText(this, "你点击了“设备状态信息”按键！", Toast.LENGTH_SHORT).show();
			Intent intent=new Intent(dianzhanliebiao.this,sbztxx.class);
			dianzhanliebiao.this.startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
    
    

               
    

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		switch (scrollState) {
		case SCROLL_STATE_FLING:
			Log.i("main", "离开时用力滑动的效果");
			break;
		case SCROLL_STATE_IDLE:
			Log.i("main", "视图停止滑动");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", "111");
			map.put("plantId", "111");
			map.put("gonglv", "111");
			map.put("zhuangtai", R.drawable.yuan);
			map.put("time", "111");
			list.add(map);// 滑动后添加新项
			arr_adapter.notifyDataSetChanged();//通知适配器
			break;
		case SCROLL_STATE_TOUCH_SCROLL:
			Log.i("main", "手指没离开，视图正在活动");			
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		//获取每一个item的内容
		tv_name=(TextView) view.findViewById(R.id.tv_name);
		String dzmc=tv_name.getText().toString();
//		name_id.get(dzmc);
		intent.putExtra("id", name_id.get(dzmc));
		intent.setClass(dianzhanliebiao.this, zhuyemian.class);
		dianzhanliebiao.this.startActivity(intent);

	}
    
}
