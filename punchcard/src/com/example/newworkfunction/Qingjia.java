package com.example.newworkfunction;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Adapter.ListViewpersonnameAadpter;
import com.example.Adapter.listviewadapterqingjiajilu;
import com.example.firstjob.MainActivity;
import com.example.pulltoflash.PullToRefreshView;
import com.example.pulltoflash.PullToRefreshView.OnFooterRefreshListener;
import com.example.pulltoflash.PullToRefreshView.OnHeaderRefreshListener;
import com.example.punchcard.R;
import com.pm.common.XRequest;


import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;

import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class Qingjia extends Activity implements OnHeaderRefreshListener,OnFooterRefreshListener{
	private ArrayAdapter<String> adapter;  
	final static String[] LEVEL = { "全部", "新建请假申请", "刷新本页面" };
	PullToRefreshView mPullToRefreshView;
	TextView tv;
	ListView mlistview;
	Spinner spinner;
	listviewadapterqingjiajilu listviewadapter;
	ProgressDialog progressDialog;
	JSONObject json ;
	int currentpage=1;//当前页
	String rows;//获取二维数组的变量很重要
	double total;//获取下总记录数
	public static int totalpage = 0;//总页数
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qingjia);
		mPullToRefreshView = (PullToRefreshView)findViewById(R.id.main_pull_refresh_view);
		listviewadapter = new listviewadapterqingjiajilu(getApplicationContext());
		mlistview= (ListView) findViewById(R.id.list_qingjia);
		mlistview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				String pp = Integer.toString(position);
				intent.putExtra("position", pp);//将获取到的位置数字直接传入  从另外一边直接获取所得对应位置的值
				intent.setClass(getApplicationContext(), Qingjiaxiangqinng.class);
				startActivity(intent);
			}
		});
		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		if (listviewadapterqingjiajilu.qjjl_type.size() > 0) {
			mlistview.setAdapter(listviewadapter);
		} else {
			post();
		}
		spinner = (Spinner) findViewById(R.id.qingjia_spinner);  
		adapter = new ArrayAdapter<String>(getApplicationContext(),  
				android.R.layout.simple_spinner_item, LEVEL);
		adapter.setDropDownViewResource(R.layout.myspinner);
		spinner.setAdapter(adapter);
		//		spinner.setBackground(color.black);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
				// TODO Auto-generated method stub
				//设置spinner可多次点击同一项
				Field field;
				try {
					field = AdapterView.class.getDeclaredField("mOldSelectedPosition");
					field.setAccessible(true);  //设置mOldSelectedPosition可访问  
					field.setInt(spinner, AdapterView.INVALID_POSITION);
				} catch (NoSuchFieldException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} //设置mOldSelectedPosition的值
				Intent intent = new Intent();
				switch (position) {
				case 1:
					intent.setClass(getApplicationContext(), xinjianqingjia.class);
					if(intent!=null){
						startActivity(intent);
					}
					break;
				case 2:
					post();
					break;
				}	
			}
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
	}
	//	@Override  
	//    public boolean onKeyDown(int keyCode, KeyEvent event)  
	//    {  
	//        if (keyCode == KeyEvent.KEYCODE_BACK )  
	//        {  
	//        	progressDialog.dismiss();
	//        }  
	//        return false;  
	//    }
	public void post(){
		String uid = MainActivity.getuserid();
		progressDialog = ProgressDialog.show(this, "请稍等...","获取数据中...", true);
		progressDialog.setCancelable(true);
		final List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("page","1"));
		params.add(new BasicNameValuePair("emp",uid));
		int a =  currentpage*10;
		params.add(new BasicNameValuePair("rows",a+""));
		XRequest request = new XRequest();
		request.PostServerAttendance("get_leave_list&type=1", params, new Handler() {
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
					String massage = "";
					try {
						massage = object.getString("msg").toString();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					switch (status_code) {
					case -1:
						progressDialog.dismiss();
						Toast.makeText(getApplicationContext() ,"系统错误,请稍候重试", Toast.LENGTH_SHORT).show();
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
					objectt = new JSONObject(strResult);
					total = Integer.parseInt(objectt.getString("total"));
					double signopage = total/10;
					int ii = (int)signopage;
					double rr = signopage - ii;
					if(rr>0){
						totalpage=ii+1;
					}else{totalpage = ii;}
					rows = objectt.getString("rows");//获取二维数组中的第二维json
					JSONArray jsonlxt;
					jsonlxt = new JSONArray(rows);
					listviewadapterqingjiajilu.qjjl_type.clear();
					listviewadapterqingjiajilu.qjjl_zhuangtai.clear();
					listviewadapterqingjiajilu.qjjl_shenqingshiyou.clear();
					listviewadapterqingjiajilu.qjjl_gongzuojiaojie.clear();
					listviewadapterqingjiajilu.qjjl_qishishijian.clear();
					listviewadapterqingjiajilu.qjjl_zhongzhishijian.clear();
					for(int i=0 ; i < jsonlxt.length() ;i++){
						try {
							json=jsonlxt.getJSONObject(i);
							listviewadapterqingjiajilu.qjjl_type.add(json.getString("className"));
							listviewadapterqingjiajilu.qjjl_zhuangtai.add(json.getString("Apply"));
							listviewadapterqingjiajilu.qjjl_shenqingshiyou.add(json.getString("Deatils"));
							listviewadapterqingjiajilu.qjjl_gongzuojiaojie.add(json.getString("Remark2"));
							listviewadapterqingjiajilu.qjjl_qishishijian.add(json.getString("StartTime"));
							listviewadapterqingjiajilu.qjjl_zhongzhishijian.add(json.getString("EndTime"));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					//加载数据结束时提示框消失
					if(listviewadapterqingjiajilu.qjjl_type.size()>0){
						progressDialog.dismiss();
						mlistview.setAdapter(listviewadapter);
						mPullToRefreshView.onHeaderRefreshComplete();
					}else{
						progressDialog.dismiss();
						mlistview.setAdapter(listviewadapter);
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
	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				//				mPullToRefreshView.onFooterRefreshComplete();
				//				Toast.makeText(getApplicationContext(), "上拉加载", Toast.LENGTH_SHORT).show();
				postjiazai();
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
				post();
			}
		},1000);
	}
	public void postjiazai(){
		String uid = MainActivity.getuserid();
		if(currentpage<totalpage){
		progressDialog = ProgressDialog.show(this, "请稍等...","获取数据中...", true);
		progressDialog.setCancelable(true);
		final List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("page",currentpage+""));
		params.add(new BasicNameValuePair("emp",uid));
		params.add(new BasicNameValuePair("rows","10"));
		XRequest request = new XRequest();
		request.PostServerAttendance("get_leave_list&type=1", params, new Handler() {
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
					String massage = "";
					try {
						massage = object.getString("msg").toString();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					switch (status_code) {
					case -1:
						progressDialog.dismiss();
						Toast.makeText(getApplicationContext() ,massage, Toast.LENGTH_SHORT).show();
						//						Toast.makeText(getApplicationContext(), "执行post刷新完毕", Toast.LENGTH_SHORT).show();
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
					rows = objectt.getString("rows");//获取二维数组中的第二维json
					JSONArray jsonlxt;
					jsonlxt = new JSONArray(rows);
					for(int i=0 ; i < jsonlxt.length() ;i++){
						try {
							json=jsonlxt.getJSONObject(i);
							listviewadapterqingjiajilu.qjjl_type.add(json.getString("className"));
							listviewadapterqingjiajilu.qjjl_zhuangtai.add(json.getString("Apply"));
							listviewadapterqingjiajilu.qjjl_shenqingshiyou.add(json.getString("Deatils"));
							listviewadapterqingjiajilu.qjjl_gongzuojiaojie.add(json.getString("Remark2"));
							listviewadapterqingjiajilu.qjjl_qishishijian.add(json.getString("StartTime"));
							listviewadapterqingjiajilu.qjjl_zhongzhishijian.add(json.getString("EndTime"));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					//加载数据结束时提示框消失
					progressDialog.dismiss();
					mlistview.setAdapter(listviewadapter);
					Toast.makeText(getApplicationContext(), "上拉加载", Toast.LENGTH_SHORT).show();
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
			Toast.makeText(getApplicationContext(), "已加载全部数据", Toast.LENGTH_SHORT).show();
		}
	}
}
