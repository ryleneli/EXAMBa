package com.example.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.Adapter.MyAnswerRecyclerView;
import com.example.Adapter.MyRecyclerViewAdapter;
import com.example.UI.TitleBarView;
import com.example.testsys.R;

/**
 * Created by 15151 on 2019/10/27.
 */

public class MyAnswerActivity extends Activity {
    private static String TAG = "LRL MyAnswerActivity";
    private TitleBarView titleBarView;
    private TextView numText;
    private RecyclerView recyclerView ;
    private MyAnswerRecyclerView recyclerViewAdapter;
    private int myAnswer[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.answer_record);
        Intent intent = getIntent() ;
        myAnswer = intent.getIntArrayExtra("answer");
        titleBarView = (TitleBarView) findViewById(R.id.myAnswer_titlebar);
        titleBarView.setTitleText("答题卡");
        recyclerView = (RecyclerView)findViewById(R.id.my_answer);
        GridLayoutManager layoutManager = new GridLayoutManager(this,5);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new MyAnswerRecyclerView(this,MyAnswerActivity.this,myAnswer);
        recyclerView.setAdapter(recyclerViewAdapter);
        for (int i=0;i<15;i++)
        {
            Log.i(TAG, "my answer====="+i+" is  ************"+myAnswer[i]);
        }
    }
}
