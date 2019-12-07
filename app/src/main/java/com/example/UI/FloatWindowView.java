package com.example.UI;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.util.Log;

import com.example.Manager.MyWindowManager;
import com.example.testsys.R;

import java.text.DecimalFormat;

/**
 * Created by rylene_li on 2019/1/21.
 */

public class FloatWindowView extends RelativeLayout implements View.OnTouchListener{

    private static String TAG = "FloatWindowView";

    /**将布局floatwindow.xml中设置的相关参数给悬浮窗以设置其宽高*/
    public static int viewWidth;                        //小悬浮窗的宽度
    public static int viewHeight;                       //记录小悬浮窗的高度

    public View view;
    private WindowManager windowManager;                //用于更新小悬浮窗的位置
    private WindowManager.LayoutParams mParams;         //小悬浮窗的参数
/*
    private float xInScreen;                            //记录当前手指在屏幕上的横坐标值
    private float yInScreen;                            //记录当前手指在屏幕上的纵坐标值
    private float xInFloatWindow;                       //记录手指按下时在悬浮窗上的横坐标的值
    private float yInFloatWindow;                       //记录手指按下时在悬浮窗上的纵坐标的值
*/
    /**
     * 记录当前手指位置在屏幕上的横坐标值
     */
    private float xInScreen;

    /**
     * 记录当前手指位置在屏幕上的纵坐标值
     */
    private float yInScreen;

    /**
     * 记录手指按下时在屏幕上的横坐标的值
     */
    private float xDownInScreen;

    /**
     * 记录手指按下时在屏幕上的纵坐标的值
     */
    private float yDownInScreen;

    /**
     * 记录手指按下时在小悬浮窗的View上的横坐标的值
     */
    private float xInView;

    /**
     * 记录手指按下时在小悬浮窗的View上的纵坐标的值
     */
    private float yInView;

    private TextView testTimer;
    private boolean isMenuOpen = false;
    boolean inRight = true;
   //private RelativeLayout menu;
    private ImageView pangxie,purple_1,purple_2;
    private  FloatWindowBigView floatWindowBig;


    public FloatWindowView(Context context) {
        super(context);
        //floatWindowBig = new FloatWindowBigView(getContext());
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.float_window, this);
        view = findViewById(R.id.view_floatwindow);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;


        testTimer = (TextView) findViewById(R.id.time_floatwindow);
        //menu = (RelativeLayout) findViewById(R.id.view_floatwindow);

        pangxie = (ImageView) findViewById(R.id.pangxie_floatwindow);

        purple_1 = (ImageView) findViewById(R.id.purple1);
        purple_2 = (ImageView) findViewById(R.id.purple2);
        animOfPurple();
        pangxie.setOnTouchListener(this);

    }

    /**如果发现用户触发了ACTION_DOWN事件，会记录按下时的坐标等数据。
     * 如果发现用户触发了ACTION_MOVE事件，则根据当前移动的坐标更新悬浮窗在屏幕中的位置。
     **/
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //Log.i(TAG, "lrl enter onTouchEvent=====");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
                xInView = event.getX();
                yInView = event.getY();
                xDownInScreen = event.getRawX();

                yDownInScreen = event.getRawY() - MyWindowManager.getStatusBarHeight(getContext());

                xInScreen = event.getRawX();
                Log.i(TAG, "lrl xDownInScreen is     ====="+xInScreen);
                yInScreen = event.getRawY() - MyWindowManager.getStatusBarHeight(getContext());
                Log.i(TAG, "lrl yDownInScreen is     *****"+yInScreen);
                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() -MyWindowManager.getStatusBarHeight(getContext());

                // 手指移动的时候更新小悬浮窗的位置
                updateViewPosition();
                if (isMenuOpen){
                    if (windowManager.getDefaultDisplay().getWidth()-view.getLayoutParams().width-mParams.x>floatWindowBig.getWidth())
                        inRight = true;
                    else inRight = false;
                }
                if (isMenuOpen ) {
                    if (inRight)
                        floatWindowBig.updateBigViewPosition(mParams.x+MyWindowManager.smallWindow_w,mParams.y+MyWindowManager.smallWindow_h-MyWindowManager.floatWindowParamsBig.height);
                    else
                        floatWindowBig.updateBigViewPosition(mParams.x-floatWindowBig.getWidth(),mParams.y+MyWindowManager.smallWindow_h-MyWindowManager.floatWindowParamsBig.height);
                }
                break;
            case MotionEvent.ACTION_UP:
                // 如果手指离开屏幕时，xDownInScreen和xInScreen相等，且yDownInScreen和yInScreen相等，则视为触发了单击事件。
                if (xDownInScreen == xInScreen && yDownInScreen == yInScreen) {
                    if (!isMenuOpen) {
                        //Log.i(TAG, "lrl mParams.x is     *********====="+mParams.x);
                        floatWindowBig = MyWindowManager.createWindow(getContext(),mParams.x,mParams.y,xInScreen);
                        isMenuOpen = true;
                    }else {
                        MyWindowManager.removeBigWindow(getContext());
                        isMenuOpen = false;
                    }
                }
                break;
            default:
                break;
        }
        return true;
    }
/*
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "lrl enter onTouchEvent=====");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 手指按下时记录必要数据,纵坐标的值都需要减去状态栏高度
                xInView = event.getX();
                yInView = event.getY();
                xDownInScreen = event.getRawX();
                yDownInScreen = event.getRawY() - getStatusBarHeight();
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();
                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();
                // 手指移动的时候更新小悬浮窗的位置
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                // 如果手指离开屏幕时，xDownInScreen和xInScreen相等，且yDownInScreen和yInScreen相等，则视为触发了单击事件。
                if (xDownInScreen == xInScreen && yDownInScreen == yInScreen) {
                    Log.i(TAG, "lrl ready to click=====");
                    if (!isMenuOpen) {
                        MyWindowManager.createBigWindow(getContext());
                        Log.i(TAG, "lrl createBigWindow=====");
                        isMenuOpen = true;
                    }else {
                        MyWindowManager.removeBigWindow(getContext());
                        isMenuOpen = false;
                        Log.i(TAG, "lrl removeBigWindow=====");
                    }
                }


                break;
            default:
                break;
        }
        return true;
    }*/
    public ImageView getPangxie() {
        return pangxie;
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
     * 更新悬浮窗在屏幕中的位置需要限制边界。
     */
    private void updateViewPosition() {
        mParams.x = (int) (xInScreen - xInView);
        mParams.y = (int) (yInScreen - yInView-dip2px(42));
        if (mParams.x <= 0)
            mParams.x =0;
        if (mParams.x >= windowManager.getDefaultDisplay().getWidth()-viewWidth)
            mParams.x = windowManager.getDefaultDisplay().getWidth()-viewWidth;
        if (mParams.y <= 0)
            mParams.y =0;
        if (mParams.y >= windowManager.getDefaultDisplay().getHeight()-viewHeight)
            mParams.y = windowManager.getDefaultDisplay().getHeight()-viewHeight;//限制边界
        windowManager.updateViewLayout(this, mParams);
    }

    private void animOfPurple()
    {
        purple_1.setVisibility(View.VISIBLE);
        purple_2.setVisibility(View.VISIBLE);
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
    }
    private int dip2px(int value) {
        float density = getResources().getDisplayMetrics().density;
        Log.i(TAG,"==========="+density);

        float abs = density * value+0.5f;
        Log.i(TAG,"==========="+abs);
        return (int) (density * value + 0.5f);
    }

}
