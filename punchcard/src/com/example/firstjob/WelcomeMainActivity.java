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
		requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ��������
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  
				WindowManager.LayoutParams.FLAG_FULLSCREEN);  //ȫ��
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
//		init();
		//����ʱ����
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//��תҳ��
				statyActivitylxt();
				WelcomeMainActivity.this.finish();
			}
		}, 3*1000);
	}
	public void statyActivitylxt() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(WelcomeMainActivity.this,MainActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);//���õ������붯���л�Ч������û��ʵ�� �����
	}
	// ��ʼ�� JPush������Ѿ���ʼ������û�е�¼�ɹ�����ִ�����µ�¼��
		private void init(){
			 JPushInterface.init(getApplicationContext());
		}
	//�˴���ӵ�Ϊͳ�������������ķ���
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
