package com.example.myfragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
//û�õ�
public class myfragment extends Fragment {
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	//layout�����ļ�ת����VIew����
	/*
	 * resource:Fragment��Ҫ���صĲ����ļ�
	 * root������layout�ĸ���ViewGroup
	 * attachToRoot��false�������ظ�ViewGroup
	 */
	View view = inflater.inflate(R.layout.fragment, container, false);
	TextView text=(TextView) view.findViewById(R.id.text);
	text.setText("��̬����Fragment");
	return view;
}
}
