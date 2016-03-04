package com.pm.common;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;
public class XRequest {
	private  Thread postthread;
	public void Postupdate(String action, final List<NameValuePair> params, final Handler handler) {
		PostServerCore("AndroidHandler",action,params,handler);
	}
	public void PostOA(String action, final List<NameValuePair> params, final Handler handler) {
		PostServerCore("OA_handler",action,params,handler);
	}
	public void PostServer(String action, final List<NameValuePair> params, final Handler handler) {
		PostServerCore("public_handler",action,params,handler);
	}
	public void PostServerAttendance(String action, final List<NameValuePair> params, final Handler handler) {
		PostServerCore("AttendanceHandler",action,params,handler);
	}
	public void PostServerGPM(String action, final List<NameValuePair> params, final Handler handler) {
		PostServerCore("GPMHandler",action,params,handler);
	}
	public void PostServerCore(String psotName,String action, final List<NameValuePair> params, final Handler handler) {
		String url = "http://117.34.115.45/"+psotName+".ashx?action="+ action;
		//117.34.115.45   //192.168.0.163
		final HttpPost httpRequest = new HttpPost(url);
		new Thread() {
			@Override
			public void run() {
				Message msg = new Message();
				try {
					HttpEntity entity = new UrlEncodedFormEntity(params);
					httpRequest.setEntity(entity);
					HttpClient httpclient = new DefaultHttpClient();
					//请求超时
					httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 12000);
					//读取超时
					httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 12000);
					httpRequest.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
					HttpResponse httpResponse = httpclient.execute(httpRequest);
					if (httpResponse.getStatusLine().getStatusCode() == 200) {
						String strResult = EntityUtils.toString(httpResponse.getEntity());
						strResult = java.net.URLDecoder.decode(strResult,"UTF-8");
						msg.obj = strResult;
						handler.sendMessage(msg);
					} else {
						String strResult = "{\"status\":-1, \"msg\":\"服务器异常或网络异常\"}";
						JSONObject object = new JSONObject(strResult);
						msg.obj = object;
						handler.sendMessage(msg);
					}
				} catch (Exception e) {
					String strResult = "{\"status\":-1, \"msg\":\"服务器异常或网络异常\"}";
					JSONObject object;
					try {
						object = new JSONObject(strResult);
						msg.obj = object;
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					handler.sendMessage(msg);
				}

			}
		}.start();
	}
}
