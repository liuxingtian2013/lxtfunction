package com.example.newworkfunction;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Adapter.ListViewPersonnelnameselfAdapter;
import com.example.Adapter.ListViewpersonAadpter;
import com.example.Adapter.ListViewpersonnameAadpter;
import com.example.pulltoflash.PullToRefreshView;
import com.example.pulltoflash.PullToRefreshView.OnFooterRefreshListener;
import com.example.pulltoflash.PullToRefreshView.OnHeaderRefreshListener;
import com.example.punchcard.R;
import com.pm.common.XRequest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class Personnelname extends Activity implements OnHeaderRefreshListener,OnFooterRefreshListener{
	int posi;//获取数组中对应位置的元素
	PullToRefreshView mPullToRefreshView;
	private ListView lvpersonname;
	private ListViewpersonnameAadpter  adapter;
	ProgressDialog progressDialog;
//	int currentpage=1;//当前页
	JSONObject json;
//	String rows;//二维数组的数据
//	double total;//获取下总记录数
//	public static int totalpage = 0;//总页数
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personnelname);
		mPullToRefreshView = (PullToRefreshView)findViewById(R.id.main_pull_refresh_view);
		Intent intent = getIntent();
		String position = intent.getStringExtra("positionnum");
		posi = Integer.parseInt(position);
		lvpersonname = (ListView) findViewById(R.id.ExpandableListView02);
		adapter = new ListViewpersonnameAadpter(getApplicationContext());
		lvpersonname.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				String pp = Integer.toString(position);
				intent.putExtra("positionnamenum", pp);//将获取到的位置数字直接传入  从另外一边直接获取所得对应位置的值
				intent.setClass(getApplicationContext(), Personnelnameself.class);
				startActivity(intent);
			}
		});
		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		postpersonname();
	}


	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				mPullToRefreshView.onFooterRefreshComplete();
				//				Toast.makeText(getApplicationContext(), "上拉操作", Toast.LENGTH_SHORT).show();
//				postjiazai();
				Toast.makeText(getApplicationContext(), "已加载全部数据", Toast.LENGTH_SHORT).show();
			}
		}, 1000);
	}
	@Override
	public void onHeaderRefresh(PullToRefreshView view) {

		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				//设置更新时间
				//mPullToRefreshView.onHeaderRefreshComplete("最近更新:01-23 12:01");
				postpersonname();//下拉刷新时进行的操作
			}
		},1000);
	}
	private void postpersonname() {
		// TODO Auto-generated method stub
		progressDialog = ProgressDialog.show(this, "请稍等...","获取数据中...", true);
		progressDialog.setCancelable(true);
		final List<NameValuePair> params = new ArrayList<NameValuePair>();
//		int a =  currentpage*10;
//		params.add(new BasicNameValuePair("page","1"));
//		params.add(new BasicNameValuePair("rows",a+""));
		long sysTime = System.currentTimeMillis();
		CharSequence sysTimeStr = DateFormat.format("yyyy-MM-dd", sysTime);
		params.add(new BasicNameValuePair("date",sysTimeStr+""));
		params.add(new BasicNameValuePair("id",(ListViewpersonAadpter.bumenmingchenid.get(posi))+""));
		XRequest request = new XRequest();
		request.PostServerAttendance("get_real_time", params, new Handler() {
			// 当有消息发送出来的时候就执行Handler的这个方法
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				//方法一   遍历
				String strResult = msg.obj.toString();
				int status_code = 200;
				try {
					JSONObject object = new JSONObject();
					object = new JSONObject(strResult);
					status_code = Integer.parseInt(object.getString("status"));
					String message = "";
					try {
						message = object.getString("msg").toString();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					switch (status_code) {
					case -1:
						progressDialog.dismiss();
						Toast.makeText(getApplicationContext() ,message, Toast.LENGTH_SHORT).show();
						mPullToRefreshView.onHeaderRefreshComplete();
						break;
					}
				} catch (NumberFormatException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (JSONException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
//					JSONObject objectt = new JSONObject();
					//					//					if(strResult!=null && !strResult.equals("")){
//					objectt = new JSONObject(strResult);
					//					rows = objectt.getString("rows");//获取二维数组中的第二维json
//					total = Integer.parseInt(object.getString("total"));
//					Toast.makeText(getApplicationContext(), "看看总数为多少"+total, Toast.LENGTH_SHORT).show();
//					double signopage = total/10;
//					int ii = (int)signopage;
//					double rr = signopage - ii;
//					if(rr>0){
//						totalpage=ii+1;
//					}else{totalpage = ii;}
					JSONArray jsonlxt;
					jsonlxt = new JSONArray(strResult);
					ListViewpersonnameAadpter.name.clear();
					ListViewpersonnameAadpter.nameid.clear();
					ListViewpersonnameAadpter.checktimefirst.clear();
					ListViewpersonnameAadpter.checktimelast.clear();
					for(int i=0 ; i < jsonlxt.length() ;i++){
						try {
							json=jsonlxt.getJSONObject(i);
							ListViewpersonnameAadpter.name.add(json.getString("EmpName"));
							ListViewpersonnameAadpter.nameid.add(json.getString("EmpId"));
							ListViewpersonnameAadpter.checktimefirst.add(json.getString("CheckTimeFirst"));
							ListViewpersonnameAadpter.checktimelast.add(json.getString("CheckTimeLast"));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					//加载数据结束时提示框消失
					if(ListViewpersonnameAadpter.name.size()>0){
						progressDialog.dismiss();
						lvpersonname.setAdapter(adapter);
						mPullToRefreshView.onHeaderRefreshComplete();
					}else{
						progressDialog.dismiss();
						lvpersonname.setAdapter(adapter);
						Toast.makeText(getApplicationContext(), "无记录", Toast.LENGTH_SHORT).show();
						mPullToRefreshView.onHeaderRefreshComplete();
					}
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
				);
	}
//	private void postjiazai() {
//		// TODO Auto-generated method stub
////		if(currentpage<totalpage){
//			progressDialog = ProgressDialog.show(this, "请稍等...","获取数据中...", true);
//			progressDialog.setCancelable(true);
//			final List<NameValuePair> params = new ArrayList<NameValuePair>();
////			params.add(new BasicNameValuePair("page",currentpage+""));
////			params.add(new BasicNameValuePair("rows","10"));
//			long sysTime = System.currentTimeMillis();
//			CharSequence sysTimeStr = DateFormat.format("  yyyy-MM-dd ", sysTime);
//			params.add(new BasicNameValuePair("date",sysTimeStr+""));
//			params.add(new BasicNameValuePair("id",ListViewpersonAadpter.bumenmingchenid.get(posi)+""));
//			XRequest request = new XRequest();
//			request.PostServerAttendance("get_real_time", params, new Handler() {
//				@SuppressLint("NewApi") @Override
//				// 当有消息发送出来的时候就执行Handler的这个方法
//				public void handleMessage(Message msg) {
//					super.handleMessage(msg);
//					//方法一   遍历
//					String strResult = msg.obj.toString();
//					int status_code = 200;
//					try {
//						JSONObject object = new JSONObject();
//						object = new JSONObject(strResult);
//						status_code = Integer.parseInt(object.getString("status"));
//						switch (status_code) {
//						case -3:
//							progressDialog.dismiss();
//							Toast.makeText(getApplicationContext() ,"网络链接超时,请稍候重试", Toast.LENGTH_SHORT).show();
//							mPullToRefreshView.onFooterRefreshComplete();
//							break;
//						case -4:
//							progressDialog.dismiss();
//							Toast.makeText(getApplicationContext() ,"联网失败，请检查您的网络设置", Toast.LENGTH_SHORT).show();
//							mPullToRefreshView.onFooterRefreshComplete();
//							break;
//						}
//					} catch (NumberFormatException e2) {
//						// TODO Auto-generated catch block
//						e2.printStackTrace();
//					} catch (JSONException e2) {
//						// TODO Auto-generated catch block
//						e2.printStackTrace();
//					}
//					try {
//						//					JSONObject objectt = new JSONObject();
//						//					//					if(strResult!=null && !strResult.equals("")){
//						//					objectt = new JSONObject(strResult);
//						//					rows = objectt.getString("rows");//获取二维数组中的第二维json
//						JSONArray jsonlxt;
//						jsonlxt = new JSONArray(strResult);
//						for(int i=0 ; i < jsonlxt.length() ;i++){
//							try {
//								json=jsonlxt.getJSONObject(i);
//								ListViewpersonnameAadpter.name.add(json.getString("EmpName"));
//								ListViewpersonnameAadpter.nameid.add(json.getString("EmpId"));
//								ListViewpersonnameAadpter.checktimefirst.add(json.getString("CheckTimeFirst"));
//								ListViewpersonnameAadpter.checktimefirst.add(json.getString("CheckTimeLast"));
//							} catch (JSONException e) {
//								e.printStackTrace();
//							}
//						}
//						//加载数据结束时提示框消失
//						progressDialog.dismiss();
//						lvpersonname.setAdapter(adapter);
//						mPullToRefreshView.onFooterRefreshComplete();
//					} catch (JSONException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//				}
//			}
//					);
////			currentpage++;
////		}else{
//			Toast.makeText(getApplicationContext(), "已加载全部数据", Toast.LENGTH_SHORT).show();
////		}
//	}
}
