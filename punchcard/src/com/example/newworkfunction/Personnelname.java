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
	int posi;//��ȡ�����ж�Ӧλ�õ�Ԫ��
	PullToRefreshView mPullToRefreshView;
	private ListView lvpersonname;
	private ListViewpersonnameAadpter  adapter;
	ProgressDialog progressDialog;
//	int currentpage=1;//��ǰҳ
	JSONObject json;
//	String rows;//��ά���������
//	double total;//��ȡ���ܼ�¼��
//	public static int totalpage = 0;//��ҳ��
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
				intent.putExtra("positionnamenum", pp);//����ȡ����λ������ֱ�Ӵ���  ������һ��ֱ�ӻ�ȡ���ö�Ӧλ�õ�ֵ
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
				//				Toast.makeText(getApplicationContext(), "��������", Toast.LENGTH_SHORT).show();
//				postjiazai();
				Toast.makeText(getApplicationContext(), "�Ѽ���ȫ������", Toast.LENGTH_SHORT).show();
			}
		}, 1000);
	}
	@Override
	public void onHeaderRefresh(PullToRefreshView view) {

		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				//���ø���ʱ��
				//mPullToRefreshView.onHeaderRefreshComplete("�������:01-23 12:01");
				postpersonname();//����ˢ��ʱ���еĲ���
			}
		},1000);
	}
	private void postpersonname() {
		// TODO Auto-generated method stub
		progressDialog = ProgressDialog.show(this, "���Ե�...","��ȡ������...", true);
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
			// ������Ϣ���ͳ�����ʱ���ִ��Handler���������
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				//����һ   ����
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
					//					rows = objectt.getString("rows");//��ȡ��ά�����еĵڶ�άjson
//					total = Integer.parseInt(object.getString("total"));
//					Toast.makeText(getApplicationContext(), "��������Ϊ����"+total, Toast.LENGTH_SHORT).show();
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
					//�������ݽ���ʱ��ʾ����ʧ
					if(ListViewpersonnameAadpter.name.size()>0){
						progressDialog.dismiss();
						lvpersonname.setAdapter(adapter);
						mPullToRefreshView.onHeaderRefreshComplete();
					}else{
						progressDialog.dismiss();
						lvpersonname.setAdapter(adapter);
						Toast.makeText(getApplicationContext(), "�޼�¼", Toast.LENGTH_SHORT).show();
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
//			progressDialog = ProgressDialog.show(this, "���Ե�...","��ȡ������...", true);
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
//				// ������Ϣ���ͳ�����ʱ���ִ��Handler���������
//				public void handleMessage(Message msg) {
//					super.handleMessage(msg);
//					//����һ   ����
//					String strResult = msg.obj.toString();
//					int status_code = 200;
//					try {
//						JSONObject object = new JSONObject();
//						object = new JSONObject(strResult);
//						status_code = Integer.parseInt(object.getString("status"));
//						switch (status_code) {
//						case -3:
//							progressDialog.dismiss();
//							Toast.makeText(getApplicationContext() ,"�������ӳ�ʱ,���Ժ�����", Toast.LENGTH_SHORT).show();
//							mPullToRefreshView.onFooterRefreshComplete();
//							break;
//						case -4:
//							progressDialog.dismiss();
//							Toast.makeText(getApplicationContext() ,"����ʧ�ܣ�����������������", Toast.LENGTH_SHORT).show();
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
//						//					rows = objectt.getString("rows");//��ȡ��ά�����еĵڶ�άjson
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
//						//�������ݽ���ʱ��ʾ����ʧ
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
//			Toast.makeText(getApplicationContext(), "�Ѽ���ȫ������", Toast.LENGTH_SHORT).show();
////		}
//	}
}
