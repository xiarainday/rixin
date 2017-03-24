package com.example.myfragment;

import java.util.ArrayList;
import java.util.List;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.AMap.OnMapLoadedListener;
import com.amap.api.maps.LocationSource.OnLocationChangedListener;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.overlay.BusRouteOverlay;
import com.amap.api.maps.overlay.DrivingRouteOverlay;
import com.amap.api.maps.overlay.WalkRouteOverlay;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.RouteOverLay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.RouteSearch.OnRouteSearchListener;
import com.amap.api.services.route.RouteSearch.WalkRouteQuery;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.rain.db.TTSController;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class map extends Activity implements LocationSource,
		AMapLocationListener, OnRouteSearchListener, OnMapLoadedListener,
		AMapNaviViewListener {
	private MapView mMapView = null;
	private AMap aMap;
	// private LocationManagerProxy mLocationManagerProxy;
	private AMapLocationClient locationClient = null;
	private AMapLocationClientOption locationOption = null;
	private OnLocationChangedListener mListener;
	TextView mLocationErrText;

	RouteSearch.FromAndTo fromAndTo;// 起始点和终点的经纬度
	private RouteSearch mRouteSearch;

	private TextView mRouteDistanceView;// 距离显示控件
	private TextView mRouteTimeView;// 时间显示控件
	private TextView mRouteCostView;// 花费显示控件
	private AMapNaviView mAmapAMapNaviView;
	private AMapNavi mAmapNavi;
	private RouteOverLay routeOverLay;
	private boolean mIsMapLoaded = false;
	private AMapNaviListener mAmapNaviListener;
	private ProgressDialog mProgressDialog;// 路径规划过程显示状态
	private int mNaviMethod;
	private boolean mIsGetGPS = false;// 记录GPS定位是否成功
	private NaviLatLng mStartPoint = new NaviLatLng();
	private List<NaviLatLng> mStartPoints = new ArrayList<NaviLatLng>();
	private Marker mGPSMarker;
	// 记录地图点击事件相应情况，根据选择不同，地图响应不同
	private int mMapClickMode = MAP_CLICK_NO;
	private static final int MAP_CLICK_NO = 0;// 地图不接受点击事件
	private static final int MAP_CLICK_START = 1;// 地图点击设置起点
	private static final int MAP_CLICK_WAY = 2;// 地图点击设置途经点
	private static final int MAP_CLICK_END = 3;// 地图点击设置终点
	
	double endlat;
	double endlon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);

		mLocationErrText = (TextView) findViewById(R.id.map_err);

		// 获取地图控件引用
		mMapView = (MapView) findViewById(R.id.map);
		// 在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
		mMapView.onCreate(savedInstanceState);

		if (aMap == null) {// 初始化aMap
			aMap = mMapView.getMap();
		}
		aMap.setLocationSource(this);// 设置定位监听
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
		aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
		// 设置定位的类型为定位模式，参见类AMap。定位（AMap.LOCATION_TYPE_LOCATE）、跟随（AMap.LOCATION_TYPE_MAP_FOLLOW）根据面向方向旋转（AMap.LOCATION_TYPE_MAP_ROTATE）
		aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

		aMap.getUiSettings().setCompassEnabled(true);// 指南针
		aMap.getUiSettings().setZoomGesturesEnabled(true);// 通过手势缩放地图
		aMap.getUiSettings().setScrollGesturesEnabled(true);// 手势平移（滑动）地图
		aMap.getUiSettings().setRotateGesturesEnabled(true);// 手势旋转地图
		aMap.getUiSettings().setTiltGesturesEnabled(true);// 手势倾斜地图

		aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
		// //修改地图的中心点位置
		// CameraPosition cp = aMap.getCameraPosition();
		// CameraPosition cpNew = CameraPosition.fromLatLngZoom(new
		// LatLng(31.22, 121.48), cp.zoom);
		// CameraUpdate cu = CameraUpdateFactory.newCameraPosition(cpNew);
		// aMap.moveCamera(cu);

		// 获取目标电站经纬度
		Intent intent = getIntent();
		String longitude = intent.getStringExtra("jingdu");//经度
		String latitude = intent.getStringExtra("weidu");//纬度
		endlat=Double.parseDouble(latitude);
		endlon=Double.parseDouble(longitude);
		mRouteSearch = new RouteSearch(this);// 初始化routeSearch 对象
		mRouteSearch.setRouteSearchListener(this);// 设置数据回调监听器
		LatLonPoint start = new LatLonPoint(30.577082, 114.33167);// 起点114.331662,30.577063物电学院//坐标拾取的坐标反了
																	// 为安师大新校区的经纬度31.286389,
																	// 118.378039
		LatLonPoint end = new LatLonPoint(endlat, endlon);// 终点114.334886,30.57836图书馆
																// 为皖南医学院的经纬度31.288702,
																// 118.360532
		fromAndTo = new RouteSearch.FromAndTo(start, end);// 实例化FromAndTo，字面意思,哪到哪//这里的路径规划及iumeiyou用了
		setVolumeControlStream(AudioManager.STREAM_MUSIC);// 设置声音控制
		// 设置语音模块播报
		mAmapNavi = AMapNavi.getInstance(this);// 初始化导航引擎
		mRouteDistanceView = (TextView) findViewById(R.id.navi_route_distance_1);
		mRouteTimeView = (TextView) findViewById(R.id.navi_route_time_1);
		mRouteCostView = (TextView) findViewById(R.id.navi_route_cost_1);

		routeOverLay = new RouteOverLay(aMap, null);
		TTSController ttsManager = TTSController.getInstance(this);// 初始化语音模块
		ttsManager.init();
		AMapNavi.getInstance(this).setAMapNaviListener(ttsManager);// 设置语音模块播报
		mGPSMarker = aMap.addMarker(new MarkerOptions()
				.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
						.decodeResource(getResources(),
								R.drawable.location_marker))));

		/* 显示App icon左侧的back键 */
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	public void doClick(View view) {

		switch (view.getId()) {
		case R.id.btn_bus:
			RouteSearch.BusRouteQuery busRouteQuery = new RouteSearch.BusRouteQuery(
					fromAndTo, RouteSearch.BusDefault, "武汉", 0);// 第一个参数表示路径规划的起点和终点，第二个参数表示公交查询模式，第三个参数表示公交查询城市区号，第四个参数表示是否计算夜班车，0表示不计算
			mRouteSearch.calculateBusRouteAsyn(busRouteQuery);
			break;
		case R.id.btn_car:
			RouteSearch.DriveRouteQuery driveRouteQuery = new RouteSearch.DriveRouteQuery(
					fromAndTo, RouteSearch.DrivingDefault, null, null, "");
			mRouteSearch.calculateDriveRouteAsyn(driveRouteQuery);

			aMap.clear();// 清除地图上的标注之类
			// 驾车点
			List<NaviLatLng> mEndPoints = new ArrayList<NaviLatLng>();
			// 114.332926,30.576153
			List<NaviLatLng> mWayPoints = new ArrayList<NaviLatLng>();
			mWayPoints.add(new NaviLatLng(
					(mStartPoint.getLatitude() + endlat) / 2, (mStartPoint
							.getLongitude() + endlon) / 2));//途径点，这里的中点选择不太合理
			mEndPoints.add(new NaviLatLng(endlat, endlon));
			mAmapNavi.calculateDriveRoute(mStartPoints, mEndPoints, mWayPoints,
					AMapNavi.DrivingDefault);

			// initNavi();
			break;
		case R.id.btn_walk:
			RouteSearch.WalkRouteQuery walkRouteQuery = new RouteSearch.WalkRouteQuery(
					fromAndTo, RouteSearch.WalkDefault);
			mRouteSearch.calculateWalkRouteAsyn(walkRouteQuery);

			aMap.clear();// 清除地图上的标注之类
			NaviLatLng start = new NaviLatLng(mStartPoint.getLatitude(),
					mStartPoint.getLongitude());
			NaviLatLng end = new NaviLatLng(endlat, endlon);// 114.339726,30.573219
			mAmapNavi.calculateWalkRoute(start, end);

			// initNavi();

			break;
		case R.id.btn_beginNavigation:
			Intent standIntent = new Intent(map.this, NaviCustomActivity.class);
			standIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			// initNavi();
			startActivity(standIntent);
//			finish();
			break;
		}
	}

	// 顶部返回键
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * 初始化路线描述信息和加载线路
	 */
	private void initNavi() {

		// NaviLatLng start = new NaviLatLng(30.577082,
		// 114.33167);//114.33246,30.57548嘉会园
		// NaviLatLng start = new
		// NaviLatLng(mStartPoint.getLatitude(),mStartPoint.getLongitude());
		// NaviLatLng end = new NaviLatLng(30.57836, 114.334886);//
		// 114.339726,30.573219
		// mAmapNavi.calculateWalkRoute(start, end);
		// //驾车点
		// List<NaviLatLng> mEndPoints = new ArrayList<NaviLatLng>();
		// //114.332926,30.576153
		// List<NaviLatLng> mWayPoints = new ArrayList<NaviLatLng>();
		// mWayPoints.add(new
		// NaviLatLng((mStartPoint.getLatitude()+30.576153)/2,
		// (mStartPoint.getLongitude()+114.332926)/2));
		// mEndPoints.add(new NaviLatLng(30.57836, 114.334886));
		// mAmapNavi.calculateDriveRoute(mStartPoints, mEndPoints,mWayPoints,
		// AMapNavi.DrivingDefault);

		mAmapNavi.startGPS();
		TTSController.getInstance(this).startSpeaking();

		mAmapNavi = AMapNavi.getInstance(this);
		AMapNaviPath naviPath = mAmapNavi.getNaviPath();
		if (naviPath == null) {
			return;
		}
		// 获取路径规划线路，显示到地图上
		routeOverLay.setRouteInfo(naviPath);
		routeOverLay.addToMap();
		// if (mIsMapLoaded) {
		// routeOverLay.zoomToSpan();
		// }
		routeOverLay.zoomToSpan();

		double length = ((int) (naviPath.getAllLength() / (double) 1000 * 10))
				/ (double) 10;
		// 不足分钟 按分钟计
		int time = (naviPath.getAllTime() + 59) / 60;
		int cost = naviPath.getTollCost();
		mRouteDistanceView.setText(String.valueOf(length));
		mRouteTimeView.setText(String.valueOf(time));
		mRouteCostView.setText(String.valueOf(cost));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// 在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
		mMapView.onResume();
		// initNavi();
		// 以下两句逻辑是为了保证进入首页开启定位和加入导航回调
		AMapNavi.getInstance(this).setAMapNaviListener(getAMapNaviListener());
		// NaviLatLng start = new NaviLatLng(30.577082,
		// 114.33167);//114.33246,30.57548嘉会园
		// //NaviLatLng start = new NaviLatLng(location.getLatitude(),
		// location.getLongitude());
		// NaviLatLng end = new NaviLatLng(30.57836,
		// 114.334886);//114.339726,30.573219
		// mAmapNavi.calculateWalkRoute(start,end);
		// mAmapNavi.startGPS();
		// TTSController.getInstance(this).startSpeaking();
	}

	@Override
	protected void onPause() {
		super.onPause();
		deactivate();
		// 在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
		mMapView.onPause();
		// 下边逻辑是移除监听
		AMapNavi.getInstance(this)
				.removeAMapNaviListener(getAMapNaviListener());
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// 在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState
		// (outState)，实现地图生命周期管理
		mMapView.onSaveInstanceState(outState);
	}

	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		// TODO Auto-generated method stub
		if (mListener != null && amapLocation != null) {
			if (amapLocation != null && amapLocation.getErrorCode() == 0) {
				mLocationErrText.setVisibility(View.GONE);
				mStartPoint = new NaviLatLng(amapLocation.getLatitude(),
						amapLocation.getLongitude());
				mListener.onLocationChanged(amapLocation);// 显示系统小蓝点

				mGPSMarker.setPosition(new LatLng(mStartPoint.getLatitude(),
						mStartPoint.getLongitude()));
				mStartPoints.clear();
				mStartPoints.add(mStartPoint);

			} else {
				String errText = "定位失败," + amapLocation.getErrorCode();// + ": "
																		// +
																		// amapLocation.getErrorInfo()
				Log.e("AmapErr", errText);
				mLocationErrText.setVisibility(View.VISIBLE);
				mLocationErrText.setText(errText);
			}
		}
	}

	@Override
	public void activate(OnLocationChangedListener onLocationChangedListener) {
		// TODO Auto-generated method stub
		mListener = onLocationChangedListener;
		if (locationClient == null) {
			locationClient = new AMapLocationClient(
					this.getApplicationContext());
			locationOption = new AMapLocationClientOption();
			locationClient.setLocationListener(this);// 设置定位监听
			// locationOption.setOnceLocation(true);// 设置为单次定位
			locationOption.setLocationMode(AMapLocationMode.Hight_Accuracy); // 设置定位模式为低功耗模式
			locationClient.setLocationOption(locationOption); // 设置定位参数
			locationClient.startLocation(); // 启动定位
		}

	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		mListener = null;
		if (locationClient != null) {
			locationClient.stopLocation();
			locationClient.onDestroy();
		}
		locationClient = null;
		locationOption = null;
	}

	@Override
	public void onBusRouteSearched(BusRouteResult busRouteResult, int arg1) {
		// TODO Auto-generated method stub
		BusPath busPath = busRouteResult.getPaths().get(0);// 取其中一个路线
		aMap.clear();
		BusRouteOverlay routeOverlay = new BusRouteOverlay(this, aMap, busPath,
				busRouteResult.getStartPos(), busRouteResult.getTargetPos());
		routeOverlay.removeFromMap();
		routeOverlay.addToMap();
		routeOverlay.zoomToSpan();
	}

	@Override
	public void onDriveRouteSearched(DriveRouteResult driveRouteResult,
			int rCode) {
		// TODO Auto-generated method stub
		if (rCode == 1000) {
			// DrivePath drivePath = driveRouteResult.getPaths().get(0);
			// aMap.clear();
			// DrivingRouteOverlay routeOverlay = new DrivingRouteOverlay(this,
			// aMap, drivePath, driveRouteResult.getStartPos(),
			// driveRouteResult.getTargetPos());
			// routeOverlay.removeFromMap();
			// routeOverlay.addToMap();
			// routeOverlay.zoomToSpan();

			initNavi();
		}
	}

	@Override
	public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int rCode) {
		// TODO Auto-generated method stub
		if (rCode == 1000) {
			if (walkRouteResult != null && walkRouteResult.getPaths() != null
					&& walkRouteResult.getPaths().size() > 0) {
				// WalkPath walkPath = walkRouteResult.getPaths().get(0);//
				// 取其中一个路线
				// aMap.clear();// 清除地图上的标注之类
				// WalkRouteOverlay routeOverlay = new WalkRouteOverlay(this,
				// aMap, walkPath, walkRouteResult.getStartPos(),
				// walkRouteResult.getTargetPos());
				// routeOverlay.removeFromMap();
				// routeOverlay.addToMap();
				// routeOverlay.zoomToSpan();

				initNavi();
			} else {
				showToast("对不起，没有搜索到相关数据！");
			}
		} else if (rCode == 1102) {
			showToast("搜索失败,请检查网络连接！");
		} else if (rCode == 1002) {
			showToast("key验证无效！");
		}else if (rCode == 3003) {
			showToast("步行算路起点、终点距离过长导致算路失败！");
		}else {
			showToast("未知错误，请稍后重试!错误码为" + rCode);
		}

		// mIsGetGPS=false;

	}

	/**
	 * toast封装
	 * 
	 * @param str
	 */
	private void showToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onMapLoaded() {
		// TODO Auto-generated method stub
		mIsMapLoaded = true;
		if (routeOverLay != null) {
			routeOverLay.zoomToSpan();

		}
	}

	/**
	 * 导航回调函数
	 * 
	 * @return
	 */
	private AMapNaviListener getAMapNaviListener() {
		if (mAmapNaviListener == null) {

			mAmapNaviListener = new AMapNaviListener() {

				@Override
				public void onTrafficStatusUpdate() {
					// TODO Auto-generated method stub

				}

				@Override
				public void onStartNavi(int arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onReCalculateRouteForYaw() {
					// TODO Auto-generated method stub

				}

				@Override
				public void onReCalculateRouteForTrafficJam() {
					// TODO Auto-generated method stub

				}

				@Override
				public void onLocationChange(AMapNaviLocation location) {

				}

				@Override
				public void onInitNaviSuccess() {
					// TODO Auto-generated method stub

				}

				@Override
				public void onInitNaviFailure() {
					// TODO Auto-generated method stub

				}

				@Override
				public void onGetNavigationText(int arg0, String arg1) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onEndEmulatorNavi() {
					// TODO Auto-generated method stub

				}

				@Override
				public void onCalculateRouteSuccess() {
					dissmissProgressDialog();
					switch (mNaviMethod) {
					// 路径规划
					case R.id.btn_beginNavigation:
						Intent standIntent = new Intent(map.this,
								NaviRouteActivity.class);
						// standIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
						startActivity(standIntent);
						finish();
						break;
					// //模拟导航
					// case NAVI_METHOD:
					// Intent standIntent = new Intent(MainActivity.this,
					// NaviCustomActivity.class);
					// standIntent
					// .addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
					// startActivity(standIntent);
					// break;
					}
				}

				@Override
				public void onCalculateRouteFailure(int arg0) {
					dissmissProgressDialog();
					showToast("路径规划出错");
				}

				@Override
				public void onArrivedWayPoint(int arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onArriveDestination() {
					// TODO Auto-generated method stub

				}

				@Override
				public void onGpsOpenStatus(boolean arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onNaviInfoUpdated(AMapNaviInfo arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onNaviInfoUpdate(NaviInfo arg0) {

					// TODO Auto-generated method stub

				}

				@Override
				@Deprecated
				public void OnUpdateTrafficFacility(
						AMapNaviTrafficFacilityInfo arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				@Deprecated
				public void OnUpdateTrafficFacility(TrafficFacilityInfo arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void OnUpdateTrafficFacility(
						AMapNaviTrafficFacilityInfo[] arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void hideCross() {
					// TODO Auto-generated method stub

				}

				@Override
				public void hideLaneInfo() {
					// TODO Auto-generated method stub

				}

				@Override
				public void notifyParallelRoad(int arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onCalculateMultipleRoutesSuccess(int[] arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void showCross(AMapNaviCross arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void showLaneInfo(AMapLaneInfo[] arg0, byte[] arg1,
						byte[] arg2) {
					// TODO Auto-generated method stub

				}

				@Override
				public void updateAimlessModeCongestionInfo(
						AimLessModeCongestionInfo arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void updateAimlessModeStatistics(AimLessModeStat arg0) {
					// TODO Auto-generated method stub

				}
			};
		}
		return mAmapNaviListener;
	}

	/**
	 * 隐藏进度框
	 */
	private void dissmissProgressDialog() {
		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
		}
	}

	@Override
	public void onLockMap(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onNaviBackClick() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onNaviCancel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNaviMapMode(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNaviSetting() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNaviTurnClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNaviViewLoaded() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNextRoadClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScanViewButtonClick() {
		// TODO Auto-generated method stub

	}

}