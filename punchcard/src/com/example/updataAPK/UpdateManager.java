package com.example.updataAPK;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Adapter.PupupwindowGirdViewAdapter;
import com.example.firstjob.MainActivity;
import com.example.punchcard.R;
import com.pm.common.XRequest;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class UpdateManager {
	JSONObject json ;
	ProgressDialog progressDialog;
    private Context mContext;
    // ���½�����
    private ProgressBar mUpdateProgressBar;
    // ��¼����������
    private int progress;
    // ���������XML��Ϣ
    private HashMap<String, String> mHashMap = new HashMap<String, String>();
    // �Ƿ�ȡ������
    private boolean cancelUpdate = false;
    // ����״̬--������
    private static final int DOWNLOAD_ING = 1;
    // ����״̬--���سɹ�
    private static final int DOWNLOAD_SUCCESS = 2;
    // ����״̬--����ʧ��
    private static final int DOWNLOAD_FAIL = 3;
    // ���ر���·��
    private String mSavePath;
    //��ȡ�汾�ű���
     private  float versionCode = 0;
    //���ضԻ���
    private Dialog mDownloadDialog;
    private Handler mHandler;
    private boolean isLoop;
    public UpdateManager(Context context) {
        super();
        this.mContext = context;
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                case DOWNLOAD_ING:
                    mUpdateProgressBar.setProgress(progress);
                    break;
                case DOWNLOAD_SUCCESS:
                    //��װAPK
                    installApk();
//                    GetBroadcast.registerReceiver(mContext);//ע��㲥�����ڼ���Ӧ���Ƿ�װ���
                    break;
                case DOWNLOAD_FAIL:
                    Toast.makeText(mContext, "�ļ�����ʧ�ܣ�", Toast.LENGTH_LONG).show();
                    break;
                }
            }
        };
    }
    /**
     * ����������
     * 
     * @param isAuto
     *            Ϊtrue:����Զ������£�false���û��ֶ������¡�
     */
//    public void checkUpdate(boolean isAuto) {
//        try {
//            if (isUpdate()) {
//            	 Toast.makeText(mContext, "���°汾����Ҫ���£�", Toast.LENGTH_SHORT).show();
//                // ��ʾ��ʾ�Ի���
//                showNoticeDialog();
////            } else if (!isAuto) {
//            }else{
//                // �����û��������°汾������Ҫ���¡�
//                Toast.makeText(mContext, "��������������°汾������Ҫ���£�", Toast.LENGTH_SHORT).show();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    /**
     * �������Ƿ��и���
     * 
     * @return
     * @throws Exception 
     */
//    private boolean isUpdate() throws Exception {
//        //��ȡ��ǰ����汾
//        int versionCode = getVersionVode(mContext);
//        //��ȡ����֮ǰ���ڷ������˵�app_version.xml���ļ���Ϣ
//        //Android 3.0������֮��������綼���������߳���
//        isLoop = true;
//        postupdate();
//        new Thread(){
//            @Override
//            public void run() {
//            	
//                try {
//                    InputStream inStream = HttpUtil.getInputStream("apks/app_version.xml", null, HttpUtil.METHOD_GET);
//                    //����xml�ļ�������XML�ļ���С�����ǲ���DOM��ʽ���н���
//                    ParseXmlService service = new ParseXmlService();//��������Լ�д�Ľ���XML�Ĺ�����
//                    try {
//                        mHashMap = service.parseXml(inStream);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }finally{
//                        isLoop = false;
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//        while (isLoop) {
//            Thread.sleep(1000);
//        }
//        System.out.println("������������ʲô"+mHashMap);
//        if (null != mHashMap) {
//            int serviceCode = Integer.valueOf(mHashMap.get("vveerr"));
//            //�жϰ汾��
//            if (serviceCode > versionCode) {
//                return true;
//            }
//        }
//        return false;
//    }
    /**
     * ��ȡ�����ǰ�汾��
     * 
     * @param context
     * @return
     */
    public String getVersionVode(Context context){
        String versionCode ="" ;
        // ��ȡ����汾�ţ���ӦAndroidManifest.xml��android:versionCode
        try {
            versionCode = context.getPackageManager().getPackageInfo("com.example.punchcard", 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
    /**
     * ��ʾ������¶Ի���
     */
    private void showNoticeDialog() {
        AlertDialog.Builder builder = new Builder(mContext);
        builder.setTitle("�������")
        .setMessage("������°汾��Ҫ������")
        .setPositiveButton("��������", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //��ʾ������ضԻ���
                showDownloadDialog();
            }
        })
        .setNegativeButton("�Ժ���˵", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }
    /**
     * ��ʾ������ضԻ���+
     */
    private void showDownloadDialog() {
        AlertDialog.Builder builder = new Builder(mContext);
        builder.setTitle("���ڸ���");
        builder.setMessage("�����ĵȴ�...");
        //���Ի������ӽ�����
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.update_progress, null);
        mUpdateProgressBar = (ProgressBar) v.findViewById(R.id.updateapk_progressBar);
        mDownloadDialog = builder.setView(v).setNegativeButton("ȡ������", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //����ȡ��״̬
                cancelUpdate = true;
            }
        }).create();
        mDownloadDialog.show();
        //����APK�ļ�
        downloadApk();
    }
    /**
     * ����APK�ļ�
     */
    private void downloadApk() {
        //��������APK�߳�
        new DownloadApkThread().start();
    }
    /**
     * ����APK�ļ��߳�
     */
    private class DownloadApkThread extends Thread {
        @Override
        public void run() {
            try {
                // �ж�SD���Ƿ���ڣ������Ƿ��ж�дȨ��
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // ��ȡSD��·��
                    String sdPadth = Environment.getExternalStorageDirectory()+ "";
                    mSavePath = sdPadth + "/download/chunhui";
            		 URL url = new URL(mHashMap.get("url"));
                    // ��������
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    // ��ȡ�ļ���С
                    int fileSize = conn.getContentLength();
                    // ����������
                    InputStream inStream = conn.getInputStream();
                    File file = new File(mSavePath);
                    // �ж��ļ�Ŀ¼�Ƿ����,�������򴴽���Ŀ¼    
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    File apkFile = new File(mSavePath, mHashMap.get("filename"));
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // ����
                    byte[] b = new byte[1024];
                    do {
                        int numRead = inStream.read(b);
                        count += numRead;
                        // ���������λ��
                        progress = (int) (((float) count / fileSize) * 100);
                        // ���½���
                        mHandler.sendEmptyMessage(DOWNLOAD_ING);
                        if (numRead <= 0) {// �������
                            mHandler.sendEmptyMessage(DOWNLOAD_SUCCESS);
                            break;
                        }
                        // д���ļ�
                        fos.write(b, 0, numRead);
                    } while (!cancelUpdate);
                }
            } catch (Exception e) {
                mHandler.sendEmptyMessage(DOWNLOAD_FAIL);
                e.printStackTrace();
            } finally {
                mDownloadDialog.dismiss();
            }
        }
    }
    /**
     * ��װAPK
     */
    private void installApk() {
        File apk = new File(mSavePath,mHashMap.get("filename"));
        if (!apk.exists()) {
            return;
        }
        //ͨ��Intent��װAPK�ļ�
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://"+apk.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(intent);
//        GetBroadcast.registerReceiver(mContext);//����Ҫ������װ�ĵط����ô˷������м���
    }
    public void postupdate(){
    	mHashMap.clear();
    	//��ȡ��ǰ����汾
    	versionCode = Float.parseFloat(getVersionVode(mContext));
        //��ȡ����֮ǰ���ڷ������˵�app_version.xml���ļ���Ϣ
        //Android 3.0������֮��������綼���������߳���
        isLoop = true;
		progressDialog = ProgressDialog.show(mContext, "���Ե�...","�����", true);
//		progressDialog.setCancelable(true);
		final List<NameValuePair> params = new ArrayList<NameValuePair>();
		XRequest request = new XRequest();
		request.Postupdate("check_ver", params, new Handler() {
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
					Toast.makeText(mContext ,"ϵͳ����,���Ժ�����", Toast.LENGTH_SHORT).show();
					break;
				case -3:
					progressDialog.dismiss();
					Toast.makeText(mContext ,"�������ӳ�ʱ,���Ժ�����", Toast.LENGTH_SHORT).show();
					break;
				case -4:
					progressDialog.dismiss();
					Toast.makeText(mContext ,"����ʧ�ܣ�����������������", Toast.LENGTH_SHORT).show();
					break;
				}
				try {
					object = new JSONObject(strResult);
					String vernum = object.getString("ver");
					mHashMap.put("vveerr", vernum);
//				        if (null != mHashMap) {
				            float serviceCode = Float.parseFloat(mHashMap.get("vveerr"));
				            //�жϰ汾��
				            if (serviceCode > versionCode) {
				            	progressDialog.dismiss();
//				            	 Toast.makeText(mContext, "���°汾����Ҫ���£�", Toast.LENGTH_SHORT).show();
									String url = object.getString("url");
									System.out.println("���������ַ�ǣ���"+url);
									mHashMap.put("url", url);
				            		String filename = url.substring(url.lastIndexOf("/")+1,url.lastIndexOf("."))+".apk";
				            		mHashMap.put("filename", filename);
				                 // ��ʾ��ʾ�Ի���
				                 showNoticeDialog();
				            }else{
				            	// �����û��������°汾������Ҫ���¡�
				            	progressDialog.dismiss();
				                Toast.makeText(mContext, "��������������°汾������Ҫ���£�", Toast.LENGTH_SHORT).show();
				            }
//				        }
				        
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
				);
	}}
//class GetBroadcast extends BroadcastReceiver{//�˴��Ĺ㲥���ڼ���APK�Ƿ�װ�ɹ����ɹ�����������
//	public static GetBroadcast mReceive = new GetBroadcast();
//	private static IntentFilter mIntentFilter;
//	public static  void registerReceiver(Context context) {
//		mIntentFilter = new IntentFilter();
//		mIntentFilter.addDataScheme("package");
//		mIntentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);//�����װ����
////		mIntentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);//���ɾ������
////		mIntentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);//����滻����
//		context.registerReceiver(mReceive, mIntentFilter);
//	}
////	public void unregisterReceiver(Context context) {
////		context.unregisterReceiver(mReceive);
////	}
//	@Override
//	public void onReceive(Context context, Intent intent) {
//		// TODO Auto-generated method stub
//		String action = intent.getAction();
//		String packageName = intent.getData().getSchemeSpecificPart();
//		if (Intent.ACTION_PACKAGE_ADDED.equals(action)) {
//		Toast.makeText(context, "������µ�Ӧ�ã�Ӧ����Ϊ"+packageName, Toast.LENGTH_LONG).show();
//		PackageManager pm = context.getPackageManager();
//		Intent intent1 = new Intent();
//		try {
//		intent1 = pm.getLaunchIntentForPackage(packageName);
//		} catch (Exception e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//		}
//		intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		context.startActivity(intent1);
//		}
//	}
//}
