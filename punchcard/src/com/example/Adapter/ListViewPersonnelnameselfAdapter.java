package com.example.Adapter;

import java.util.ArrayList;

import com.example.punchcard.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewPersonnelnameselfAdapter  extends BaseAdapter{
	private Context Mcontext;
	public static ArrayList<String> selftime = new ArrayList<String>();
	public ListViewPersonnelnameselfAdapter(Context Mcontext){
		super();
		this.Mcontext = Mcontext;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return selftime.size();
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
		if(convertView==null){
			convertView = LayoutInflater.from(Mcontext)
					.inflate(R.layout.personnameself_list_item, parent,false);
		}
		TextView tv = (TextView) convertView.findViewById(R.id.personnameself_list_item_tv);
		tv.setText(selftime.get(position));
		return convertView;
	}

}
