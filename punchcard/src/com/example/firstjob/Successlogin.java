package com.example.firstjob;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.Adapter.MainAdapter;
import com.example.Myselffunction.shezhi;
import com.example.punchcard.R;
//import com.pm.Memu.Floatmemu;

import android.support.v4.app.FragmentActivity;
public class Successlogin extends FragmentActivity{
	private ArrayList<Fragment> mylist;
	private ViewPager vp;
	private MainAdapter myadapter;
	private RadioGroup rg;
	//@Override
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		shezhi.getInstance().addActivity(this);
		setContentView(R.layout.successlogn);
		initdata();
		initview();
	}
	//按返回键到界面，再按程序  回到原界面（防止重复登陆）
	   @Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {  
				if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {  
				moveTaskToBack(true);  
					return false;  
				}  
				return super.onKeyDown(keyCode, event);  
			}
	private void initdata() {
		// TODO Auto-generated method stub
		mylist=new ArrayList<Fragment>();
		mylist.add(new Fragementnewwork());
		mylist.add(new Fragementmyself());
	}
	private void initview() {
		// TODO Auto-generated method stub
		vp=(ViewPager) findViewById(R.id.vp);
		rg= (RadioGroup) findViewById(R.id.rg);
		myadapter = new MainAdapter(getSupportFragmentManager(), mylist); 
		vp.setAdapter(myadapter);
		rg.check(R.id.rb1);//此句写在这里即为将该项设为默认界面  （表现为进入界面时“聊天”两个字是绿色的）
		vp.setCurrentItem(0);
		vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				switch (arg0) {
				case 0:
					rg.check(R.id.rb1);
					break;
				case 1:
					rg.check(R.id.rb2);
					break;
				}
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
			}
		});
		rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int chackedId) {
				// TODO Auto-generated method stub
				switch (chackedId) {
				case R.id.rb1:
					vp.setCurrentItem(0);
					break;
				case R.id.rb2:
					vp.setCurrentItem(1);
					break;
				}
			}
		});
	}
}
