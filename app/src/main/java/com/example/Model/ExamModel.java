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

    String TESTSUBJECT;
    String TESTANSWER;
    String ANSWERA;
    String ANSWERB;
    String ANSWERC;
    String ANSWERD;
    int TESTTPYE;

    public Boolean isHandIn;
    public ExamModel(Context context,int mode) {
        dbAdapter = new DBAdapter(context);
        dbAdapter.open();
        cursor = dbAdapter.getAllData();
        numberOfAll = cursor.getCount();
        numberOfChosen = 15;
        initData(mode);
    }
    public void initData(int chapterNum)
    {
        problemRand = new int[numberOfAll];
        testTurn = new int[numberOfChosen];
        testOfChosen(chapterNum);
        handlerOftestAnswer();
    }

    //记录答案
    public void myAnswerRecord(RadioButton radio_a,
                               RadioButton radio_b, RadioButton radio_c, RadioButton radio_d)
    {
        if (!isHandIn) {
            if (radio_a.isChecked() && mySelect[curIndex] != 1) {//return to change answer
                mySelect[curIndex] = 1;

            } else if (radio_b.isChecked() && mySelect[curIndex] != 2) {
                mySelect[curIndex] = 2;

            } else if (radio_c.isChecked() && mySelect[curIndex] != 3) {
                mySelect[curIndex] = 3;

            } else if (radio_d.isChecked() && mySelect[curIndex] != 4) {
                mySelect[curIndex] = 4;

            }
        }
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
    public void handleOfAnswer(int curIndex,TextView myAnswer)
    {
        String temp = null;
        if (curIndex<=4)
        {
            if (mySelect[curIndex] == 1) {
                temp = "对";
                myAnswer.setText("您的答案"+temp);
            } else if (mySelect[curIndex] == 3) {
                temp = "错";
                myAnswer.setText("您的答案"+temp);
            }
            else if (mySelect[curIndex] == 0) {
                temp = "未选";
                myAnswer.setText("您的答案："+temp);
            }
        }
        else if (curIndex > 4 && curIndex <=14)
        {
            if (mySelect[curIndex] == 1) {
                temp = "A";
                myAnswer.setText("您的答案"+temp);
            } else if (mySelect[curIndex] == 2) {
                temp = "B";
                myAnswer.setText("您的答案"+temp);
            } else if (mySelect[curIndex] == 3) {
                temp = "C";
                myAnswer.setText("您的答案"+temp);
            } else if (mySelect[curIndex] == 4) {
                temp = "D";
                myAnswer.setText("您的答案"+temp);
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
    public void OnPaint(TextView proTextView, TextView rightAnswer,TextView myAnswer,RadioGroup radioGroup, RadioButton radio_a, RadioButton radio_b, RadioButton radio_c, RadioButton radio_d)//显示题目
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
            proTextView.setText((curIndex + 1) + "." + TESTSUBJECT);
            ANSWERA = cursor.getString(cursor.getColumnIndex(DBAdapter.ANSWERA));
            ANSWERB = cursor.getString(cursor.getColumnIndex(DBAdapter.ANSWERB));
            ANSWERC = cursor.getString(cursor.getColumnIndex(DBAdapter.ANSWERC));
            ANSWERD = cursor.getString(cursor.getColumnIndex(DBAdapter.ANSWERD));
            if (ANSWERA.compareTo("") == 0) {
                // 判断题
                radio_a.setText("对");
                radio_c.setText("错");
                radio_b.setVisibility(View.GONE);
                radio_d.setVisibility(View.GONE);
            } else {
                // 选择题
                radio_a.setText("A." + ANSWERA);
                radio_b.setText("B." + ANSWERB);
                radio_c.setText("C." + ANSWERC);
                radio_d.setText("D." + ANSWERD);
                radio_a.setVisibility(View.VISIBLE);
                radio_b.setVisibility(View.VISIBLE);
                radio_c.setVisibility(View.VISIBLE);
                radio_d.setVisibility(View.VISIBLE);
            }
            switch (mySelect[curIndex]) {
                case 1:
                    radio_a.setChecked(true);
                    break;
                case 2:
                    radio_b.setChecked(true);
                    break;
                case 3:
                    radio_c.setChecked(true);
                    break;
                case 4:
                    radio_d.setChecked(true);
                    break;
                default:
                    break;
            }
        }
        if (isHandIn)
        {
            handleOfAnswer(curIndex,myAnswer);
            rightAnswer.setText("正确答案："+TESTANSWER);
        }

    }

}
