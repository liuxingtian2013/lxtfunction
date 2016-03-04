package com.example.newworkfunction;
import com.example.Adapter.listviewadapterqingjiajilu;
import com.example.punchcard.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class Qingjiaxiangqinng extends Activity{
	private TextView tv1,tv2,tv3,tv4,tv5,tv6;
	int posi;//获取数组中对应位置的元素
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qingjiaxiangqing);
		Intent intent = getIntent();
		String position = intent.getStringExtra("position");
		posi = Integer.parseInt(position); 
		initview();
		initdata();
	}
	private void initview() {
		// TODO Auto-generated method stub
		tv1 = (TextView) findViewById(R.id.xinjianqingjia_xuanxiangxiangqing_tv);
		tv2 = (TextView) findViewById(R.id.xinjianqingjia_zhuangtaixiangqing_tv);
		tv3 = (TextView) findViewById(R.id.xinjianqingjia_qishishijianxiangqing_tv);
		tv4 = (TextView) findViewById(R.id.xinjianqingjia_zhongzhishijianxiangqing_tv);
		tv5 = (TextView) findViewById(R.id.xinjianqingjia_waichushiyouxiangqing_tv);
		tv6 = (TextView) findViewById(R.id.xinjianqingjia_gongzuojiaojiexiangqing_tv);
	}
	private void initdata() {
		// TODO Auto-generated method stub
		tv1.setText(listviewadapterqingjiajilu.qjjl_type.get(posi));
		if(listviewadapterqingjiajilu.qjjl_zhuangtai.get(posi).equals("10")){
			tv2.setText("通过");
		}if(listviewadapterqingjiajilu.qjjl_zhuangtai.get(posi).equals("1")){
			tv2.setText("待审批");
		}if(listviewadapterqingjiajilu.qjjl_zhuangtai.get(posi).equals("2")){
			tv2.setText("审批中");
		}if(listviewadapterqingjiajilu.qjjl_zhuangtai.get(posi).equals("-1")){
			tv2.setText("拒绝");
		}if(listviewadapterqingjiajilu.qjjl_zhuangtai.get(posi).equals("3")){
			tv2.setText("审批超时");
		}if(listviewadapterqingjiajilu.qjjl_zhuangtai.get(posi).equals("0")){
			tv2.setText("未提交");
		}if(listviewadapterqingjiajilu.qjjl_zhuangtai.get(posi).equals("4")){
			tv2.setText("撤回");
		}
		tv3.setText(listviewadapterqingjiajilu.qjjl_qishishijian.get(posi));
		tv4.setText(listviewadapterqingjiajilu.qjjl_zhongzhishijian.get(posi));
		tv5.setText(listviewadapterqingjiajilu.qjjl_shenqingshiyou.get(posi));
		tv6.setText(listviewadapterqingjiajilu.qjjl_gongzuojiaojie.get(posi));
	}
}
