package com.example.newworkfunction;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import com.baidu.location.LocationClient;
import com.example.Myselffunction.shezhi;
import com.example.firstjob.MainActivity;
import com.example.punchcard.R;
import com.pm.common.GpsCore;
import com.pm.common.GpsService;
import com.pm.common.Utils;
import com.pm.common.XRequest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
public class Punchcard extends Activity implements OnClickListener{
	public LocationClient mLocationClient = null;
	private ImageButton bt;
	ProgressDialog progressDialog;
	//	private GpsReceiver receiver = null;
	private static List<String> adrr = new ArrayList<String>();
	private TextView tv1 ,tv2,tv3,tv5;
	private LinearLayout ly,lly;
	JSONObject json ;
	private Toast toast = null;
	private final static String TAG = GpsCore.class.getSimpleName();
	public String addressString=null;
	public String lat=null;
	public String lon=null;
	private static GpsService gpsHelper;
	private boolean  GPSflag = true ;
	private boolean  TIMflag = true ;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		shezhi.getInstance().addActivity(this);//退出时杀死所有activity
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		setContentView(R.layout.punchcard);
		initView();
		gpsHelper = new GpsService();
		gpsHelper.start(this.getApplicationContext());
		new GPSThread().start();
		new TimeThread().start();//启动新的线程
		//		postlasttime();
		mLocationClient = gpsHelper.mLocationClient;
	}
	public void initView() {
		// TODO Auto-generated method stub
		bt =  (ImageButton)findViewById(R.id.workbt);
		bt.setOnClickListener(this);
		tv1 = (TextView)findViewById(R.id.get_time_tv);
		tv2 = (TextView) findViewById(R.id.punchercard_tvvvvvvvv);
		tv3 = (TextView)findViewById(R.id.punch_tv_wodeweizhi);
		//				tv4 = (TextView)findViewById(R.id.punch_tv_last_punchtime);
		tv5 = (TextView)findViewById(R.id.punch_tv_signname_num);
		ImageButton ib = (ImageButton) findViewById(R.id.punchcard_shuaxin_ib);
		ib.setOnClickListener(this);
		ly = (LinearLayout) findViewById(R.id.punch_Louyt_dakajilu);
		ly.setOnClickListener(this);
		lly = (LinearLayout) findViewById(R.id.layout_puncher);
		lly.setOnClickListener(this);
	}
	private void getaddress(){
		if(addressString!=null){
			String adr = addressString;
			if(!adr.equals("null")){
				adrr.add(adr);
			} if ( !adrr.isEmpty()) {
				tv3.setText(adrr.get(adrr.size()-1));
			} else{
				tv3.setText("无法获取您的具体位置，请退出重试");
			}
		}
		//		else{tv3.setText("无法获取您的具体位置，请退出重试");
		//		}
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		//Toast.makeText(getActivity(), "正在获取地址", Toast.LENGTH_SHORT).show();
		// getLocation();
		// 判断GPS是否可用
		switch (v.getId()) {
		case R.id.workbt:
			if (!Utils.isGpsEnabled((LocationManager) this
					.getSystemService(Context.LOCATION_SERVICE))) {
				Toast.makeText(Punchcard.this, "GSP当前已禁用，请在您的系统设置",
						Toast.LENGTH_LONG).show();
				Intent callGPSSettingIntent = new Intent(
						android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(callGPSSettingIntent);
				return ;
			}
			progressDialog = ProgressDialog.show(Punchcard.this, "请稍等...","正在提交数据...", true);
			sendCoords();
			break;
		case R.id.punch_Louyt_dakajilu:
			mLocationClient.stop();
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), dakajilu.class);

			startActivity(intent);
			mLocationClient.stop();
			Punchcard.this.finish();
			break;
		case R.id.layout_puncher:
			mLocationClient.stop();
			Intent intent1 = new Intent();
			intent1.setClass(getApplicationContext(), Map.class);
			startActivity(intent1);
			mLocationClient.stop();
			Punchcard.this.finish();
			break;
		case R.id.punchcard_shuaxin_ib:
			//			postlasttime();
			break;
		}
	} 
	public void sendCoords(){
		if(lon!=null&&!lon.equals("0.0")){
			String uid = MainActivity.getuserid();
			final List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("lon",lon));
			params.add(new BasicNameValuePair("lat",lat));
			params.add(new BasicNameValuePair("empid",uid));
			XRequest request = new XRequest();
//			tv2.setText("看看经度"+lon+"纬度"+lat);
			request.PostServerCore("AttendanceHandler","check_inout",   params, new Handler(){
				// 当有消息发送出来的时候就执行Handler的这个方法
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					String strResult = msg.obj.toString();
					JSONObject object = new JSONObject();
					try {
						object = new JSONObject(strResult);
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String message = "";
					try {
						message = object.getString("msg").toString();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					int status_code = 500;
					try {
						status_code = Integer.parseInt(object.getString("status"));
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					progressDialog.dismiss();
					// 处理UI
					switch (status_code) {
					case 200:
						toast = Toast.makeText(getApplicationContext(), message,
								Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);//设置toast的方位。0,0表示在显示屏幕的正中间
						LinearLayout layout = (LinearLayout) toast.getView();
						ImageView image = new ImageView(getApplicationContext());
						image.setImageResource(R.drawable.right);
						layout.addView(image, 0);
						toast.show();
						break;
					case -1:
						toast = Toast.makeText(getApplicationContext(), message,
								Toast.LENGTH_LONG);
						toast.setGravity(Gravity.CENTER, 0, 0);//设置toast的方位。0,0表示在显示屏幕的正中间
						LinearLayout layout1 = (LinearLayout) toast.getView();
						ImageView image1 = new ImageView(getApplicationContext());
						image1.setImageResource(R.drawable.woring);
						layout1.addView(image1, 0);
						toast.show();
						break;
					case 500:
						Toast.makeText(Punchcard.this.getApplicationContext() ,message, Toast.LENGTH_SHORT).show();
						break;
					}
				}
			});
		}else if(lon.equals("0.0")){
			progressDialog.dismiss();
//			mLocationClient.stop();
			//			CloseGPS();
			Toast.makeText(getApplicationContext() ,"获取坐标中，请稍候...", Toast.LENGTH_SHORT).show();
		}
		else{
			progressDialog.dismiss();
//			mLocationClient.stop();
			//			CloseGPS();
			Toast.makeText(getApplicationContext() ,"无法获取坐标，请检查您的网络链接", Toast.LENGTH_SHORT).show();
		}
	}
	class TimeThread extends Thread{
		@Override
		public void run(){
			do{
				try {
					Thread.sleep(1000);
					Message msg = new Message();
					msg.what = 1;//消息（一个整型值）
					handler.sendMessage(msg);//每隔1秒发送一个msg给handler
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}while(TIMflag);
		}
		private Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg){
				super.handleMessage(msg);
				switch (msg.what) {
				case 1:
					long sysTime = System.currentTimeMillis();
					CharSequence sysTimeStr = DateFormat.format("   hh:mm:ss"+"\n"+"   yyyy-MM-dd ", sysTime);
					tv1.setText(sysTimeStr);//更新时间
					break;
				}
			}	};
	}



	class GPSThread extends Thread{
		@Override
		public void run(){
			do{
				try {
					Message msg = new Message();
					msg.obj = gpsHelper.model;
					handler.sendMessage(msg);
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}while(GPSflag);
		}
		private Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg){
				super.handleMessage(msg);

				GpsService.GPSModel model = (GpsService.GPSModel)msg.obj;
				addressString = model.getAddr();
				lat=model.getLat()+"";
				lon=model.getLon()+"";
				getaddress();
			}	
		};
	}

	//	public void postlasttime(){
	//		String uid = MainActivity.getuserid();
	//		progressDialog = ProgressDialog.show(this, "请稍等...","获取数据中...", true);
	//		progressDialog.setCancelable(true);
	//		final List<NameValuePair> params = new ArrayList<NameValuePair>();
	//		params.add(new BasicNameValuePair("emp",uid));
	//		XRequest request = new XRequest();
	//		request.PostServerAttendance("get_check_last", params, new Handler() {
	//			@Override
	//			// 当有消息发送出来的时候就执行Handler的这个方法
	//			public void handleMessage(Message msg) {
	//				super.handleMessage(msg);
	//				//方法一   遍历
	//				String strResult = msg.obj.toString();
	//				int status_code = 200;
	//				try {
	//					JSONObject object = new JSONObject();
	//					object = new JSONObject(strResult);
	//					status_code = Integer.parseInt(object.getString("status"));
	//					String message = "";
	//					try {
	//						message = object.getString("msg").toString();
	//					} catch (JSONException e) {
	//						// TODO Auto-generated catch block
	//						e.printStackTrace();
	//					}
	//					switch (status_code) {
	//					case 500:
	//						progressDialog.dismiss();
	//						tv4.setText(message);
	//						break;
	//					case 200:
	//						progressDialog.dismiss();
	//						tv4.setText(message);
	//						break;
	//					case -2:
	//						progressDialog.dismiss();
	//						tv4.setText("无法获取上次打卡时间");
	//						Toast.makeText(getApplicationContext() ,"系统错误,请稍候重试", Toast.LENGTH_SHORT).show();
	//						break;
	//					case -3:
	//						progressDialog.dismiss();
	//						tv4.setText("无法获取上次打卡时间");
	//						Toast.makeText(getApplicationContext() ,"网络链接超时,请稍候重试", Toast.LENGTH_SHORT).show();
	//						//						Toast.makeText(getApplicationContext(), "执行post刷新完毕", Toast.LENGTH_SHORT).show();
	//						break;
	//					case -4:
	//						progressDialog.dismiss();
	//						tv4.setText("无法获取上次打卡时间");
	//						Toast.makeText(getApplicationContext() ,"联网失败，请检查您的网络设置", Toast.LENGTH_SHORT).show();
	//						//						Toast.makeText(getApplicationContext(), "执行post刷新完毕", Toast.LENGTH_SHORT).show();
	//						break;
	//					}
	//				} catch (NumberFormatException e2) {
	//					// TODO Auto-generated catch block
	//					e2.printStackTrace();
	//				} catch (JSONException e2) {
	//					// TODO Auto-generated catch block
	//					e2.printStackTrace();
	//				}
	//			}
	//		}
	//				);
	//	}
	@Override  
	protected void onDestroy() {  //Activity的销毁事件
		Punchcard.this.stopService(new Intent(Punchcard.this, GpsService.class));
		mLocationClient.stop();
		GPSflag=false;
		TIMflag=false;
		super.onDestroy();

	}
	 @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK&& event.getRepeatCount() == 0) {
			Punchcard.this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}

