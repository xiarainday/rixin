package com.example.myfragment;

import com.rain.xiaoguo.yhfh;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class xxzx extends Activity{
	
	 protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xxzx);

		/* 显示App icon左侧的back键 */
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		final TextView tv = (TextView) findViewById(R.id.tv_xxzx);
		Button btn = (Button) findViewById(R.id.btn_xxzx);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				tv.setText("点击反映了");
			}
		});
		       	        
	 }
	 //顶部返回键
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
	
//	//这里的类调用有问题
//	yhfh yh = new yhfh(this);
//	@Override
//	public boolean dispatchTouchEvent(MotionEvent event) {		
//		return yh.dispatchTouchEvent(event);
//		//return super.dispatchTouchEvent(event);
//	}
	
	 
	// 手指上下滑动时的最小速度
	private static final int YSPEED_MIN = 1000;
	// 手指向右滑动时的最小距离
	private static final int XDISTANCE_MIN = 50;
	// 手指向上滑或下滑时的最小距离
	private static final int YDISTANCE_MIN = 100;
	// 记录手指按下时的横坐标。
	private float xDown;
	// 记录手指按下时的纵坐标。
	private float yDown;
	// 记录手指移动时的横坐标。
	private float xMove;
	// 记录手指移动时的纵坐标。
	private float yMove;
	// 用于计算手指滑动的速度。
	private VelocityTracker mVelocityTracker;

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		createVelocityTracker(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			xDown = event.getRawX();
			yDown = event.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			xMove = event.getRawX();
			yMove = event.getRawY();
			// 滑动的距离
			int distanceX = (int) (xMove - xDown);
			int distanceY = (int) (yMove - yDown);
			// 获取顺时速度
			int ySpeed = getScrollVelocity();
			// 关闭Activity需满足以下条件：
			// 1.x轴滑动的距离>XDISTANCE_MIN
			// 2.y轴滑动的距离在YDISTANCE_MIN范围内
			// 3.y轴上（即上下滑动的速度）<XSPEED_MIN，如果大于，则认为用户意图是在上下滑动而非左滑结束Activity
			if (distanceX > XDISTANCE_MIN
					&& (distanceY < YDISTANCE_MIN && distanceY > -YDISTANCE_MIN)
					&& ySpeed < YSPEED_MIN) {
				finish();
			}
			break;
		case MotionEvent.ACTION_UP:
			recycleVelocityTracker();
			break;
		default:
			break;
		}
		return super.dispatchTouchEvent(event);
	}

	/**
	 * 创建VelocityTracker对象，并将触摸界面的滑动事件加入到VelocityTracker当中。
	 * 
	 * @param event
	 * 
	 */
	private void createVelocityTracker(MotionEvent event) {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(event);
	}

	/**
	 * 回收VelocityTracker对象。
	 */
	private void recycleVelocityTracker() {
		mVelocityTracker.recycle();
		mVelocityTracker = null;
	}

	/**
	 * 
	 * @return 滑动速度，以每秒钟移动了多少像素值为单位。
	 */
	private int getScrollVelocity() {
		mVelocityTracker.computeCurrentVelocity(1000);
		int velocity = (int) mVelocityTracker.getYVelocity();
		return Math.abs(velocity);
	}

	 
	 
}
