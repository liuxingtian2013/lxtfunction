package com.pm.common;
import cn.jpush.android.api.JPushInterface;

import com.baidu.mapapi.SDKInitializer;
import android.app.Application;
public class GpsCore  extends Application {
	@Override 
	public void onCreate() {
		super.onCreate();

		// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
		SDKInitializer.initialize(this);
		JPushInterface.init(this);     		// 初始化 JPush   初始化推送很重要
	}
}
