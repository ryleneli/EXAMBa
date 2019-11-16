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
import com.example.Service.FloatWindowService;
import com.example.testsys.R;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rylene_li on 2019/1/21.
 */

public class FloatWindowView extends RelativeLayout implements View.OnClickListener{

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
    private Button enter_btn,exit_btn;
    private RelativeLayout menu;
    private ImageView pangxie,purple_1,purple_2;
    private boolean isMenuOpen = false;
    private List<Button> buttons = new ArrayList<>();


    public FloatWindowView(Context context) {
        super(context);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.float_window, this);
        View view = findViewById(R.id.view_floatwindow);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
        Log.d(TAG, " lrlll xInFloatWindow="+viewWidth+"============"+viewHeight);

        testTimer = (TextView) findViewById(R.id.time_floatwindow);
        menu = (RelativeLayout) findViewById(R.id.view_floatwindow);
        enter_btn = (Button) findViewById(R.id.enter_test);
        exit_btn = (Button) findViewById(R.id.exit_test);
        pangxie = (ImageView) findViewById(R.id.pangxie);
        int H = pangxie.getHeight();
        int W = pangxie.getWidth();
        Log.d(TAG, " lrlll xInFloatWindow="+W+"============"+H);
        purple_1 = (ImageView) findViewById(R.id.purple1);
        purple_2 = (ImageView) findViewById(R.id.purple2);
        animOfPurple();
        buttons.add(enter_btn);
        buttons.add(exit_btn);
        menu.setOnClickListener(this);
        purple_1.setVisibility(View.VISIBLE);
        purple_2.setVisibility(View.VISIBLE);

        enter_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getContext(), ExamActivity.class);
                //startActivity(intent);
            }
        });
        exit_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_floatwindow:
                if (!isMenuOpen) {
                    showOpenAnim(20);
                    //imgPublish.setImageResource(R.mipmap.publish_select);
                }else {
                    showCloseAnim(20);
                    //imgPublish.setImageResource(R.mipmap.fabu);
                }
                break;
        }
    }
    /**如果发现用户触发了ACTION_DOWN事件，会记录按下时的坐标等数据。
     * 如果发现用户触发了ACTION_MOVE事件，则根据当前移动的坐标更新悬浮窗在屏幕中的位置。
     **/

    @Override
    public boolean onTouchEvent(MotionEvent event) {
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
                Log.d(TAG, " lrlll xInFloatWindow="+xInFloatWindow);
                Log.d(TAG, " lrlll yInFloatWindow="+yInFloatWindow);
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
        Log.d(TAG, " lrl enter floatwindow set text" );
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
        ObjectAnimator objectAnimator4 = ObjectAnimator.ofFloat(purple_1, "translationY", 0, -dip2px(70));
        ObjectAnimator objectAnimator5 = ObjectAnimator.ofFloat(purple_2, "scaleX", 0f, 1.0f);
        ObjectAnimator objectAnimator6 = ObjectAnimator.ofFloat(purple_2, "scaleY", 0f, 1.0f);
        ObjectAnimator objectAnimator7 = ObjectAnimator.ofFloat(purple_2, "translationY", 0, -dip2px(70));
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
        set.setDuration(3000);
        set.start();

        Log.i(TAG,"puple==========");
    }

    //打开扇形菜单的属性动画， dp为半径长度

    private void showOpenAnim(int dp) {
        enter_btn.setVisibility(View.VISIBLE);
        exit_btn.setVisibility(View.VISIBLE);
        //for循环来开始小图标的出现动画
        //float density = getResources().getDisplayMetrics().density;
        for (int i = 0; i < buttons.size(); i++) {
            AnimatorSet set = new AnimatorSet();
            int dp_tv1_X = 40;
            int px_tv1_X = dip2px(dp_tv1_X);
            int dp_tv2_X = 70;
            int px_tv2_X = dip2px(dp_tv2_X);
            int pxTemp[] = {px_tv1_X,px_tv2_X};
            set.playTogether(
                    ObjectAnimator.ofFloat(buttons.get(i),"translationY",(float)0,pxTemp[i])
                    //, ObjectAnimator.ofFloat(imageViews.get(i),"translationY",(float)0,28)
                    , ObjectAnimator.ofFloat(buttons.get(i), "alpha", 0, 1).setDuration(2000)
            );
            set.setInterpolator(new BounceInterpolator());
            set.setDuration(500).setStartDelay(100);
            set.start();
            set.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }
                @Override
                public void onAnimationEnd(Animator animation) {
                    //菜单状态置打开
                    isMenuOpen = true;
                }
                @Override
                public void onAnimationCancel(Animator animation) {
                }
                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
        }
        //转动加号大图标本身45°
        //ObjectAnimator rotate = ObjectAnimator.ofFloat(menu, "rotation", 0, 90).setDuration(300);
        //rotate.setInterpolator(new BounceInterpolator());
        //rotate.start();
    }
    //关闭扇形菜单的属性动画，参数与打开时相反

    private void showCloseAnim(int dp) {
        //for循环来开始小图标的出现动画
        for (int i = 0; i < buttons.size(); i++) {
            AnimatorSet set = new AnimatorSet();
            int dp_tv1_X = 40;
            int px_tv1_X = dip2px(dp_tv1_X);
            int dp_tv2_X = 70;
            int px_tv2_X = dip2px(dp_tv2_X);
            int pxTemp[] = {px_tv1_X,px_tv2_X};
            set.playTogether(
                    //ObjectAnimator.ofFloat(imageViews.get(i),"translationY",pxTemp[i],(float)0),
                    //, ObjectAnimator.ofFloat(imageViews.get(i),"translationY",(float)0,28)
                    ObjectAnimator.ofFloat(buttons.get(i), "alpha", 1, 0)
            );

//      set.setInterpolator(new AccelerateInterpolator());
            set.setDuration(500);
            set.start();
            set.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }
                @Override
                public void onAnimationEnd(Animator animation) {
                    enter_btn.setVisibility(View.GONE);
                    exit_btn.setVisibility(View.GONE);
                    //菜单状态置关闭
                    isMenuOpen = false;
                }
                @Override
                public void onAnimationCancel(Animator animation) {
                }
                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
        }
        //转动加号大图标本身45°
        //ObjectAnimator rotate = ObjectAnimator.ofFloat(menu, "rotation", 0, 90).setDuration(300);
        //rotate.setInterpolator(new BounceInterpolator());
        //rotate.start();
    }
    private int dip2px(int value) {
        float density = getResources().getDisplayMetrics().density;
        Log.i(TAG,"==========="+density);

        float abs = density * value+0.5f;
        Log.i(TAG,"==========="+abs);
        return (int) (density * value + 0.5f);
    }

}
