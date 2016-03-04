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
public class PupupwindowGirdViewAdapter extends BaseAdapter{
	private Context mContext;
	public static ArrayList<String> img_text = new ArrayList<String>();
	public static ArrayList<String> img_name = new ArrayList<String>();
	public static Map< String, Object> Icons = new HashMap< String, Object>();
//	public String[] img_text={"¿¼ÇÚ" };
//	public int[] img={R.drawable.placeicon};
	public PupupwindowGirdViewAdapter(Context mContext){
		super();
		this.mContext=mContext;
		Icons.put("attendance_online",R.drawable.people);
		Icons.put("attendance_holidayorder",R.drawable.qingjia);
		Icons.put("attendance_outorder",R.drawable.out);
		Icons.put("attendance_check",R.drawable.signname);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return img_text.size();
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
					(R.layout.child_imgbtn_popupwindow, parent,false);
		}
		TextView tv = BaseViewHolder.get(convertView, R.id.imgbtn_text);
		ImageView iv = BaseViewHolder.get(convertView, R.id.imgbtn_img);
		String name = img_name.get(position);
		Object image = Icons.get(name);
		if(image != null){
			iv.setBackgroundResource((Integer) image);
		}
		tv.setText(img_text.get(position));
		return convertView;
	}
	
	}

