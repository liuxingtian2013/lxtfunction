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
//     * ����XML,����汾��Ϣ
//     * @param inStream
//     * @return
//     * @throws Exception
//     */
//    public HashMap<String, String> parseXml(InputStream inStream)
//            throws Exception {
//        HashMap<String, String> hashMap = new HashMap<String, String>();
//
//        // ʵ����һ���ĵ�����������
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        // ͨ���ĵ�������������ȡһ���ĵ�������
//        DocumentBuilder builder = factory.newDocumentBuilder();
//        // ͨ���ĵ�����������һ���ĵ�ʵ��
//        Document document = builder.parse(inStream);
//        // ��ȡXML�ļ����ڵ�
//        Element root = document.getDocumentElement();
//        // ��������ӽڵ�
//        NodeList childNodes = root.getChildNodes();
//        for (int j = 0; j < childNodes.getLength(); j++) {
//            // �����ӽڵ�
//            Node childNode = (Node) childNodes.item(j);
//            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
//                Element childElement = (Element) childNode;
//                // �汾��
//                if ("version".equals(childElement.getNodeName())) {
//                    hashMap.put("version", childElement.getFirstChild().getNodeValue());
//                }
//                // �������
//                else if (("name".equals(childElement.getNodeName()))) {
//                    hashMap.put("name", childElement.getFirstChild().getNodeValue());
//                }
//                // ���ص�ַ
//                else if (("url".equals(childElement.getNodeName()))) {
//                    hashMap.put("url", childElement.getFirstChild().getNodeValue());
//                }
//            }
//        }
//        return hashMap;
//    }
//}
//public class ParseXmlService extends BroadcastReceiver{//�˴��Ĺ㲥���ڼ���APK�Ƿ�װ�ɹ����ɹ�����������
//	public static ParseXmlService mReceive = new ParseXmlService();
//	private static IntentFilter mIntentFilter;
//	public static  void registerReceiver(Context context) {
//		mIntentFilter = new IntentFilter();
//		mIntentFilter.addDataScheme("package");
//		mIntentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);//�����װ����
////		mIntentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);//���ɾ������
////		mIntentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);//����滻����
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
//
