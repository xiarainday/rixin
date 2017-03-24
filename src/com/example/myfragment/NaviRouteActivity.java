package com.example.myfragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.AMap.OnMapLoadedListener;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.view.RouteOverLay;

/**
 * ·���滮���չʾ����
 */
public class NaviRouteActivity extends Activity implements OnClickListener,
        OnMapLoadedListener {

    // View
    private Button mStartNaviButton;// ʵʱ������ť
    private MapView mMapView;// ��ͼ�ؼ�
    private AutoCompleteTextView mThemeText;// ѡ�񵼺�����ķ��
    private ImageView mThemeImage;// ѡ��ť
    private ImageView mRouteBackView;// ���ذ�ť
    private TextView mRouteDistanceView;// ������ʾ�ؼ�
    private TextView mRouteTimeView;// ʱ����ʾ�ؼ�
    private TextView mRouteCostView;// ������ʾ�ؼ�
    // ��ͼ������Դ
    private AMap mAmap;
    private AMapNavi mAmapNavi;
    private RouteOverLay mRouteOverLay;
    // ��������
    private String mTheme[];

    private boolean mIsMapLoaded = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);
        initView(savedInstanceState);
        //MainApplication.getInstance().addActivity(this);
    }

    // -----------------------��ʼ��----------------------------------



    /**
     * ��ʼ���ؼ�
     */
    private void initView(Bundle savedInstanceState) {
        mStartNaviButton = (Button) findViewById(R.id.routestartnavi);

        mRouteBackView = (ImageView) findViewById(R.id.route_back_view);       
        mRouteDistanceView = (TextView) findViewById(R.id.navi_route_distance);
        mRouteTimeView = (TextView) findViewById(R.id.navi_route_time);
        mRouteCostView = (TextView) findViewById(R.id.navi_route_cost);
        mMapView = (MapView) findViewById(R.id.routemap);
        mMapView.onCreate(savedInstanceState);
        mAmap = mMapView.getMap();
        mAmap.setOnMapLoadedListener(this);
        mThemeImage.setOnClickListener(this);
        mThemeText.setOnClickListener(this);
        mStartNaviButton.setOnClickListener(this);
        mRouteBackView.setOnClickListener(this);
        mRouteOverLay = new RouteOverLay(mAmap, null);
    }

    /**
     * ��ʼ��·��������Ϣ�ͼ�����·
     */
    private void initNavi() {
        
        mAmapNavi = AMapNavi.getInstance(this);
        AMapNaviPath naviPath = mAmapNavi.getNaviPath();
        if (naviPath == null) {
            return;
        }
        // ��ȡ·���滮��·����ʾ����ͼ��
        mRouteOverLay.setRouteInfo(naviPath);
        mRouteOverLay.addToMap();
        if (mIsMapLoaded) {
            mRouteOverLay.zoomToSpan();
        }

        double length = ((int) (naviPath.getAllLength() / (double) 1000 * 10))
                / (double) 10;
        // ������� �����Ӽ�
        int time = (naviPath.getAllTime() + 59) / 60;
        int cost = naviPath.getTollCost();
        mRouteDistanceView.setText(String.valueOf(length));
        mRouteTimeView.setText(String.valueOf(time));
        mRouteCostView.setText(String.valueOf(cost));
    }

    /**
     * ��ȡ��������������ʽ
     * 
     * @param themeColor
     * @return
     */
    private int getThemeStyle(String themeColor) {
        int theme = AMapNaviViewOptions.BLUE_COLOR_TOPIC;
        if (mTheme[0].equals(themeColor)) {
            theme = AMapNaviViewOptions.BLUE_COLOR_TOPIC;
        } else if (mTheme[1].equals(themeColor)) {
            theme = AMapNaviViewOptions.PINK_COLOR_TOPIC;
        } else if (mTheme[2].equals(themeColor)) {
            theme = AMapNaviViewOptions.WHITE_COLOR_TOPIC;
        }
        return theme;
    }

    // ------------------------------�¼�����-----------------------------
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        // ʵʱ��������
        case R.id.routestartnavi:
            Bundle bundle = new Bundle();
            bundle.putInt("theme", getThemeStyle(mThemeText.getText().toString()));
            Intent routeIntent = new Intent(NaviRouteActivity.this,
                    NaviCustomActivity.class);
            routeIntent.putExtras(bundle);
            startActivity(routeIntent);
            break;
        // ���ز���
        case R.id.route_back_view:
            Intent startIntent = new Intent(NaviRouteActivity.this,
            		map.class);
//            startIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(startIntent);
            //MainApplication.getInstance().deleteActivity(this);
            finish();
            break;
        }

    }

    /**
     * 
     * ���ؼ�����
     * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(NaviRouteActivity.this,
            		map.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            //MainApplication.getInstance().deleteActivity(this);
            finish();

        }
        return super.onKeyDown(keyCode, event);
    }

    // ------------------------------�������ڱ�����д����---------------------------
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
        initNavi();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onMapLoaded() {
        mIsMapLoaded = true;
        if (mRouteOverLay != null) {
            mRouteOverLay.zoomToSpan();

        }
    }

}
