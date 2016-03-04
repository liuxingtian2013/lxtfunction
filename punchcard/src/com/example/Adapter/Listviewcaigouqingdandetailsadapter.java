package com.example.Adapter;

import com.example.punchcard.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Listviewcaigouqingdandetailsadapter extends BaseAdapter {
	private Context mContext;
	public static TextView Alreadybought_tv;
	public Listviewcaigouqingdandetailsadapter(Context mContext) {
		super();
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
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
	public View getView(int position, View convertview, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertview == null){
			convertview=LayoutInflater.from(mContext).inflate
					(R.layout.listviewcaigouqingdandetailsitem, parent,false);
		}
		Alreadybought_tv = (TextView) convertview.findViewById(R.id.Alreadybought_tv);
		return convertview;
	}

}
