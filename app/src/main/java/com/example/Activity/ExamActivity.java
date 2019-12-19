package com.example.Activity;

import com.bumptech.glide.util.Util;
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
import android.app.AlertDialog;
import android.content.DialogInterface;
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

	private int flag;
	private int index;
	public boolean isHandIn;
	public String title;
	public TitleBarView titleBarView;
	public TextView proTextView;//问题
	public TextView numText;
	public TextView TextMode;
	public TextView rightAnswer;
	public TextView myAnswer;
	public RadioButton radioA;
	public RadioButton radioB;
	public RadioButton radioC;
	public RadioButton radioD;//答案按钮组
	public RadioGroup radioGroup;
	public Button forword_btn;//上一题按钮
	public Button next_btn;//下一题按钮
	private ImageView back_img;

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
        initView();
		back_img.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) {
				AlertDialog.Builder normalDialog = new AlertDialog.Builder(ExamActivity.this);
				normalDialog.setTitle("提示");
				normalDialog.setMessage("您确定要退出测试吗?");
				normalDialog.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						Intent intent = new Intent(ExamActivity.this, MainActivity.class);
						startActivity(intent);
					}
			    });
				normalDialog.setNegativeButton("关闭",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						//showToastShort("点击了关闭");
					}
				});        // 显示
			   normalDialog.show();
		}
		});
		forword_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				examPresenter.forwordBtn();
			}
		});
		next_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				examPresenter.nextBtn();
			}
		});

		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				examPresenter.answerRecord();
			}
		});
		answerButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
                examPresenter.answerBtn();
			}
		});

	}

	private void initView()
	{
		titleBarView = (TitleBarView) findViewById(R.id.test_titlebar);
		back_img = (ImageView)titleBarView.getTitleBack();
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
		answerButton.setVisibility(View.VISIBLE);
		imageView = titleBarView.getTimerBack();
		imageView.setVisibility(View.VISIBLE);
		chronometer.setBase(SystemClock.elapsedRealtime());
		chronometer.start();
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
		}

		TextMode_text = examPresenter.testMode(flag);
		titleBarView.getTitleText().setText(title);
		TextMode.setText(TextMode_text);
		examPresenter.setIndex(index);
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

		examPresenter.examTestShow();
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
					//mySelect = data.getIntArrayExtra("answer");
				}
				break;
			default : break;
		}
	}


	@Override
	protected void onDestroy() {
        examPresenter.close();
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
		Log.i(TAG, "startservice    =====");
		Intent intentToHome = new Intent(ExamActivity.this, FloatWindowService.class);
		temp=timeTrans();
		intentToHome.putExtra("TIME",temp);
		startService(intentToHome);
		Toast.makeText(this, "onUserLeaveHint", Toast.LENGTH_SHORT).show();
	}
	public int timeTrans ( )
	{
		int temp0 = Integer.parseInt(chronometer.getText().toString().split(":")[0]);
		int temp1 =Integer.parseInt(chronometer.getText().toString().split(":")[1]);
		return temp0*60+temp1;
	}


}
