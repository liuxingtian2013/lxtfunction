package com.example.firstjob;

import com.example.punchcard.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class ForgetActivity extends Activity{
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ��������
	setContentView(R.layout.forget);
}
}
