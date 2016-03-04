package com.example.Adapter;

import java.util.List;

import com.example.entity.Myselfwork;
import com.example.punchcard.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListViewAdapterMyself extends ArrayAdapter<Myselfwork>{
	private int resourceId;
	public ListViewAdapterMyself(Context context, int textViewRssourceId,List<Myselfwork> objects) {
		super(context, textViewRssourceId,objects);
		// TODO Auto-generated constructor stub
		resourceId=textViewRssourceId;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Myselfwork mywork = getItem(position);
		View view;
		ViewHolder viewholder;
		if(convertView == null){
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewholder = new ViewHolder();
			viewholder.img = (ImageView) view.findViewById(R.id.fragmentmyself_iv);
			viewholder.workname = (TextView) view.findViewById(R.id.fragmentmyself_tv);
			view.setTag(viewholder);//将viewholder存储在view中
		}else{
			view = convertView;
			viewholder = (ViewHolder) view.getTag();//重新获取viewholder
		}
		viewholder.img.setImageResource(mywork.getImg());
		viewholder.workname.setText(mywork.getWorkname());
		return view;
	}
	class ViewHolder{
		ImageView img;
		TextView workname;
	}
}
