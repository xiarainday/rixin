package com.rain.db;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Handler;

public class Constant {
	
	public static final String url="http://115.159.158.208:8080/pv_api/v1/";//10.16.95.90
	public static Handler handler;
//	public static String fdl_id;//����������id
	public static SharedPreferences sharedata;//����������������
	public static Point point;//��ȡ��Ļ�Ĵ�С
	
	//��λҪ�õĶ���
    public static final boolean DAY_MODE = false;
    public static final boolean NIGHT_MODE = true;
    public static final boolean YES_MODE = true;
    public static final boolean NO_MODE = false;
    public static final boolean OPEN_MODE = true;
    public static final boolean CLOSE_MODE = false;
    
    public static final String DAY_NIGHT_MODE = "daynightmode";
    public static final String DEVIATION = "deviationrecalculation";
    public static final String JAM = "jamrecalculation";
    public static final String TRAFFIC = "trafficbroadcast";
    public static final String CAMERA = "camerabroadcast";
    public static final String SCREEN = "screenon";
    public static final String THEME = "theme";
    public static final String ISEMULATOR = "isemulator";
    
    public static final String ACTIVITYINDEX = "activityindex";

    public static final int SIMPLEHUDNAVIE = 0;
    public static final int EMULATORNAVI = 1;
    public static final int SIMPLEGPSNAVI = 2;
    public static final int SIMPLEROUTENAVI = 3;




	
}
