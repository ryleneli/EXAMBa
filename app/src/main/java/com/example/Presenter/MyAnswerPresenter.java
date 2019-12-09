package com.example.Presenter;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.example.Activity.ExamActivity;
import com.example.Activity.MyAnswerActivity;
import com.example.Adapter.MyAnswerRecyclerView;
import com.example.Model.MyAnswerModel;

import static android.app.Activity.RESULT_OK;

/**
 * com.example.Presenter
 * Created by Ryleneli 15151
 * 2019/12/8
 */
public class MyAnswerPresenter {
    private Context context;
    private MyAnswerActivity myAnswerActivity;
    private MyAnswerModel myAnswerModel;

    public MyAnswerPresenter(Context context, MyAnswerActivity myAnswerActivity){
        this.myAnswerActivity=myAnswerActivity;
        this.context=context;
        this.myAnswerModel = new MyAnswerModel();
    }
    public void getData (Intent intent)
    {
        myAnswerModel.getIntentData(intent);
    }
    public void putData (boolean isHandIn)
    {
        Intent intent = new Intent(myAnswerActivity, ExamActivity.class);
        myAnswerModel.putIntentData(intent,isHandIn);
        myAnswerActivity.setResult(RESULT_OK, intent);
        myAnswerActivity.finish();
    }
    public void handIn(boolean isHandIn)
    {
        myAnswerActivity.recyclerViewAdapter = new MyAnswerRecyclerView(myAnswerActivity.getBaseContext(),myAnswerActivity,
                    myAnswerModel.myAnswer,myAnswerModel.testAnswer,isHandIn);
        myAnswerActivity.recyclerView.setAdapter(myAnswerActivity.recyclerViewAdapter);
    }
    public void setTime ()
    {
        myAnswerActivity.chronometer.setBase(SystemClock.elapsedRealtime()-myAnswerModel.temp*1000);
        myAnswerActivity.chronometer.start();
    }
    public String getResult ()
    {
        String temp_text = "正确："+myAnswerModel.answerCount() + "/15";
        return temp_text;
    }
    public String showTime ()
    {
        return myAnswerModel.tempToTimer();
    }
}
