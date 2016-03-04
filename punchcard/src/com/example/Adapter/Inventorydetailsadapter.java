package com.example.Adapter;

import com.baidu.platform.comapi.map.C;
import com.example.punchcard.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class Inventorydetailsadapter extends BaseAdapter{
private Context mContext;
	public Inventorydetailsadapter(Context mContext) {
		super();
		this.mContext = mContext;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.inventorydetailsitem, parent,false);
		}
		return convertView;
	}

}
