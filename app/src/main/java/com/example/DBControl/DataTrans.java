package com.example.DBControl;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.util.Log;

import com.example.testsys.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by 15151 on 2019/10/21.
 */

public class DataTrans {
    private static String TAG = "DataTrans";
    private Context mContext;
    public DataTrans (Context context) {
        // TODO Auto-generated method stub
          this.mContext = context;
        }

    public void Trans(Context context) {
        InputStream in = context.getResources().openRawResource(R.raw.testsubject);
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(in, "gb2312"));
        } catch (UnsupportedEncodingException e1) {
            // TODO Auto-generated catch block
            Log.e("debug", e1.toString());

        }
        String tmp;
        String TESTSUBJECT;
        String TESTANSWER;
        String ANSWERA;
        String ANSWERB;
        String ANSWERC;
        String ANSWERD;
        String IMAGENAME;
        int TESTTPYE;
        int TESTBELONG;
        int EXPR1;
        String[] strings;
        ContentValues values = new ContentValues();
        try {
            DBAdapter dbAdapter = new DBAdapter(mContext);
            dbAdapter.open();
            Log.i(TAG,"LRL GetAllData");
            while ((tmp = br.readLine()) != null) {//当读取完所有的数据时会返回null
                strings = tmp.split("#");//单个符号作为分隔符

                TESTSUBJECT = strings[1];
                TESTANSWER = strings[2];
                TESTTPYE = Integer.parseInt(strings[3]);// int
                TESTBELONG = Integer.parseInt(strings[4]);// 将字符串解析成int
                ANSWERA = strings[5];
                ANSWERB = strings[6];
                ANSWERC = strings[7];
                ANSWERD = strings[8];
                IMAGENAME = "image" + strings[9];
                IMAGENAME.replace("-", "_");
                EXPR1 = Integer.parseInt(strings[10]);// int

                values.clear();
                values.put(DBAdapter.TESTSUBJECT, TESTSUBJECT);
                values.put(DBAdapter.TESTANSWER, TESTANSWER);
                values.put(DBAdapter.TESTTPYE, TESTTPYE);
                values.put(DBAdapter.TESTBELONG, TESTBELONG);
                values.put(DBAdapter.ANSWERA, ANSWERA);
                values.put(DBAdapter.ANSWERB, ANSWERB);
                values.put(DBAdapter.ANSWERC, ANSWERC);
                values.put(DBAdapter.ANSWERD, ANSWERD);
                values.put(DBAdapter.IMAGENAME, IMAGENAME);
                values.put(DBAdapter.EXPR1, EXPR1);

                dbAdapter.DBInsert(values);

                Log.i(tmp, tmp);
            }
            br.close();
            in.close();
            Cursor cursor = dbAdapter.getAllData();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e("debug", e.toString());
        }
    }

}
