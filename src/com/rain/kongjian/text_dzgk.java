package com.rain.kongjian;

import java.util.List;
import java.util.Map;

import com.example.myfragment.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
//电站概况控件，暂时没有用
public class text_dzgk extends TextView {
    private List<Map<String, Object>> data;  
    private LayoutInflater layoutInflater;  
    private Context context;

	public text_dzgk(Context context,List<Map<String, Object>> data) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context=context;
		this.data=data; 
        this.layoutInflater=LayoutInflater.from(context);
	}

    public final class Zujian{
        public TextView tv_dzgk_nr,tv_dzgk_sz,tv_dzgk_dw;   
 
    } 
    
    public View getView(int position, View convertView, ViewGroup parent) {  
        Zujian zujian=null;  
        if(convertView==null){  
            zujian=new Zujian();  
            //获得组件，实例化组件  
            convertView=layoutInflater.inflate(R.layout.list_nbq, null);  
            zujian.tv_dzgk_nr=(TextView)convertView.findViewById(R.id.tv_dzgk_nr);  
            zujian.tv_dzgk_sz=(TextView) convertView.findViewById(R.id.tv_dzgk_sz);  
            zujian.tv_dzgk_dw=(TextView) convertView.findViewById(R.id.tv_dzgk_dw); 
            convertView.setTag(zujian);  
        }else{  
            zujian=(Zujian)convertView.getTag();  
        }  
        //绑定数据  
        //zujian.image.setBackgroundResource((Integer)data.get(position).get("zhuangtai"));  
        zujian.tv_dzgk_nr.setText((String)data.get(position).get("nr"));  
        zujian.tv_dzgk_sz.setText((String)data.get(position).get("sz"));
        zujian.tv_dzgk_dw.setText((String)data.get(position).get("dw"));
        return convertView;  
    }  
	
}
