package com.pm.common;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

public class GpsService{

	private LocationListener mLocationListener;
	public LocationClient mLocationClient;

	public GPSModel model;

	public void start(Context context)
	{
		model = new GPSModel();
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，
		option.setScanSpan(3000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);//可选，默认false,设置是否使用gps
		option.setAddrType("all");
		//option..disableCache(true);//禁止启用缓存定位
		option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		mLocationListener = new LocationListener();
		mLocationClient = new LocationClient(context);
		mLocationClient.setLocOption(option);
		mLocationClient.registerLocationListener(mLocationListener);
		mLocationClient.start();//定位SDK start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request
		//		mLocationClient.requestLocation();


	}


	public void Close()
	{
		mLocationClient.stop();
	}

	//public void onDestroyView(){
	//	onDestroy();
	//}
	/**
	 * 实现实时位置回调监听
	 */
	public class LocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			//Receive Location
			if(location == null){
				return;
			}
			mLocationClient.requestLocation();
			if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
				model.lat = (Double) (location == null ? "0" : location.getLatitude());
				model.lon = (Double) (location == null ? "0" : location.getLongitude());
				model.addr = location == null ? "0" : location.getAddrStr();
				System.out.println("gps看看能不能取到坐标lat"+model.lat+"lon"+model.lon+"addr"+model.addr);
			}else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
				model.lat = (Double) (location == null ? "0" : location.getLatitude());
				model.lon = (Double) (location == null ? "0" : location.getLongitude());
				model.addr = location == null ? "0" : location.getAddrStr();
				System.out.println("network看看能不能取到坐标lat"+model.lat+"lon"+model.lon+"addr"+model.addr);
			}else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
				model.lat = (Double) (location == null ? "0" : location.getLatitude());
				model.lon = (Double) (location == null ? "0" : location.getLongitude());
				model.addr = location == null ? "0" : location.getAddrStr();
				System.out.println("离线看看能不能取到坐标lat"+model.lat+"lon"+model.lon+"addr"+model.addr);
			}
		}
	}

	public class GPSModel{
		private double lat;
		private double lon;
		private String addr;
		public double getLat() {
			return lat;
		}
		public void setLat(double lat) {
			this.lat = lat;
		}
		public double getLon() {
			return lon;
		}
		public void setLon(double lon) {
			this.lon = lon;
		}
		public String getAddr() {
			return addr;
		}
		public void setAddr(String addr) {
			this.addr = addr;
		}
	}
}





