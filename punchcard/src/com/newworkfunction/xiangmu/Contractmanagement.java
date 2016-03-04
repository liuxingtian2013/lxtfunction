package com.newworkfunction.xiangmu;

import com.example.Adapter.HetongguanliListviewadapter;
import com.example.pulltoflash.PullToRefreshView;
import com.example.pulltoflash.PullToRefreshView.OnFooterRefreshListener;
import com.example.pulltoflash.PullToRefreshView.OnHeaderRefreshListener;
import com.example.punchcard.R;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class Contractmanagement extends Activity implements OnFooterRefreshListener,OnHeaderRefreshListener,OnClickListener{
	PullToRefreshView mPullToRefreshView;
	private ImageButton fanhuiib,xinjianib;
	private HetongguanliListviewadapter adapter;
	private LinearLayout lyout;
	private ListView lv;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	super.onCreate(savedInstanceState);
	setContentView(R.layout.hetongguanli);
	initview();
}

private void initview() {//��ʼ���ؼ�
	// TODO Auto-generated method stub
	mPullToRefreshView = (PullToRefreshView) findViewById(R.id.main_pull_refresh_view);
	mPullToRefreshView.setOnFooterRefreshListener(this);
	mPullToRefreshView.setOnHeaderRefreshListener(this);
	findViewById(R.id.hetongfanhuiib).setOnClickListener(this);
	findViewById(R.id.xinjianhetong).setOnClickListener(this);
	findViewById(R.id.shuaxinyemian_hetong).setOnClickListener(this);
	lv = (ListView) findViewById(R.id.hetongguanliListView);
	adapter = new HetongguanliListviewadapter(getApplicationContext());//ʵ����������
	lv.setAdapter(adapter);
	lv.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view , int position,long id) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			String contractnumber = Integer.toString(position);
			intent.putExtra("contractnumber", contractnumber);
			intent.setClass(getApplicationContext(), Contractdetails.class);
			startActivity(intent);
			
		}
	});
}

@Override//����ˢ�²���
public void onHeaderRefresh(PullToRefreshView view) {
	// TODO Auto-generated method stub
	mPullToRefreshView.postDelayed(new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
	},1000);
}

@Override//�������ز���
public void onFooterRefresh(PullToRefreshView view) {
	// TODO Auto-generated method stub
	mPullToRefreshView.postDelayed(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
	},1000);
}

@Override//���а�ť�ĵ���¼�
public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.hetongfanhuiib:
		Contractmanagement.this.finish();
		break;
	case R.id.xinjianhetong:
		Toast.makeText(getApplicationContext(), "ģ���½��С�����", Toast.LENGTH_SHORT).show();
		break;
	case R.id.shuaxinyemian_hetong:
//		gethetonglist();
		break;
	}
}
}
