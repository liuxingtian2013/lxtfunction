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
	int posi;//��ȡ�����ж�Ӧλ�õ�Ԫ��
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
			tv2.setText("ͨ��");
		}if(listviewadapterqingjiajilu.qjjl_zhuangtai.get(posi).equals("1")){
			tv2.setText("������");
		}if(listviewadapterqingjiajilu.qjjl_zhuangtai.get(posi).equals("2")){
			tv2.setText("������");
		}if(listviewadapterqingjiajilu.qjjl_zhuangtai.get(posi).equals("-1")){
			tv2.setText("�ܾ�");
		}if(listviewadapterqingjiajilu.qjjl_zhuangtai.get(posi).equals("3")){
			tv2.setText("������ʱ");
		}if(listviewadapterqingjiajilu.qjjl_zhuangtai.get(posi).equals("0")){
			tv2.setText("δ�ύ");
		}if(listviewadapterqingjiajilu.qjjl_zhuangtai.get(posi).equals("4")){
			tv2.setText("����");
		}
		tv3.setText(listviewadapterqingjiajilu.qjjl_qishishijian.get(posi));
		tv4.setText(listviewadapterqingjiajilu.qjjl_zhongzhishijian.get(posi));
		tv5.setText(listviewadapterqingjiajilu.qjjl_shenqingshiyou.get(posi));
		tv6.setText(listviewadapterqingjiajilu.qjjl_gongzuojiaojie.get(posi));
	}
}
