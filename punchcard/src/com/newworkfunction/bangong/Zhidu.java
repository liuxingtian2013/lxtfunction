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
	public static int currentpage=1;//当前页
	private PopupWindow popupWindow;
	private ProgressDialog progressdialog;
	private LinearLayout lly,shuaxin;
	private int i = 1;
	JSONObject json;
	String rows;
	double total;//获取下总记录数
	public static int totalpage = 0;//总页数
	private EditText ed;
	private String[] name = {"制度1","制度2","制度3","制度4","制度5","制度6","制度7"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		super.onCreate(savedInstanceState);
		shezhi.getInstance().addActivity(this);//退出时杀死所有activity
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
				Toast.makeText(getApplication(), "输入框被点击",Toast.LENGTH_SHORT).show();
				ed.requestFocus();
			}
		});
		ed.setOnEditorActionListener(new TextView.OnEditorActionListener() {  
			//给edittext调出的键盘添加右下角的监听事件
			@Override  
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {  
				/*判断是否是“GO”键*/  
				if(actionId == EditorInfo.IME_ACTION_SEARCH){  
					/*隐藏软键盘*/  
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
		adapter = new Listviewzhiduadapter(getApplicationContext());//给适配器初始化
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
					Toast.makeText(getApplicationContext(), "模块开发中，敬请期待", Toast.LENGTH_SHORT).show();
					break;
				}
			}
		});
		lv.setOnTouchListener(new OnTouchListener() {
			//给主布局添加触摸监听，一旦触摸Edittext之外的地方，此edittext就是去焦点
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
				intent.putExtra("positionnum", pp);//将获取到的位置数字直接传入  从另外一边直接获取所得对应位置的值
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
				//设置更新时间
				//mPullToRefreshView.onHeaderRefreshComplete("最近更新:01-23 12:01");
				getzhidulist();//下拉刷新时进行的操作
			}
		},1000);
	}
	private void initPopWindow(){  
		View contentView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.popupwindow, null);  
		contentView.setBackgroundColor(Color.alpha(R.color.transparent));  
		popupWindow = new PopupWindow(findViewById(R.id.zhidu_layout), 500, 900);//设置popwindow的宽度和高度
		popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
			public void onDismiss() {//这是popupWindow销毁的事件，注意跟方法不同，在销毁事件发生时将i置为1
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
				Toast.makeText(getApplicationContext(), "看看点击的是什么"+name[position], Toast.LENGTH_SHORT).show();	
				popupWindow.setFocusable(false);
				popupWindow.dismiss();
			}
		});
		//		popupWindow.setFocusable(false);  
		popupWindow.setOutsideTouchable(true);
		popupWindow.setFocusable(true);//这里必须设置为true才能点击区域外或者消失
		popupWindow.setTouchable(true);//这个控制PopupWindow内部控件的点击事件
		popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		popupWindow.update();
		popupWindow.showAsDropDown(lly);  
	}  
	public void getzhidulist(){
		String uid = MainActivity.getuserid();
		progressdialog = ProgressDialog.show(this,"请稍后","数据加载中...",true);
		//		progressdialog.setCancelable(true);
		int a =  currentpage*10;
		final List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("emp",uid));
		params.add(new BasicNameValuePair("page", "1"));
		params.add(new BasicNameValuePair("rows",a+""));
		XRequest request = new XRequest();
		request.PostOA("get_rules_list", params, new Handler(){
			@Override
			// 当有消息发送出来的时候就执行Handler的这个方法
			public void handleMessage(Message msg){
				super.handleMessage(msg);
				String strResult = msg.obj.toString();
				System.out.println("看看这个zhidu里面都是什么"+strResult);
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
						ed.setEnabled(false);//关闭屏蔽该输入框
						shuaxin.setVisibility(0);//其中的0表示可见   4表示不可见  8表示gone
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
					rows = object.getString("rows");//获取二维数组中的第二维json
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
					//加载数据结束时提示框消失
					if(Listviewzhiduadapter.title.size()>0){
						progressdialog.dismiss();
						lv.setAdapter(adapter);
						mPullToRefreshView.onHeaderRefreshComplete();
					}else{
						progressdialog.dismiss();
						lv.setAdapter(adapter);
						Toast.makeText(getApplicationContext(), "无记录", Toast.LENGTH_SHORT).show();
						mPullToRefreshView.onHeaderRefreshComplete();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		// 当有消息发送出来的时候就执行Handler的这个方法
		progressdialog.dismiss();
	}
	public void postjiazai(){
		String uid = MainActivity.getuserid();
		if(currentpage<totalpage){
			progressdialog = ProgressDialog.show(this, "请稍等...","获取数据中...", true);
//		progressDialog.setCancelable(true);
		final List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("emp",uid));
		params.add(new BasicNameValuePair("page",currentpage+""));
		params.add(new BasicNameValuePair("rows","10"));
		XRequest request = new XRequest();
		request.PostOA("get_rules_list", params, new Handler() {
			@Override
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
						progressdialog.dismiss();
						Toast.makeText(getApplicationContext() ,message, Toast.LENGTH_SHORT).show();
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
							Listviewzhiduadapter.title.add(json.getString("Title"));
							Listviewzhiduadapter.time.add(json.getString("PublishTime"));
							Listviewzhiduadapter.content.add(json.getString("Summary"));
							Listviewzhiduadapter.colorarr.add(json.getString("Color"));
							Listviewzhiduadapter.id.add(json.getString("id"));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					//加载数据结束时提示框消失
					progressdialog.dismiss();
					lv.setAdapter(adapter);
//					Toast.makeText(getApplicationContext(), "下拉可刷新", Toast.LENGTH_SHORT).show();
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
		mPullToRefreshView.onFooterRefreshComplete();
	}
		}
}
