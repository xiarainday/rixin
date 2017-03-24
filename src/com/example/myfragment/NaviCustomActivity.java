package com.example.myfragment;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.rain.db.Constant;
import com.rain.db.TTSController;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

public class NaviCustomActivity extends Activity implements
		AMapNaviViewListener {
	private AMapNaviView mAmapAMapNaviView;
	// �����������õĲ���
    private boolean mDayNightFlag = Constant.DAY_MODE;// Ĭ��Ϊ����ģʽ
    private boolean mDeviationFlag = Constant.YES_MODE;// Ĭ�Ͻ���ƫ������
    private boolean mJamFlag = Constant.YES_MODE;// Ĭ�Ͻ���ӵ������
    private boolean mTrafficFlag = Constant.OPEN_MODE;// Ĭ�Ͻ��н�ͨ����
    private boolean mCameraFlag = Constant.OPEN_MODE;// Ĭ�Ͻ�������ͷ����
    private boolean mScreenFlag = Constant.YES_MODE;// Ĭ������Ļ����
    // ����������
    private int mThemeStle;
    // ��������
    private AMapNaviListener mAmapNaviListener;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_navi);
		//����������ʼ
        TTSController.getInstance(this).startSpeaking();
        // ʵʱ������ʽ���е���
        AMapNavi.getInstance(this).startNavi(AMapNavi.GPSNaviMode);
		
		init(savedInstanceState);
	}

	/**
	 * ��ʼ��
	 * 
	 * @param savedInstanceState
	 */
	private void init(Bundle savedInstanceState) {
		mAmapAMapNaviView = (AMapNaviView) findViewById(R.id.navimap);
		mAmapAMapNaviView.onCreate(savedInstanceState);
		// ���õ����������
		mAmapAMapNaviView.setAMapNaviViewListener(this);
		setAmapNaviViewOptions();

//		// ����ģ���ٶ�
//		AMapNavi.getInstance(this).setEmulatorNaviSpeed(100);
//		// ����ģ�⵼��
//		AMapNavi.getInstance(this).startNavi(AMapNavi.EmulatorNaviMode);
	}	
	/**
     * ���õ����Ĳ���
     */
    private void setAmapNaviViewOptions() {
        if (mAmapAMapNaviView == null) {
            return;
        }
        AMapNaviViewOptions viewOptions = new AMapNaviViewOptions();
        viewOptions.setSettingMenuEnabled(true);// ���õ���setting����
        viewOptions.setNaviNight(mDayNightFlag);// ���õ����Ƿ�Ϊ��ҹģʽ
        viewOptions.setReCalculateRouteForYaw(mDeviationFlag);// ���õ�ƫ���Ƿ�����
        viewOptions.setReCalculateRouteForTrafficJam(mJamFlag);// ���ý�ͨӵ���Ƿ�����
        viewOptions.setTrafficInfoUpdateEnabled(mTrafficFlag);// �����Ƿ����·��
        viewOptions.setCameraInfoUpdateEnabled(mCameraFlag);// ��������ͷ����
        viewOptions.setScreenAlwaysBright(mScreenFlag);// ������Ļ�������
        viewOptions.setNaviViewTopic(mThemeStle);// ���õ�������������ʽ

        mAmapAMapNaviView.setViewOptions(viewOptions);

    }
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
                    // ������Ƶ������ʱ��������,�������֮��
					int i = 0;
					i++;
					if (i >= 5) {
						AMapNaviViewOptions viewOptions = new AMapNaviViewOptions();
						viewOptions.setReCalculateRouteForYaw(false);
						mAmapAMapNaviView.setViewOptions(viewOptions);
					}
                }

                @Override
                public void onReCalculateRouteForTrafficJam() {

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

                }

                @Override
                public void onCalculateRouteFailure(int arg0) {

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
	 * �����������½Ƿ��ذ�ť�ص�
	 * 
	 */
	@Override
	public void onNaviCancel() {
		Intent intent = new Intent(NaviCustomActivity.this,
				map.class);
//		intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(intent);
		finish();
	}

	/**
	 * �����������½ǹ������ð�ť�ص�
	 * 
	 */
	@Override
	public void onNaviSetting() {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.THEME, mThemeStle);
        bundle.putBoolean(Constant.DAY_NIGHT_MODE, mDayNightFlag);
        bundle.putBoolean(Constant.DEVIATION, mDeviationFlag);
        bundle.putBoolean(Constant.JAM, mJamFlag);
        bundle.putBoolean(Constant.TRAFFIC, mTrafficFlag);
        bundle.putBoolean(Constant.CAMERA, mCameraFlag);
        bundle.putBoolean(Constant.SCREEN, mScreenFlag);
        Intent intent = new Intent(NaviCustomActivity.this,
                NaviSettingActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);

	}

	@Override
	public void onNaviMapMode(int arg0) {
		// TODO Auto-generated method stub

	}

	private void processBundle(Bundle bundle) {

		if (bundle != null) {
			mDayNightFlag = bundle.getBoolean(Constant.DAY_NIGHT_MODE,
					mDayNightFlag);
			mDeviationFlag = bundle.getBoolean(Constant.DEVIATION, mDeviationFlag);
			mJamFlag = bundle.getBoolean(Constant.JAM, mJamFlag);
			mTrafficFlag = bundle.getBoolean(Constant.TRAFFIC, mTrafficFlag);
			mCameraFlag = bundle.getBoolean(Constant.CAMERA, mCameraFlag);
			mScreenFlag = bundle.getBoolean(Constant.SCREEN, mScreenFlag);
			mThemeStle = bundle.getInt(Constant.THEME);

		}
	}
	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		setIntent(intent);
	}
	/**
	 * ���ؼ��̼���
	 * 
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(NaviCustomActivity.this,
					map.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	// ------------------------------�������ڷ���---------------------------
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mAmapAMapNaviView.onSaveInstanceState(outState);
	}

	@Override
	public void onResume() {
		super.onResume();
		Bundle bundle = getIntent().getExtras();
        processBundle(bundle);
        setAmapNaviViewOptions();
        AMapNavi.getInstance(this).setAMapNaviListener(getAMapNaviListener());
		mAmapAMapNaviView.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		mAmapAMapNaviView.onPause();
		AMapNavi.getInstance(this).removeAMapNaviListener(getAMapNaviListener());
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mAmapAMapNaviView.onDestroy();
		 //ҳ�����ʱ��ֹͣ��������
        TTSController.getInstance(this).stopSpeaking();
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
