package com.example.Adapter;

import java.util.ArrayList;

import com.example.punchcard.R;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Listviewgonggaoadapter extends BaseAdapter{
	private Context mContext;
	//	public  static String [] sa = {"1","2","3","4"};
	public static ArrayList<String> title = new ArrayList<String>();
	public static ArrayList<String> content = new ArrayList<String>();
	public static ArrayList<String> time = new ArrayList<String>();
	public static ArrayList<String> colorarr = new ArrayList<String>();
	public static ArrayList<String> id = new ArrayList<String>();
	public Listviewgonggaoadapter(Context mContext) {
		super();
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return title.size();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.gonggaoitem, parent,false);
		}
		TextView tv = (TextView) convertView.findViewById(R.id.tv_gonggaoitem_sign);
		TextView tv1 = (TextView) convertView.findViewById(R.id.tv_gonggaoitem_text);
		TextView tv2 = (TextView) convertView.findViewById(R.id.tv_gonggaoitem_time);
		//		tv.setText(sa[position]);
		if(colorarr.get(position).equals("000")||colorarr.get(position).equals("#000")){
			tv.setTextColor(Color.RED);
		}else{tv.setTextColor(Color.BLACK);}
		tv.setText(title.get(position));
		tv1.setText("\t\t\t"+content.get(position));
		System.out.println("看看这些都是什么颜色"+colorarr.get(position));

		tv2.setText(time.get(position));
		return convertView;
	}
}
