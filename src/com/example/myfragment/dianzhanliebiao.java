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
	//private ArrayList<String> arr=new ArrayList<String>();//������ʱ�������
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
			public void onRefresh() {//����Ҫʵ�ֵ�ˢ���߼����������������
				// TODO Auto-generated method stub
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				refreshableView.finishRefreshing();//��һ��Ҫ�ǲ�����ȥ����һֱ��ʾ����ˢ��
			}
		},0);
        
        //1.�½�һ������������
        //ArrayAdapter�������ģ���ǰListView���ص�ÿһ���б�������Ӧ�Ĳ����ļ�����
        //2.���������ص�����Դ
/*        String[] arr_data={"��Ʒ����Ʒ","�ٺȶ��ٴ�Ҳ�ı䲻��","���µĴ���ӡ��","����ʵ����","���ҵĴ�����",
        		"�ڲ�����Ȧ��Ϊ��Ҫ����","�����ߵ���Ϊ��Ҫ��","��ϲ����Ǩ��","�Ҿ���һ���������ʯͷ","������","���������","�ҿ���","����Ȼ��Ц"};*/
//        String[] arr_data={"��վѡ��","��վһ","��վ��","��վ��","��վ��",
//        		"��վ��","��վ��","��վ��","��վ��","��վ��","��վʮ","��վʮһ","��վʮ��"};
//        for (int i=0;i<arr_data.length;i++) {
//			arr.add(arr_data[i]);
//		}
        //simp_adapter=SimpleAdapter(this, getData(), R.layout.list_dzlb, new String[]{"name","gonglv","time","zhuangtai"}, new int[]{R.id.tv_name,R.id.tv_gonglv,R.id.tv_time,R.id.iv_zhuangtai});
        //arr_adapter=new ArrayAdapter<String>(this, R.layout.list_dzlb, arr);
        //3.��ͼListView����������
        //listView.setAdapter(new MyAdspter(this, list));
        
        //final List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();

		// ��ȡ����
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
//						//��ȡAPI���ص�����   Message�����������ݼ�����β��Ϣ��
//						int begin=msg.toString().indexOf("[");
//						int end=msg.toString().indexOf("]");
//						String request=msg.toString().substring(begin, end+1);
						
						JSONArray jsonArray = new JSONArray(result);
						for (int i = 0; i < jsonArray.length(); i++) {
							JSONObject item = jsonArray.getJSONObject(i); // ÿ����¼���ɼ���Object�������
							String plantName = item.getString("plantName"); // ��ȡ�����Ӧ��ֵ{"plantName":"湖北大学","plantId":"10000","power":"2100.0000","state":"1","time":"21:14:20"}
							String plantId = item.getString("plantId");
							String power=item.getString("power");
							String state = item.getString("state");
							String time = item.getString("time");
							
							name_id.put(plantName, plantId);//��������¼���ֵ��

							map = new HashMap<String, Object>(); // ��ŵ�MAP����
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
							arr_adapter.notifyDataSetChanged();//ʵʱlistview��������
							listView.setAdapter(arr_adapter);							
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break;
				default:
					Toast.makeText(dianzhanliebiao.this, "��ʱû������",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		};
        
        
        
        
        listView.setOnItemClickListener(this);//������Ŀ����ļ�����
        listView.setOnScrollListener(this);//�����仯�ļ�����
        
        /* ��ʾApp icon����back�� */
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
    }
    
//	private List<Map<String,Object>> getData(){
//		final List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
//
//		// ��ȡ����
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
//						//��ȡAPI���ص�����   Message�����������ݼ�����β��Ϣ��
//						int begin=msg.toString().indexOf("[");
//						int end=msg.toString().indexOf("]");
//						String request=msg.toString().substring(begin, end+1);
//						
//						JSONArray jsonArray = new JSONArray(request);
//						for (int i = 0; i < jsonArray.length(); i++) {
//							JSONObject item = jsonArray.getJSONObject(i); // ÿ����¼���ɼ���Object�������
//							String plantName = item.getString("plantName"); // ��ȡ�����Ӧ��ֵ{"plantName":"湖北大学","plantId":"10000","power":"2100.0000","state":"1","time":"21:14:20"}
//							String plantId = item.getString("plantId");
//							String power=item.getString("power");
//							String state = item.getString("state");
//							String time = item.getString("time");
//
//							map = new HashMap<String, Object>(); // ��ŵ�MAP����
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
//					Toast.makeText(dianzhanliebiao.this, "��ʱû������",
//							Toast.LENGTH_SHORT).show();
//					break;
//				}
//			}
//		};
//								
//		
////		for (int i = 0; i <20; i++) {
////			Map<String,Object> map=new HashMap<String,Object>();
////			map.put("name", "��վ..."+i);
////			map.put("gonglv", "����..."+i);
////			map.put("time", "ʱ��..."+i);
////			map.put("zhuangtai", R.drawable.yuan);
////			list.add(map);
////		}
//		return list;
//	}
	   
       
    
    //actionbar�������ûд���Զ���Ķ����˵���û��
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
			Toast.makeText(this, "�����ˡ��豸״̬��Ϣ��������", Toast.LENGTH_SHORT).show();
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
			Log.i("main", "�뿪ʱ����������Ч��");
			break;
		case SCROLL_STATE_IDLE:
			Log.i("main", "��ͼֹͣ����");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", "111");
			map.put("plantId", "111");
			map.put("gonglv", "111");
			map.put("zhuangtai", R.drawable.yuan);
			map.put("time", "111");
			list.add(map);// �������������
			arr_adapter.notifyDataSetChanged();//֪ͨ������
			break;
		case SCROLL_STATE_TOUCH_SCROLL:
			Log.i("main", "��ָû�뿪����ͼ���ڻ");			
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Intent intent=new Intent();
		//��ȡÿһ��item������
		tv_name=(TextView) view.findViewById(R.id.tv_name);
		String dzmc=tv_name.getText().toString();
//		name_id.get(dzmc);
		intent.putExtra("id", name_id.get(dzmc));
		intent.setClass(dianzhanliebiao.this, zhuyemian.class);
		dianzhanliebiao.this.startActivity(intent);

	}
    
}
