package com.newworkfunction.xiangmu;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Adapter.ListviewYusuanzonglanadapter;
import com.example.Adapter.Listviewgonggaoadapter;
import com.example.Myselffunction.shezhi;
import com.example.firstjob.MainActivity;
import com.example.pulltoflash.PullToRefreshView;
import com.example.pulltoflash.PullToRefreshView.OnFooterRefreshListener;
import com.example.pulltoflash.PullToRefreshView.OnHeaderRefreshListener;
import com.example.punchcard.R;
import com.pm.common.XRequest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class Yusuanzonglan extends Activity implements OnFooterRefreshListener,OnHeaderRefreshListener{
	PullToRefreshView mPullToRefreshView;
	private ListView lv;
	private ListviewYusuanzonglanadapter  adapter;
	public static int currentpage=1;//��ǰҳ
	private ProgressDialog progressdialog;
	JSONObject json;
	String rows;
	double total;//��ȡ���ܼ�¼��
	private LinearLayout shuaxin;
	public static int totalpage = 0;//��ҳ��
	private ImageButton xinjianib,fanhuiib;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		shezhi.getInstance().addActivity(this);//�˳�ʱɱ������activity
		setContentView(R.layout.yusuanzonglan);
		initview();
		getyusuanzonglanlist();
	}

	private void initview() {
		// TODO Auto-generated method stub
		mPullToRefreshView = (PullToRefreshView) findViewById(R.id.main_pull_refresh_view);
		lv = (ListView) findViewById(R.id.yusuanzonglanListView);
		adapter  = new ListviewYusuanzonglanadapter(getApplicationContext());
		lv.setAdapter(adapter);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		mPullToRefreshView.setOnHeaderRefreshListener(this);
		fanhuiib = (ImageButton) findViewById(R.id.yusuanfanhuiib);
		fanhuiib.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Yusuanzonglan.this.finish();
			}
		});
		xinjianib = (ImageButton) findViewById(R.id.xinjianyusuan);
		xinjianib.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "ģ�鿪���У������ڴ�", Toast.LENGTH_SHORT).show();
			}
		});
		shuaxin = (LinearLayout) findViewById(R.id.shuaxinyemian_yusuanzonglan);
		shuaxin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getyusuanzonglanlist();
				shuaxin.setVisibility(8);
			}
		});
	}
	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				//				mPullToRefreshView.onFooterRefreshComplete();
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
				getyusuanzonglanlist();
			}
		},1000);
	}
	public void getyusuanzonglanlist(){
		String uid = MainActivity.getuserid();
		progressdialog = ProgressDialog.show(this,"���Ժ�","���ݼ�����...",true);
		//		progressdialog.setCancelable(true);
		int a =  currentpage*10;
		final List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("emp",uid));
		params.add(new BasicNameValuePair("page", "1"));
		params.add(new BasicNameValuePair("rows",a+""));
		XRequest request = new XRequest();
		request.PostServer("get_project_list", params, new Handler(){
			@Override
			// ������Ϣ���ͳ�����ʱ���ִ��Handler���������
			public void handleMessage(Message msg){
				super.handleMessage(msg);
				String strResult = msg.obj.toString();
				System.out.println("�������Ԥ���������涼��ʲô"+strResult);
				int status_code = 200;
				try {
					JSONObject json= new JSONObject();
					json = new JSONObject(strResult);
					status_code = Integer.parseInt(json.getString("status"));
					String message = "";
					message = json.getString("msg").toString();
					switch (status_code) {
					case -1:
						progressdialog.dismiss();
						Toast.makeText(getApplicationContext() ,message, Toast.LENGTH_SHORT).show();
						mPullToRefreshView.onHeaderRefreshComplete();
						if(ListviewYusuanzonglanadapter.mingcheng.size()==0){
							shuaxin.setVisibility(0);//���е�0��ʾ�ɼ�   4��ʾ���ɼ�  8��ʾgone
						}
						break;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JSONObject object = new JSONObject();
				try {
					object = new JSONObject(strResult);
					total = Integer.parseInt(object.getString("total"));
					double signopage = total/10;
					int ii = (int)signopage;
					double rr = signopage - ii;
					if(rr>0){
						totalpage=ii+1;
					}else{totalpage = ii;}
					rows = object.getString("rows");//��ȡ��ά�����еĵڶ�άjson
					JSONArray jsonlxt;
					jsonlxt = new JSONArray(rows);
					ListviewYusuanzonglanadapter.mingcheng.clear();
					ListviewYusuanzonglanadapter.leixing.clear();
					ListviewYusuanzonglanadapter.xuqiu.clear();
					ListviewYusuanzonglanadapter.gongyingshang.clear();
					for (int i = 0; i < jsonlxt.length(); i++) {
						json = jsonlxt.getJSONObject(i);
						ListviewYusuanzonglanadapter.mingcheng.add(json.getString("Name"));
//						ListviewYusuanzonglanadapter.leixing.add(json.getString("PublishTime"));
//						ListviewYusuanzonglanadapter.xuqiu.add(json.getString("Summary"));
//						ListviewYusuanzonglanadapter.gongyingshang.add(json.getString("Color"));
					}
					//�������ݽ���ʱ��ʾ����ʧ
					if(ListviewYusuanzonglanadapter.mingcheng.size()>0){
						progressdialog.dismiss();
						lv.setAdapter(adapter);
						mPullToRefreshView.onHeaderRefreshComplete();
					}else{
						progressdialog.dismiss();
						lv.setAdapter(adapter);
						Toast.makeText(getApplicationContext(), "�޼�¼", Toast.LENGTH_SHORT).show();
						mPullToRefreshView.onHeaderRefreshComplete();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		// ������Ϣ���ͳ�����ʱ���ִ��Handler���������
		progressdialog.dismiss();
	}
	public void postjiazai(){
		String uid = MainActivity.getuserid();
		if(currentpage<totalpage){
			progressdialog = ProgressDialog.show(this, "���Ե�...","��ȡ������...", true);
			//		progressDialog.setCancelable(true);
			final List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("emp",uid));
			params.add(new BasicNameValuePair("page",currentpage+""));
			params.add(new BasicNameValuePair("rows","10"));
			XRequest request = new XRequest();
			request.PostServer("get_budget_summary", params, new Handler(){
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
							progressdialog.dismiss();
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
								ListviewYusuanzonglanadapter.mingcheng.add(json.getString("Title"));
								ListviewYusuanzonglanadapter.leixing.add(json.getString("PublishTime"));
								ListviewYusuanzonglanadapter.xuqiu.add(json.getString("Summary"));
								ListviewYusuanzonglanadapter.gongyingshang.add(json.getString("Color"));
							} catch (JSONException e) {
								e.printStackTrace();
							}
						}
						//�������ݽ���ʱ��ʾ����ʧ
						progressdialog.dismiss();
						lv.setAdapter(adapter);
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
}
