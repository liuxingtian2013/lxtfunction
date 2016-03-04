package com.example.MyDatabaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper{//�����ݿ�ͼ���
	public static final String CREATE_USER = "create table User ("
			+" id integer primary Key autoincrement," 
			+" accountnumber text," 
			+" password text," 
			+" userid text)";
	private Context mContext;
	public MyDatabaseHelper(Context context, String name,CursorFactory factory, int version) {
		super(context, name, factory, version);
		mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_USER);//�������ݿ�ִ��sql
//		Toast.makeText(mContext, "�����ɹ�", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         db.execSQL("drop table if exists User");//
	}
}
