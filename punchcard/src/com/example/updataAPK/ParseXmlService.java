package com.example.updataAPK;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.widget.Toast;
//import java.io.InputStream;
//import java.util.HashMap;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//
//public class ParseXmlService {
//
//    /**
//     * 解析XML,软件版本信息
//     * @param inStream
//     * @return
//     * @throws Exception
//     */
//    public HashMap<String, String> parseXml(InputStream inStream)
//            throws Exception {
//        HashMap<String, String> hashMap = new HashMap<String, String>();
//
//        // 实例化一个文档构建器工厂
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        // 通过文档构建器工厂获取一个文档构建器
//        DocumentBuilder builder = factory.newDocumentBuilder();
//        // 通过文档构建器构建一个文档实例
//        Document document = builder.parse(inStream);
//        // 获取XML文件根节点
//        Element root = document.getDocumentElement();
//        // 获得所有子节点
//        NodeList childNodes = root.getChildNodes();
//        for (int j = 0; j < childNodes.getLength(); j++) {
//            // 遍历子节点
//            Node childNode = (Node) childNodes.item(j);
//            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
//                Element childElement = (Element) childNode;
//                // 版本号
//                if ("version".equals(childElement.getNodeName())) {
//                    hashMap.put("version", childElement.getFirstChild().getNodeValue());
//                }
//                // 软件名称
//                else if (("name".equals(childElement.getNodeName()))) {
//                    hashMap.put("name", childElement.getFirstChild().getNodeValue());
//                }
//                // 下载地址
//                else if (("url".equals(childElement.getNodeName()))) {
//                    hashMap.put("url", childElement.getFirstChild().getNodeValue());
//                }
//            }
//        }
//        return hashMap;
//    }
//}
//public class ParseXmlService extends BroadcastReceiver{//此处的广播用于监听APK是否安装成功，成功则经行自启动
//	public static ParseXmlService mReceive = new ParseXmlService();
//	private static IntentFilter mIntentFilter;
//	public static  void registerReceiver(Context context) {
//		mIntentFilter = new IntentFilter();
//		mIntentFilter.addDataScheme("package");
//		mIntentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);//软件安装监听
////		mIntentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);//软件删除监听
////		mIntentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);//软件替换监听
//		context.registerReceiver(mReceive, mIntentFilter);
//	}
//	public void unregisterReceiver(Context context) {
//		context.unregisterReceiver(mReceive);
//	}
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
//
