package com.example.DBControl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class DBHelper extends SQLiteOpenHelper {

	private static String TAG = "DBHelper";

	public final static String DATABASE_NAME = "DTSS_DB";//database_name
	public final static int DATABASE_VERSION = 1;
	public final static String DATABASE_TABLE = "TestSubject";//table_name
	
	private final static String DATABASE_CREATE = "create table " + DATABASE_TABLE + " (TestID integer primary key autoincrement,"
    + "TestSubject text not null, TestAnswer text not null, TestType integer,TestBelong integer,AnswerA text," +
    		"AnswerB text,AnswerC text,AnswerD text,ImageName text,Expr1 integer);"; //将建表语句写成字符串常量
	public DBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	public DBHelper(Context context){
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.i(TAG,"LRL DBHelper onCreate");
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		  db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
	      onCreate(db);   
	}
	
}