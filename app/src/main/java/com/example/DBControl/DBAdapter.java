package com.example.DBControl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBAdapter {
	private static String TAG = "DBAdapter";
	public final static String DATABSE_TABLE = "TestSubject";
	public static final String TESTID = "TestID";
	public static final String TESTSUBJECT = "TestSubject";
	public static final String TESTANSWER = "TestAnswer";
	public static final String ANSWERA = "AnswerA";
	public static final String ANSWERB = "AnswerB";
	public static final String ANSWERC = "AnswerC";
	public static final String ANSWERD = "AnswerD";
	public static final String TESTTPYE = "TestType";
	public static final String TESTBELONG = "TestBelong";
	public static final String EXPR1 = "Expr1";
	public static final String IMAGENAME = "ImageName";
	private DBHelper dataBaseHelper;
	//Context
	private Context context;
	//SQLiteDatabase;
	SQLiteDatabase sqLiteDatabase;
	
	
	public DBAdapter(Context context){
		this.context = context;
	}
	/*
	 * Open the Database;
	 */
	public SQLiteDatabase open(){
		dataBaseHelper = new DBHelper(context);
		try {
			sqLiteDatabase = dataBaseHelper.getWritableDatabase();
		} catch (Exception e) {
			sqLiteDatabase = dataBaseHelper.getReadableDatabase();
			Log.i("open-->",e.toString());
		}
		return sqLiteDatabase;
	}
	 // Close the Database
	public void close(){
		sqLiteDatabase.close();
	}

	public long DBInsert(ContentValues cv){
		return sqLiteDatabase.insert(DBAdapter.DATABSE_TABLE,null,cv);//插入数据的三个参数：表名，null，values
	}
	
	 public Cursor getAllData(){
	        String[] searchResult =  {TESTID,TESTSUBJECT, TESTANSWER,TESTTPYE, TESTBELONG
	        		,ANSWERA,ANSWERB,ANSWERC,ANSWERD,IMAGENAME,EXPR1};  
	        Log.i("GetAllData","YES");
	        return sqLiteDatabase.query(dataBaseHelper.DATABASE_TABLE, searchResult, null, null, null, null, null);
	 }
	public static boolean HaveData(SQLiteDatabase db,String tablename){
		Cursor cursor;
		boolean a=false;
		cursor = db.rawQuery("select name from sqlite_master where type='table' ", null);
		while(cursor.moveToNext()){
			//遍历出表名
			String name = cursor.getString(0);
			if(name.equals(tablename))
			{
				a=true;
			}
			Log.i("System.out", name);
		}
		if(a)
		{
			cursor=db.query(tablename,null,null,null,null,null,null);
			//检查是不是空表
			if(cursor.getCount()>0)
				return true;
			else
				return false;
		}
		else
			return false;

	}
}