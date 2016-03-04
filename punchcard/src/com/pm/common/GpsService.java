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
		option.setLocationMode(LocationMode.Hight_Accuracy);//��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
		option.setCoorType("bd09ll");//��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ��
		option.setScanSpan(3000);//��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
		option.setIsNeedAddress(true);//��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
		option.setOpenGps(true);//��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
		option.setAddrType("all");
		//option..disableCache(true);//��ֹ���û��涨λ
		option.setLocationNotify(true);//��ѡ��Ĭ��false�������Ƿ�gps��Чʱ����1S1��Ƶ�����GPS���
		option.setIgnoreKillProcess(true);//��ѡ��Ĭ��true����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ�ϲ�ɱ��
		mLocationListener = new LocationListener();
		mLocationClient = new LocationClient(context);
		mLocationClient.setLocOption(option);
		mLocationClient.registerLocationListener(mLocationListener);
		mLocationClient.start();//��λSDK start֮���Ĭ�Ϸ���һ�ζ�λ���󣬿����������ж�isstart����������request
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
	 * ʵ��ʵʱλ�ûص�����
	 */
	public class LocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			//Receive Location
			if(location == null){
				return;
			}
			mLocationClient.requestLocation();
			if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS��λ���
				model.lat = (Double) (location == null ? "0" : location.getLatitude());
				model.lon = (Double) (location == null ? "0" : location.getLongitude());
				model.addr = location == null ? "0" : location.getAddrStr();
				System.out.println("gps�����ܲ���ȡ������lat"+model.lat+"lon"+model.lon+"addr"+model.addr);
			}else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// ���綨λ���
				model.lat = (Double) (location == null ? "0" : location.getLatitude());
				model.lon = (Double) (location == null ? "0" : location.getLongitude());
				model.addr = location == null ? "0" : location.getAddrStr();
				System.out.println("network�����ܲ���ȡ������lat"+model.lat+"lon"+model.lon+"addr"+model.addr);
			}else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// ���߶�λ���
				model.lat = (Double) (location == null ? "0" : location.getLatitude());
				model.lon = (Double) (location == null ? "0" : location.getLongitude());
				model.addr = location == null ? "0" : location.getAddrStr();
				System.out.println("���߿����ܲ���ȡ������lat"+model.lat+"lon"+model.lon+"addr"+model.addr);
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





