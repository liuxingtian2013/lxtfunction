package com.example.Adapter;
import java.util.ArrayList;
import com.example.punchcard.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
public class listviewadapterqingjiajilu extends BaseAdapter{
	private Context mContext;
	public static ArrayList<String> qjjl_type = new ArrayList<String>();
	public static ArrayList<String> qjjl_zhuangtai = new ArrayList<String>();
	public static ArrayList<String> qjjl_shenqingshiyou = new ArrayList<String>();
	public static ArrayList<String> qjjl_gongzuojiaojie = new ArrayList<String>();
	public static ArrayList<String> qjjl_qishishijian = new ArrayList<String>();
	public static ArrayList<String> qjjl_zhongzhishijian = new ArrayList<String>();
	public listviewadapterqingjiajilu(Context mContext) {
		super();
		this.mContext = mContext;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return qjjl_type.size();
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
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.list_qingjiajilu, parent, false);
		}
		TextView tv1 = (TextView) convertView.findViewById(R.id.qingjiajilu_type);
		TextView tv2 = (TextView) convertView.findViewById(R.id.qingjiajilu_zhuangtai);
		TextView tv3 = (TextView) convertView.findViewById(R.id.qingjiajilu_shenqingshiyou);
//		TextView tv4 = (TextView) convertView.findViewById(R.id.waichujilu_gongzuojiaojie);
		TextView tv5 = (TextView) convertView.findViewById(R.id.qingjiajilu_qishishijian);
		TextView tv6 = (TextView) convertView.findViewById(R.id.qingjiajilu_zhongzhishijian);
		tv1.setText(qjjl_type.get(position));
		if(qjjl_zhuangtai.get(position).equals("10")){
			tv2.setText("通过");
		}if(qjjl_zhuangtai.get(position).equals("1")){
			tv2.setText("待审批");
		}if(qjjl_zhuangtai.get(position).equals("2")){
			tv2.setText("审批中");
		}if(qjjl_zhuangtai.get(position).equals("-1")){
			tv2.setText("拒绝");
		}if(qjjl_zhuangtai.get(position).equals("3")){
			tv2.setText("审批超时");
		}if(qjjl_zhuangtai.get(position).equals("0")){
			tv2.setText("未提交");
		}if(qjjl_zhuangtai.get(position).equals("4")){
			tv2.setText("撤回");
		}
		tv3.setText(qjjl_shenqingshiyou.get(position));
//		tv4.setText(wcjl_gongzuojiaojie.get(position));
		tv5.setText(qjjl_qishishijian.get(position));
		tv6.setText(qjjl_zhongzhishijian.get(position));
		return convertView;
	}

}
