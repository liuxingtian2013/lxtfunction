package com.example.Myselffunction;
import java.util.ArrayList;
import java.util.List;

import com.example.punchcard.R;
import com.pm.common.PopMenu;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;
public class shezhi extends Activity implements OnClickListener{
	private List<Activity> list = new ArrayList<Activity>();
	 
    private static shezhi ExitApplication;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		shezhi.getInstance().addActivity(this);//�˳�ʱɱ������activity
		setContentView(R.layout.shezhi);
		initview();
	}
	private void initview() {
		findViewById(R.id.shezhi).setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		showTips();
	}
	//	�����˳�����Ĵ���
	public void showTips() {  
		AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("����")
				.setMessage("�Ƿ��˳�����")  
				.setNegativeButton("ȷ��", new DialogInterface.OnClickListener() { 
					@Override
					public void onClick(DialogInterface dialog, int which) { 
//						Intent intent = new Intent(Intent.ACTION_MAIN); 
//						intent.addCategory(Intent.CATEGORY_HOME);     
//						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//						System.out.println("qqqqqqqqqqqqqqqqqqqqqq"+list.toString()+ExitApplication);
						getInstance().exit(shezhi.this);
//						startActivity(intent); 
//						dialog.dismiss();
//						android.os.Process.killProcess(android.os.Process.myPid());
					} 
				}).setPositiveButton("ȡ��",   
						new DialogInterface.OnClickListener() { 
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						return;
					}
				}).create(); // �����Ի���      
		alertDialog.show();// ��ʾ�Ի���    
	}
	public shezhi() {
    }
    public static shezhi getInstance() {
        if (null == ExitApplication) {
        	ExitApplication = new shezhi();
        }
        return ExitApplication;
    }
    public void addActivity(Activity activity) {
        list.add(activity);
    }
    public void exit(Context context) {
        for (Activity activity : list) {
            activity.finish();
        }
//        System.exit(0);����Ǹ��ؼ��� �����Ļ�  ϵͳ��ȫ�˳�  ʲô��û����  �����ס����Ҳû��  
    }
}
