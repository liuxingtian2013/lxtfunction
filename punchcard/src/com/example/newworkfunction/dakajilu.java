package com.example.newworkfunction;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Adapter.ListViewpersonnameAadpter;
import com.example.Adapter.listviewadapterdakajilu;
import com.example.firstjob.MainActivity;
import com.example.pulltoflash.PullToRefreshView;
import com.example.pulltoflash.PullToRefreshView.OnFooterRefreshListener;
import com.example.pulltoflash.PullToRefreshView.OnHeaderRefreshListener;
import com.example.punchcard.R;
import com.pm.common.XRequest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
public class dakajilu extends Activity implements OnHeaderRefreshListener,OnFooterRefreshListener{
	PullToRefreshView mPullToRefreshView;
	TextView tv;
	ListView mlistview;
	ImageButton ib ;
	listviewadapterdakajilu listviewadapter;
	ProgressDialog progressDialog;
	JSONObject json ;
	public static int currentpage=1;//��ǰҳ
	String rows;//��ȡ��ά����ı�������Ҫ
	double total;//��ȡ���ܼ�¼��
	public static int totalpage = 0;//��ҳ��
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ��������
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dakajilu);
        ib = (ImageButton) findViewById(R.id.shuaxinbangong_ib);
        ib.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				post();
			}
		});
		mPullToRefreshView = (PullToRefreshView)findViewById(R.id.main_pull_refresh_view);
		listviewadapter = new listviewadapterdakajilu(getApplicationContext());
		mlistview= (ListView) findViewById(R.id.list);
		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		if (listviewadapterdakajilu.shijian.size() > 0) {
			mlistview.setAdapter(listviewadapter);
		} else {
			post();
		}
	}
	public void post(){
		String uid = MainActivity.getuserid();
		progressDialog = ProgressDialog.show(this, "���Ե�...","��ȡ������...", true);
//		progressDialog.setCancelable(true);
		final List<NameValuePair> params = new ArrayList<NameValuePair>();
		int a =  currentpage*10;
		params.add(new BasicNameValuePair("emp",uid));
		params.add(new BasicNameValuePair("page","1"));
		params.add(new BasicNameValuePair("rows",a+""));
		XRequest request = new XRequest();
		request.PostServerAttendance("get_check_record", params, new Handler() {
			@Override
			// ������Ϣ���ͳ�����ʱ���ִ��Handler���������
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				//����һ   ����
				String strResult = msg.obj.toString();
//				System.out.println(strResult);
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
					JSONObject objectt = new JSONObject();
					//					if(strResult!=null && !strResult.equals("")){
					objectt = new JSONObject(strResult);
					total = Integer.parseInt(objectt.getString("total"));
					double signopage = total/10;
					int ii = (int)signopage;
					double rr = signopage - ii;
					if(rr>0){
						totalpage=ii+1;
					}else{totalpage = ii;}
					rows = objectt.getString("rows");//��ȡ��ά�����еĵڶ�άjson
					JSONArray jsonlxt;
					jsonlxt = new JSONArray(rows);
					listviewadapterdakajilu.shijian.clear();
					listviewadapterdakajilu.zhuangtai.clear();
					for(int i=0 ; i < jsonlxt.length() ;i++){
						try {
							json=jsonlxt.getJSONObject(i);
							listviewadapterdakajilu.shijian.add(json.getString("CheckTime"));
							listviewadapterdakajilu.zhuangtai.add(json.getString("state"));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					//�������ݽ���ʱ��ʾ����ʧ
					if(listviewadapterdakajilu.shijian.size()>0){
						progressDialog.dismiss();
						mlistview.setAdapter(listviewadapter);
						mPullToRefreshView.onHeaderRefreshComplete();
					}else{
						progressDialog.dismiss();
						mlistview.setAdapter(listviewadapter);
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
	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				mPullToRefreshView.onFooterRefreshComplete();
				postjiazai();
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
				post();//����ˢ��ʱ���еĲ���
			}
		},1000);
	}
	public void postjiazai(){
		String uid = MainActivity.getuserid();
		if(currentpage<totalpage){
		progressDialog = ProgressDialog.show(this, "���Ե�...","��ȡ������...", true);
		progressDialog.setCancelable(true);
		final List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("emp",uid));
		params.add(new BasicNameValuePair("page",currentpage+""));
		params.add(new BasicNameValuePair("rows","10"));
		XRequest request = new XRequest();
		request.PostServerAttendance("get_check_record", params, new Handler() {
			@Override
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
						//						Toast.makeText(getApplicationContext(), "ִ��postˢ�����", Toast.LENGTH_SHORT).show();
						mPullToRefreshView.onFooterRefreshComplete();
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
					JSONObject objectt = new JSONObject();
					objectt = new JSONObject(strResult);
					rows = objectt.getString("rows");//��ȡ��ά�����еĵڶ�άjson
					JSONArray jsonlxt;
					jsonlxt = new JSONArray(rows);
					for(int i=0 ; i < jsonlxt.length() ;i++){
						try {
							json=jsonlxt.getJSONObject(i);
							listviewadapterdakajilu.shijian.add(json.getString("CheckTime"));
							listviewadapterdakajilu.zhuangtai.add(json.getString("state"));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					//�������ݽ���ʱ��ʾ����ʧ
					progressDialog.dismiss();
					mlistview.setAdapter(listviewadapter);
//					Toast.makeText(getApplicationContext(), "������ˢ��", Toast.LENGTH_SHORT).show();
					mPullToRefreshView.onFooterRefreshComplete();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
				);
		currentpage++;
	}else{
		Toast.makeText(getApplicationContext(), "�Ѽ���ȫ������", Toast.LENGTH_SHORT).show();
		mPullToRefreshView.onFooterRefreshComplete();
	}
		}
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//
//		if (keyCode == KeyEvent.KEYCODE_BACK
//				&& event.getRepeatCount() == 0) {
//			dakajilu.this.finish();
//			Intent intent1 = new Intent();
//			intent1.setClass(getApplicationContext(), punchcard.class);
//			startActivity(intent1);
//			return true;
//		}
//		return super.onKeyDown(keyCode, event);
//	}
}
