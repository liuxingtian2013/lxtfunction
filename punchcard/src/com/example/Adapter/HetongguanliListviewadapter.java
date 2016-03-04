package com.example.Adapter;

import com.example.punchcard.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class HetongguanliListviewadapter extends BaseAdapter{
private Context mContext;
	public HetongguanliListviewadapter(Context mContext) {
		super();
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
	public View getView(int position, View contrenView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(contrenView==null){
			contrenView = LayoutInflater.from(mContext)
					.inflate(R.layout.hetongguanliitem, parent,false);
		}
		return contrenView;
	}

}
