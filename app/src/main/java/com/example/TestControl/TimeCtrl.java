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
import com.example.UI.TestView;
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

public class TimeCtrl { //4种不同的test类型对应不同的动作
    // 剩余时间string

    private void showScore(Context mcontext,int resultInt) {//仅在考试场景使用
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
    private int score(int m_numberOfChosen,int my_answer[],int test_answer[])//计分
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
    public String nowtime(int minutes, int seconds) {//考试结束前十分钟跳出弹窗提示考试即将结束
        // TODO Auto-generated method stub
        if (seconds < 10) {
            return (minutes + ":0" + seconds);
        } else {
            return (minutes + ":" + seconds);
        }
    }
    /*仅在考试场景使用
		Chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
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
