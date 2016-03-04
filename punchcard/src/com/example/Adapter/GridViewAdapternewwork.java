package com.example.Adapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.example.firstjob.BaseViewHolder;
import com.example.punchcard.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
public class GridViewAdapternewwork extends BaseAdapter{
	private Context mContext;
//	public static ArrayList<String> img_text = new ArrayList<String>();
//	public static ArrayList<String> img_name = new ArrayList<String>();
//	public static Map< String, Object> Icons = new HashMap< String, Object>();
	public String[] img_text={"考勤","公告","制度","预算总览","合同管理" ,"采购计划","采购清单","现场盘点","照相","设置"};
	public int[] img={R.drawable.placeicon,R.drawable.notebook,R.drawable.masses,R.drawable.lcd,R.drawable.cloud,R.drawable.contact
			,R.drawable.mike,R.drawable.msg,R.drawable.camera,R.drawable.seting};
	public GridViewAdapternewwork(Context mContext){
		super();
		this.mContext=mContext;
//		Icons.put("attendance_online",R.drawable.people);
//		Icons.put("attendance_holidayorder",R.drawable.qingjia);
//		Icons.put("attendance_outorder",R.drawable.out);
//		Icons.put("attendance_check",R.drawable.signname);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return img_text.length;
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
		if(convertView==null){
			convertView = LayoutInflater.from(mContext).inflate
					(R.layout.fragmentnewwork, parent,false);
		}
		TextView tv = BaseViewHolder.get(convertView, R.id.TVproject);
		ImageView iv = BaseViewHolder.get(convertView, R.id.IVproject);
		iv.setBackgroundResource(img[position]);
		tv.setText(img_text[position]);
		return convertView;
	}
	}

