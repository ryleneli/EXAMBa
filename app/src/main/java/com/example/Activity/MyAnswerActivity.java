package com.example.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.Adapter.MyAnswerRecyclerView;
import com.example.Adapter.MyRecyclerViewAdapter;
import com.example.UI.TitleBarView;
import com.example.testsys.R;

import static android.view.View.GONE;

/**
 * Created by 15151 on 2019/10/27.
 */

public class MyAnswerActivity extends Activity {
    private static String TAG = "LRL ExamActivity";
    private TitleBarView titleBarView;
    private TextView numText,resultText,timeText;
    private RecyclerView recyclerView ;
    private MyAnswerRecyclerView recyclerViewAdapter;
    private Button button ;
    private RelativeLayout relativeLayout;
    private View view;
    private Chronometer chronometer;
    private int myAnswer[] = new int [15];
    private int testAnswer[] = new int [15];
    boolean isHandIn = false;
    private int sum;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_record);
        Log.i(TAG,"LRL mySelect now is **********onCreate");
        Intent intent = getIntent() ;
        myAnswer = intent.getIntArrayExtra("answer");
        testAnswer = intent.getIntArrayExtra("test_answer");
        titleBarView = (TitleBarView) findViewById(R.id.myAnswer_titlebar);
        chronometer = titleBarView.getChronometer();
        int temp = intent.getIntExtra("timer",0);
        Log.i(TAG, "my temp======================"+temp);
        chronometer.setBase(SystemClock.elapsedRealtime()-temp*1000);
        chronometer.start();
        button = (Button) findViewById(R.id.upload_button);
        relativeLayout = (RelativeLayout) findViewById(R.id.question_type_1);
        resultText = (TextView) findViewById(R.id.result);
        timeText = (TextView) findViewById(R.id.time);
        view = (View) findViewById(R.id.view_last);
        titleBarView.setTitleText("答题卡");
        recyclerView = (RecyclerView)findViewById(R.id.my_answer);
        GridLayoutManager layoutManager = new GridLayoutManager(this,5);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new MyAnswerRecyclerView(this,MyAnswerActivity.this,myAnswer,testAnswer,isHandIn);
        recyclerView.setAdapter(recyclerViewAdapter);
        for (int i=0;i<15;i++)
        {
            Log.i(TAG, "my answer====="+i+" is  ==========="+myAnswer[i]);
            Log.i(TAG, "test answer*******"+i+" is  ************"+testAnswer[i]);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                isHandIn = true ;
                button.setText("查看试题");
                relativeLayout.setVisibility(GONE);
                resultText.setVisibility(View.VISIBLE);
                timeText.setVisibility(View.VISIBLE);
                view.setVisibility(View.VISIBLE);
                titleBarView.setTitleText("结果报告");
                titleBarView.setTimer(false);
                chronometer.setVisibility(View.INVISIBLE);
                recyclerViewAdapter = new MyAnswerRecyclerView(getBaseContext(),MyAnswerActivity.this,myAnswer,testAnswer,isHandIn);
                recyclerView.setAdapter(recyclerViewAdapter);
                sum = answerCount();
                String temp = "正确："+sum + "/15";
                resultText.setText(temp);
            }
        });
    }
    private int answerCount()
    {
        int sum = 0;
        for (int i=0;i<15;i++)
        {
            if (myAnswer[i] == testAnswer[i])
                sum ++;
        }
        return sum;
    }
    @Override
    protected void onStart() {//ExamActivity走singletask模式，在活动站中只保存一个实例
        super.onStart();
        Log.i(TAG,"LRL mySelect now is **********on start");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"LRL mySelect now is **********onRestart");
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"LRL mySelect now is **********onDestroy");
    }
}
