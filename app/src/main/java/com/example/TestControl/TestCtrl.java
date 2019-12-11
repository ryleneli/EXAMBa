package com.example.TestControl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Activity.ExamActivity;
import com.example.Activity.LoginActivity;
import com.example.Activity.MyAnswerActivity;
import com.example.Constant;
import com.example.DBControl.DBAdapter;
import com.example.UI.TestView;

import java.util.Random;

import static android.content.ContentValues.TAG;

/**
 * Created by 15151 on 2019/10/21.
 */

public class TestCtrl {/*
    //具体的题目显示逻辑
    private static String TAG = "LRL ExamActivity";
    private Context mcontext;
    private int m_numberOfAll;
    private int m_numberOfChosen;
    private int temp;


    // public static final int TESTLIMIT = 25;
    int curIndex;
    String myAnswer;
    int[] myWAset = new int[m_numberOfAll];// 以往错题
    //int[] problemTurn = new int[m_numberOfAll];//所有题目顺序
    int[] problemRand;//所有题目顺序
    int[] testTurn;//试题
    int[] testAnswer;//试题答案
    int[] mySelect;// 我的答案
    int resultInt;
    boolean isHandIn = false;// 表示交卷后
    int minutes, seconds;
    public enum TESTMODE{ CHAPTER, ERROR, RANDOM, TEST };
    private TESTMODE flag;

    String TESTSUBJECT;
    String TESTANSWER;
    String ANSWERA;
    String ANSWERB;
    String ANSWERC;
    String ANSWERD;

    int TESTTPYE;
    int TESTBELONG;
    int EXPR1;

    Cursor cursor;
    DBAdapter dbAdapter;
    Chronometer chronometer;
    private ExamActivity examActivity;

    public TestCtrl(Context context, ExamActivity examActivity,DBAdapter dbAdapter, Cursor cursor_1, Chronometer chronometer,int numberOfAll, int numberOfChosen, int[]problemRand_1, int[]testTurn_1, int[]testAnswer_1, int[]mySelect_1)
    {
        this.mcontext = context;
        this.examActivity = examActivity;
        this.dbAdapter = dbAdapter;
        this.cursor = cursor_1;
        this.chronometer = chronometer;
        this.m_numberOfAll = numberOfAll;
        this.m_numberOfChosen = numberOfChosen;
        this.problemRand = problemRand_1;
        this.testTurn = testTurn_1;
        this.mySelect = mySelect_1;
        this.testAnswer =testAnswer_1;
    }
    public String testMode(int flag) {
        String mode = null;
        switch (flag) {
            case 1:
            {mode = Constant.CHAPTER;}
            break;
            case 2:
            {mode = Constant.RANDOM;}
            break;
            case 3:
            {mode = Constant.ERROR;}
            break;
            case 4:
            {mode = Constant.TEST;}
            break;
            default:
                break;
        }
        return mode;
    }
    public void setIndex(int index)
    {
        curIndex = index;
    }

*//*    public int timeTrans ( )
    {
        int temp0 = Integer.parseInt(chronometer.getText().toString().split(":")[0]);
        int temp1 =Integer.parseInt(chronometer.getText().toString().split(":")[1]);
        return temp0*60+temp1;
    }*//*
    public void forwordBtn (Button button_for,Button button_nex,TextView textView_num, TextView textView_pro, RadioGroup radioGroup, RadioButton radio_a, RadioButton radio_b, RadioButton radio_c, RadioButton radio_d) {

            if (isHandIn) {// 交卷后
                int tindex = curIndex;
                while (--tindex >= 0) {
                    if (mySelect[tindex] != testAnswer[tindex])//显示错题
                    {
                        curIndex = tindex;//交卷后为第一题
                        OnPaint(textView_pro,radioGroup,radio_a,radio_b,radio_c,radio_d);
                        return;
                    }
                }
                //ShowToast("当前为第一题");
                return;
            } else {
                curIndex--;//不是第一页且没有交卷返回上一题
                if (curIndex <= m_numberOfChosen - 1) {
                    button_nex.setText("下一题");
                }
                if (curIndex == 0) {
                    button_for.setText("无");
                }
                if (curIndex < 0) {
                    Toast.makeText(mcontext, "默认的Toast", Toast.LENGTH_SHORT).show();
                    curIndex = 0;
                }

                OnPaint(textView_pro,radioGroup,radio_a,radio_b,radio_c,radio_d);
                String temp = (curIndex + 1) + "/" + m_numberOfChosen;
                textView_num.setText(temp);
            }
    }
    public void nextBtn(Activity activity, boolean handIn,Button button_for, Button button_nex, TextView textView_num, TextView textView_pro, RadioGroup radioGroup, RadioButton radio_a, RadioButton radio_b, RadioButton radio_c, RadioButton radio_d)
    {
            if (isHandIn) {
                int tindex = curIndex;
                while (++tindex < m_numberOfChosen) {
                    if (mySelect[tindex] != testAnswer[tindex]) {
                        curIndex = tindex;
                        OnPaint(textView_pro,radioGroup,radio_a,radio_b,radio_c,radio_d);
                        return;
                    }
                }
                //ShowToast("当前为最后一题");再点击的话跳到答题卡页面
                return;
            } else {
                curIndex++;
                if (curIndex >= 1)
                    button_for.setText("上一题");

                if (curIndex == m_numberOfChosen - 1) {
                    button_nex.setText("提交");
                }
                if (curIndex == 15)
                {   Intent intent = new Intent(activity, MyAnswerActivity.class);
                    intent.putExtra("answer",mySelect);
                    intent.putExtra("test_answer",testAnswer);
                    temp = timeTrans();
                    intent.putExtra("timer",temp);
                    Log.i(TAG,"LRL temp  is ============================="+temp );
                    activity.startActivityForResult(intent,1);
                    curIndex = 14;
                }
                if (handIn && curIndex == 14)
                {
                    button_nex.setEnabled(false);
                    button_nex.setText("无");
                }
                else button_nex.setEnabled(true);
                Log.i(TAG,"LRL curIndex now is ======"+curIndex);
                OnPaint(textView_pro,radioGroup,radio_a,radio_b,radio_c,radio_d);//直接下一题
                String temp = (curIndex + 1) + "/" + m_numberOfChosen;
                textView_num.setText(temp);
            }
    }

    //记录答案
    public void myAnswerRecord(RadioButton radio_a,RadioButton radio_b,RadioButton radio_c,RadioButton radio_d)
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


    //
    public void handlerOftestAnswer() {//将答案转换成数据格式
        // TODO Auto-generated method stub
        //isHandIn = true;
        String tmpAnswer;
        for (int i = m_numberOfChosen-1; i >= 0; i--) {
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
    public void handleOfAnswer(int curIndex)
    {
        String temp = null;
        if (curIndex<=4)
        {
            if (mySelect[curIndex] == 1) {
                temp = "对";
                examActivity.myAnswer.setText("您的答案"+temp);
            } else if (mySelect[curIndex] == 3) {
                temp = "错";
                examActivity.myAnswer.setText("您的答案"+temp);
            }
            else if (mySelect[curIndex] == 0) {
                temp = "未选";
                examActivity.myAnswer.setText("您的答案："+temp);
            }
        }
        else if (curIndex > 4 && curIndex <=14)
        {
            if (mySelect[curIndex] == 1) {
                temp = "A";
                examActivity.myAnswer.setText("您的答案"+temp);
            } else if (mySelect[curIndex] == 2) {
                temp = "B";
                examActivity.myAnswer.setText("您的答案"+temp);
            } else if (mySelect[curIndex] == 3) {
                temp = "C";
                examActivity.myAnswer.setText("您的答案"+temp);
            } else if (mySelect[curIndex] == 4) {
                temp = "D";
                examActivity.myAnswer.setText("您的答案"+temp);
            }
        }
    }
    private void toWrongItem()
    {
        curIndex = 0;
        for (int i = 0; i < m_numberOfChosen; i++) {
            if (mySelect[i] != testAnswer[i]) {
                curIndex = i;
                break;
            }
        }
        //OnPaint();
    }




    public void testOfChosen(int chapterNum)//取题
    {   //顺序all,乱序all，chapter all,error all
        curIndex = 0;
        Random r = new Random();
        int t, rt1, rt2;
        for (int i = 0; i < m_numberOfAll; i++) {
            this.problemRand[i] = i;
        }
        for (int i = 0; i < m_numberOfAll; i++) {
            rt1 = r.nextInt(m_numberOfAll);
            rt2 = r.nextInt(m_numberOfAll);//160道题目中随机抽取，打乱顺序
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





    public void OnPaint(TextView proTextView,RadioGroup radioGroup,RadioButton radio_a,RadioButton radio_b,RadioButton radio_c,RadioButton radio_d)//显示题目
    {
        if (cursor.getCount() == 0) {
            //Toast.makeText(this, "hello", Toast.LENGTH_LONG).show();
        } else {
            cursor.moveToPosition(testTurn[curIndex]);
            if (mySelect[curIndex] == 0) {
                radioGroup.clearCheck();
            }
            TESTSUBJECT = cursor.getString(cursor.getColumnIndex(DBAdapter.TESTSUBJECT));
            // TESTSUBJECT = TESTSUBJECT.replace("“|”", "下图");
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
        //ExamActivity examActivity = new ExamActivity();
        if (examActivity.isHandIn)
        {
            handleOfAnswer(curIndex);
            //examActivity.myAnswer.setText("nindedaa："+mySelect[curIndex]);
            //examActivity.myAnswer.setVisibility(View.VISIBLE);
            examActivity.rightAnswer.setText("正确答案："+TESTANSWER);
        }

    }*/
}
