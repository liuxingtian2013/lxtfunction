package com.example.firstjob;
import com.example.Adapter.GridViewAdapternewwork;
import com.example.Adapter.PupupwindowGirdViewAdapter;
import com.example.Myselffunction.shezhi;
import com.example.newworkfunction.Punchcard;
import com.example.punchcard.R;
import com.newworkfunction.bangong.Gonggao;
import com.newworkfunction.bangong.Zhidu;
import com.newworkfunction.xiangmu.Contractmanagement;
import com.newworkfunction.xiangmu.Inventory;
import com.newworkfunction.xiangmu.Procurementlist;
import com.newworkfunction.xiangmu.Procurementplan;
import com.newworkfunction.xiangmu.Yusuanzonglan;
import com.pm.common.MyAnimations;
import com.pm.common.PopMenu;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONObject;
public class Fragementnewwork extends Fragment implements OnClickListener   {
	GridViewAdapternewwork gvadapter;
	JSONObject json ;
	public static GridView gridview;
	private View view;
	private PopMenu popMenu;
	private boolean areButtonsShowing;
	private RelativeLayout composerButtonsWrapper;
	private ImageView composerButtonsShowHideButtonIcon;
	private RelativeLayout composerButtonsShowHideButton;
	private ImageButton ib1,ib2,ib3,ib4,ib5,ib6;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreateView(inflater, container, savedInstanceState);
		shezhi.getInstance().addActivity(getActivity());//退出时杀死所有activity
		if (null != view) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (null != parent) {
				parent.removeView(view);
			}
		} else {
			view = inflater.inflate(R.layout.newwork, container,false);
			gvadapter= new GridViewAdapternewwork(getActivity());
			gridview = (GridView) view.findViewById(R.id.Gvproject);
			gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));//设置gridview点击时没有背景颜色
			gridview.setAdapter(gvadapter);
			popMenu = new PopMenu(getActivity(), R.drawable.tips_bg);
			gridview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
					if(position==0){
						popMenu.show(view, getActivity());//启动半透明界面
					}else if(position==1){
						Intent intent = new Intent();
						intent.setClass(getActivity(), Gonggao.class);
						startActivity(intent);
					}else if(position==2){
						Intent intent = new Intent();
						intent.setClass(getActivity(), Zhidu.class);
						startActivity(intent);
					}else if(position==3){
						Intent intent = new Intent();
						intent.setClass(getActivity(), Yusuanzonglan.class);
						startActivity(intent);
					}else if(position==4){
						Intent intent = new Intent();
						intent.setClass(getActivity(), Contractmanagement.class);
						startActivity(intent);
					}else if(position==5){
						Intent intent = new Intent();
						intent.setClass(getActivity(), Procurementplan.class);
						startActivity(intent);
					}else if(position==6){
						Intent intent = new Intent();
						intent.setClass(getActivity(), Procurementlist.class);
						startActivity(intent);
					}else if(position==7){
						Intent intent = new Intent();
						intent.setClass(getActivity(), Inventory.class);
						startActivity(intent);
					}else{
						Toast.makeText(getActivity(), "虚拟模块,请点击考勤", Toast.LENGTH_SHORT).show();
					}
				}
			});
			PopMenu.gridviewpop.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
					// TODO Auto-generated method stub
					Intent intent = new Intent();
					String name = PupupwindowGirdViewAdapter.img_name.get(position);//获取类名
					if(name.equals("attendance_online")){
						name ="Personnel";
					}if(name.equals("attendance_holidayorder")){
						name ="Qingjia";
					}if(name.equals("attendance_outorder")){
						name ="Waichu";
					}if(name.equals("attendance_check")){
						name ="punchcard";
					}
					try {
						Class cls = Class.forName("com.example.newworkfunction."+name);
						intent.setClass(getActivity(), cls);
						if(intent!=null){
							startActivity(intent);
						}
//						popMenu.dismiss();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} 
				}
			}
					);
			//以下到return之前为实现Pathmenu的代码（悬浮菜单）
			MyAnimations.initOffset(getActivity());
			composerButtonsWrapper = (RelativeLayout) view.findViewById(R.id.composer_buttons_wrapper);
			composerButtonsShowHideButton = (RelativeLayout) view.findViewById(R.id.composer_buttons_show_hide_button);
			composerButtonsShowHideButtonIcon = (ImageView) view.findViewById(R.id.composer_buttons_show_hide_button_icon);
			composerButtonsShowHideButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (!areButtonsShowing) {
						MyAnimations.startAnimationsIn(composerButtonsWrapper, 800);
						composerButtonsShowHideButtonIcon
						.startAnimation(MyAnimations.getRotateAnimation(0,-270, 800));
					} else {
						MyAnimations
						.startAnimationsOut(composerButtonsWrapper, 500);
						composerButtonsShowHideButtonIcon
						.startAnimation(MyAnimations.getRotateAnimation(-270, 0, 500));
					}
					areButtonsShowing = !areButtonsShowing;
				}
			});
			for (int i = 0; i < composerButtonsWrapper.getChildCount(); i++) {
				composerButtonsWrapper.getChildAt(i).setOnClickListener(
						new View.OnClickListener() {
							@Override
							public void onClick(View arg0) {
							}
						});
			}
			composerButtonsShowHideButton.startAnimation(MyAnimations.getRotateAnimation(0, 360, 200));
		}
		ib1 = (ImageButton) view.findViewById(R.id.composer_button_photo);
		ib1.setOnClickListener(this);
		ib2 = (ImageButton) view.findViewById(R.id.composer_button_people);
		ib2.setOnClickListener( this);
		ib3 = (ImageButton) view.findViewById(R.id.composer_button_place);
		ib3.setOnClickListener( this);
		ib4 = (ImageButton) view.findViewById(R.id.composer_button_music);
		ib4.setOnClickListener(this);
		ib5 = (ImageButton) view.findViewById(R.id.composer_button_thought);
		ib5.setOnClickListener(this);
		ib6 = (ImageButton) view.findViewById(R.id.composer_button_sleep);
		ib6.setOnClickListener(this);
		return view;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.composer_button_photo:
			Toast.makeText(getActivity(), "照相    位置1", Toast.LENGTH_SHORT).show();
			break;
		case R.id.composer_button_people:
			Toast.makeText(getActivity(), "这里计划要换成我    位置2", Toast.LENGTH_SHORT).show();
			break;
		case R.id.composer_button_place:
			Intent  intent = new Intent();
			intent.setClass(getActivity(), Punchcard.class);
			startActivity(intent);
			break;
		case R.id.composer_button_music:
			Toast.makeText(getActivity(), "这个将来要换成我的任务    位置4", Toast.LENGTH_SHORT).show();
			break;
		case R.id.composer_button_thought:
			Toast.makeText(getActivity(), "这个将来直接到聊天界面    位置5", Toast.LENGTH_SHORT).show();
			break;
		case R.id.composer_button_sleep:
			Toast.makeText(getActivity(), "这个将来要换成设置    位置6", Toast.LENGTH_SHORT).show();
			break;

		}
	}
}


