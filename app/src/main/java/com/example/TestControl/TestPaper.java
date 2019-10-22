package com.example.TestControl;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.DBControl.DBAdapter;
import com.example.DBControl.WrongSetShowList;
import com.example.testsys.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import static android.content.ContentValues.TAG;

/**
 * Created by 15151 on 2019/10/21.
 */

public class TestPaper { //4种不同的test类型对应不同的动作

    private Context mcontext;
    private int m_numberOfAll;
    private int m_numberOfChosen;


   // public static final int TESTLIMIT = 25;
    int curIndex;
    String myAnswer;
    int[] myWAset = new int[m_numberOfAll];// 以往错题
    int[] problemTurn = new int[m_numberOfAll];//所有题目顺序
    int[] problemRand = new int[m_numberOfAll];//所有题目顺序
    int[] testTurn = new int[m_numberOfChosen];//试题
    int[] testAnswer = new int[m_numberOfChosen];//试题答案
    int[] mySelect = new int[m_numberOfChosen];// 我的答案
    int resultInt;
    boolean isHandIn;// 表示交卷后
    int minutes, seconds;

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

    Cursor cursor;
    DBAdapter dbAdapter;

    private TestPaper(Context context,int numberOfAll,int numberOfChosen)
    {
        this.mcontext = context;
        this.m_numberOfAll = numberOfAll;
        this.m_numberOfChosen = numberOfChosen;
    }
    private void Forwordbtn () {
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
                        //OnPaint();
                        return;
                    }
                }
                //ShowToast("当前为第一题");
                return;
            } else {
                curIndex--;//不是第一页且没有交卷返回上一题
                //OnPaint();
            }
        }
    }
    private void Nextbtn()
    {
        if (curIndex == m_numberOfChosen - 1) {
            //ShowToast("当前为最后一题");//如果做到最后一题还在下一题执行
        } else {
            if (isHandIn) {
                int tindex = curIndex;
                while (++tindex < m_numberOfChosen) {
                    if (mySelect[tindex] != testAnswer[tindex]) {
                        curIndex = tindex;
                        //OnPaint();
                        return;
                    }
                }
                //ShowToast("当前为最后一题");再点击的话跳到答题卡页面
                return;
            } else {
                curIndex++;
                //OnPaint();//直接下一题
            }
        }
    }
    /*在考试页面执行，记录答案
    private void MyAnswerRecord()
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
*/
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
        //OnPaint();
    }
    private int score(int my_answer[],int test_answer[])
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

    private void problemTurn()
    {
        for (int i = 0; i < m_numberOfAll; i++)
        {
            problemTurn[i] = i;
        }
    }
    private void random()
    {
        for (int i = 0; i < m_numberOfAll; i++)
        {
            problemRand[i] = problemTurn[i];
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
    private void testMode (int flag)
    {
        //flag 1/2/3/4
    }
    private void testOfChosen(DBAdapter dbAdapter,int problem[])
    {   //顺序all,乱序all，chapter all,error all
        curIndex = 0;
        try {
            dbAdapter.open();
            cursor = dbAdapter.getAllData();
            int cnt = 0;
            for (int i = 0; cnt < 10; i++) {
                cursor.moveToPosition(problem[i]);
                if (cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTTPYE)) == 2) {// 判断题
                    mySelect[cnt] = 0;
                    testTurn[cnt++] = problem[i];
                }
            }
            for (int i = 0; cnt < 15; i++) {
                cursor.moveToPosition(problem[i]);
                if (cursor.getInt(cursor.getColumnIndex(DBAdapter.TESTTPYE)) == 1) {// 选择题
                    mySelect[cnt] = 0;
                    testTurn[cnt++] = problem[i];
                }
            }
        } catch (Exception e) {
            //ShowToast(e.toString());
        }
    }

    // 剩余时间string
    private String nowtime() {//考试结束前十分钟跳出弹窗提示考试即将结束
        // TODO Auto-generated method stub
        if (seconds < 10) {
            return (minutes + ":0" + seconds);
        } else {
            return (minutes + ":" + seconds);
        }
    }


}
