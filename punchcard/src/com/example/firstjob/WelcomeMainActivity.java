package com.example.firstjob;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;

import com.example.punchcard.R;
import com.example.updataAPK.UpdateManager;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
public class WelcomeMainActivity extends Activity {
	private Timer timer;
	private ImageView IV;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
				WindowManager.LayoutParams.FLAG_FULLSCREEN);  //全屏
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
//		init();
		//倒计时三秒
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//跳转页面
				statyActivitylxt();
				WelcomeMainActivity.this.finish();
			}
		}, 3*1000);
	}
	public void statyActivitylxt() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(WelcomeMainActivity.this,MainActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);//设置淡出淡入动画切换效果这里没有实现 待解决
	}
	// 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
		private void init(){
			 JPushInterface.init(getApplicationContext());
		}
	//此处添加的为统计推送总条数的方法
	@Override
	protected void onResume() {
		super.onResume();
		JPushInterface.onResume(this);
	}
	@Override
	protected void onPause() {
		super.onPause();
		JPushInterface.onPause(this);
	}
}
