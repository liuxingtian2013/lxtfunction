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

	// �洢ÿ����ť������Ӧ���ɵ�drawable
	public  Map<View, Drawable> mapDrawable = new HashMap<View, Drawable>();
	ProgressDialog progressDialog;
	private Context context;
	private PopupWindow popupWindow;	
	public static GridView gridviewpop;
	public static ImageButton ib;
	private int screenwidth;// ��Ļ�Ŀ��
	private PupupwindowGirdViewAdapter gridadapter;
	private int popupWindowHeight = 0;// popupWindow�ĸ߶�
	private Drawable popBg; //����ͼƬ
	private Drawable bg;//���ɵı���ͼƬ
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
				.getResources().getDimensionPixelSize(R.dimen.popmenu_h));//����popupWindow�ĸ߶����ñ���ͼƬ��С��   �߶���ò�Ҫ����800 ���ܻ������Դ���  OOM����
		// ����drawable
		this.popBg = context.getResources().getDrawable(drawableID);
				post();//��ȡ����
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
		bg = mapDrawable.get(view);// Ϊ��ʡ��Դ��map�лᱣ����ǰ���ɵı��������ݸ��ؼ������
		popupWindowHeight = popupWindow.getHeight();// �õ�popupWindow�ĸ߶ȣ���popupWindow���������ܻ�ȡ
		if (bg == null)// ����Ϊ��
		{
			createDrawable(context);// �������ɱ���ͼ
			mapDrawable.put(view, bg);// ���浽map��
		}
//		Toast.makeText(context, "���ҿ������hashmap�ĳ���Ϊ��"+mapDrawable.size(), Toast.LENGTH_SHORT).show();
		popupWindow.setBackgroundDrawable(bg);// ��popupWindow���ñ���
		popupWindow.showAtLocation(view, Gravity.BOTTOM, 0,view.getHeight());// ����ײ���λ��
		popupWindow.setAnimationStyle(R.style.popwin_anim_style);
		popupWindow.setFocusable(true);
		popupWindow.setOutsideTouchable(true);
		popupWindow.update();
	}
	public void dismiss() {
		popupWindow.dismiss();
	}
	/**
	 * ��һ��BitmapΪ����������һ��Bitmap
	 * 
	 * @param canvasBitmap
	 *            ��Ϊ������Bitmap
	 * @param drawBitmap
	 *            Ҫ�����Ƶ�Bitmap
	 * @param top
	 *            �ӻ����ľ��붥����topλ�ÿ�ʼ
	 * @param left
	 *            �ӻ����ľ�����ߵ�leftλ�ÿ�ʼ
	 */
	private void drawbitMap(Bitmap canvasBitmap, Bitmap drawBitmap, int top,int left){
		Canvas localCanvas = new Canvas(canvasBitmap);// ��canvasBitmap���ɻ���
		localCanvas.drawBitmap(drawBitmap, left, top, null);// �ڻ�������left��top��꿪ʼ����drawBitmap
		localCanvas.save(Canvas.ALL_SAVE_FLAG);// ����
		localCanvas.restore();
		drawBitmap.recycle();// �ͷŵ�drawBitmap����ֹ�ڴ�й©
	}
	/**
	 * ��Drawable���ɶ�Ӧ��Bitmap
	 * 
	 * @param paramRect
	 *            ���ɵ�Bitmap��С��һЩ����
	 * @param drawable
	 *            Ҫ���Ƶ�drawable
	 * @param canvasBitmap
	 *            ��drawable���Ƶ�canvasBitmap��
	 */
	private void getBitMap(Rect paramRect, Drawable drawable,
			Bitmap canvasBitmap) {
		drawable.setBounds(0, 0, paramRect.right, paramRect.bottom);
		// ��canvasBitmap����һ������
		Canvas localCanvas = new Canvas(canvasBitmap);
		drawable.draw(localCanvas);// ʹ��drawable��draw��������������
		localCanvas.save(Canvas.ALL_SAVE_FLAG);// ����
		localCanvas.restore();
	}
	private void createDrawable(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		@SuppressWarnings("deprecation")
		int width = wm.getDefaultDisplay().getWidth();
		// ͼƬ�Ĵ�С��һЩ����
		Rect arrayOfRect = new Rect();
		arrayOfRect.top = 0;
		arrayOfRect.left = 0;
		arrayOfRect.right = width;
		arrayOfRect.bottom = this.popupWindowHeight;
		// ���ɱ���
		bg = getDrawable(context, arrayOfRect, popBg);
	}
	/**
	 * ���ɱ���ͼ
	 */
	private Drawable getDrawable(Context context, Rect ArrayOfRect,Drawable ArrayOfDrawable) {
		Bitmap.Config localConfig = Bitmap.Config.ARGB_8888;
		Bitmap paramBitmap = null;
		// �ȸ���popupWindow�Ĵ�С����һ��Bitmap
			paramBitmap = Bitmap.createBitmap(screenwidth,popupWindowHeight, localConfig);
		Bitmap localBitmap = Bitmap.createBitmap(ArrayOfRect.right,ArrayOfRect.bottom, localConfig);
		getBitMap(ArrayOfRect, ArrayOfDrawable, localBitmap);// �õ���Ӧ��drawable��BitMap
		drawbitMap(paramBitmap, localBitmap, ArrayOfRect.top, ArrayOfRect.left);// ��paramBitmap�л���localBitmap
		localBitmap.recycle();// �ͷŵ���Ҫ����������п��ܻ��ڴ�й©
		return new BitmapDrawable(context.getResources(), paramBitmap);
	}
	public void post(){
		String uid = MainActivity.getuserid();
		progressDialog = ProgressDialog.show(context, "���Ե�...","��ȡ������...", true);
		progressDialog.setCancelable(true);
		final List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("emp",uid));
		params.add(new BasicNameValuePair("type","3"));
		XRequest request = new XRequest();
		request.PostServer("get_emp_func", params, new Handler() {
			@Override
			// ������Ϣ���ͳ�����ʱ���ִ��Handler���������
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				//����һ   ���� 
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
					Toast.makeText(context ,"ϵͳ����,���Ժ�����", Toast.LENGTH_SHORT).show();
					break;
				case -3:
					progressDialog.dismiss();
					Toast.makeText(context ,"�������ӳ�ʱ,���Ժ�����", Toast.LENGTH_SHORT).show();
					break;
				case -4:
					progressDialog.dismiss();
					Toast.makeText(context ,"����ʧ�ܣ�����������������", Toast.LENGTH_SHORT).show();
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
					//					System.out.println("�ҿ���ȡ������"+GridViewAdapternewwork.img_text+GridViewAdapternewwork.img_name);
					//�������ݽ���ʱ��ʾ����ʧ
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

