package com.example.Adapter;
import java.util.ArrayList;

import com.example.punchcard.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListviewYusuanzonglanadapter extends BaseAdapter{
   private Context mContext;
   public static ArrayList<String> mingcheng = new ArrayList<String>();
   public static ArrayList<String> leixing = new ArrayList<String>();
   public static ArrayList<String> xuqiu = new ArrayList<String>();
   public static ArrayList<String> gongyingshang = new ArrayList<String>();
   public ListviewYusuanzonglanadapter(Context mContext){
	   super();
	   this.mContext = mContext;
   }
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mingcheng.size();
	}

	@Override
	public Object getItem(int position ) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position , View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			convertView = LayoutInflater.from(mContext)
					.inflate(R.layout.yusuanzonglanitem, parent,false);
		}
		TextView tv = (TextView) convertView.findViewById(R.id.yusuanzonglan_gundong_tv);
		tv.setText(mingcheng.get(position));
		TextView tv1 = (TextView) convertView.findViewById(R.id.zonglanlei_tv);
		TextView tv2 = (TextView) convertView.findViewById(R.id.zonglanxu_tv);
		TextView tv3 = (TextView) convertView.findViewById(R.id.zonglangong_tv);
		return convertView;
	}

}
