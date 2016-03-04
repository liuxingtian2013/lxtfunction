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
		shezhi.getInstance().addActivity(this);//退出时杀死所有activity
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
	//	这是退出程序的代码
	public void showTips() {  
		AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("提醒")
				.setMessage("是否退出程序")  
				.setNegativeButton("确定", new DialogInterface.OnClickListener() { 
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
				}).setPositiveButton("取消",   
						new DialogInterface.OnClickListener() { 
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						return;
					}
				}).create(); // 创建对话框      
		alertDialog.show();// 显示对话框    
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
//        System.exit(0);这句是个关键点 有这句的话  系统完全退出  什么都没有了  例如记住密码也没了  
    }
}
