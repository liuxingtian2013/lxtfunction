package com.example.Myselffunction;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.Adapter.listviewadapterdakajilu;
import com.example.firstjob.Fragementmyself;
import com.example.firstjob.MainActivity;
import com.example.punchcard.R;
import com.pm.common.Utils;
import com.pm.common.XRequest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
public class xinxi extends Activity {
	//	private static String requestURL = "http://192.168.0.163/FileHandler.ashx";
	private String[] items = new String[] { "选择本地图片", "拍照" };
	/* 头像名称 */
	//	private static final String IMAGE_FILE_NAME = "faceImage.jpg";
	private static final String IMAGE_FILE_NAME = Environment.getExternalStorageDirectory()+File.separator+"faceimage.jpg";
	public final static String FILE_NAME = "image.png";
	public static ImageView faceImage;
	private TextView tv1,tv2,tv3,tv4,tv5,tv6; 
	JSONObject json;
	ProgressDialog progressDialog;
	/* 请求码 */
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
				shezhi.getInstance().addActivity(this);//退出时杀死所有activity
		//		if(null == savedInstanceState){
		setContentView(R.layout.xinxi);
		initview();
		postxinxi();
		
		//		}
		//		setContentView(R.layout.xinxi);
	}
	private void initview() {
		// TODO Auto-generated method stub
		tv1=(TextView) findViewById(R.id.xinxi_tv_name);
		tv2=(TextView) findViewById(R.id.xinxi_tv_logename);
		tv3=(TextView) findViewById(R.id.xinxi_tv_NameOfPath);
		tv4=(TextView) findViewById(R.id.xinxi_tv_StationName);
		tv5=(TextView) findViewById(R.id.xinxi_tv_DutyName);
		tv6=(TextView) findViewById(R.id.xinxi_tv_phonenumber);
		ImageButton ib = (ImageButton) findViewById(R.id.xinxishuaxin_ib);
		ib.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				postxinxi();
			}
		});
		faceImage = (ImageView) findViewById(R.id.xinxi_iv);
		if(Fragementmyself.picture != null){
			faceImage.setImageBitmap(Fragementmyself.picture);
		}
		if(Fragementmyself.name!=null){
			tv1.setText(Fragementmyself.name+"  "+Fragementmyself.sex);
			tv2.setText(Fragementmyself.logename);
			tv3.setText(Fragementmyself.NameOfPath+Fragementmyself.DeptName);
			tv4.setText(Fragementmyself.StationName);
			tv5.setText(Fragementmyself.DutyName);
			tv6.setText(Fragementmyself.Mobile);
		}
	}
	
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		showDialog();
//	}
	/**
	 * 显示选择对话框
	 */
	private void showDialog() {
		new AlertDialog.Builder(this)
		.setTitle("设置头像")
		.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0:
					Intent intentFromGallery = new Intent();
					intentFromGallery.setType("image/*"); // 设置文件类型
					intentFromGallery
					.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(intentFromGallery,
							IMAGE_REQUEST_CODE);
					break;
				case 1:
					Intent intentFromCapture = new Intent(
							MediaStore.ACTION_IMAGE_CAPTURE);
					// 判断存储卡是否可以用，可用进行存储
					if (Tools.hasSdcard()) {
						//intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(Environment.getExternalStorageDirectory(),IMAGE_FILE_NAME)));
						intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(IMAGE_FILE_NAME)));
					}
					System.out.println("进入拍照！");
					startActivityForResult(intentFromCapture,
							CAMERA_REQUEST_CODE);
					break;
				}
			}
		})
		.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).show();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		System.out.println("进入onActivityResult  requestCode: "+requestCode+"    resultCode:"+resultCode+"  data "+data);
		//结果码不等于取消时候
		if (resultCode != RESULT_CANCELED) {
			switch (requestCode) {
			case IMAGE_REQUEST_CODE:
				startPhotoZoom(data.getData());
				break;
			case CAMERA_REQUEST_CODE:
				if (Tools.hasSdcard()) {
					//File tempFile = new File(Environment.getExternalStorageDirectory()+ IMAGE_FILE_NAME);
					File tempFile = new File(IMAGE_FILE_NAME);
					startPhotoZoom(Uri.fromFile(tempFile));
				} else {
					System.out.println("照片照完，但是找不到照片，无法切图！");
					Toast.makeText(xinxi.this, "未找到存储卡，无法存储照片！",
							Toast.LENGTH_LONG).show();
				}
				break;
			case RESULT_REQUEST_CODE:
				if (data != null) {  
					getImageToView(data);
				}
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 2);
	}
	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	private void getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			@SuppressWarnings("deprecation")
			Drawable drawable = new BitmapDrawable(photo);
			//			在此处获取裁剪之后的照片
			//			try {
			//				SaveBitmap(drawable);
			//			} catch (IOException e) {
			//				// TODO Auto-generated catch block
			//				e.printStackTrace();
			//			}
//			picture = drawable;
			//			UploadUtil.uploadFile(picture, requestURL);
			//			post();
//			System.out.println("这张图片的信息是什么呢"+drawable);
//			faceImage.setImageDrawable(picture);
//			(Fragementmyself.ib).setImageDrawable(picture);
		}
	}
	public void SaveBitmap(Drawable drawable) throws IOException {
		FileOutputStream fOut= null;
		//注解1
		String path = "/sdcard/Android/lxtfiles";
		try {
			//查看这个路径是否存在，
			//如果并没有这个路径，
			//			  创建这个路径
			File destDir = new File(path);
			if (!destDir.exists())
			{
				destDir.mkdirs();
			}
			fOut = new FileOutputStream(path) ;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//将Bitmap对象写入本地路径中，Unity在去相同的路径来读取这个文件
		//		bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		//		try {
		//			fOut.flush();
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void postxinxi(){
		String uid = MainActivity.getuserid();
		progressDialog = ProgressDialog.show(this, "请稍等...","获取数据中...", true);
		progressDialog.setCancelable(true);
		final List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("emp",uid));
		XRequest request = new XRequest();
		request.PostServerGPM("get_emp_model", params, new Handler() {
			@Override
			// 当有消息发送出来的时候就执行Handler的这个方法
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				//方法一   遍历
				String strResult = msg.obj.toString();
				int status_code = 200;
				try {
					JSONObject object = new JSONObject();
					object = new JSONObject(strResult);
					status_code = Integer.parseInt(object.getString("status"));
					String message = "";
					try {
						message = object.getString("msg").toString();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					switch (status_code) {
					case -1:
						progressDialog.dismiss();
						Toast.makeText(getApplicationContext() ,message, Toast.LENGTH_SHORT).show();
						//						Toast.makeText(getApplicationContext(), "执行post刷新完毕", Toast.LENGTH_SHORT).show();
						break;
					}
				} catch (NumberFormatException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (JSONException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					JSONObject jsonlxt;
					//					if(strResult!=null && !strResult.equals("")){
					jsonlxt = new JSONObject(strResult);
					try {
						//							json=jsonlxt.getJSONObject(i);
						String name = jsonlxt.getString("Name");
						String sex = jsonlxt.getString("Sex");
						if(sex.equals("1")){
							sex = "男";
						}else{sex="女";}
						String logename = jsonlxt.getString("LoginName");
						String  Mobile  = jsonlxt.getString("Mobile");
						String NameOfPath = jsonlxt.getString("NameOfPath");
						String DeptName = jsonlxt.getString("DeptName");
						String StationName = jsonlxt.getString("StationName");
						String DutyName = jsonlxt.getString("DutyName");
						tv1.setText(name+"  "+sex);
						tv2.setText(logename);
						tv3.setText(NameOfPath+DeptName);
						tv4.setText(StationName);
						tv5.setText(DutyName);
						tv6.setText(Mobile);
						 faceImage.setImageBitmap(Fragementmyself.picture);
//						 (Fragementmyself.ib).setImageBitmap(bitmap);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					//加载数据结束时提示框消失
					progressDialog.dismiss();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
				);
	}
}
class Tools {
	/**
	 * 检查是否存在SDCard
	 * @return
	 */
	public static boolean hasSdcard(){
		String state = Environment.getExternalStorageState();
		if(state.equals(Environment.MEDIA_MOUNTED)){
			return true;
		}else{
			return false;
		}
	}
}