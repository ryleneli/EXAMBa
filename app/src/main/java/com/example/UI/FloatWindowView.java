package com.example.UI;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.Log;

import com.example.Activity.ExamActivity;
import com.example.Activity.MyAnswerActivity;
import com.example.Object.MyRelativeLayout;
import com.example.Service.FloatWindowService;
import com.example.Service.MyWindowManager;
import com.example.testsys.R;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rylene_li on 2019/1/21.
 */

public class FloatWindowView extends MyRelativeLayout implements View.OnClickListener{

    private static String TAG = "FloatWindowView";

    /**将布局floatwindow.xml中设置的相关参数给悬浮窗以设置其宽高*/
    public static int viewWidth;                        //小悬浮窗的宽度
    public static int viewHeight;                       //记录小悬浮窗的高度

    private static int statusBarHeight;                 //记录系统状态栏的高度
    private WindowManager windowManager;                //用于更新小悬浮窗的位置
    private WindowManager.LayoutParams mParams;         //小悬浮窗的参数

    private float xInScreen;                            //记录当前手指在屏幕上的横坐标值
    private float yInScreen;                            //记录当前手指在屏幕上的纵坐标值
    private float xInFloatWindow;                       //记录手指按下时在悬浮窗上的横坐标的值
    private float yInFloatWindow;                       //记录手指按下时在悬浮窗上的纵坐标的值
    private TextView testTimer;
    private boolean isMenuOpen = false;
   //private RelativeLayout menu;
    private ImageView pangxie,purple_1,purple_2;



    public FloatWindowView(Context context) {
        super(context);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.float_window, this);
        View view = findViewById(R.id.view_floatwindow);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
        Log.d(TAG, " lrlll xInFloatWindow="+viewWidth+"============"+viewHeight);

        testTimer = (TextView) findViewById(R.id.time_floatwindow);
        //menu = (RelativeLayout) findViewById(R.id.view_floatwindow);

        pangxie = (ImageView) findViewById(R.id.pangxie_floatwindow);

        purple_1 = (ImageView) findViewById(R.id.purple1);
        purple_2 = (ImageView) findViewById(R.id.purple2);
        animOfPurple();
        pangxie.setOnClickListener(this);
        purple_1.setVisibility(View.VISIBLE);
        purple_2.setVisibility(View.VISIBLE);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pangxie_floatwindow:
                if (!isMenuOpen) {
                    MyWindowManager.createBigWindow(getContext());
                    Log.i(TAG, "lrl createBigWindow=====");
                    isMenuOpen = true;
                }else {
                    MyWindowManager.removeBigWindow(getContext());
                    isMenuOpen = false;
                    Log.i(TAG, "lrl removeBigWindow=====");
                }
                break;
        }
    }
    /**如果发现用户触发了ACTION_DOWN事件，会记录按下时的坐标等数据。
     * 如果发现用户触发了ACTION_MOVE事件，则根据当前移动的坐标更新悬浮窗在屏幕中的位置。
     **/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, " lrl really ready to ACTION_MOVE");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
                xInFloatWindow = event.getX();
                Log.d(TAG, " lrlll xInFloatWindow="+xInFloatWindow);
                yInFloatWindow = event.getY();
                Log.d(TAG, " lrlll yInFloatWindow="+yInFloatWindow);
                xInScreen = event.getRawX();
                yInScreen = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen = event.getRawX();
                yInScreen = event.getRawY();
                // 手指移动的时候更新小悬浮窗的位置
                updateViewPosition();
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 将MyWindowManager中悬浮窗window的参数传入，用于更新小悬浮窗的位置。
     */
    public void setParams(WindowManager.LayoutParams params) {
        mParams = params;
    }

    public void setText(int time) {
        String hh = new DecimalFormat("00").format(time / 3600);
        String mm = new DecimalFormat("00").format(time % 3600 / 60);
        String ss = new DecimalFormat("00").format(time % 60);
       // Log.d(TAG, " lrl enter floatwindow set text" );
        testTimer.setText(hh + ":" + mm + ":" + ss);
    }

    /**
     * 更新悬浮窗在屏幕中的位置。
     */
    private void updateViewPosition() {
        mParams.x = (int) (xInScreen - xInFloatWindow);
        //Log.d(TAG, " lrll mParams.x="+mParams.x);
        mParams.y = (int) ((yInScreen - yInFloatWindow));
        //Log.d(TAG, " lrll mParams.y="+mParams.y);
        windowManager.updateViewLayout(this, mParams);
    }
    private void animOfPurple()
    {
        Log.i(TAG,"puple");

        AnimatorSet set = new AnimatorSet();
        AnimatorSet set_puple1 = new AnimatorSet();
        AnimatorSet set_puple2 = new AnimatorSet();
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(purple_1, "alpha",0f,1.0f,0.8f,0f);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(purple_1, "scaleX", 0f, 1.0f);
        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(purple_1, "scaleY", 0f, 1.0f);
        ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(purple_1, "translationY", 0, -dip2px(40));
        ObjectAnimator objectAnimator5 = ObjectAnimator.ofFloat(purple_2, "scaleX", 0f, 1.0f);
        ObjectAnimator objectAnimator6 = ObjectAnimator.ofFloat(purple_2, "scaleY", 0f, 1.0f);
        ObjectAnimator objectAnimator7 = ObjectAnimator.ofFloat(purple_2, "translationY", 0, -dip2px(40));
        ObjectAnimator objectAnimator8 = ObjectAnimator.ofFloat(purple_2, "alpha",0f,1.0f,0.8f,0f);
        objectAnimator1.setRepeatCount(-1);
        objectAnimator2.setRepeatCount(-1);
        objectAnimator3.setRepeatCount(-1);
        objectAnimator4.setRepeatCount(-1);

        objectAnimator5.setRepeatCount(-1);
        objectAnimator6.setRepeatCount(-1);
        objectAnimator7.setRepeatCount(-1);
        objectAnimator8.setRepeatCount(-1);
        set_puple1.playTogether(objectAnimator1,objectAnimator2,objectAnimator3,objectAnimator4);
        set_puple1.setStartDelay(500);
        set_puple2.playTogether(objectAnimator5,objectAnimator6,objectAnimator7,objectAnimator8);
        set.play(set_puple1).with(set_puple2);
        set.setDuration(4000);
        set.start();

        Log.i(TAG,"puple==========");
    }
    private int dip2px(int value) {
        float density = getResources().getDisplayMetrics().density;
        Log.i(TAG,"==========="+density);

        float abs = density * value+0.5f;
        Log.i(TAG,"==========="+abs);
        return (int) (density * value + 0.5f);
    }

}
