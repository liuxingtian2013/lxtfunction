package com.pm.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Adapter.PupupwindowGirdViewAdapter;
import com.example.Myselffunction.shezhi;
import com.example.firstjob.MainActivity;
import com.example.punchcard.R;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
@SuppressLint("CutPasteId")
public class PopMenu {

	// 存储每个按钮点击后对应生成的drawable
	public  Map<View, Drawable> mapDrawable = new HashMap<View, Drawable>();
	ProgressDialog progressDialog;
	private Context context;
	private PopupWindow popupWindow;	
	public static GridView gridviewpop;
	public static ImageButton ib;
	private int screenwidth;// 屏幕的宽度
	private PupupwindowGirdViewAdapter gridadapter;
	private int popupWindowHeight = 0;// popupWindow的高度
	private Drawable popBg; //背景图片
	private Drawable bg;//生成的背景图片
	JSONObject json ;
	public PopMenu(Context context, int drawableID) {
		this.context = context;
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		View view = inflater.inflate(R.layout.popmenu_layout, null);
		setCursorWidth();
		gridviewpop = (GridView) view.findViewById(R.id.myGridView);
		ib = (ImageButton) view.findViewById(R.id.ImageButton_tuichubeijing);
		gridadapter = new PupupwindowGirdViewAdapter(context);
		popupWindow = new PopupWindow(view, LayoutParams.MATCH_PARENT, context
				.getResources().getDimensionPixelSize(R.dimen.popmenu_h));//设置popupWindow的高度设置背景图片大小的   高度最好不要超过800 可能会造成资源溢出  OOM问题
		// 生成drawable
		this.popBg = context.getResources().getDrawable(drawableID);
				post();//获取数据
		ib.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
	}
	public void setCursorWidth() {
		screenwidth = getScreenWidth();
		Matrix matrix = new Matrix();
		matrix.postTranslate(screenwidth, 0);
	}
	public int getScreenWidth() {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
		.getMetrics(dm);
		int screenW = dm.widthPixels;
		return screenW;
	}
	public void show(View view, Context context) {
		if(mapDrawable.size()>=0){
			mapDrawable.clear();
		}
		bg = mapDrawable.get(view);// 为节省资源，map中会保存以前生成的背景，根据父控件来获得
		popupWindowHeight = popupWindow.getHeight();// 得到popupWindow的高度，在popupWindow构造完后才能获取
		if (bg == null)// 背景为空
		{
			createDrawable(context);// 重新生成背景图
			mapDrawable.put(view, bg);// 保存到map中
		}
//		Toast.makeText(context, "让我看看这个hashmap的长度为："+mapDrawable.size(), Toast.LENGTH_SHORT).show();
		popupWindow.setBackgroundDrawable(bg);// 给popupWindow设置背景
		popupWindow.showAtLocation(view, Gravity.BOTTOM, 0,view.getHeight());// 距离底部的位置
		popupWindow.setAnimationStyle(R.style.popwin_anim_style);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.update();
	}
	public void dismiss() {
		popupWindow.dismiss();
	}
	/**
	 * 以一个Bitmap为画布，画上一个Bitmap
	 * 
	 * @param canvasBitmap
	 *            作为画布的Bitmap
	 * @param drawBitmap
	 *            要被绘制的Bitmap
	 * @param top
	 *            从画布的距离顶部的top位置开始
	 * @param left
	 *            从画布的距离左边的left位置开始
	 */
	private void drawbitMap(Bitmap canvasBitmap, Bitmap drawBitmap, int top,int left){
		Canvas localCanvas = new Canvas(canvasBitmap);// 以canvasBitmap生成画布
		localCanvas.drawBitmap(drawBitmap, left, top, null);// 在画布上移left和top左标开始绘制drawBitmap
		localCanvas.save(Canvas.ALL_SAVE_FLAG);// 保存
		localCanvas.restore();
		drawBitmap.recycle();// 释放掉drawBitmap，防止内存泄漏
	}
	/**
	 * 把Drawable生成对应的Bitmap
	 * 
	 * @param paramRect
	 *            生成的Bitmap大小等一些参数
	 * @param drawable
	 *            要绘制的drawable
	 * @param canvasBitmap
	 *            将drawable绘制到canvasBitmap中
	 */
	private void getBitMap(Rect paramRect, Drawable drawable,
			Bitmap canvasBitmap) {
		drawable.setBounds(0, 0, paramRect.right, paramRect.bottom);
		// 用canvasBitmap生成一个画布
		Canvas localCanvas = new Canvas(canvasBitmap);
		drawable.draw(localCanvas);// 使用drawable的draw方法画到画布上
		localCanvas.save(Canvas.ALL_SAVE_FLAG);// 保存
		localCanvas.restore();
	}
	private void createDrawable(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		@SuppressWarnings("deprecation")
		int width = wm.getDefaultDisplay().getWidth();
		// 图片的大小等一些参数
		Rect arrayOfRect = new Rect();
		arrayOfRect.top = 0;
		arrayOfRect.left = 0;
		arrayOfRect.right = width;
		arrayOfRect.bottom = this.popupWindowHeight;
		// 生成背景
		bg = getDrawable(context, arrayOfRect, popBg);
	}
	/**
	 * 生成背景图
	 */
	private Drawable getDrawable(Context context, Rect ArrayOfRect,Drawable ArrayOfDrawable) {
		Bitmap.Config localConfig = Bitmap.Config.ARGB_8888;
		Bitmap paramBitmap = null;
		// 先根据popupWindow的大小生成一个Bitmap
			paramBitmap = Bitmap.createBitmap(screenwidth,popupWindowHeight, localConfig);
		Bitmap localBitmap = Bitmap.createBitmap(ArrayOfRect.right,ArrayOfRect.bottom, localConfig);
		getBitMap(ArrayOfRect, ArrayOfDrawable, localBitmap);// 得到相应的drawable的BitMap
		drawbitMap(paramBitmap, localBitmap, ArrayOfRect.top, ArrayOfRect.left);// 在paramBitmap中绘制localBitmap
		localBitmap.recycle();// 释放掉，要不多次运行有可能会内存泄漏
		return new BitmapDrawable(context.getResources(), paramBitmap);
	}
	public void post(){
		String uid = MainActivity.getuserid();
		progressDialog = ProgressDialog.show(context, "请稍等...","获取数据中...", true);
		progressDialog.setCancelable(true);
		final List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("emp",uid));
		params.add(new BasicNameValuePair("type","3"));
		XRequest request = new XRequest();
		request.PostServer("get_emp_func", params, new Handler() {
			@Override
			// 当有消息发送出来的时候就执行Handler的这个方法
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				//方法一   遍历 
				String strResult = msg.obj.toString();
				JSONObject object = new JSONObject();
				try {
					object = new JSONObject(strResult);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				int status_code = 500;
				try {
					status_code = Integer.parseInt(object.getString("status"));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				switch (status_code) {
				case -2:
					progressDialog.dismiss();
					Toast.makeText(context ,"系统错误,请稍候重试", Toast.LENGTH_SHORT).show();
					break;
				case -3:
					progressDialog.dismiss();
					Toast.makeText(context ,"网络链接超时,请稍候重试", Toast.LENGTH_SHORT).show();
					break;
				case -4:
					progressDialog.dismiss();
					Toast.makeText(context ,"联网失败，请检查您的网络设置", Toast.LENGTH_SHORT).show();
					break;
				}
				JSONArray jsonlxt;
				try {
					jsonlxt = new JSONArray(strResult);
					PupupwindowGirdViewAdapter.img_text.clear();
					PupupwindowGirdViewAdapter.img_name.clear();
					for(int i=0 ; i < jsonlxt.length() ;i++){
						try {
							json=jsonlxt.getJSONObject(i);
							PupupwindowGirdViewAdapter.img_text.add(json.getString("Title"));
							PupupwindowGirdViewAdapter.img_name.add(json.getString("Name"));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					//					System.out.println("我看看取到的类"+GridViewAdapternewwork.img_text+GridViewAdapternewwork.img_name);
					//加载数据结束时提示框消失
					progressDialog.dismiss();
					gridviewpop.setAdapter(gridadapter);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
				);
	}
}

