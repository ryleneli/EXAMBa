package com.example.Model;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DBControl.DBAdapter;

import java.util.Random;

/**
 * com.example.Model
 * Created by Ryleneli
 * 2019/12/9
 */
public class ExamModel {
    public Cursor cursor;
    public DBAdapter dbAdapter;
    public int numberOfAll ;
    public int numberOfChosen ;
    public int curIndex;
    //public int chapterNum;
    public int[] problemRand;//所有题目顺序
    public int[] testTurn;//试题
    public int[] testAnswer;//试题答案
    public int[] mySelect;// 我的答案

    public String TESTSUBJECT;
    public String TESTANSWER;
    public String ANSWERA;
    public String ANSWERB;
    public String ANSWERC;
    public String ANSWERD;
    public int TESTTPYE;

    public Boolean isHandIn;
    public ExamModel(Context context,int mode) {
        dbAdapter = new DBAdapter(context);
        dbAdapter.open();
        cursor = dbAdapter.getAllData();
        numberOfAll = cursor.getCount();
        numberOfChosen = 15;
        //problemRand = new int[numberOfAll];
        initData(mode);
    }
    public void initData(int chapterNum)
    {
        problemRand = new int[numberOfAll];
        testTurn = new int[numberOfChosen];
        testAnswer = new int[numberOfChosen];
        mySelect = new int[numberOfChosen];
        testOfChosen(chapterNum);
        handlerOftestAnswer();
    }

    public void handlerOftestAnswer() {//将答案转换成数据格式
        // TODO Auto-generated method stub
        //isHandIn = true;
        String tmpAnswer;
        for (int i = numberOfChosen-1; i >= 0; i--) {
            cursor.moveToPosition(testTurn[i]);//试题游标
            tmpAnswer = cursor.getString(cursor.getColumnIndex(DBAdapter.TESTANSWER));
            if (tmpAnswer.compareTo("对") == 0) {
                testAnswer[i] = 1;
            } else if (tmpAnswer.compareTo("错") == 0) {
                testAnswer[i] = 3;
            } else if (tmpAnswer.compareTo("A") == 0) {
                testAnswer[i] = 1;
            } else if (tmpAnswer.compareTo("B") == 0) {
                testAnswer[i] = 2;
            } else if (tmpAnswer.compareTo("C") == 0) {
                testAnswer[i] = 3;
            } else if (tmpAnswer.compareTo("D") == 0) {
                testAnswer[i] = 4;
            }
        }
    }

    public void testOfChosen(int chapterNum)//取题
    {   //顺序all,乱序all，chapter all,error all
        curIndex = 0;
        Random r = new Random();
        int t, rt1, rt2;
        for (int i = 0; i < numberOfAll; i++) {
            this.problemRand[i] = i;
        }
        for (int i = 0; i < numberOfAll; i++) {
            rt1 = r.nextInt(numberOfAll);
            rt2 = r.nextInt(numberOfAll);//160道题目中随机抽取，打乱顺序
            t = problemRand[rt1];
            problemRand[rt1] = problemRand[rt2];
            problemRand[rt2] = t;
        }
        try {
            int cnt = 0;
            for (int i = 0; cnt < 5; i++) {
                cursor.moveToPosition(problemRand[i]);
                if (cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTTPYE)) == 2 && (chapterNum == 0)) {// 判断题
                    mySelect[cnt] = 0;
                    testTurn[cnt++] = problemRand[i];
                }else if (cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTTPYE)) == 2 && cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTBELONG)) == chapterNum)
                {
                    mySelect[cnt] = 0;
                    testTurn[cnt++] = problemRand[i];
                }
            }
            for (int i = 0; cnt < 15; i++) {
                cursor.moveToPosition(problemRand[i]);
                if (cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTTPYE)) == 1 && (chapterNum == 0)) {// 选择题
                    mySelect[cnt] = 0;
                    testTurn[cnt++] = problemRand[i];
                }else if (cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTTPYE)) == 1 && cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTBELONG)) == chapterNum)
                {
                    mySelect[cnt] = 0;
                    testTurn[cnt++] = problemRand[i];
                }
            }
        } catch (Exception e) {
            //ShowToast(e.toString());
        }
    }
    public void getProTxt(RadioGroup radioGroup)//显示题目
    {
        if (cursor.getCount() == 0) {
            //Toast.makeText(gthis, "hello,there is no test found", Toast.LENGTH_LONG).show();
        } else {
            cursor.moveToPosition(testTurn[curIndex]);
            if (mySelect[curIndex] == 0) {
                radioGroup.clearCheck();
            }
            TESTSUBJECT = cursor.getString(cursor.getColumnIndex(DBAdapter.TESTSUBJECT));

            TESTANSWER = cursor.getString(cursor.getColumnIndex(DBAdapter.TESTANSWER));
            TESTTPYE = cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTTPYE));

            ANSWERA = cursor.getString(cursor.getColumnIndex(DBAdapter.ANSWERA));
            ANSWERB = cursor.getString(cursor.getColumnIndex(DBAdapter.ANSWERB));
            ANSWERC = cursor.getString(cursor.getColumnIndex(DBAdapter.ANSWERC));
            ANSWERD = cursor.getString(cursor.getColumnIndex(DBAdapter.ANSWERD));
        }
    }
}
