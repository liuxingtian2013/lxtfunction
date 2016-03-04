package com.newworkfunction.bangong;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Adapter.ListViewpersonAadpter;
import com.example.Adapter.Listviewgonggaoadapter;
import com.example.Adapter.Listviewzhiduadapter;
import com.example.Adapter.listviewadapterdakajilu;
import com.example.Myselffunction.shezhi;
import com.example.firstjob.MainActivity;
import com.example.pulltoflash.PullToRefreshView;
import com.example.pulltoflash.PullToRefreshView.OnFooterRefreshListener;
import com.example.pulltoflash.PullToRefreshView.OnHeaderRefreshListener;
import com.example.punchcard.R;
import com.pm.common.XRequest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Zhidu extends Activity implements OnFooterRefreshListener,OnHeaderRefreshListener{
	PullToRefreshView mPullToRefreshView;
	public static ListView lv;
	Listviewzhiduadapter adapter;
	public static int currentpage=1;//��ǰҳ
	private PopupWindow popupWindow;
	private ProgressDialog progressdialog;
	private LinearLayout lly,shuaxin;
	private int i = 1;
	JSONObject json;
	String rows;
	double total;//��ȡ���ܼ�¼��
	public static int totalpage = 0;//��ҳ��
	private EditText ed;
	private String[] name = {"�ƶ�1","�ƶ�2","�ƶ�3","�ƶ�4","�ƶ�5","�ƶ�6","�ƶ�7"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ��������
		super.onCreate(savedInstanceState);
		shezhi.getInstance().addActivity(this);//�˳�ʱɱ������activity
		setContentView(R.layout.zhidu);
		initview();
		getzhidulist();
	}

	private void initview() {
		// TODO Auto-generated method stub
		mPullToRefreshView = (PullToRefreshView) findViewById(R.id.main_pull_refresh_view);
		lly = (LinearLayout) findViewById(R.id.zhidu_layout);
		lly.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				i++;
				if(i==2){
					initPopWindow();
				}else{popupWindow.dismiss();}
			}
		});
		shuaxin = (LinearLayout) findViewById(R.id.shuaxinyemian_zhidu);
		shuaxin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getzhidulist();
				shuaxin.setVisibility(8);
			}
		});
		ed = (EditText) findViewById(R.id.zhidu_sousuo);
		ed.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ed.setFocusable(true);
				Toast.makeText(getApplication(), "����򱻵��",Toast.LENGTH_SHORT).show();
				ed.requestFocus();
			}
		});
		ed.setOnEditorActionListener(new TextView.OnEditorActionListener() {  
			//��edittext�����ļ���������½ǵļ����¼�
			@Override  
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {  
				/*�ж��Ƿ��ǡ�GO����*/  
				if(actionId == EditorInfo.IME_ACTION_SEARCH){  
					/*���������*/  
					InputMethodManager imm = (InputMethodManager) v  
							.getContext().getSystemService(  
									Context.INPUT_METHOD_SERVICE);  
					if (imm.isActive()) {  
						imm.hideSoftInputFromWindow(  
								v.getApplicationWindowToken(), 0);  
					}  
					ed.setText("success");  
					return true;  
				}  
				return false;  
			}
		});  
		mPullToRefreshView.setOnFooterRefreshListener(this);
		mPullToRefreshView.setOnHeaderRefreshListener(this);
		lv = (ListView) findViewById(R.id.zhiduListView);
		adapter = new Listviewzhiduadapter(getApplicationContext());//����������ʼ��
		lv.setAdapter(adapter);
		ImageButton ib = (ImageButton) findViewById(R.id.xinjianzhidu);
		ib.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.xinjianzhidu:
//					Intent intent = new Intent();
//					intent.setClass(getApplicationContext(), xinjiangonggao.class);
//					startActivity(intent);
					Toast.makeText(getApplicationContext(), "ģ�鿪���У������ڴ�", Toast.LENGTH_SHORT).show();
					break;
				}
			}
		});
		lv.setOnTouchListener(new OnTouchListener() {
			//����������Ӵ���������һ������Edittext֮��ĵط�����edittext����ȥ����
			@Override
			public boolean onTouch(View v, MotionEvent arg1) {
				// TODO Auto-generated method stub
				lv.setFocusable(true);
				lv.setFocusableInTouchMode(true);
				lv.requestFocus();
				return false;
			}
		});
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				String pp = Integer.toString(position);
				intent.putExtra("positionnum", pp);//����ȡ����λ������ֱ�Ӵ���  ������һ��ֱ�ӻ�ȡ���ö�Ӧλ�õ�ֵ
				intent.setClass(getApplicationContext(), ZhiduParticulars.class);
				startActivity(intent);
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
				getzhidulist();//����ˢ��ʱ���еĲ���
			}
		},1000);
	}
	private void initPopWindow(){  
		View contentView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.popupwindow, null);  
		contentView.setBackgroundColor(Color.alpha(R.color.transparent));  
		popupWindow = new PopupWindow(findViewById(R.id.zhidu_layout), 500, 900);//����popwindow�Ŀ�Ⱥ͸߶�
		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			public void onDismiss() {//����popupWindow���ٵ��¼���ע���������ͬ���������¼�����ʱ��i��Ϊ1
				// TODO Auto-generated method stub
				i=1;
			}
		});
		popupWindow.setContentView(contentView);  
		ListView listView = (ListView) contentView.findViewById(R.id.list);  
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, name);  
		listView.setAdapter(adapter);  
		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				// TODO Auto-generated method stub
				popupWindow.setFocusable(true);
				Toast.makeText(getApplicationContext(), "�����������ʲô"+name[position], Toast.LENGTH_SHORT).show();	
				popupWindow.setFocusable(false);
				popupWindow.dismiss();
			}
		});
		//		popupWindow.setFocusable(false);  
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);//�����������Ϊtrue���ܵ�������������ʧ
		popupWindow.setTouchable(true);//�������PopupWindow�ڲ��ؼ��ĵ���¼�
		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		popupWindow.update();
		popupWindow.showAsDropDown(lly);  
	}  
	public void getzhidulist(){
		String uid = MainActivity.getuserid();
		progressdialog = ProgressDialog.show(this,"���Ժ�","���ݼ�����...",true);
		//		progressdialog.setCancelable(true);
		int a =  currentpage*10;
		final List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("emp",uid));
		params.add(new BasicNameValuePair("page", "1"));
		params.add(new BasicNameValuePair("rows",a+""));
		XRequest request = new XRequest();
		request.PostOA("get_rules_list", params, new Handler(){
			@Override
			// ������Ϣ���ͳ�����ʱ���ִ��Handler���������
			public void handleMessage(Message msg){
				super.handleMessage(msg);
				String strResult = msg.obj.toString();
				System.out.println("�������zhidu���涼��ʲô"+strResult);
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
						if(Listviewzhiduadapter.title.size()==0){
						ed.setEnabled(false);//�ر����θ������
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
					Listviewzhiduadapter.title.clear();
					Listviewzhiduadapter.time.clear();
					Listviewzhiduadapter.content.clear();
					Listviewzhiduadapter.colorarr.clear();
					Listviewzhiduadapter.id.clear();
					for (int i = 0; i < jsonlxt.length(); i++) {
						json = jsonlxt.getJSONObject(i);
						Listviewzhiduadapter.title.add(json.getString("Title"));
						Listviewzhiduadapter.time.add(json.getString("PublishTime"));
						Listviewzhiduadapter.content.add(json.getString("Summary"));
						Listviewzhiduadapter.colorarr.add(json.getString("Color"));
						Listviewzhiduadapter.id.add(json.getString("id"));
					}
					//�������ݽ���ʱ��ʾ����ʧ
					if(Listviewzhiduadapter.title.size()>0){
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
		request.PostOA("get_rules_list", params, new Handler() {
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
							Listviewzhiduadapter.title.add(json.getString("Title"));
							Listviewzhiduadapter.time.add(json.getString("PublishTime"));
							Listviewzhiduadapter.content.add(json.getString("Summary"));
							Listviewzhiduadapter.colorarr.add(json.getString("Color"));
							Listviewzhiduadapter.id.add(json.getString("id"));
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
