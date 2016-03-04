package com.example.firstjob;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.example.Jpush.ExampleUtil;
import com.example.MyDatabaseHelper.MyDatabaseHelper;
import com.example.Myselffunction.shezhi;
import com.example.newworkfunction.Punchcard;
import com.example.punchcard.R;
import com.example.updataAPK.UpdateManager;
import com.pm.common.Utils;
import com.pm.common.XRequest;
public class MainActivity extends Activity implements OnClickListener {
	public static boolean isForeground = false;
	private static MyDatabaseHelper mydatabase;
    private static final String TAG = "JPush";
	public  MainActivity mactivity;//ΪXRequest���toast�����ľ�̬����
	private EditText etname, etposs;
	ProgressDialog progressDialog;
	private CheckBox cb;
	static String name, password;
	JSONObject obj;
	public  SQLiteDatabase db;//�������ݿ����
	ImageButton ib1,ib2;
	public String uid = null;
	// ����Handler����
	private Handler handler = new Handler() {
		@Override
		// ������Ϣ���ͳ�����ʱ���ִ��Handler���������
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
			try {
				uid = object.getString("uid");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// ����UI
			switch (status_code) {
			case 200:
				remenber();
				progressDialog.dismiss();
				Toast.makeText(getApplicationContext() ,message, Toast.LENGTH_SHORT).show();
				Intent intent2 = new Intent();
				intent2.setClass(MainActivity.this, Successlogin.class);
				startActivity(intent2);
				break;
			case 500:
				progressDialog.dismiss();
				Toast.makeText(getApplicationContext() ,message, Toast.LENGTH_SHORT).show();
				break;
			case -1:
				progressDialog.dismiss();
				Toast.makeText(getApplicationContext() ,message, Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		shezhi.getInstance().addActivity(this);
		setContentView(R.layout.activity_main);
		mydatabase = new MyDatabaseHelper(this, "Userinfo.db", null, 2);
		mydatabase.getWritableDatabase();
		db = mydatabase.getWritableDatabase();
		mactivity=this;
		registerMessageReceiver();  // used for receive msg
		initView();
		getnumposs(); 
	}
	private void initView() {
		// TODO Auto-generated method stub
		findViewById(R.id.successlogin).setOnClickListener(this);
		ib1=(ImageButton)findViewById(R.id.mainactivity_ib1);
		ib1.setVisibility(View.GONE);
		ib1.setOnClickListener(this);
		ib2=(ImageButton)findViewById(R.id.mainactivity_ib2);
		ib2.setVisibility(View.GONE);
		ib2.setOnClickListener(this);
		etname = (EditText) findViewById(R.id.et_qqNum);
		etname.addTextChangedListener(tw1);// Ϊ������һ���������ֱ仯�ļ�����
		etname.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus&&!etname.getText().toString().equals("")){ 
					showBtn1();
				}
				else{
					hideBtn1();
				}
			}
		});
		etposs = (EditText) findViewById(R.id.et_qqPwd);
		etposs.addTextChangedListener(tw2);// Ϊ������һ���������ֱ仯�ļ�����
		etposs.setOnFocusChangeListener(new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(hasFocus&&!etposs.getText().toString().equals("")){ 
					showBtn2();
				}
				else{
					hideBtn2();
				}
			}});
		cb=(CheckBox) findViewById(R.id.cb);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.successlogin:
			progressDialog = ProgressDialog.show(this, "���Ժ�","������½��...", true);
			name = etname.getText().toString();
			password = etposs.getText().toString();
			setTag();//�˴���ʼ���˺�����Ϊ���ͱ�ǩ
			final List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("txtLoginName",name));
			params.add(new BasicNameValuePair("txtPassword",password));
			XRequest request = new XRequest();
			request.PostServer("login", params, handler);
			break;
		case R.id.mainactivity_ib1:
			hideBtn1();// ���ذ�ť
			etname.setText("");
			break;
		case R.id.mainactivity_ib2:
			hideBtn2();// ���ذ�ť
			etposs.setText("");
			break;
		}
	}
	// �������״̬�ı�ʱ���������Ӧ�ķ���
	TextWatcher tw1 = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
		}

		// �����ָı�����
		@Override
		public void afterTextChanged(Editable s) {
			if (s.length() == 0) {
				hideBtn1();// ���ذ�ť
			} else {
				showBtn1();// ��ʾ��ť
			}
		}

	};

	public void hideBtn1() {
		// ���ð�ť���ɼ�
		if (ib1.isShown())
			ib1.setVisibility(View.GONE);
	}


	public void showBtn1() {
		// ���ð�ť�ɼ�
		if (!ib1.isShown())
			ib1.setVisibility(View.VISIBLE);
	}
	// �������״̬�ı�ʱ���������Ӧ�ķ���
	TextWatcher tw2 = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
		}

		// �����ָı�����
		@Override
		public void afterTextChanged(Editable s) {
			if (s.length() == 0) {
				hideBtn2();// ���ذ�ť
			} else {
				showBtn2();// ��ʾ��ť
			}
		}

	};

	public void hideBtn2() {
		// ���ð�ť���ɼ�
		if (ib2.isShown())
			ib2.setVisibility(View.GONE);
	}


	public void showBtn2() {
		// ���ð�ť�ɼ�
		if (!ib2.isShown())
			ib2.setVisibility(View.VISIBLE);
	}
	// remenber���������ж��Ƿ��ס���룬�������ݿ�ķ�ʽ�����ݴ洢�����ݿ���
	public void remenber() {
		if (cb.isChecked()) {
			db.delete("User", "id >= ?", new String[]{"0"});//������֮ǰ��������ݿ⣬�Ա�֤���ݿ��е�����ֻ��һ��
			ContentValues values = new ContentValues();
			//��ʼ��װ��һ������
			values.put("accountnumber",etname.getText().toString());
			values.put("password",etposs.getText().toString());
			values.put("userid",uid);
			db.insert("User", null, values);
		} else if (!cb.isChecked()) {
			db.delete("User", "id >= ?", new String[]{"0"});
		}
	}
	public void getnumposs(){
		//��ѯ�������е����ݣ�
		Cursor cursor = db.query("User", null, null, null, null, null,null);
		if(cursor.moveToFirst()){
			do{
				//				����Cursor����ȡ������
				String accountnumber = cursor.getString(cursor.getColumnIndex("accountnumber"));
				String password = cursor.getString(cursor.getColumnIndex("password"));
				etname.setText(accountnumber);
				etposs.setText(password);
			}while(cursor.moveToNext());}
		cursor.close();
	}
	public  static String  getuserid(){
		String userid = "";
		SQLiteDatabase db = mydatabase.getWritableDatabase();
		//��ѯ�������е�����
		Cursor cursor = db.query("User", null, null, null, null, null,null);
		if(cursor.moveToFirst()){
			do{
				//����Cursor����ȡ������
				userid = cursor.getString(cursor.getColumnIndex("userid"));
			}while(cursor.moveToNext());
		}cursor.close();
		return userid;
	}
	@Override
	protected void onDestroy() {
		unregisterReceiver(mMessageReceiver);
		super.onDestroy();
	}
	

	//for receive customer msg from jpush server
	private MessageReceiver mMessageReceiver;//���ü������͵ı�ǩ��
	public static final String MESSAGE_RECEIVED_ACTION = "com.example.Jpush.MESSAGE_RECEIVED_ACTION";
	public static final String KEY_TITLE = "title";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_EXTRAS = "extras";
	
	public void registerMessageReceiver() {
		mMessageReceiver = new MessageReceiver();
		IntentFilter filter = new IntentFilter();
		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
		filter.addAction(MESSAGE_RECEIVED_ACTION);
		registerReceiver(mMessageReceiver, filter);
	}

	public class MessageReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
              String messge = intent.getStringExtra(KEY_MESSAGE);
              String extras = intent.getStringExtra(KEY_EXTRAS);
              StringBuilder showMsg = new StringBuilder();
              showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
              if (!ExampleUtil.isEmpty(extras)) {
            	  showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
              }
//              setCostomMsg(showMsg.toString());
			}
		}
	}
	
	private void setTag(){//���ñ�ǩ�������˺���Ϊ��ǩ��
//		EditText tagEdit = (EditText) findViewById(R.id.et_tag);
		String tag = name;
		// ","�����Ķ�� ת���� Set
		String[] sArray = tag.split(",");
		Set<String> tagSet = new LinkedHashSet<String>();
		for (String sTagItme : sArray) {
			tagSet.add(sTagItme);
		}
		//����JPush API����Tag
		mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, tagSet));
	} 
//	private void setAlias(){//���ñ����������û��Ĳ���ID��Ϊ������
//		EditText aliasEdit = (EditText) findViewById(R.id.et_alias);
//		String alias = aliasEdit.getText().toString().trim();
//		//����JPush API����Alias
//		mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
//	}
	private static final int MSG_SET_ALIAS = 1001;
	private static final int MSG_SET_TAGS = 1002;
	
	

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case MSG_SET_ALIAS:
                Log.d(TAG, "Set alias in handler.");
                JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, null);
                break;
                
            case MSG_SET_TAGS:
                Log.d(TAG, "Set tags in handler.");
                JPushInterface.setAliasAndTags(getApplicationContext(), null, (Set<String>) msg.obj, null);
                break;
                
            default:
                Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };
}