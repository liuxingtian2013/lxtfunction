package com.example.newworkfunction;

import com.example.Adapter.listviewadapterwaichujilu;
import com.example.punchcard.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class Waichuxiangqinng extends Activity{
	private TextView tv1,tv2,tv3,tv4,tv5,tv6;
	int posi;//��ȡ�����ж�Ӧλ�õ�Ԫ��
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.waichuxiangqing);
		Intent intent = getIntent();
		String position = intent.getStringExtra("position");
		posi = Integer.parseInt(position); 
		initview();
		initdata();
	}
	private void initview() {
		// TODO Auto-generated method stub
		tv1 = (TextView) findViewById(R.id.xinjianwaichu_xuanxiangxiangqing_tv);
		tv2 = (TextView) findViewById(R.id.xinjianwaichu_zhuangtaixiangqing_tv);
		tv3 = (TextView) findViewById(R.id.xinjianwaichu_qishishijianxiangqing_tv);
		tv4 = (TextView) findViewById(R.id.xinjianwaichu_zhongzhishijianxiangqing_tv);
		tv5 = (TextView) findViewById(R.id.xinjianwaichu_waichushiyouxiangqing_tv);
		tv6 = (TextView) findViewById(R.id.xinjianwaichu_gongzuojiaojiexiangqing_tv);
	}
	private void initdata() {
		// TODO Auto-generated method stub
		tv1.setText(listviewadapterwaichujilu.wcjl_type.get(posi));
		if(listviewadapterwaichujilu.wcjl_zhuangtai.get(posi).equals("10")){
			tv2.setText("ͨ��");
		}if(listviewadapterwaichujilu.wcjl_zhuangtai.get(posi).equals("1")){
			tv2.setText("������");
		}if(listviewadapterwaichujilu.wcjl_zhuangtai.get(posi).equals("2")){
			tv2.setText("������");
		}if(listviewadapterwaichujilu.wcjl_zhuangtai.get(posi).equals("-1")){
			tv2.setText("�ܾ�");
		}if(listviewadapterwaichujilu.wcjl_zhuangtai.get(posi).equals("3")){
			tv2.setText("������ʱ");
		}if(listviewadapterwaichujilu.wcjl_zhuangtai.get(posi).equals("0")){
			tv2.setText("δ�ύ");
		}if(listviewadapterwaichujilu.wcjl_zhuangtai.get(posi).equals("4")){
			tv2.setText("����");
		}
		tv3.setText(listviewadapterwaichujilu.wcjl_qishishijian.get(posi));
		tv4.setText(listviewadapterwaichujilu.wcjl_zhongzhishijian.get(posi));
		tv5.setText(listviewadapterwaichujilu.wcjl_shenqingshiyou.get(posi));
		tv6.setText(listviewadapterwaichujilu.wcjl_gongzuojiaojie.get(posi));
	}
}
