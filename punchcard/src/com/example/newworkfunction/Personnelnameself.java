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
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
public class Personnelnameself extends Activity implements OnHeaderRefreshListener,OnFooterRefreshListener{
	int posi;//��ȡ�����ж�Ӧλ�õ�Ԫ��
	PullToRefreshView mPullToRefreshView;
	public static DatePickDialogUtil datePicKDialog = null;
	ProgressDialog progressDialog;
	private String initEndDateTime = null; // ��ʼ������ʱ��
	private EditText et;
	ListViewPersonnelnameselfAdapter adapter;
	public static String a = "0",b ="0";//��ס�ǽ�������һ��id����һ��id
	JSONObject json;
	int currentpage=1;//��ǰҳ
	double total;//��ȡ���ܼ�¼��
	public static int totalpage = 0;//��ҳ��
	private ListView lvpersonnameself;
	String rows;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personnelnameself);
		mPullToRefreshView = (PullToRefreshView)findViewById(R.id.main_pull_refresh_view);
		TextView tv = (TextView)findViewById(R.id.personnameself_tv);
		et = (EditText)findViewById(R.id.personnelnameself_chakanshijan_et);
		et.setInputType(InputType.TYPE_NULL);
		et.setText(initEndDateTime);
		et.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(datePicKDialog==null){
					datePicKDialog = new DatePickDialogUtil(
							Personnelnameself.this, initEndDateTime);
					datePicKDialog.datePicKDialog(et);
				}
			}
		});
		ImageButton ib = (ImageButton) findViewById(R.id.personnelnameself_ib);
		Intent intent = getIntent();
		String position = intent.getStringExtra("positionnamenum");
		posi = Integer.parseInt(position);
		tv.setText(ListViewpersonnameAadpter.name.get(posi));
		a = ListViewpersonnameAadpter.nameid.get(posi).toString();
		lvpersonnameself = (ListView) findViewById(R.id.personnelnameself_lv);
		adapter = new ListViewPersonnelnameselfAdapter(getApplicationContext());
		ib.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				postpersonnameself();
			}
		});
		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		if (ListViewPersonnelnameselfAdapter.selftime.size() > 0 && b.equals(a)) {
			lvpersonnameself.setAdapter(adapter);
		} else{
			postpersonnameself();
		}
		b=a;//��һ��id����һ��id���н���
	}
	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				mPullToRefreshView.onFooterRefreshComplete();
				//				Toast.makeText(getApplicationContext(), "��������", Toast.LENGTH_SHORT).show();
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
				postpersonnameself();//����ˢ��ʱ���еĲ���
			}
		},1000);
	}
	public void postpersonnameself() {
		// TODO Auto-generated method stub
		progressDialog = ProgressDialog.show(this, "���Ե�...","��ȡ������...", true);
		progressDialog.setCancelable(true);
		final List<NameValuePair> params = new ArrayList<NameValuePair>();
		int a =  currentpage*10;
		params.add(new BasicNameValuePair("page","1"));
		params.add(new BasicNameValuePair("rows",a+""));
		params.add(new BasicNameValuePair("date",et.getText()+""));
		params.add(new BasicNameValuePair("emp",ListViewpersonnameAadpter.nameid.get(posi)+""));
		XRequest request = new XRequest();
		request.PostServerAttendance("get_check_record", params, new Handler() {
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
					case 500:
						Toast.makeText(getApplicationContext() ,message, Toast.LENGTH_SHORT).show();
						break;
					case 200:
						Toast.makeText(getApplicationContext() ,message, Toast.LENGTH_SHORT).show();
						break;
					case -1:
						Toast.makeText(getApplicationContext() ,message, Toast.LENGTH_SHORT).show();
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
					//					//					if(strResult!=null && !strResult.equals("")){
					objectt = new JSONObject(strResult);
					//					rows = objectt.getString("rows");//��ȡ��ά�����еĵڶ�άjson
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
					ListViewPersonnelnameselfAdapter.selftime.clear();
					for(int i=0 ; i < jsonlxt.length() ;i++){
						try {
							json=jsonlxt.getJSONObject(i);
							ListViewPersonnelnameselfAdapter.selftime.add(json.getString("CheckTime"));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					//�������ݽ���ʱ��ʾ����ʧ
					if(ListViewPersonnelnameselfAdapter.selftime.size()>0){
						progressDialog.dismiss();
						lvpersonnameself.setAdapter(adapter);
						mPullToRefreshView.onHeaderRefreshComplete();
					}else{
						progressDialog.dismiss();
						lvpersonnameself.setAdapter(adapter);
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
	public void postjiazai() {
		// TODO Auto-generated method stub
		if(currentpage<totalpage){
			progressDialog = ProgressDialog.show(this, "���Ե�...","��ȡ������...", true);
			progressDialog.setCancelable(true);
			final List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("page",currentpage+""));
			params.add(new BasicNameValuePair("rows","10"));
			params.add(new BasicNameValuePair("date",et.getText()+""));
			params.add(new BasicNameValuePair("emp",ListViewpersonnameAadpter.nameid.get(posi)+""));
			XRequest request = new XRequest();
			request.PostServerAttendance("get_check_record", params, new Handler() {
				@SuppressLint("NewApi") @Override
				// ������Ϣ���ͳ�����ʱ���ִ��Handler���������
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					//����һ   ����
					String strResult = msg.obj.toString();
					System.out.println("�ҿ�����ȡ������Ϊ"+strResult);
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
								ListViewPersonnelnameselfAdapter.selftime.add(json.getString("CheckTime"));;
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
						//�������ݽ���ʱ��ʾ����ʧ
						progressDialog.dismiss();
						lvpersonnameself.setAdapter(adapter);
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
		}
	}
}
