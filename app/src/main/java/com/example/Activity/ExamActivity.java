package com.example.Activity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import com.example.Constant;
import com.example.DBControl.DBAdapter;
import com.example.TestControl.TestCtrl;
import com.example.UI.TitleBarView;
import com.example.testsys.R;
import com.example.DBControl.WrongSetShowList;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Chronometer.OnChronometerTickListener;

import static android.content.ContentValues.TAG;

public class ExamActivity extends Activity {
	private static String TAG = "ExamActivity abcde";

	TitleBarView titleBarView;
	TextView proTextView;//问题
    TextView numText;
    TextView TextMode;
	RadioButton radioA;
	RadioButton radioB;
	RadioButton radioC;
	RadioButton radioD;//答案按钮组
	RadioGroup radioGroup;
	Button forword_btn;//上一题按钮
	Button next_btn;//下一题按钮
	Chronometer chronometer;
	Cursor cursor;
	DBAdapter dbAdapter;
	int[] problemRand = new int[160];//所有题目顺序
	int[] testTurn = new int[15];//试题
	int[] testAnswer = new int[15];//试题答案
	int[] mySelect = new int[15];// 我的答案
	String TextMode_text;
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
		dbAdapter = new DBAdapter(this);
		dbAdapter.open();
		cursor = dbAdapter.getAllData();
		int numberOfAlltest = cursor.getCount();
		int numberOfChosen = 15;
		int[] problemRand = new int[numberOfAlltest];//所有题目顺序
		int[] testTurn = new int[numberOfChosen];//试题
		int[] testAnswer = new int[numberOfChosen];//试题答案
		int[] mySelect = new int[numberOfChosen];// 我的答案
		Intent intent = getIntent();
		int flag = intent.getIntExtra("test_mode",0);
		TextMode_text = testMode(flag);
		titleBarView.getTitleText().setText(intent.getStringExtra("lesson_name"));
		TextMode.setText(TextMode_text);
		final TestCtrl testctrl = new TestCtrl(this,dbAdapter,cursor,numberOfAlltest,numberOfChosen,problemRand,testTurn,testAnswer,mySelect,radioGroup,proTextView,numText,radioA,
				radioB,radioC,radioD);
		//testctrl.random();
		testctrl.testOfChosen(0);
        testctrl.OnPaint();
		numText.setText("1/15");
		forword_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				testctrl.forwordBtn();
			}
		});
		next_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				testctrl.nextBtn();
			}
		});

		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				testctrl.myAnswerRecord();
			}
		});

	}

	@Override
	protected void onDestroy() {
		dbAdapter.close();
		cursor.close();
		super.onDestroy();
	}
	public String testMode(int flag) {
		String mode = null;
		switch (flag) {
			case 1:
			{mode = Constant.CHAPTER;}
			break;
			case 2:
			{mode = Constant.RANDOM;;}
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
