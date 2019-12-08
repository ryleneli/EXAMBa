package com.example.Model;

import android.content.Intent;
import android.util.Log;

/**
 * com.example.Model
 * Created by Ryleneli 15151
 * 2019/12/8
 */
public class MyAnswerModel {
    private static String TAG = "MyAnswerModel";
    public int myAnswer[] = new int [15];
    public int testAnswer[] = new int [15];
    public int temp;
    public void getIntentData(Intent intent)
    {
        myAnswer = intent.getIntArrayExtra("answer");
        testAnswer = intent.getIntArrayExtra("test_answer");
        temp = intent.getIntExtra("timer",0);
    }
    public void putIntentData(Intent intent,boolean isHandIn)
    {
        intent.putExtra("isHandIn",isHandIn);
        intent.putExtra("answer",myAnswer);
    }
    public String tempToTimer()
    {
        int minute = temp/60;
        int second = temp%60;
        Log.i(TAG, "my answer====="+minute+" is  ==========="+second);
        String time_text = "考试用时："+minute + ":"+second;
        return time_text;
    }
    public int answerCount()
    {
        int sum = 0;
        for (int i=0;i<15;i++)
        {
            if (myAnswer[i] == testAnswer[i])
                sum ++;
        }
        return sum;
    }
}
