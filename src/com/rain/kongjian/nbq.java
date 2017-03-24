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


public class nbq extends BaseAdapter {  
  
    private List<Map<String, Object>> data;  
    private LayoutInflater layoutInflater;  
    private Context context;  
    public nbq(Context context,List<Map<String, Object>> data){  
        this.context=context;  
        this.data=data;  
        this.layoutInflater=LayoutInflater.from(context);  
    }  
    /** 
     * 组件集合，对应list.xml中的控件 
     * @author Administrator 
     */  
    public final class Zujian{
    	ImageView image;
        public TextView tv_nbq_bm,tv_nbq_fdl;   
 
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
            convertView=layoutInflater.inflate(R.layout.list_nbq, null);  
            zujian.image=(ImageView)convertView.findViewById(R.id.iv_nbq_zt);  
            zujian.tv_nbq_bm=(TextView)convertView.findViewById(R.id.tv_nbq_bm);  
            zujian.tv_nbq_fdl=(TextView) convertView.findViewById(R.id.tv_nbq_fdl);   
            convertView.setTag(zujian);  
        }else{  
            zujian=(Zujian)convertView.getTag();  
        }  
        //绑定数据  
        //zujian.image.setBackgroundResource((Integer)data.get(position).get("zhuangtai"));  
        zujian.tv_nbq_bm.setText((String)data.get(position).get("bm"));  
        zujian.tv_nbq_fdl.setText((String)data.get(position).get("fdl"));
        return convertView;  
    }  
  
}  
