package com.example.Jpush;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.firstjob.MainActivity;
import com.example.newworkfunction.Punchcard;


import android.R.integer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

/**
 * �Զ��������
 * 
 * ������������ Receiver����
 * 1) Ĭ���û����������
 * 2) ���ղ����Զ�����Ϣ
 */
public class MyReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		//		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
			String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
			//            Log.d(TAG, "[MyReceiver] ����Registration Id : " + regId);
			//send the Registration Id to your server...

		} else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
			//        	Log.d(TAG, "[MyReceiver] ���յ������������Զ�����Ϣ: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
			processCustomMessage(context, bundle);

		} else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
			//            Log.d(TAG, "[MyReceiver] ���յ�����������֪ͨ");
			int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
			//            Log.d(TAG, "[MyReceiver] ���յ�����������֪ͨ��ID: " + notifactionId);
			System.out.println("���ҿ������Id�Ƕ��٣�"+notifactionId);
		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			//            Log.d(TAG, "[MyReceiver] �û��������֪ͨ");
			Intent intent1 = new Intent();
			if (null != intent) {
				Bundle bundle1 = intent1.getExtras();
				String mess = bundle.getString(JPushInterface.EXTRA_EXTRA);
				JSONObject json = new JSONObject();
				try {
					json = new JSONObject(mess);
					int type = Integer.parseInt(json.getString("type"));
					switch (type) {
					case 1:
						//���Զ����Activity
						Intent i = new Intent(context, Punchcard.class);
						i.putExtras(bundle);
						//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						//����ڵ�ǰTask�У���Ҫ������Activity����ô�Ѹ�Acitivity֮ǰ������Activity���ص������Ѵ�Activity��ǰ�Ա��ⴴ��Activity��ʵ��
						i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
						context.startActivity(i);
						break;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}
		} else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
			//            Log.d(TAG, "[MyReceiver] �û��յ���RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
			//��������� JPushInterface.EXTRA_EXTRA �����ݴ�����룬������µ�Activity�� ��һ����ҳ��..

		} else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
			boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
		} else {
		}
	}


	//send msg to MainActivity
	private void processCustomMessage(Context context, Bundle bundle) {
		if (MainActivity.isForeground) {
			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
			String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
			Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
			msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
			if (!ExampleUtil.isEmpty(extras)) {
				try {
					JSONObject extraJson = new JSONObject(extras);
					if (null != extraJson && extraJson.length() > 0) {
						msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
					}
				} catch (JSONException e) {

				}
			}
			context.sendBroadcast(msgIntent);
		}
	}
}
