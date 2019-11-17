package com.example.UI;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.Activity.ExamActivity;
import com.example.Service.FloatWindowService;
import com.example.Service.MyWindowManager;
import com.example.dpTopx;
import com.example.testsys.R;

import java.util.ArrayList;
import java.util.List;

public class FloatWindowBigView extends RelativeLayout {
    private static String TAG = "FloatWindowViewBig";
/**	 * 记录大悬浮窗的宽度	 */
    public static int viewWidth;
    /**	 * 记录大悬浮窗的高度	 */
    public static int viewHeight;
    private WindowManager windowManager;                //用于更新小悬浮窗的位置
    private WindowManager.LayoutParams mBigParams;         //小悬浮窗的参数
    private ImageButton enter_btn,exit_btn;

    private List<ImageButton> buttons = new ArrayList<>();

    public FloatWindowBigView(final Context context) {
        super(context);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.floatwindow_big, this);
        View view = findViewById(R.id.view_floatwindowbig);
        viewWidth = view.getLayoutParams().width;
        Log.i(TAG, "lrl =====-----------viewWidth"+viewWidth);
        viewHeight = view.getLayoutParams().height;
        enter_btn = (ImageButton) findViewById(R.id.enter_test);
        exit_btn = (ImageButton) findViewById(R.id.exit_test);
        buttons.add(exit_btn);
        buttons.add(enter_btn);
        //if (!isMenuOpen) {
            showOpenAnim(20);
            //imgPublish.setImageResource(R.mipmap.publish_select);
       // }else {
      //      showCloseAnim(20);
            //imgPublish.setImageResource(R.mipmap.fabu);
       // }
        enter_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击关闭悬浮窗的时候，移除所有悬浮窗，并停止Service
                Log.i(TAG, "lrl enter_btn.setOnClickListener");
                Intent intent = new Intent(getContext(), FloatWindowService.class);
                context.stopService(intent);
                Log.i(TAG, "lrl ready go back to enter_btn.setOnClickListener");
                Intent enterTest = new Intent(getContext(), ExamActivity.class);
                context.startActivity(enterTest);
            }
        });
        exit_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {                // 点击返回的时候，移除大悬浮窗，创建小悬浮窗

                System.exit(0);
                /*FloatWindowManager.removeBigWindow(context);
                FloatWindowManager.createSmallWindow(context);*/
            }
        });
    }

    public void setBigParams(WindowManager.LayoutParams params) {
        mBigParams = params;
    }
    public void updateBigViewPosition(int x,int y) {
        //
        if (mBigParams==null)
        {
            Log.i(TAG, "lrl updateViewbigPosition=====-----------fail");
        }
        mBigParams.x = x;
        mBigParams.y = y;
        windowManager.updateViewLayout(this, mBigParams);
    }
    //打开扇形菜单的属性动画， dp为半径长度

    private void showOpenAnim(int dp) {
        enter_btn.setVisibility(View.VISIBLE);
        exit_btn.setVisibility(View.VISIBLE);
        //for循环来开始小图标的出现动画
        //float density = getResources().getDisplayMetrics().density;
        for (int i = 0; i < buttons.size(); i++) {
            AnimatorSet set = new AnimatorSet();
            set.playTogether(
                    ObjectAnimator.ofFloat(buttons.get(i),"translationX",(float)0,dpTopx.dip2px(getContext(),10*(i+1)))
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
                   // isMenuOpen = true;
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
/*
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
                    //isMenuOpen = false;
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
    }*/


}
