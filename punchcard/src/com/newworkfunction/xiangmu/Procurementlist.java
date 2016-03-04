package com.newworkfunction.xiangmu;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Adapter.ListviewYusuanzonglanadapter;
import com.example.Adapter.Listviewcaigoujihuaadapter;
import com.example.Adapter.Listviewcaigouqingdanadapter;
import com.example.Adapter.Listviewgonggaoadapter;
import com.example.Myselffunction.shezhi;
import com.example.firstjob.MainActivity;
import com.example.firstjob.WelcomeMainActivity;
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
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class Procurementlist extends Activity implements OnFooterRefreshListener,OnHeaderRefreshListener{
	PullToRefreshView mPullToRefreshView;
	private ListView lv;
	private Timer timer;
	private Listviewcaigouqingdanadapter  adapter;
	public static int currentpage=1;//当前页
	private ProgressDialog progressdialog;
	JSONObject json;
	String rows;
	double total;//获取下总记录数
	private LinearLayout shuaxin;
	public static int totalpage = 0;//总页数
	private ImageButton xinjianib,fanhuiib;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		shezhi.getInstance().addActivity(this);//退出时杀死所有activity
		setContentView(R.layout.procurementlist);
		initview();
//		getcaigoujihualist();
	}

	private void initview() {
		// TODO Auto-generated method stub
		mPullToRefreshView = (PullToRefreshView) findViewById(R.id.main_pull_refresh_view);
		lv = (ListView) findViewById(R.id.caigouqingdanListView);
		adapter  = new Listviewcaigouqingdanadapter(getApplicationContext());
		lv.setAdapter(adapter);
		Toast.makeText(getApplicationContext(), "! 长按各项可进行操作", Toast.LENGTH_LONG).show();
		mPullToRefreshView.setOnFooterRefreshListener(this);
		mPullToRefreshView.setOnHeaderRefreshListener(this);
		fanhuiib = (ImageButton) findViewById(R.id.caigouqingdanfanhuiib);
		fanhuiib.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Procurementlist.this.finish();
			}
		});
		xinjianib = (ImageButton) findViewById(R.id.xinjiancaigouqingdan);
		xinjianib.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
					Toast.makeText(getApplicationContext(), "模块开发中，敬请期待", Toast.LENGTH_SHORT).show();
			}
		});
		shuaxin = (LinearLayout) findViewById(R.id.shuaxinyemian_caigouqingdan);
		shuaxin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				getcaigoujihualist();
				shuaxin.setVisibility(8);
			}
		});
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.putExtra("caigouqingdanid", position );
				intent.setClass(getApplicationContext(), Procurementlistdetails.class);
				startActivity(intent);
			}
		});
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {//监听获取长按点击的listview中的子项id

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View v,
					int position , long id) {
				// TODO Auto-generated method stub
			       Toast.makeText(getApplicationContext(), "长按了listview里面的第"+position+"个项目", Toast.LENGTH_SHORT).show();
				return false;
			}
		});
		lv.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {//监听长按后弹出对话框中点击的子项ID
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuinfo) {
				// TODO Auto-generated method stub
				 menu.setHeaderTitle("请选择操作项");     
	                menu.add(0, 0, 0, "提交入库");  
	                menu.add(0, 1, 0, "取消");
			}
		});
	}
	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				//				mPullToRefreshView.onFooterRefreshComplete();
//				postjiazai();
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
//				getcaigoujihualist();
			}
		},1000);
	}
//	public void getcaigoujihualist(){
//		String uid = MainActivity.getuserid();
//		progressdialog = ProgressDialog.show(this,"请稍后","数据加载中...",true);
//		//		progressdialog.setCancelable(true);
//		int a =  currentpage*10;
//		final List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("emp",uid));
//		params.add(new BasicNameValuePair("page", "1"));
//		params.add(new BasicNameValuePair("rows",a+""));
//		XRequest request = new XRequest();
//		request.PostServer("get_project_list", params, new Handler(){
//			@Override
//			// 当有消息发送出来的时候就执行Handler的这个方法
//			public void handleMessage(Message msg){
//				super.handleMessage(msg);
//				String strResult = msg.obj.toString();
//				System.out.println("看看这个采购计划里面都是什么"+strResult);
//				int status_code = 200;
//				try {
//					JSONObject json= new JSONObject();
//					json = new JSONObject(strResult);
//					status_code = Integer.parseInt(json.getString("status"));
//					String message = "";
//					message = json.getString("msg").toString();
//					switch (status_code) {
//					case -1:
//						progressdialog.dismiss();
//						Toast.makeText(getApplicationContext() ,message, Toast.LENGTH_SHORT).show();
//						mPullToRefreshView.onHeaderRefreshComplete();
//						if(Listviewcaigoujihuaadapter.mingcheng.size()==0){
//							shuaxin.setVisibility(0);//其中的0表示可见   4表示不可见  8表示gone
//						}
//						break;
//					}
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				JSONObject object = new JSONObject();
//				try {
//					object = new JSONObject(strResult);
//					total = Integer.parseInt(object.getString("total"));
//					double signopage = total/10;
//					int ii = (int)signopage;
//					double rr = signopage - ii;
//					if(rr>0){
//						totalpage=ii+1;
//					}else{totalpage = ii;}
//					rows = object.getString("rows");//获取二维数组中的第二维json
//					JSONArray jsonlxt;
//					jsonlxt = new JSONArray(rows);
//					Listviewcaigoujihuaadapter.mingcheng.clear();
//					Listviewcaigoujihuaadapter.leixing.clear();
//					Listviewcaigoujihuaadapter.xuqiu.clear();
//					Listviewcaigoujihuaadapter.gongyingshang.clear();
//					for (int i = 0; i < jsonlxt.length(); i++) {
//						json = jsonlxt.getJSONObject(i);
//						Listviewcaigoujihuaadapter.mingcheng.add(json.getString("Name"));
////						ListviewYusuanzonglanadapter.leixing.add(json.getString("PublishTime"));
////						ListviewYusuanzonglanadapter.xuqiu.add(json.getString("Summary"));
////						ListviewYusuanzonglanadapter.gongyingshang.add(json.getString("Color"));
//					}
//					//加载数据结束时提示框消失
//					if(Listviewcaigoujihuaadapter.mingcheng.size()>0){
//						progressdialog.dismiss();
//						lv.setAdapter(adapter);
//						mPullToRefreshView.onHeaderRefreshComplete();
//					}else{
//						progressdialog.dismiss();
//						lv.setAdapter(adapter);
//						Toast.makeText(getApplicationContext(), "无记录", Toast.LENGTH_SHORT).show();
//						mPullToRefreshView.onHeaderRefreshComplete();
//					}
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		});
//		// 当有消息发送出来的时候就执行Handler的这个方法
//		progressdialog.dismiss();
//	}
//	public void postjiazai(){
//		String uid = MainActivity.getuserid();
//		if(currentpage<totalpage){
//			progressdialog = ProgressDialog.show(this, "请稍等...","获取数据中...", true);
//			//		progressDialog.setCancelable(true);
//			final List<NameValuePair> params = new ArrayList<NameValuePair>();
//			params.add(new BasicNameValuePair("emp",uid));
//			params.add(new BasicNameValuePair("page",currentpage+""));
//			params.add(new BasicNameValuePair("rows","10"));
//			XRequest request = new XRequest();
//			request.PostServer("get_budget_summary", params, new Handler(){
//				@Override
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
//						String message = "";
//						try {
//							message = object.getString("msg").toString();
//						} catch (JSONException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						switch (status_code) {
//						case -1:
//							progressdialog.dismiss();
//							Toast.makeText(getApplicationContext() ,message, Toast.LENGTH_SHORT).show();
//							//						Toast.makeText(getApplicationContext(), "执行post刷新完毕", Toast.LENGTH_SHORT).show();
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
//						JSONObject objectt = new JSONObject();
//						objectt = new JSONObject(strResult);
//						rows = objectt.getString("rows");//获取二维数组中的第二维json
//						JSONArray jsonlxt;
//						jsonlxt = new JSONArray(rows);
//						for(int i=0 ; i < jsonlxt.length() ;i++){
//							try {
//								json=jsonlxt.getJSONObject(i);
//								Listviewcaigoujihuaadapter.mingcheng.add(json.getString("Title"));
//								Listviewcaigoujihuaadapter.leixing.add(json.getString("PublishTime"));
//								Listviewcaigoujihuaadapter.xuqiu.add(json.getString("Summary"));
//								Listviewcaigoujihuaadapter.gongyingshang.add(json.getString("Color"));
//							} catch (JSONException e) {
//								e.printStackTrace();
//							}
//						}
//						//加载数据结束时提示框消失
//						progressdialog.dismiss();
//						lv.setAdapter(adapter);
//						//					Toast.makeText(getApplicationContext(), "下拉可刷新", Toast.LENGTH_SHORT).show();
//						mPullToRefreshView.onFooterRefreshComplete();
//					} catch (JSONException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//				}
//			}
//					);
//			currentpage++;
//		}else{
//			Toast.makeText(getApplicationContext(), "已加载全部数据", Toast.LENGTH_SHORT).show();
//			mPullToRefreshView.onFooterRefreshComplete();
//		}
//	}
}
