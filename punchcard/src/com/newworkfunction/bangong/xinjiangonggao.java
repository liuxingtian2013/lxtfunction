package com.newworkfunction.bangong;


import com.example.punchcard.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;
public class xinjiangonggao extends Activity implements OnClickListener{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ��������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xinjiangonggao);
		initview();
	}
	private void initview() {
		// TODO Auto-generated method stub
		findViewById(R.id.shangchuanfujian).setOnClickListener(this);
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "�ϴ�����", Toast.LENGTH_SHORT).show();
	}
}
