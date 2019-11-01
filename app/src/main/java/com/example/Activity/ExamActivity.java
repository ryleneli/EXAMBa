package com.example.Activity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Random;

import com.example.Constant;
import com.example.DBControl.DBAdapter;
import com.example.TestControl.TestCtrl;
import com.example.UI.TitleBarView;
import com.example.testsys.R;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ExamActivity extends Activity {
	private static String TAG = "ExamActivity";

	private int flag;
	private int index;
	private boolean isHandIn;
	private String title;
	private TitleBarView titleBarView;
	private TextView proTextView;//问题
	private TextView numText;
	private TextView TextMode;
	private TextView rightAnswer;
	private TextView myAnswer;
	private RadioButton radioA;
	private RadioButton radioB;
	private RadioButton radioC;
	private RadioButton radioD;//答案按钮组
	private RadioGroup radioGroup;
	private Button forword_btn;//上一题按钮
	private Button next_btn;//下一题按钮
	public Chronometer chronometer;
	private ImageButton answerButton;
	private ImageView imageView;
	private int temp;
	Cursor cursor;
	DBAdapter dbAdapter;
	/*int[] problemRand = new int[160];//所有题目顺序
	int[] testTurn = new int[15];//试题
	int[] testAnswer = new int[15];//试题答案
	int[] mySelect = new int[15];// 我的答案*/
	String TextMode_text;
	TestCtrl testctrl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_fragment);
		titleBarView = (TitleBarView) findViewById(R.id.test_titlebar);
		proTextView = (TextView) findViewById(R.id.pro_text);
		numText = (TextView) findViewById(R.id.test_number);
		TextMode = (TextView) findViewById(R.id.test_mode);
		radioA = (RadioButton) findViewById(R.id.radioA);
		radioB = (RadioButton) findViewById(R.id.radioB);
		radioC = (RadioButton) findViewById(R.id.radioC);
		radioD = (RadioButton) findViewById(R.id.radioD);
		forword_btn = (Button) findViewById(R.id.last);
		next_btn = (Button) findViewById(R.id.next);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		rightAnswer = (TextView) findViewById(R.id.right_answer);
		myAnswer = (TextView) findViewById(R.id.my_answer);
		chronometer = titleBarView.getChronometer();
		answerButton = titleBarView.getTitleAnswer();
		imageView = titleBarView.getTimerBack();
		chronometer.setBase(SystemClock.elapsedRealtime());
		chronometer.start();
		dbAdapter = new DBAdapter(this);
		dbAdapter.open();
		cursor = dbAdapter.getAllData();
		int numberOfAlltest = cursor.getCount();
		int numberOfChosen = 15;
		int[] problemRand = new int[numberOfAlltest];//所有题目顺序
		int[] testTurn = new int[numberOfChosen];//试题
		final int[] testAnswer = new int[numberOfChosen];//试题答案
		final int[] mySelect = new int[numberOfChosen];// 我的答案

		testctrl = new TestCtrl(this,dbAdapter,cursor,chronometer,numberOfAlltest,numberOfChosen,problemRand,testTurn,testAnswer,mySelect);


		testctrl.testOfChosen(0);
		testctrl.handlerOftestAnswer();
        //testctrl.OnPaint(proTextView,radioGroup,radioA,radioB,radioC,radioD);
		//numText.setText("1/15");
		//forword_btn.setText("无");
		forword_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				testctrl.forwordBtn(forword_btn,next_btn,numText,proTextView,radioGroup,radioA,radioB,radioC,radioD);
			}
		});
		next_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				testctrl.nextBtn(ExamActivity.this,isHandIn,forword_btn,next_btn,numText,proTextView,radioGroup,radioA,radioB,radioC,radioD);
			}
		});

		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				testctrl.myAnswerRecord(radioA,radioB,radioC,radioD);
			}
		});
		answerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				    Intent intent = new Intent(ExamActivity.this, MyAnswerActivity.class);
					intent.putExtra("answer",mySelect);
					intent.putExtra("test_answer",testAnswer);
				    temp=testctrl.timeTrans();
				Log.i(TAG,"LRL temp in listener  is =======-----------========"+temp );
					intent.putExtra("timer",temp);
				    startActivityForResult(intent,1);
			}
		});

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
	}
	@Override
	protected void onStart() {//ExamActivity走singletask模式，在活动站中只保存一个实例
		Log.i(TAG,"LRL =======-----------========onStart" );
		super.onStart();
		Intent intent_H_E = getIntent();
		flag = intent_H_E.getIntExtra("test_mode",0);
		title = intent_H_E.getStringExtra("lesson_name");
		if (isHandIn)
		{
			Log.i(TAG,"LRL =======-----------========onStart&&&&&&&&&ishandein == false" );
			answerButton.setVisibility(View.INVISIBLE);
			imageView.setVisibility(View.INVISIBLE);
			chronometer.stop();
			chronometer.setVisibility(View.INVISIBLE);
			index = 0;
			rightAnswer.setVisibility(View.VISIBLE);
			myAnswer.setVisibility(View.VISIBLE);
		}
		TextMode_text = testctrl.testMode(flag);
		titleBarView.getTitleText().setText(title);
		TextMode.setText(TextMode_text);
		testctrl.setIndex(index);
		String temp = (index + 1) + "/" + 15;
		numText.setText(temp);
		Log.i(TAG,"LRL index  is =======-----------========"+index );
		if (index == 0)
		{
			forword_btn.setText("无");
			next_btn.setText("下一题");
			Log.i(TAG,"LRL index  is =======-----------========enter 00000？？？" );
		}else if (index == 14)
		{
			forword_btn.setText("上一题");
			next_btn.setText("提交");
			Log.i(TAG,"LRL index  is =======-----------========enter 141414141？？？" );
		}else
		{
			forword_btn.setText("上一题");
			next_btn.setText("下一题");
			Log.i(TAG,"LRL index  is =======-----------========enter -----？？？" );
		}

		testctrl.OnPaint(proTextView,radioGroup,radioA,radioB,radioC,radioD);
	}
	@Override
	protected void onRestart() {
		Log.i(TAG,"LRL =======-----------========onRestart" );
		super.onRestart();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch(requestCode)
		{
			case 1 :
				if(resultCode == RESULT_OK)
				{
					index = data.getIntExtra("select_index",0);
					Log.i(TAG,"LRL mySelect now is *****==========***"+index);
					isHandIn = data.getBooleanExtra("isHandIn",false);
				}
				break;
			default : break;
		}
	}


	@Override
	protected void onDestroy() {
		dbAdapter.close();
		cursor.close();
		chronometer.stop();
		super.onDestroy();
	}

}
