package com.example.newworkfunction;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Myselffunction.shezhi;
import com.example.firstjob.MainActivity;
import com.example.punchcard.R;
import com.pm.common.XRequest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
public class xinjianqingjia extends Activity{
	private EditText startDateTime,endDateTime,qingjiashiyou,gongzuojiaojie;
	private String initStartDateTime = null; // 初始化开始时间
	private String initEndDateTime = null; // 初始化结束时间
	ProgressDialog progressDialog;
	public static DateTimePickDialogUtil dateTimePicKDialog = null;
	Button button;
	JSONObject json ;
	TextView tv;
	Spinner spinner;
	String xuanxiangmashuzi;
	private ArrayAdapter<String> adapter;  
	private ArrayList<String> xuanxiang  = new ArrayList<String>();
	private ArrayList<String> xuanxiangma = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xinjianqingjia);
		ImageButton ib = (ImageButton) findViewById(R.id.xinjinaqingjiashenqingshuaxin_ib);
		ib.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				post();
			}
		});
		// 两个输入框
		startDateTime = (EditText) findViewById(R.id.xinjianqingjia_qishishijian_et);
		endDateTime = (EditText) findViewById(R.id.xinjianqingjia_zhongzhishijian_et);
		qingjiashiyou = (EditText) findViewById(R.id.xinjianqingjai_qingjiashiyou_et);
		gongzuojiaojie = (EditText) findViewById(R.id.xinjianqingjia_gongzuojiaojie_et);
		button = (Button) findViewById(R.id.xinjianqingjia_tijiao_button);
		//提交外出申请按钮监听
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showTips();
			}
		});
		startDateTime.setInputType(InputType.TYPE_NULL);
		endDateTime.setInputType(InputType.TYPE_NULL);
		startDateTime.setText(initStartDateTime);
		endDateTime.setText(initEndDateTime);

		startDateTime.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(dateTimePicKDialog==null){
				 dateTimePicKDialog = new DateTimePickDialogUtil(
						xinjianqingjia.this, initEndDateTime);
				dateTimePicKDialog.dateTimePicKDialog(startDateTime);
			}
			}
		});
		endDateTime.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(dateTimePicKDialog==null){
					dateTimePicKDialog = new DateTimePickDialogUtil(xinjianqingjia.this, initEndDateTime);
					dateTimePicKDialog.dateTimePicKDialog(endDateTime);
				}
			}
		});
		post();
		tv = (TextView) findViewById(R.id.xinjianqingjia_xuanxiang_tv);
		spinner = (Spinner) findViewById(R.id.xinjianqingjia_spinner);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent, View view, int position,long id){
				// TODO Auto-generated method stub
				//设置spinner可多次点击同一项
				Field field;
				try {
					field = AdapterView.class.getDeclaredField("mOldSelectedPosition");
					field.setAccessible(true);  //设置mOldSelectedPosition可访问  
					field.setInt(spinner, AdapterView.INVALID_POSITION);
					//获取spinner中的值
					spinner = (Spinner) parent;
					String type = (String) spinner.getItemAtPosition(position);
					xuanxiangmashuzi  = xuanxiangma.get(position);
					tv.setText(type);
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
			}
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
	}
	public void post(){
		progressDialog = ProgressDialog.show(this, "请稍等...","获取外出类型中...", true);
		progressDialog.setCancelable(true);
		final List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("type","50"));
//		params.add(new BasicNameValuePair("id","0"));
		XRequest request = new XRequest();
		request.PostServer("get_classes_tree", params, new Handler() {
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
						progressDialog.dismiss();
						Toast.makeText(getApplicationContext() ,message, Toast.LENGTH_SHORT).show();
						//						Toast.makeText(getApplicationContext(), "执行post刷新完毕", Toast.LENGTH_SHORT).show();
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
					JSONArray jsonlxt;
					//					if(strResult!=null && !strResult.equals("")){
					jsonlxt = new JSONArray(strResult);
					xuanxiang.clear();
					xuanxiangma.clear();
					for(int i=0 ; i < jsonlxt.length() ;i++){
						try {
							json=jsonlxt.getJSONObject(i);
							xuanxiang.add(json.getString("className"));
							xuanxiangma.add(json.getString("id"));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					//加载数据结束时提示框消失
					progressDialog.dismiss();
					adapter = new ArrayAdapter<String>(getApplicationContext(),  
							android.R.layout.simple_spinner_item, xuanxiang);
					adapter.setDropDownViewResource(R.layout.myspinner);
					spinner.setAdapter(adapter);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
				);
	}
	//	这是提醒提交外出申请的代码
	public void showTips() {  
		AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("提醒")
				.setMessage("是否确认并提交外出申请")  
				.setNegativeButton("确定", new DialogInterface.OnClickListener() { 
					@Override
					public void onClick(DialogInterface dialog, int which) { 
						postwaichushenqing();
					}
				}).setPositiveButton("取消",   
						new DialogInterface.OnClickListener() { 
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						return;
					}
				}).create(); // 创建对话框      
		alertDialog.show();// 显示对话框    
	}

	public void postwaichushenqing() {
		// TODO Auto-generated method stub
		String uid = MainActivity.getuserid();
		progressDialog = ProgressDialog.show(xinjianqingjia.this, "请稍等...","提交申请中...", true);
		String startTime = startDateTime.getText().toString();
		String endTime = endDateTime.getText().toString();
		String shiyou = qingjiashiyou.getText().toString();
		String jiaojie =gongzuojiaojie.getText().toString();
		String qingjiatype = tv.getText().toString();
		final List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("txtEmpId",uid));
		params.add(new BasicNameValuePair("ddType",xuanxiangmashuzi));
		params.add(new BasicNameValuePair("txtStartTime",startTime));
		params.add(new BasicNameValuePair("txtEndTime",endTime));
		params.add(new BasicNameValuePair("txtDetails",shiyou));
		params.add(new BasicNameValuePair("txtRemark",jiaojie));
		params.add(new BasicNameValuePair("txtLeaveType","1"));
		XRequest request = new XRequest();
		request.PostServerAttendance("submit_leave",params,new Handler(){
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
			}
		}
				);
		progressDialog.dismiss();
		//		Toast.makeText(getApplicationContext(), startTime+endTime+shiyou+jiaojie+waichutype, Toast.LENGTH_SHORT).show();
	} 
}
