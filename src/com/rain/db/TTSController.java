package com.rain.db;

import android.content.Context;
import android.os.Bundle;

import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.example.myfragment.R;
import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechListener;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SpeechUser;
import com.iflytek.cloud.speech.SynthesizerListener;

/**
 * 璇煶鎾姤缁勪欢
 */
public class TTSController implements SynthesizerListener, AMapNaviListener {

    public static TTSController ttsManager;
    boolean isfinish = true;
    private Context mContext;
    // 鍚堟垚瀵硅薄.
    private SpeechSynthesizer mSpeechSynthesizer;
    /**
     * 鐢ㄦ埛鐧诲綍鍥炶皟鐩戝惉鍣�.
     */
    private SpeechListener listener = new SpeechListener() {

        @Override
        public void onData(byte[] arg0) {
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error != null) {

            }
        }

        @Override
        public void onEvent(int arg0, Bundle arg1) {
        }
    };

    TTSController(Context context) {
        mContext = context;
    }

    public static TTSController getInstance(Context context) {
        if (ttsManager == null) {
            ttsManager = new TTSController(context);
        }
        return ttsManager;
    }

    public void init() {
        SpeechUser.getUser().login(mContext, null, null,
                "appid=" + mContext.getString(R.string.app_id), listener);
        // 鍒濆鍖栧悎鎴愬璞�.
        mSpeechSynthesizer = SpeechSynthesizer.createSynthesizer(mContext);
        initSpeechSynthesizer();
    }

    /**
     * 浣跨敤SpeechSynthesizer鍚堟垚璇煶锛屼笉寮瑰嚭鍚堟垚Dialog.
     *
     * @param
     */
    public void playText(String playText) {
        if (!isfinish) {
            return;
        }
        if (null == mSpeechSynthesizer) {
            // 鍒涘缓鍚堟垚瀵硅薄.
            mSpeechSynthesizer = SpeechSynthesizer.createSynthesizer(mContext);
            initSpeechSynthesizer();
        }
        // 杩涜璇煶鍚堟垚.
        mSpeechSynthesizer.startSpeaking(playText, this);

    }

    public void stopSpeaking() {
        if (mSpeechSynthesizer != null)
            mSpeechSynthesizer.stopSpeaking();
    }

    public void startSpeaking() {
        isfinish = true;
    }

    private void initSpeechSynthesizer() {
        // 璁剧疆鍙戦煶浜�
        mSpeechSynthesizer.setParameter(SpeechConstant.VOICE_NAME,
                mContext.getString(R.string.preference_default_tts_role));
        // 璁剧疆璇��
        mSpeechSynthesizer.setParameter(SpeechConstant.SPEED,
                "" + mContext.getString(R.string.preference_key_tts_speed));
        // 璁剧疆闊抽噺
        mSpeechSynthesizer.setParameter(SpeechConstant.VOLUME,
                "" + mContext.getString(R.string.preference_key_tts_volume));
        // 璁剧疆璇皟
        mSpeechSynthesizer.setParameter(SpeechConstant.PITCH,
                "" + mContext.getString(R.string.preference_key_tts_pitch));

    }

    @Override
    public void onBufferProgress(int arg0, int arg1, int arg2, String arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onCompleted(SpeechError arg0) {
        // TODO Auto-generated method stub
        isfinish = true;
    }

    @Override
    public void onSpeakBegin() {
        // TODO Auto-generated method stub
        isfinish = false;

    }

    @Override
    public void onSpeakPaused() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSpeakProgress(int arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSpeakResumed() {
        // TODO Auto-generated method stub

    }

    public void destroy() {
        if (mSpeechSynthesizer != null) {
            mSpeechSynthesizer.stopSpeaking();
        }
    }

    @Override
    public void onArriveDestination() {
        // TODO Auto-generated method stub
        this.playText("鍒拌揪鐩殑鍦�");
    }

    @Override
    public void onArrivedWayPoint(int arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onCalculateRouteFailure(int arg0) {
        this.playText("璺緞璁＄畻澶辫触锛岃妫�鏌ョ綉缁滄垨杈撳叆鍙傛暟");
    }

    @Override
    public void onCalculateRouteSuccess() {
        String calculateResult = "璺緞璁＄畻灏辩华";

        this.playText(calculateResult);
    }

    @Override
    public void onEndEmulatorNavi() {
        this.playText("瀵艰埅缁撴潫");

    }

    @Override
    public void onGetNavigationText(int arg0, String arg1) {
        // TODO Auto-generated method stub
        this.playText(arg1);
    }

    @Override
    public void onInitNaviFailure() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onInitNaviSuccess() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onLocationChange(AMapNaviLocation arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {
        // TODO Auto-generated method stub
        this.playText("鍓嶆柟璺嚎鎷ュ牭锛岃矾绾块噸鏂拌鍒�");
    }

    @Override
    public void onReCalculateRouteForYaw() {

        this.playText("鎮ㄥ凡鍋忚埅");
    }

    @Override
    public void onStartNavi(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTrafficStatusUpdate() {
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
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {

    }

    @Override
    public void hideCross() {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {

    }

    @Override
    public void hideLaneInfo() {

    }

    @Override
    public void onCalculateMultipleRoutesSuccess(int[] ints) {

    }

    @Override
    public void notifyParallelRoad(int i) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

    }


    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }
}

