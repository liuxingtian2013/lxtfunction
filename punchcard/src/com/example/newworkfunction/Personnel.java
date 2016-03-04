package com.example.newworkfunction;

import java.util.ArrayList;
import java.util.List;  

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Adapter.ListViewpersonAadpter;
import com.example.Adapter.ListViewpersonnameAadpter;
import com.example.Adapter.listviewadapterdakajilu;
import com.example.Adapter.listviewadapterqingjiajilu;
import com.example.pulltoflash.PullToRefreshView;
import com.example.pulltoflash.PullToRefreshView.OnFooterRefreshListener;
import com.example.pulltoflash.PullToRefreshView.OnHeaderRefreshListener;
import com.example.punchcard.R;
import com.pm.common.XRequest;

import android.annotation.SuppressLint;
import android.app.Activity;  
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;  
import android.os.Handler;
import android.os.Message;
import android.view.View;  
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;  
public class Personnel extends Activity implements OnHeaderRefreshListener,OnFooterRefreshListener{  
	/** Called when the activity is first created. */  
//	ExpandableListView expandableList; 
	PullToRefreshView mPullToRefreshView;
	ProgressDialog progressDialog;
	ListViewpersonAadpter adapter;
	JSONObject json;
	int currentpage=1;//��ǰҳ
	double total;//��ȡ���ܼ�¼��
	public static int totalpage = 0;//��ҳ��
	public static ListView lv;
	String rows;
//	TreeViewAdapter adapter;  
	//   SuperTreeViewAdapter superAdapter;  
//	Button btnNormal,btnSuper;  
	// Sample data set.  children[i] contains the children (String[]) for groups[i].  
	//    public List<String> groups = new ArrayList<String>(); 
	//   public String[] groups = {"  ���̲�","  ����","  ������"};
//	    public String[][]  child= {  
//	            { "A��", "B��", "C��", "D��" },  
//	           { "ͬѧ��", "ͬѧ��", "ͬѧ��"},  
//	            { "��Ω��", "������" ,"����" }  
//	    };  
	@Override  
	public void onCreate(Bundle savedInstanceState) {  
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);  
		setContentView(R.layout.personnel);
		mPullToRefreshView = (PullToRefreshView)findViewById(R.id.main_pull_refresh_view);
		mPullToRefreshView.setOnHeaderRefreshListener(this);
		mPullToRefreshView.setOnFooterRefreshListener(this);
		lv = (ListView) findViewById(R.id.ExpandableListView01);
		adapter = new ListViewpersonAadpter(getApplicationContext());//������һ��Ҫ��ʼ��
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				String pp = Integer.toString(position);
				intent.putExtra("positionnum", pp);//����ȡ����λ������ֱ�Ӵ���  ������һ��ֱ�ӻ�ȡ���ö�Ӧλ�õ�ֵ
				intent.setClass(getApplicationContext(), Personnelname.class);
				startActivity(intent);
			}
		});
		if (ListViewpersonAadpter.bumenmingchen.size() > 0) {
			lv.setAdapter(adapter);
		} else {
			postperson();
		}
//		adapter=new TreeViewAdapter(this,TreeViewAdapter.PaddingLeft>>1);  
//		expandableList=(ExpandableListView) Personnel.this.findViewById(R.id.ExpandableListView01);
//		//        ������һ�������������˵������ã�
//		adapter.RemoveAll();  
//		adapter.notifyDataSetChanged();  
//		List<TreeViewAdapter.TreeNode> treeNode = adapter.GetTreeNode();  
//		for(int i=0;i<groups.length;i++)  
//		{  
//			TreeViewAdapter.TreeNode node=new TreeViewAdapter.TreeNode();  
//			node.parent=groups[i];  
//			for(int ii=0;ii<child[i].length;ii++)  
//			{  
//				node.childs.add(child[i][ii]);  
//			}  
//			treeNode.add(node);  
//		}  
//		adapter.UpdateTreeNode(treeNode);       
//		expandableList.setAdapter(adapter);  
//		expandableList.setOnChildClickListener(new OnChildClickListener(){  
//			@Override  
//			public boolean onChildClick(ExpandableListView arg0, View arg1,  
//					int parent, int children, long arg4) {  
//				String str="parent id:"+String.valueOf(parent)+",children id:"+String.valueOf(children);  
//				Toast.makeText(Personnel.this, str, 300).show();  
//				return false;  
//			}  
//		});  
	}  
	/** 
120.     * �������β˵����¼����ٿ��ã����������������β˵�����������˵������лص� 
121.     */  
//	OnChildClickListener stvClickEvent=new OnChildClickListener(){  
//
//		@Override  
//		public boolean onChildClick(ExpandableListView parent,  
//				View v, int groupPosition, int childPosition,  
//				long id) {  
//			String str="parent id:"+String.valueOf(groupPosition)+",children id:"+String.valueOf(childPosition);  
//			Toast.makeText(Personnel.this, str, 300).show();  
//
//			return false;  
//		}  
//
//	}; 
	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				mPullToRefreshView.onFooterRefreshComplete();
//				Toast.makeText(getApplicationContext(), "��������", Toast.LENGTH_SHORT).show();
				postjiazai();
			}
		}, 1000);
	}
	@Override
	public void onHeaderRefresh(PullToRefreshView view) {

		mPullToRefreshView.postDelayed(new Runnable() {
			@Override
			public void run() {
				//���ø���ʱ��
				//mPullToRefreshView.onHeaderRefreshComplete("�������:01-23 12:01");
				postperson();//����ˢ��ʱ���еĲ���
			}
		},1000);
	}
	public void postperson(){
		progressDialog = ProgressDialog.show(this, "���Ե�...","��ȡ������...", true);
		progressDialog.setCancelable(true);
		final List<NameValuePair> params = new ArrayList<NameValuePair>();
		int a =  currentpage*10;
		params.add(new BasicNameValuePair("page","1"));
		params.add(new BasicNameValuePair("rows",a+""));
		XRequest request = new XRequest();
		request.PostServerAttendance("get_class_list", params, new Handler() {
			@SuppressLint("NewApi") @Override
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
						mPullToRefreshView.onHeaderRefreshComplete();
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
					JSONObject objectt = new JSONObject();
					//					if(strResult!=null && !strResult.equals("")){
					objectt = new JSONObject(strResult);
					total = Integer.parseInt(objectt.getString("total"));
//					Toast.makeText(getApplicationContext(), "��������Ϊ����"+total, Toast.LENGTH_SHORT).show();
					double signopage = total/10;
					int ii = (int)signopage;
					double rr = signopage - ii;
					if(rr>0){
						totalpage=ii+1;
					}else{totalpage = ii;}
					rows = objectt.getString("rows");//��ȡ��ά�����еĵڶ�άjson
					JSONArray jsonlxt;
					jsonlxt = new JSONArray(rows);
					ListViewpersonAadpter.bumenmingchen.clear();
					ListViewpersonAadpter.bumenmingchenid.clear();
					for(int i=0 ; i < jsonlxt.length() ;i++){
						try {
							json=jsonlxt.getJSONObject(i);
							ListViewpersonAadpter.bumenmingchen.add(json.getString("Name"));
							ListViewpersonAadpter.bumenmingchenid.add(json.getString("id"));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					//�������ݽ���ʱ��ʾ����ʧ
					if(ListViewpersonAadpter.bumenmingchen.size()>0){
						progressDialog.dismiss();
						lv.setAdapter(adapter);
						mPullToRefreshView.onHeaderRefreshComplete();
					}else{
						progressDialog.dismiss();
						lv.setAdapter(adapter);
						Toast.makeText(getApplicationContext(), "�޼�¼", Toast.LENGTH_SHORT).show();
						mPullToRefreshView.onHeaderRefreshComplete();
					}
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
				);}
	private void postjiazai() {
		// TODO Auto-generated method stub
		if(currentpage<totalpage){
		progressDialog = ProgressDialog.show(this, "���Ե�...","��ȡ������...", true);
		progressDialog.setCancelable(true);
		final List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("page",currentpage+""));
		params.add(new BasicNameValuePair("rows","10"));
		XRequest request = new XRequest();
		request.PostServerAttendance("get_class_list", params, new Handler() {
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
						mPullToRefreshView.onFooterRefreshComplete();
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
					JSONObject objectt = new JSONObject();
					objectt = new JSONObject(strResult);
					rows = objectt.getString("rows");//��ȡ��ά�����еĵڶ�άjson
					JSONArray jsonlxt;
					jsonlxt = new JSONArray(rows);
					for(int i=0 ; i < jsonlxt.length() ;i++){
						try {
							json=jsonlxt.getJSONObject(i);
							ListViewpersonAadpter.bumenmingchen.add(json.getString("Name"));
							ListViewpersonAadpter.bumenmingchenid.add(json.getString("id"));
							} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					//�������ݽ���ʱ��ʾ����ʧ
					progressDialog.dismiss();
					lv.setAdapter(adapter);
					Toast.makeText(getApplicationContext(), "��������", Toast.LENGTH_SHORT).show();
					mPullToRefreshView.onFooterRefreshComplete();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		currentpage++;
		}else{
			Toast.makeText(getApplicationContext(), "�Ѽ���ȫ������", Toast.LENGTH_SHORT).show();
		}
	}
	
	}
