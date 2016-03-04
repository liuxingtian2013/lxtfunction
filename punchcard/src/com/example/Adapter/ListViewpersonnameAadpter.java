package com.example.Adapter;
import java.util.ArrayList;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.punchcard.R;
public class ListViewpersonnameAadpter extends BaseAdapter{
	private Context mContext;
	public static ArrayList<String> name = new ArrayList<String>();
	public static ArrayList<String> nameid = new ArrayList<String>();
	public static ArrayList<String> checktimefirst = new ArrayList<String>();
	public static ArrayList<String> checktimelast = new ArrayList<String>();
	public ListViewpersonnameAadpter(Context mContext) {
		super();
		this.mContext = mContext;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return name.size();
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
			convertView = LayoutInflater.from(mContext)
					.inflate(R.layout.list_personname, parent, false);
		}
		TextView tv1 = (TextView) convertView.findViewById(R.id.personname_tv_list);
		TextView tv2 = (TextView) convertView.findViewById(R.id.personname_qishishijian);
		TextView tv3 = (TextView) convertView.findViewById(R.id.personname_zhongzhishijian);
		TextView tv4 = (TextView) convertView.findViewById(R.id.personname_meiyouneinong);
		tv1.setText(name.get(position));
		if((checktimefirst.get(position)).length()>0){
			System.out.println("我看看这个first是多少"+checktimefirst.get(position));
			tv2.setText(checktimefirst.get(position));
			tv3.setText(checktimelast.get(position));
			tv4.setText("<<......>>");
		}else{
			tv4.setText("没有签到");
			tv4.setTextColor(android.graphics.Color.RED);
		}
		return convertView;
	}

}
