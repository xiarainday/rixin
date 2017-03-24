package com.example.myfragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
//没用到
public class myfragment extends Fragment {
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	//layout布局文件转换成VIew对象
	/*
	 * resource:Fragment需要加载的布局文件
	 * root：加载layout的父类ViewGroup
	 * attachToRoot：false，不返回父ViewGroup
	 */
	View view = inflater.inflate(R.layout.fragment, container, false);
	TextView text=(TextView) view.findViewById(R.id.text);
	text.setText("静态加载Fragment");
	return view;
}
}
