package com.newworkfunction.bangong;
import com.example.Adapter.Listviewgonggaoadapter;
import com.example.Adapter.Listviewzhiduadapter;
import com.example.punchcard.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageButton;
public class ZhiduParticulars extends Activity {
	/** Called when the activity is first created. */
	String str;
	private WebView web;
	int posi;//获取数组中对应位置的元素
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhiduparticulars);
		Intent intent = getIntent();
		String position = intent.getStringExtra("positionnum");
		posi = Integer.parseInt(position);
		web = (WebView) findViewById(R.id.zhidu_webview);
		web.getSettings().setJavaScriptEnabled(true);
		web.loadUrl("http://192.168.0.163/manage/oa/notice/show.aspx?id="+Listviewzhiduadapter.id.get(posi));
		initview();
	}
	/**
	 * 
	 */
	private void initview() {
		// TODO Auto-generated method stubS
		ImageButton shuaxinib = (ImageButton) findViewById(R.id.shuaxinzhiduxiangqing_ib);
		ImageButton fanhuiib = (ImageButton) findViewById(R.id.fanhuizhidu_ib);
		shuaxinib.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				web.loadUrl("http://192.168.0.163/manage/oa/notice/show.aspx?id="+Listviewzhiduadapter.id.get(posi));
			}
		});
		fanhuiib.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ZhiduParticulars.this.finish();
			}
		});
	}
}