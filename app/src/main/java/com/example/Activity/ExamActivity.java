package com.example.Activity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import com.example.DBControl.DBAdapter;
import com.example.TestControl.TestCtrl;
import com.example.testsys.R;
import com.example.DBControl.WrongSetShowList;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
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
	private static String TAG = "ExamActivity";

	TextView proTextView;//问题
    TextView numText;
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_fragment);
		proTextView = (TextView) findViewById(R.id.pro_text);
		radioA = (RadioButton) findViewById(R.id.radioA);
		radioB = (RadioButton) findViewById(R.id.radioB);
		radioC = (RadioButton) findViewById(R.id.radioC);
		radioD = (RadioButton) findViewById(R.id.radioD);
		forword_btn = (Button) findViewById(R.id.last);
		next_btn = (Button) findViewById(R.id.next);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		dbAdapter = new DBAdapter(this);

		final TestCtrl testctrl = new TestCtrl(this,dbAdapter,160,15,radioGroup,proTextView,numText,radioA,
				radioB,radioC,radioD);
		//testctrl.random();
		testctrl.testOfChosen(0);
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
		super.onDestroy();
	}

}
