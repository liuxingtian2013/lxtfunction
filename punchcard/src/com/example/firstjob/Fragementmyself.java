package com.example.firstjob;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Adapter.ListViewAdapterMyself;
import com.example.Myselffunction.shezhi;
import com.example.Myselffunction.xinxi;
import com.example.entity.Myselfwork;
import com.example.punchcard.R;
import com.example.updataAPK.UpdateManager;
import com.pm.common.Utils;
import com.pm.common.XRequest;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
public class Fragementmyself extends Fragment {
	private ListViewAdapterMyself myselfadapter;
	//	public myself_listview_xinxi_adapter xinxiadapter;
	private List<Myselfwork> myself = new ArrayList<Myselfwork>();
	private LinearLayout lly;
	private View view;
	public static ImageView ib;
	ProgressDialog progressDialog;
	public static Bitmap picture;
	public static String name,sex,logename,Mobile,NameOfPath,DeptName,StationName,DutyName;
	TextView tv1;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initdata();
	}
	@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
		if (null != view) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (null != parent) {
				parent.removeView(view);
			}
		} else {
			view = inflater.inflate(R.layout.myself, container,false);
			ib = (ImageView)view.findViewById(R.id.xinxi_iv);
			if(picture!=null){
				ib.setImageBitmap(picture);
			}
			postxinxi();
			myselfadapter= new ListViewAdapterMyself(getActivity(), R.layout.fragmentmyself, myself);
			ListView lv = (ListView) view.findViewById(R.id.lv_myself);
			lv.setAdapter(myselfadapter);
			tv1 = (TextView) view.findViewById(R.id.xinxi_tv);
			lly = (LinearLayout) view.findViewById(R.id.LinearLayoutlxt);
			lly.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					intent.setClass(getActivity(), xinxi.class);
					startActivity(intent);
				}
			});
			//获取权限
			lv.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
					// TODO Auto-generated method stub
					Intent intent = null;
					switch (position) {
					case 0://检测软件更新
						UpdateManager updateapk = new UpdateManager(getActivity());
						updateapk.postupdate();
//						Toast.makeText(getActivity(), "检测更新中", Toast.LENGTH_SHORT).show();
						break;
					case 1:
						intent=new Intent(view.getContext(),shezhi.class);
						break;
					}
					if(intent!=null){
						startActivity(intent);
					}}
			});}
		return view;
	}
	private void initdata() {
		// TODO Auto-generated method stub
		Myselfwork updata = new Myselfwork(R.drawable.update, "检测更新");
		myself.add(updata);
		Myselfwork set = new Myselfwork(R.drawable.set, "设置");
		myself.add(set);
	}
	public void postxinxi(){
		String uid = MainActivity.getuserid();
		progressDialog = ProgressDialog.show(getActivity(), "请稍等...这是我模块的加载","获取数据中...", true);
		progressDialog.setCancelable(true);
		final List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("emp",uid));
		XRequest request = new XRequest();
		request.PostServerGPM("get_emp_model", params, new Handler() {
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
						Toast.makeText(getActivity() ,message, Toast.LENGTH_SHORT).show();
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
					JSONObject jsonlxt;
					//					if(strResult!=null && !strResult.equals("")){
					jsonlxt = new JSONObject(strResult);
					try {
						//json=jsonlxt.getJSONObject(i);
						 name = jsonlxt.getString("Name");
						 sex = jsonlxt.getString("Sex");
						 logename = jsonlxt.getString("LoginName");
						  Mobile  = jsonlxt.getString("Mobile");
						 NameOfPath = jsonlxt.getString("NameOfPath");
						 DeptName = jsonlxt.getString("DeptName");
						 StationName = jsonlxt.getString("StationName");
						 DutyName = jsonlxt.getString("DutyName");
						if(sex.equals("1")){
							sex = "男";
						}else{sex="女";}
						String photo = "http://117.34.115.45"+jsonlxt.getString("Photo");
//						System.out.println("看看这照片"+photo);
						tv1.setText(name+"  "+sex);
						Bitmap bitmap = Utils.getHttpBitmap(photo); 
						picture = bitmap;
						ib.setImageBitmap(picture);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					//加载数据结束时提示框消失
					progressDialog.dismiss();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
				);
	}
}
