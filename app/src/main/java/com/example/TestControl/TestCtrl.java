package com.example.TestControl;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.DBControl.DBAdapter;
import com.example.UI.TestView;

import java.util.Random;

import static android.content.ContentValues.TAG;

/**
 * Created by 15151 on 2019/10/21.
 */

public class TestCtrl {
    //具体的题目显示逻辑
    private static String TAG = "LRL TestCtrl";
    private Context mcontext;
    private int m_numberOfAll;
    private int m_numberOfChosen;


    // public static final int TESTLIMIT = 25;
    int curIndex;
    String myAnswer;
    int[] myWAset = new int[m_numberOfAll];// 以往错题
    //int[] problemTurn = new int[m_numberOfAll];//所有题目顺序
    int[] problemRand = new int[160];//所有题目顺序
    int[] testTurn = new int[15];//试题
    int[] testAnswer = new int[15];//试题答案
    int[] mySelect = new int[15];// 我的答案
    int resultInt;
    boolean isHandIn;// 表示交卷后
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
    RadioGroup radioGroup;
    TextView proTextView,textView;
    RadioButton radioA,radioB,radioC,radioD;

    Cursor cursor;
    DBAdapter dbAdapter;


    public TestCtrl(Context context, DBAdapter dbAdapter,int numberOfAll,int numberOfChosen,RadioGroup radio_group,TextView textView1,TextView textView2,RadioButton radio_a,
                      RadioButton radio_b,RadioButton radio_c,RadioButton radio_d)
    {
        this.mcontext = context;
        this.dbAdapter = dbAdapter;
        this.m_numberOfAll = numberOfAll;
        this.m_numberOfChosen = numberOfChosen;
        this.radioGroup = radio_group;
        this.radioA = radio_a;
        this.radioB = radio_b;
        this.radioC = radio_c;
        this.radioD = radio_d;
        this.proTextView = textView1;
        this.textView = textView2;
    }
    public void forwordBtn () {
        if (curIndex == 0) {
            //ShowToast("当前为第一题");
        } else {
            if (isHandIn) {// 交卷后
                int tindex = curIndex;
                Log.i(TAG, "LRL isHandIn" + curIndex);
                while (--tindex >= 0) {
                    Log.i(TAG, "LRL mySelect" + mySelect[tindex] + "testAnswer" + testAnswer[tindex]);
                    if (mySelect[tindex] != testAnswer[tindex])//显示错题
                    {
                        curIndex = tindex;//交卷后为第一题
                        OnPaint();
                        return;
                    }
                }
                //ShowToast("当前为第一题");
                return;
            } else {
                curIndex--;//不是第一页且没有交卷返回上一题
                OnPaint();
                String temp = (curIndex + 1) + "/" + m_numberOfChosen;
                textView.setText(temp);
            }
        }
    }
    public void nextBtn()
    {
        if (curIndex == m_numberOfChosen - 1) {
            //ShowToast("当前为最后一题");//如果做到最后一题还在下一题执行
        } else {
            if (isHandIn) {
                int tindex = curIndex;
                while (++tindex < m_numberOfChosen) {
                    if (mySelect[tindex] != testAnswer[tindex]) {
                        curIndex = tindex;
                        OnPaint();
                        return;
                    }
                }
                //ShowToast("当前为最后一题");再点击的话跳到答题卡页面
                return;
            } else {
                curIndex++;
                OnPaint();//直接下一题
                String temp = (curIndex + 1) + "/" + m_numberOfChosen;
                Log.i(TAG,"LRL mySelect"+temp);
                //textView.setText(temp);
            }
        }
    }
    //记录答案
    public void myAnswerRecord()
    {
        if (!isHandIn) {
            if (radioA.isChecked() && mySelect[curIndex] != 1) {//return to change answer
                Log.i(TAG,"LRL mySelect"+mySelect[curIndex]);
                mySelect[curIndex] = 1;
                Log.i(TAG,"LRL mySelect"+mySelect[curIndex]);//for log test initvalue is 0

            } else if (radioB.isChecked() && mySelect[curIndex] != 2) {
                mySelect[curIndex] = 2;

            } else if (radioC.isChecked() && mySelect[curIndex] != 3) {
                mySelect[curIndex] = 3;

            } else if (radioD.isChecked() && mySelect[curIndex] != 4) {
                mySelect[curIndex] = 4;

            }
        }
    }

    //仅在考试场景使用
    /*
		chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
					@Override
					public void onChronometerTick(Chronometer chronometer) {
						// TODO Auto-generated method stub
						seconds--;
						if (seconds == -1) {
							minutes--;
							seconds = 59;
						}
						if (minutes < 0) {
							chronometer.stop();
							// 直接交卷！
							handlerAfterHandIn();
						} else {
							if (minutes < 5) {
								chronometer.setTextColor(Color.RED);
								chronometer.setText(nowtime());
							} else {
								chronometer.setTextColor(Color.GREEN);
								chronometer.setText(nowtime());
							}
						}
					}
				});*/


    protected void showHandInAgainDialog(Context context) {//考试场景使用
        // TODO Auto-generated method stub
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("确认交卷吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认",new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface v, int which) {
                // TODO Auto-generated method stub
                //handlerAfterHandIn();
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }

    // 处理交卷后
    protected void handlerOftestAnswer() {//将答案转换成数据格式
        // TODO Auto-generated method stub
        isHandIn = true;
        String tmpAnswer;
        for (int i = 24; i >= 0; i--) {
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
    private void toWrongItem()
    {
        curIndex = 0;
        for (int i = 0; i < 24; i++) {
            if (mySelect[i] != testAnswer[i]) {
                curIndex = i;
                break;
            }
        }
        OnPaint();
    }
    private int score(int my_answer[],int test_answer[])//计分
    {
        int sum = 0;
        for (int i = m_numberOfChosen; i >= 0; i--)
        {
            if (test_answer[i] == my_answer[i]) {
                sum = sum+4;
            }
        }
        return sum;
    }
    private void showScore() {//仅在考试场景使用
        // TODO Auto-generated method stub
        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        builder.setTitle("结果");
        if (resultInt == 100) {
            builder.setMessage("满分！请继续保持");
        } else if (resultInt >= 60) {
            builder.setMessage("合格了！成绩为： " + resultInt + "分,请再接再厉");
        } else {
            builder.setMessage("不合格！成绩为：" + resultInt + "分,请继续努力");
        }
        builder.setPositiveButton("确定", null);
        builder.create().show();
    }

    public void problemTurn()//正序题目
    {
        for (int i = 0; i < m_numberOfAll; i++)
        {
            //problemTurn[i] = i;
        }
    }
    public void random()//乱序题目
    {
        for (int i = 0; i < m_numberOfAll; i++)
        {
            problemRand[i] = i;
        }
        Random r = new Random();
        int t, rt1, rt2;
        for (int i = 0; i < m_numberOfAll; i++) {
            rt1 = r.nextInt(m_numberOfAll);
            rt2 = r.nextInt(m_numberOfAll);//160道题目中随机抽取，打乱顺序
            t = problemRand[rt1];
            problemRand[rt1] = problemRand[rt2];
            problemRand[rt2] = t;
        }
    }
    public void testMode(TESTMODE flag) {
        switch (flag) {
            case CHAPTER:
            {

            }
            break;
            case ERROR:
            {

            }
            break;
            case RANDOM:
            {

            }
            case TEST:
            {

            }
            break;
        }
    }
    public void testOfChosen(int chapterNum)//取题
    {   //顺序all,乱序all，chapter all,error all
        curIndex = 0;
        Random r = new Random();
        int t, rt1, rt2;
        for (int i = 0; i < m_numberOfAll; i++) {

            this.problemRand[i] = i;
            Log.i(TAG,"LRL ===== --------------===========");
        }
        for (int i = 0; i < m_numberOfAll; i++) {
            Log.i(TAG,"LRL ===== is good");
            rt1 = r.nextInt(m_numberOfAll);
            Log.i(TAG,"LRL ====="+rt1);
            rt2 = r.nextInt(m_numberOfAll);//160道题目中随机抽取，打乱顺序
            Log.i(TAG,"LRL ====="+rt2);
            t = problemRand[rt1];
            problemRand[rt1] = problemRand[rt2];
            problemRand[rt2] = t;
            Log.i(TAG,"LRL ===== -----------------------------------------------");
        }
        try {
            dbAdapter.open();
            cursor = dbAdapter.getAllData();
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
            for (int i = 0; cnt < 10; i++) {
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

    // 剩余时间string
    public String nowtime() {//考试结束前十分钟跳出弹窗提示考试即将结束
        // TODO Auto-generated method stub
        if (seconds < 10) {
            return (minutes + ":0" + seconds);
        } else {
            return (minutes + ":" + seconds);
        }
    }

    public void OnPaint()//显示题目
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
                radioA.setText("对");
                radioC.setText("错");
                radioB.setVisibility(View.GONE);
                radioD.setVisibility(View.GONE);
            } else {
                // 选择题
                radioA.setText("A." + ANSWERA);
                radioB.setText("B." + ANSWERB);
                radioC.setText("C." + ANSWERC);
                radioD.setText("D." + ANSWERD);
                radioA.setVisibility(View.VISIBLE);
                radioB.setVisibility(View.VISIBLE);
                radioC.setVisibility(View.VISIBLE);
                radioD.setVisibility(View.VISIBLE);
            }
            switch (mySelect[curIndex]) {
                case 1:
                    radioA.setChecked(true);
                    break;
                case 2:
                    radioB.setChecked(true);
                    break;
                case 3:
                    radioC.setChecked(true);
                    break;
                case 4:
                    radioD.setChecked(true);
                    break;
                default:
                    break;
            }
        }
    }
}
