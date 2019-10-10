package com.example.DBControl;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {



	public final static String DATABSE_NAME = "DTSS_DB";//Driving theory simulation system
	public final static int DATABASE_VERSION = 1;
	public final static String DATABSE_TABLE = "TestSubject";
	
	private final static String DATABASE_CREATE = "create table " + DATABSE_TABLE + " (TestID integer primary key autoincrement,"  
    + "TestSubject text not null, TestAnswer text not null, TestType integer,TestBelong integer,AnswerA text," +
    		"AnswerB text,AnswerC text,AnswerD text,ImageName text,Expr1 integer);"; 
	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	public DBHelper(Context context){
		super(context,DATABSE_NAME,null,DATABASE_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		  db.execSQL("DROP TABLE IF EXISTS " + DATABSE_TABLE);   
	      onCreate(db);   
	}
	
}