package com.example.Adapter;
import java.util.ArrayList;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newworkfunction.Personnel;
import com.example.newworkfunction.Personnelname;
import com.example.punchcard.R;
public class ListViewpersonAadpter extends BaseAdapter{
	private Context mContext;
	public static ArrayList<String> bumenmingchen = new ArrayList<String>();
	public static ArrayList<String> bumenmingchenid = new ArrayList<String>();
	public ListViewpersonAadpter(Context mContext) {
		super();
		this.mContext = mContext;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return bumenmingchen.size();
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
					.inflate(R.layout.list_person, parent, false);
		}
		TextView tv1 = (TextView) convertView.findViewById(R.id.person_tv_list);
		tv1.setText(bumenmingchen.get(position));
		return convertView;
	}

}
