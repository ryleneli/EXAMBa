package com.example.Activity;

import com.example.Constant;
import com.example.DBControl.DBAdapter;
import com.example.Presenter.ExamPresenter;
import com.example.Service.FloatWindowService;
import com.example.Manager.MyWindowManager;
import com.example.TestControl.TestCtrl;
import com.example.UI.TitleBarView;
import com.example.Utils.StatusBarUtil;
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
import android.widget.Toast;

public class ExamActivity extends Activity {
	private static String TAG = "ExamActivity";
    private MyWindowManager myWindowManager;
	private int flag;
	private int index;
	public boolean isHandIn;
	private String title;
	private TitleBarView titleBarView;
	private TextView proTextView;//问题
	private TextView numText;
	private TextView TextMode;
	public TextView rightAnswer;
	public TextView myAnswer;
	private RadioButton radioA;
	private RadioButton radioB;
	private RadioButton radioC;
	private RadioButton radioD;//答案按钮组
	private RadioGroup radioGroup;
	private Button forword_btn;//上一题按钮
	private Button next_btn;//下一题按钮
	private int[] testAnswer = new int[15];//试题答案
	private int[] mySelect = new int[15];// 我的答案
	public Chronometer chronometer;
	private ImageButton answerButton;
	private ImageView imageView;
	private int temp;
	Cursor cursor;
	DBAdapter dbAdapter;

	String TextMode_text;
	//TestCtrl testctrl;
	public ExamPresenter examPresenter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_fragment);
		examPresenter = new ExamPresenter(getBaseContext(),ExamActivity.this);
		StatusBarUtil.setRootViewFitsSystemWindows(this,false);
		//设置状态栏透明
		StatusBarUtil.setTranslucentStatus(this);
		StatusBarUtil.setStatusBarDarkTheme(ExamActivity.this,false);
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
		imageView.setVisibility(View.VISIBLE);
		chronometer.setBase(SystemClock.elapsedRealtime());
		chronometer.start();
/*		dbAdapter = new DBAdapter(this);
		dbAdapter.open();
		cursor = dbAdapter.getAllData();
		int numberOfAlltest = cursor.getCount();
		int numberOfChosen = 15;
		int[] problemRand = new int[numberOfAlltest];//所有题目顺序
		int[] testTurn = new int[numberOfChosen];//试题


		testctrl = new TestCtrl(this,this,dbAdapter,cursor,chronometer,numberOfAlltest,numberOfChosen,problemRand,testTurn,testAnswer,mySelect);
		testctrl.testOfChosen(0);
		testctrl.handlerOftestAnswer();*/
        //testctrl.OnPaint(proTextView,radioGroup,radioA,radioB,radioC,radioD);
		//numText.setText("1/15");
		//forword_btn.setText("无");
		forword_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				examPresenter.forwordBtn(forword_btn,next_btn,numText,proTextView,radioGroup,radioA,radioB,radioC,radioD);
			}
		});
		next_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				examPresenter.nextBtn(ExamActivity.this,isHandIn,forword_btn,next_btn,numText,proTextView,radioGroup,radioA,radioB,radioC,radioD);
			}
		});

		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				examPresenter.record(radioA,radioB,radioC,radioD);
			}
		});
		answerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				    Intent intent = new Intent(ExamActivity.this, MyAnswerActivity.class);
					intent.putExtra("answer",mySelect);
					intent.putExtra("test_answer",testAnswer);
				    temp=timeTrans();
					intent.putExtra("timer",temp);
				    startActivityForResult(intent,1);
			}
		});

	}
	@Override
	protected void onStart() {//ExamActivity走singletask模式，在活动站中只保存一个实例
		super.onStart();
		Intent intent_H_E = getIntent();
		flag = intent_H_E.getIntExtra("test_mode",0);
		title = intent_H_E.getStringExtra("lesson_name");
		if (isHandIn)
		{
			answerButton.setVisibility(View.INVISIBLE);
			imageView.setVisibility(View.INVISIBLE);
			chronometer.stop();
			chronometer.setVisibility(View.INVISIBLE);
			index = 0;
			rightAnswer.setVisibility(View.VISIBLE);
			myAnswer.setVisibility(View.VISIBLE);

			for (int i=0;i<15;i++)
			{
				Log.i(TAG, "my answer====="+i+" is  ==========="+mySelect[i]);
				Log.i(TAG, "test answer*******"+i+" is  ************"+testAnswer[i]);
			}
		}

		TextMode_text = testctrl.testMode(flag);
		titleBarView.getTitleText().setText(title);
		TextMode.setText(TextMode_text);
		testctrl.setIndex(index);
		String temp = (index + 1) + "/" + 15;
		numText.setText(temp);
		if (index == 0)
		{
			forword_btn.setText("无");
			next_btn.setText("下一题");
		}else if (index == 14)
		{
			forword_btn.setText("上一题");
			next_btn.setText("提交");
		}else
		{
			forword_btn.setText("上一题");
			next_btn.setText("下一题");
		}

		testctrl.OnPaint(proTextView,radioGroup,radioA,radioB,radioC,radioD);
	}
	@Override
	protected void onRestart() {
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
					isHandIn = data.getBooleanExtra("isHandIn",false);
					mySelect = data.getIntArrayExtra("answer");
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
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Toast.makeText(this, "按下了back键   onBackPressed()", Toast.LENGTH_SHORT).show();
	}
	@Override
	protected void onUserLeaveHint() {
		super.onUserLeaveHint();
		Log.i(TAG, "lrl    =====");
		//MyWindowManager.initWindowManager (this,ExamActivity.this);
		Intent intentToHome = new Intent(ExamActivity.this, FloatWindowService.class);
		temp=timeTrans();
		intentToHome.putExtra("TIME",temp);
		startService(intentToHome);
		//FloatWindowService floatWindowService = new FloatWindowService();
		//floatWindowService.initService(getApplicationContext(),ExamActivity.this);
		Toast.makeText(this, "onUserLeaveHint", Toast.LENGTH_SHORT).show();
	}
	public int timeTrans ( )
	{
		int temp0 = Integer.parseInt(chronometer.getText().toString().split(":")[0]);
		int temp1 =Integer.parseInt(chronometer.getText().toString().split(":")[1]);
		return temp0*60+temp1;
	}
	public void setIndex(int index)
	{
		curIndex = index;
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
}
