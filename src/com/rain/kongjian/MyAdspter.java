package com.rain.kongjian;

import java.util.List;
import java.util.Map;

import com.example.myfragment.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdspter extends BaseAdapter {  
  
    private List<Map<String, Object>> data;  
    private LayoutInflater layoutInflater;  
    private Context context;  
    public MyAdspter(Context context,List<Map<String, Object>> data){  
        this.context=context;  
        this.data=data;  
        this.layoutInflater=LayoutInflater.from(context);  
    }  
    /** 
     * 组件集合，对应list.xml中的控件 
     * @author Administrator 
     */  
    public final class Zujian{  
        public ImageView image;  
        public TextView tv_name,tv_gonglv,tv_time;   
 
    }  
    @Override  
    public int getCount() {  
        return data.size();  
    }  
    /** 
     * 获得某一位置的数据 
     */  
    @Override  
    public Object getItem(int position) {  
        return data.get(position);  
    }  
    /** 
     * 获得唯一标识 
     */  
    @Override  
    public long getItemId(int position) {  
        return position;  
    }  
  
    @Override  
    public View getView(int position, View convertView, ViewGroup parent) {  
        Zujian zujian=null;  
        if(convertView==null){  
            zujian=new Zujian();  
            //获得组件，实例化组件  
            convertView=layoutInflater.inflate(R.layout.list_dzlb, null);  
            zujian.image=(ImageView)convertView.findViewById(R.id.iv_zhuangtai);  
            zujian.tv_name=(TextView)convertView.findViewById(R.id.tv_name);  
            zujian.tv_gonglv=(TextView) convertView.findViewById(R.id.tv_gonglv);  
            zujian.tv_time=(TextView)convertView.findViewById(R.id.tv_time);  
            convertView.setTag(zujian);  
        }else{  
            zujian=(Zujian)convertView.getTag();  
        }  
        //绑定数据  
        zujian.image.setImageResource((Integer)data.get(position).get("zhuangtai"));  
        zujian.tv_name.setText((String)data.get(position).get("name"));  
        zujian.tv_gonglv.setText((String)data.get(position).get("gonglv"));
        zujian.tv_time.setText((String)data.get(position).get("time"));
        return convertView;  
    }  
  
}  