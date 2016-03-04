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
	private String[] items = new String[] { "ѡ�񱾵�ͼƬ", "����" };
	/* ͷ������ */
	//	private static final String IMAGE_FILE_NAME = "faceImage.jpg";
	private static final String IMAGE_FILE_NAME = Environment.getExternalStorageDirectory()+File.separator+"faceimage.jpg";
	public final static String FILE_NAME = "image.png";
	public static ImageView faceImage;
	private TextView tv1,tv2,tv3,tv4,tv5,tv6; 
	JSONObject json;
	ProgressDialog progressDialog;
	/* ������ */
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
				shezhi.getInstance().addActivity(this);//�˳�ʱɱ������activity
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
	 * ��ʾѡ��Ի���
	 */
	private void showDialog() {
		new AlertDialog.Builder(this)
		.setTitle("����ͷ��")
		.setItems(items, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0:
					Intent intentFromGallery = new Intent();
					intentFromGallery.setType("image/*"); // �����ļ�����
					intentFromGallery
					.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(intentFromGallery,
							IMAGE_REQUEST_CODE);
					break;
				case 1:
					Intent intentFromCapture = new Intent(
							MediaStore.ACTION_IMAGE_CAPTURE);
					// �жϴ洢���Ƿ�����ã����ý��д洢
					if (Tools.hasSdcard()) {
						//intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(Environment.getExternalStorageDirectory(),IMAGE_FILE_NAME)));
						intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(IMAGE_FILE_NAME)));
					}
					System.out.println("�������գ�");
					startActivityForResult(intentFromCapture,
							CAMERA_REQUEST_CODE);
					break;
				}
			}
		})
		.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).show();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		System.out.println("����onActivityResult  requestCode: "+requestCode+"    resultCode:"+resultCode+"  data "+data);
		//����벻����ȡ��ʱ��
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
					System.out.println("��Ƭ���꣬�����Ҳ�����Ƭ���޷���ͼ��");
					Toast.makeText(xinxi.this, "δ�ҵ��洢�����޷��洢��Ƭ��",
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
	 * �ü�ͼƬ����ʵ��
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// ���òü�
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 2);
	}
	/**
	 * ����ü�֮���ͼƬ����
	 * 
	 * @param picdata
	 */
	private void getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			@SuppressWarnings("deprecation")
			Drawable drawable = new BitmapDrawable(photo);
			//			�ڴ˴���ȡ�ü�֮�����Ƭ
			//			try {
			//				SaveBitmap(drawable);
			//			} catch (IOException e) {
			//				// TODO Auto-generated catch block
			//				e.printStackTrace();
			//			}
//			picture = drawable;
			//			UploadUtil.uploadFile(picture, requestURL);
			//			post();
//			System.out.println("����ͼƬ����Ϣ��ʲô��"+drawable);
//			faceImage.setImageDrawable(picture);
//			(Fragementmyself.ib).setImageDrawable(picture);
		}
	}
	public void SaveBitmap(Drawable drawable) throws IOException {
		FileOutputStream fOut= null;
		//ע��1
		String path = "/sdcard/Android/lxtfiles";
		try {
			//�鿴���·���Ƿ���ڣ�
			//�����û�����·����
			//			  �������·��
			File destDir = new File(path);
			if (!destDir.exists())
			{
				destDir.mkdirs();
			}
			fOut = new FileOutputStream(path) ;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//��Bitmap����д�뱾��·���У�Unity��ȥ��ͬ��·������ȡ����ļ�
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
		progressDialog = ProgressDialog.show(this, "���Ե�...","��ȡ������...", true);
		progressDialog.setCancelable(true);
		final List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("emp",uid));
		XRequest request = new XRequest();
		request.PostServerGPM("get_emp_model", params, new Handler() {
			@Override
			// ������Ϣ���ͳ�����ʱ���ִ��Handler���������
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				//����һ   ����
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
						//						Toast.makeText(getApplicationContext(), "ִ��postˢ�����", Toast.LENGTH_SHORT).show();
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
							sex = "��";
						}else{sex="Ů";}
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
					//�������ݽ���ʱ��ʾ����ʧ
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
	 * ����Ƿ����SDCard
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