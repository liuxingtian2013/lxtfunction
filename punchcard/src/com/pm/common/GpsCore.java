package com.pm.common;
import cn.jpush.android.api.JPushInterface;

import com.baidu.mapapi.SDKInitializer;
import android.app.Application;
public class GpsCore  extends Application {
	@Override 
	public void onCreate() {
		super.onCreate();

		// ��ʹ�� SDK �����֮ǰ��ʼ�� context ��Ϣ������ ApplicationContext
		SDKInitializer.initialize(this);
		JPushInterface.init(this);     		// ��ʼ�� JPush   ��ʼ�����ͺ���Ҫ
	}
}
