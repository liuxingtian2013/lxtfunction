package com.example.Adapter;

import java.util.ArrayList;

import com.example.firstjob.BaseViewHolder;
import com.example.newworkfunction.dakajilu;
import com.example.punchcard.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class listviewadapterdakajilu extends BaseAdapter{
	private Context mContext;
	public static ArrayList<String> shijian = new ArrayList<String>();
	public static ArrayList<String> zhuangtai = new ArrayList<String>();


	public listviewadapterdakajilu(Context mContext) {
		super();
		this.mContext = mContext;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return shijian.size();
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
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext)
					.inflate(R.layout.list_item, parent, false);
		}
		TextView tv1 = (TextView) convertView.findViewById(R.id.tv_dakashijian);
		TextView tv2 = (TextView) convertView.findViewById(R.id.tv_zhuangtai);
		tv1.setText(shijian.get(position));
//		tv2.setText(zhuangtai.get(position));
		tv2.setText("Õý³£");
		return convertView;
	}

}
