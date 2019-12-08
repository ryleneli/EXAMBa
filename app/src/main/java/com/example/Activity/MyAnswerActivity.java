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
import com.example.Presenter.MyAnswerPresenter;
import com.example.UI.TitleBarView;
import com.example.Utils.StatusBarUtil;
import com.example.testsys.R;

import static android.view.View.GONE;

/**
 * Created by 15151 on 2019/10/27.
 */

public class MyAnswerActivity extends Activity {
    private static String TAG = "MyAnswerActivity";
    private TitleBarView titleBarView;
    private TextView resultText,timeText;
    public RecyclerView recyclerView ;
    public MyAnswerRecyclerView recyclerViewAdapter;
    private Button button ;
    private RelativeLayout relativeLayout;
    private View view;
    public Chronometer chronometer;
    boolean isHandIn = false;

    public MyAnswerPresenter myAnswerPresenter;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_record);
        myAnswerPresenter = new MyAnswerPresenter(getBaseContext(),this);
        StatusBarUtil.setRootViewFitsSystemWindows(this,false);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        StatusBarUtil.setStatusBarDarkTheme(MyAnswerActivity.this,false);
        Log.i(TAG,"LRL mySelect now is **********onCreate");
        Intent intent = getIntent() ;
        myAnswerPresenter.getData(intent);
        titleBarView = (TitleBarView) findViewById(R.id.myAnswer_titlebar);
        chronometer = titleBarView.getChronometer();
        myAnswerPresenter.setTime();
        //chronometer.start();
        button = (Button) findViewById(R.id.upload_button);
        relativeLayout = (RelativeLayout) findViewById(R.id.question_type_1);
        resultText = (TextView) findViewById(R.id.result);
        timeText = (TextView) findViewById(R.id.time);
        view = (View) findViewById(R.id.view_last);
        titleBarView.setTitleText("答题卡");
        recyclerView = (RecyclerView)findViewById(R.id.my_answer);
        GridLayoutManager layoutManager = new GridLayoutManager(this,5);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerViewAdapter = new MyAnswerRecyclerView(this,MyAnswerActivity.this,myAnswer,testAnswer,isHandIn);
        //recyclerView.setAdapter(recyclerViewAdapter);
        myAnswerPresenter.handIn(isHandIn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!isHandIn)
                {
                    isHandIn = true ;
                    button.setText("查看试题");
                    relativeLayout.setVisibility(GONE);
                    resultText.setVisibility(View.VISIBLE);
                    timeText.setVisibility(View.VISIBLE);
                    view.setVisibility(View.VISIBLE);
                    titleBarView.setTitleText("结果报告");
                    titleBarView.setTimer(false);
                    chronometer.stop();
                    chronometer.setVisibility(View.INVISIBLE);

                    myAnswerPresenter.handIn(isHandIn);
                    resultText.setText(myAnswerPresenter.getResult());
                    timeText.setText(myAnswerPresenter.showTime());
                }
                else
                {
                    Log.i(TAG, "my answer====================ready to enter exam");
                    myAnswerPresenter.putData(isHandIn);
                }

            }
        });
    }

    @Override
    protected void onStart() {//ExamActivity走singletask模式，在活动站中只保存一个实例
        super.onStart();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
