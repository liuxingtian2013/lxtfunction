package com.example.Adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainAdapter extends FragmentPagerAdapter{
private ArrayList<Fragment> mylist;
	public MainAdapter(FragmentManager fm,ArrayList<Fragment> list) {
		// TODO Auto-generated constructor stub
		 super(fm);
		mylist=list;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		if(mylist!=null){
			return mylist.get(arg0);
		}
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(mylist!=null){
			return mylist.size();
		}
		return 0;
	}

}
