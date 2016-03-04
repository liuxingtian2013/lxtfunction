package com.newworkfunction.xiangmu;

import com.example.punchcard.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

public class Contractdetails extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contractdetails);
		Intent intent = getIntent();
		String pp = intent.getStringExtra("contractnumber");
	}
}
