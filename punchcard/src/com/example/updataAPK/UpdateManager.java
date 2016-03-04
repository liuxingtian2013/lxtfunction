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
    // 更新进度条
    private ProgressBar mUpdateProgressBar;
    // 记录进度条数量
    private int progress;
    // 保存解析的XML信息
    private HashMap<String, String> mHashMap = new HashMap<String, String>();
    // 是否取消更新
    private boolean cancelUpdate = false;
    // 下载状态--下载中
    private static final int DOWNLOAD_ING = 1;
    // 下载状态--下载成功
    private static final int DOWNLOAD_SUCCESS = 2;
    // 下载状态--下载失败
    private static final int DOWNLOAD_FAIL = 3;
    // 下载保存路径
    private String mSavePath;
    //获取版本号变量
     private  float versionCode = 0;
    //下载对话框
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
                    //安装APK
                    installApk();
//                    GetBroadcast.registerReceiver(mContext);//注册广播，用于监听应用是否安装完成
                    break;
                case DOWNLOAD_FAIL:
                    Toast.makeText(mContext, "文件下载失败！", Toast.LENGTH_LONG).show();
                    break;
                }
            }
        };
    }
    /**
     * 检测软件更新
     * 
     * @param isAuto
     *            为true:软件自动检测更新；false：用户手动检测更新。
     */
//    public void checkUpdate(boolean isAuto) {
//        try {
//            if (isUpdate()) {
//            	 Toast.makeText(mContext, "有新版本，需要更新！", Toast.LENGTH_SHORT).show();
//                // 显示提示对话框
//                showNoticeDialog();
////            } else if (!isAuto) {
//            }else{
//                // 告诉用户已是最新版本，不需要更新。
//                Toast.makeText(mContext, "您的软件已是最新版本，不需要更新！", Toast.LENGTH_SHORT).show();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    /**
     * 检测软件是否有更新
     * 
     * @return
     * @throws Exception 
     */
//    private boolean isUpdate() throws Exception {
//        //获取当前软件版本
//        int versionCode = getVersionVode(mContext);
//        //获取我们之前放在服务器端的app_version.xml的文件信息
//        //Android 3.0（含）之后访问网络都不能在主线程中
//        isLoop = true;
//        postupdate();
//        new Thread(){
//            @Override
//            public void run() {
//            	
//                try {
//                    InputStream inStream = HttpUtil.getInputStream("apks/app_version.xml", null, HttpUtil.METHOD_GET);
//                    //解析xml文件。由于XML文件较小，我们采用DOM方式进行解析
//                    ParseXmlService service = new ParseXmlService();//这个类是自己写的解析XML的工具类
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
//        System.out.println("看看集合中有什么"+mHashMap);
//        if (null != mHashMap) {
//            int serviceCode = Integer.valueOf(mHashMap.get("vveerr"));
//            //判断版本号
//            if (serviceCode > versionCode) {
//                return true;
//            }
//        }
//        return false;
//    }
    /**
     * 获取软件当前版本号
     * 
     * @param context
     * @return
     */
    public String getVersionVode(Context context){
        String versionCode ="" ;
        // 获取软件版本号，对应AndroidManifest.xml下android:versionCode
        try {
            versionCode = context.getPackageManager().getPackageInfo("com.example.punchcard", 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }
    /**
     * 显示软件更新对话框
     */
    private void showNoticeDialog() {
        AlertDialog.Builder builder = new Builder(mContext);
        builder.setTitle("软件更新")
        .setMessage("软件有新版本，要更新吗？")
        .setPositiveButton("立即更新", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //显示软件下载对话框
                showDownloadDialog();
            }
        })
        .setNegativeButton("稍后再说", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }
    /**
     * 显示软件下载对话框+
     */
    private void showDownloadDialog() {
        AlertDialog.Builder builder = new Builder(mContext);
        builder.setTitle("正在更新");
        builder.setMessage("请耐心等待...");
        //给对话框增加进度条
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.update_progress, null);
        mUpdateProgressBar = (ProgressBar) v.findViewById(R.id.updateapk_progressBar);
        mDownloadDialog = builder.setView(v).setNegativeButton("取消更新", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                //设置取消状态
                cancelUpdate = true;
            }
        }).create();
        mDownloadDialog.show();
        //下载APK文件
        downloadApk();
    }
    /**
     * 下载APK文件
     */
    private void downloadApk() {
        //启动下载APK线程
        new DownloadApkThread().start();
    }
    /**
     * 下载APK文件线程
     */
    private class DownloadApkThread extends Thread {
        @Override
        public void run() {
            try {
                // 判断SD卡是否存在，并且是否有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // 获取SD卡路径
                    String sdPadth = Environment.getExternalStorageDirectory()+ "";
                    mSavePath = sdPadth + "/download/chunhui";
            		 URL url = new URL(mHashMap.get("url"));
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    // 获取文件大小
                    int fileSize = conn.getContentLength();
                    // 创建输入流
                    InputStream inStream = conn.getInputStream();
                    File file = new File(mSavePath);
                    // 判断文件目录是否存在,不存在则创建该目录    
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    File apkFile = new File(mSavePath, mHashMap.get("filename"));
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // 缓存
                    byte[] b = new byte[1024];
                    do {
                        int numRead = inStream.read(b);
                        count += numRead;
                        // 计算进度条位置
                        progress = (int) (((float) count / fileSize) * 100);
                        // 更新进度
                        mHandler.sendEmptyMessage(DOWNLOAD_ING);
                        if (numRead <= 0) {// 下载完成
                            mHandler.sendEmptyMessage(DOWNLOAD_SUCCESS);
                            break;
                        }
                        // 写入文件
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
     * 安装APK
     */
    private void installApk() {
        File apk = new File(mSavePath,mHashMap.get("filename"));
        if (!apk.exists()) {
            return;
        }
        //通过Intent安装APK文件
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse("file://"+apk.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(intent);
//        GetBroadcast.registerReceiver(mContext);//在需要监听安装的地方调用此方法进行监听
    }
    public void postupdate(){
    	mHashMap.clear();
    	//获取当前软件版本
    	versionCode = Float.parseFloat(getVersionVode(mContext));
        //获取我们之前放在服务器端的app_version.xml的文件信息
        //Android 3.0（含）之后访问网络都不能在主线程中
        isLoop = true;
		progressDialog = ProgressDialog.show(mContext, "请稍等...","检测中", true);
//		progressDialog.setCancelable(true);
		final List<NameValuePair> params = new ArrayList<NameValuePair>();
		XRequest request = new XRequest();
		request.Postupdate("check_ver", params, new Handler() {
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
					Toast.makeText(mContext ,"系统错误,请稍候重试", Toast.LENGTH_SHORT).show();
					break;
				case -3:
					progressDialog.dismiss();
					Toast.makeText(mContext ,"网络链接超时,请稍候重试", Toast.LENGTH_SHORT).show();
					break;
				case -4:
					progressDialog.dismiss();
					Toast.makeText(mContext ,"联网失败，请检查您的网络设置", Toast.LENGTH_SHORT).show();
					break;
				}
				try {
					object = new JSONObject(strResult);
					String vernum = object.getString("ver");
					mHashMap.put("vveerr", vernum);
//				        if (null != mHashMap) {
				            float serviceCode = Float.parseFloat(mHashMap.get("vveerr"));
				            //判断版本号
				            if (serviceCode > versionCode) {
				            	progressDialog.dismiss();
//				            	 Toast.makeText(mContext, "有新版本，需要更新！", Toast.LENGTH_SHORT).show();
									String url = object.getString("url");
									System.out.println("看看这个地址是：："+url);
									mHashMap.put("url", url);
				            		String filename = url.substring(url.lastIndexOf("/")+1,url.lastIndexOf("."))+".apk";
				            		mHashMap.put("filename", filename);
				                 // 显示提示对话框
				                 showNoticeDialog();
				            }else{
				            	// 告诉用户已是最新版本，不需要更新。
				            	progressDialog.dismiss();
				                Toast.makeText(mContext, "您的软件已是最新版本，不需要更新！", Toast.LENGTH_SHORT).show();
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
//class GetBroadcast extends BroadcastReceiver{//此处的广播用于监听APK是否安装成功，成功则经行自启动
//	public static GetBroadcast mReceive = new GetBroadcast();
//	private static IntentFilter mIntentFilter;
//	public static  void registerReceiver(Context context) {
//		mIntentFilter = new IntentFilter();
//		mIntentFilter.addDataScheme("package");
//		mIntentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);//软件安装监听
////		mIntentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);//软件删除监听
////		mIntentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);//软件替换监听
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
//		Toast.makeText(context, "添加了新的应用，应用名为"+packageName, Toast.LENGTH_LONG).show();
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
