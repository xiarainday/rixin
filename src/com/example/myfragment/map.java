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

	RouteSearch.FromAndTo fromAndTo;// ��ʼ����յ�ľ�γ��
	private RouteSearch mRouteSearch;

	private TextView mRouteDistanceView;// ������ʾ�ؼ�
	private TextView mRouteTimeView;// ʱ����ʾ�ؼ�
	private TextView mRouteCostView;// ������ʾ�ؼ�
	private AMapNaviView mAmapAMapNaviView;
	private AMapNavi mAmapNavi;
	private RouteOverLay routeOverLay;
	private boolean mIsMapLoaded = false;
	private AMapNaviListener mAmapNaviListener;
	private ProgressDialog mProgressDialog;// ·���滮������ʾ״̬
	private int mNaviMethod;
	private boolean mIsGetGPS = false;// ��¼GPS��λ�Ƿ�ɹ�
	private NaviLatLng mStartPoint = new NaviLatLng();
	private List<NaviLatLng> mStartPoints = new ArrayList<NaviLatLng>();
	private Marker mGPSMarker;
	// ��¼��ͼ����¼���Ӧ���������ѡ��ͬ����ͼ��Ӧ��ͬ
	private int mMapClickMode = MAP_CLICK_NO;
	private static final int MAP_CLICK_NO = 0;// ��ͼ�����ܵ���¼�
	private static final int MAP_CLICK_START = 1;// ��ͼ����������
	private static final int MAP_CLICK_WAY = 2;// ��ͼ�������;����
	private static final int MAP_CLICK_END = 3;// ��ͼ��������յ�
	
	double endlat;
	double endlon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map);

		mLocationErrText = (TextView) findViewById(R.id.map_err);

		// ��ȡ��ͼ�ؼ�����
		mMapView = (MapView) findViewById(R.id.map);
		// ��activityִ��onCreateʱִ��mMapView.onCreate(savedInstanceState)��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onCreate(savedInstanceState);

		if (aMap == null) {// ��ʼ��aMap
			aMap = mMapView.getMap();
		}
		aMap.setLocationSource(this);// ���ö�λ����
		aMap.getUiSettings().setMyLocationButtonEnabled(true);// ����Ĭ�϶�λ��ť�Ƿ���ʾ
		aMap.setMyLocationEnabled(true);// ����Ϊtrue��ʾ��ʾ��λ�㲢�ɴ�����λ��false��ʾ���ض�λ�㲢���ɴ�����λ��Ĭ����false
		// ���ö�λ������Ϊ��λģʽ���μ���AMap����λ��AMap.LOCATION_TYPE_LOCATE�������棨AMap.LOCATION_TYPE_MAP_FOLLOW��������������ת��AMap.LOCATION_TYPE_MAP_ROTATE��
		aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

		aMap.getUiSettings().setCompassEnabled(true);// ָ����
		aMap.getUiSettings().setZoomGesturesEnabled(true);// ͨ���������ŵ�ͼ
		aMap.getUiSettings().setScrollGesturesEnabled(true);// ����ƽ�ƣ���������ͼ
		aMap.getUiSettings().setRotateGesturesEnabled(true);// ������ת��ͼ
		aMap.getUiSettings().setTiltGesturesEnabled(true);// ������б��ͼ

		aMap.moveCamera(CameraUpdateFactory.zoomTo(16));
		// //�޸ĵ�ͼ�����ĵ�λ��
		// CameraPosition cp = aMap.getCameraPosition();
		// CameraPosition cpNew = CameraPosition.fromLatLngZoom(new
		// LatLng(31.22, 121.48), cp.zoom);
		// CameraUpdate cu = CameraUpdateFactory.newCameraPosition(cpNew);
		// aMap.moveCamera(cu);

		// ��ȡĿ���վ��γ��
		Intent intent = getIntent();
		String longitude = intent.getStringExtra("jingdu");//����
		String latitude = intent.getStringExtra("weidu");//γ��
		endlat=Double.parseDouble(latitude);
		endlon=Double.parseDouble(longitude);
		mRouteSearch = new RouteSearch(this);// ��ʼ��routeSearch ����
		mRouteSearch.setRouteSearchListener(this);// �������ݻص�������
		LatLonPoint start = new LatLonPoint(30.577082, 114.33167);// ���114.331662,30.577063���ѧԺ//����ʰȡ�����귴��
																	// Ϊ��ʦ����У���ľ�γ��31.286389,
																	// 118.378039
		LatLonPoint end = new LatLonPoint(endlat, endlon);// �յ�114.334886,30.57836ͼ���
																// Ϊ����ҽѧԺ�ľ�γ��31.288702,
																// 118.360532
		fromAndTo = new RouteSearch.FromAndTo(start, end);// ʵ����FromAndTo��������˼,�ĵ���//�����·���滮��iumeiyou����
		setVolumeControlStream(AudioManager.STREAM_MUSIC);// ������������
		// ��������ģ�鲥��
		mAmapNavi = AMapNavi.getInstance(this);// ��ʼ����������
		mRouteDistanceView = (TextView) findViewById(R.id.navi_route_distance_1);
		mRouteTimeView = (TextView) findViewById(R.id.navi_route_time_1);
		mRouteCostView = (TextView) findViewById(R.id.navi_route_cost_1);

		routeOverLay = new RouteOverLay(aMap, null);
		TTSController ttsManager = TTSController.getInstance(this);// ��ʼ������ģ��
		ttsManager.init();
		AMapNavi.getInstance(this).setAMapNaviListener(ttsManager);// ��������ģ�鲥��
		mGPSMarker = aMap.addMarker(new MarkerOptions()
				.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
						.decodeResource(getResources(),
								R.drawable.location_marker))));

		/* ��ʾApp icon����back�� */
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	public void doClick(View view) {

		switch (view.getId()) {
		case R.id.btn_bus:
			RouteSearch.BusRouteQuery busRouteQuery = new RouteSearch.BusRouteQuery(
					fromAndTo, RouteSearch.BusDefault, "�人", 0);// ��һ��������ʾ·���滮�������յ㣬�ڶ���������ʾ������ѯģʽ��������������ʾ������ѯ�������ţ����ĸ�������ʾ�Ƿ����ҹ�೵��0��ʾ������
			mRouteSearch.calculateBusRouteAsyn(busRouteQuery);
			break;
		case R.id.btn_car:
			RouteSearch.DriveRouteQuery driveRouteQuery = new RouteSearch.DriveRouteQuery(
					fromAndTo, RouteSearch.DrivingDefault, null, null, "");
			mRouteSearch.calculateDriveRouteAsyn(driveRouteQuery);

			aMap.clear();// �����ͼ�ϵı�ע֮��
			// �ݳ���
			List<NaviLatLng> mEndPoints = new ArrayList<NaviLatLng>();
			// 114.332926,30.576153
			List<NaviLatLng> mWayPoints = new ArrayList<NaviLatLng>();
			mWayPoints.add(new NaviLatLng(
					(mStartPoint.getLatitude() + endlat) / 2, (mStartPoint
							.getLongitude() + endlon) / 2));//;���㣬������е�ѡ��̫����
			mEndPoints.add(new NaviLatLng(endlat, endlon));
			mAmapNavi.calculateDriveRoute(mStartPoints, mEndPoints, mWayPoints,
					AMapNavi.DrivingDefault);

			// initNavi();
			break;
		case R.id.btn_walk:
			RouteSearch.WalkRouteQuery walkRouteQuery = new RouteSearch.WalkRouteQuery(
					fromAndTo, RouteSearch.WalkDefault);
			mRouteSearch.calculateWalkRouteAsyn(walkRouteQuery);

			aMap.clear();// �����ͼ�ϵı�ע֮��
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

	// �������ؼ�
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
	 * ��ʼ��·��������Ϣ�ͼ�����·
	 */
	private void initNavi() {

		// NaviLatLng start = new NaviLatLng(30.577082,
		// 114.33167);//114.33246,30.57548�λ�԰
		// NaviLatLng start = new
		// NaviLatLng(mStartPoint.getLatitude(),mStartPoint.getLongitude());
		// NaviLatLng end = new NaviLatLng(30.57836, 114.334886);//
		// 114.339726,30.573219
		// mAmapNavi.calculateWalkRoute(start, end);
		// //�ݳ���
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
		// ��ȡ·���滮��·����ʾ����ͼ��
		routeOverLay.setRouteInfo(naviPath);
		routeOverLay.addToMap();
		// if (mIsMapLoaded) {
		// routeOverLay.zoomToSpan();
		// }
		routeOverLay.zoomToSpan();

		double length = ((int) (naviPath.getAllLength() / (double) 1000 * 10))
				/ (double) 10;
		// ������� �����Ӽ�
		int time = (naviPath.getAllTime() + 59) / 60;
		int cost = naviPath.getTollCost();
		mRouteDistanceView.setText(String.valueOf(length));
		mRouteTimeView.setText(String.valueOf(time));
		mRouteCostView.setText(String.valueOf(cost));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// ��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// ��activityִ��onResumeʱִ��mMapView.onResume ()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onResume();
		// initNavi();
		// ���������߼���Ϊ�˱�֤������ҳ������λ�ͼ��뵼���ص�
		AMapNavi.getInstance(this).setAMapNaviListener(getAMapNaviListener());
		// NaviLatLng start = new NaviLatLng(30.577082,
		// 114.33167);//114.33246,30.57548�λ�԰
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
		// ��activityִ��onPauseʱִ��mMapView.onPause ()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onPause();
		// �±��߼����Ƴ�����
		AMapNavi.getInstance(this)
				.removeAMapNaviListener(getAMapNaviListener());
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// ��activityִ��onSaveInstanceStateʱִ��mMapView.onSaveInstanceState
		// (outState)��ʵ�ֵ�ͼ�������ڹ���
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
				mListener.onLocationChanged(amapLocation);// ��ʾϵͳС����

				mGPSMarker.setPosition(new LatLng(mStartPoint.getLatitude(),
						mStartPoint.getLongitude()));
				mStartPoints.clear();
				mStartPoints.add(mStartPoint);

			} else {
				String errText = "��λʧ��," + amapLocation.getErrorCode();// + ": "
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
			locationClient.setLocationListener(this);// ���ö�λ����
			// locationOption.setOnceLocation(true);// ����Ϊ���ζ�λ
			locationOption.setLocationMode(AMapLocationMode.Hight_Accuracy); // ���ö�λģʽΪ�͹���ģʽ
			locationClient.setLocationOption(locationOption); // ���ö�λ����
			locationClient.startLocation(); // ������λ
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
		BusPath busPath = busRouteResult.getPaths().get(0);// ȡ����һ��·��
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
				// ȡ����һ��·��
				// aMap.clear();// �����ͼ�ϵı�ע֮��
				// WalkRouteOverlay routeOverlay = new WalkRouteOverlay(this,
				// aMap, walkPath, walkRouteResult.getStartPos(),
				// walkRouteResult.getTargetPos());
				// routeOverlay.removeFromMap();
				// routeOverlay.addToMap();
				// routeOverlay.zoomToSpan();

				initNavi();
			} else {
				showToast("�Բ���û��������������ݣ�");
			}
		} else if (rCode == 1102) {
			showToast("����ʧ��,�����������ӣ�");
		} else if (rCode == 1002) {
			showToast("key��֤��Ч��");
		}else if (rCode == 3003) {
			showToast("������·��㡢�յ�������������·ʧ�ܣ�");
		}else {
			showToast("δ֪�������Ժ�����!������Ϊ" + rCode);
		}

		// mIsGetGPS=false;

	}

	/**
	 * toast��װ
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
	 * �����ص�����
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
					// ·���滮
					case R.id.btn_beginNavigation:
						Intent standIntent = new Intent(map.this,
								NaviRouteActivity.class);
						// standIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
						startActivity(standIntent);
						finish();
						break;
					// //ģ�⵼��
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
					showToast("·���滮����");
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
	 * ���ؽ��ȿ�
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